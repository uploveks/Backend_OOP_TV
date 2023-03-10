
<span style="color:red;">

#  Project OOP TV - Part II
</span>

<div align="center"><img src="https://i.imgur.com/S1ROjQr.gif" width="300px"></div>

---------------------------

--------------------------
<span style="color:red;">

## Ojectives
</span>

- developing basic organization and object-oriented design skills
- designing a maintainable, extensible and reusable design, thus respecting the main objectives of OOP languages
- understanding the purpose and context of use of the design patterns in order to choose and implement the right pattern to solve a real problem
- adding new functionalities, based on an already existing codebase
- following a style of programming and documenting the code, in unity with the coding practices used in the first part of the project

-----------------------

-------------------------
<span style="color:red;">

## Changes and new functionalities
</span>

I made some changes in the code for part I of the project. Now the user can rate and watch a movie how many times he wants, and also he can't purchase a movie more than 1 time.
In class OnPageActions I added a functionality for subscribe and the method that implements it in MovieActions. 
I made a new functionality for back action using command pattern. 
Recommendation for premium users, Database add and delete using observer pattern, and also I changed code from OutputCommand class using builder pattern.
I created a new class in package inputData named Notifications that has 2 fields movieName and message that will appear in the output.
I changed the code in ChangePageActions and separated it in more classes using State Pattern and then Factory Pattern.

---------------------

------------------------
<span style="color:red;">

## Output
</span>

To have the right output for part II I added new variables as a queue of notifications and a list of subscribed genres.
Also in class Action I added a string as deletedMovie for the database delete, a movie addedMovie for database add and subscribedGenre for subscribe.


----------------------

--------------------------
<span style="color:red;">

## Subscribe
</span>

I created a new method in class MovieActions called subscribe for giving the user the opportunity to subscribe to a genre of movies. The method first checks if the user is in 
page see details, if the current movie genre contains the genre the user wants to subscribe to.
If either of these conditions are not met, it sets an error in the "ErrorOutput" object and exits the method. If both conditions are met, the user's subscribed genres are updated to include the new genre.
I call the method in OnPageActions when the feature is "subscribe".


----------------------

-------------------------------

<span style="color:red;">

## Database
</span>

For both database add and delete I used observer pattern. I created a class Observer that has a method update that I use in the methods databaseAdd and databaseDelete.
I decided to use observer pattern, because I needed to have a list of observers and each observer has a reference to the subject. 
So when the subject changes(a movie is added), it notifies all of its observers (users), which can then update themselves accordingly.
The observer pattern promotes loose coupling between the subject and its observers, as the subject doesn't need to know the specifics of the observers, and the observers don't need to know the specifics of the subject.
So after adding or deleting a movie from the database, I used the method update from class Observer to notify the users.


Database add:

I created a method in class DatabaseActions named databaseAdd. The method starts by creating a new FilterExecutable object and filters the movies from the database and checks if the movie exists in the database. If it exists, it can't be added to the database. 
If the movie wasn't found, the method adds the movie to the database. Then, for each user from the database it checks if the user's country is not banned for the movie and if the user is subscribed to any of the genres of the movie. If both conditions are met, it adds the movie to the user's notifications by using the observer pattern, and specifically by adding the user as an observer and then use the method update, which will notify the user.

Database delete:

I created a method in class DatabaseActions named databaseDelete. 
The method deletes a movie from the database. It creates a new  FilterExecutable object with a FilterByName filter to check if the movie exists in the database. If it doesn't, an error will be returned.
If the movie exists, it selects the first movie from the filtered list and removes it from the database movie list.
It iterates through a list of users and for each user, checks if the user has purchased the movie to be deleted. If the user has purchased the movie, it does the following:
- If the user's account type is "premium", it increments the user's number of free premium movies by 1.
- Otherwise, it increments the user's token count by 2.
- It creates a new Notifications object with the movie's name and the action "DELETE", and adds the current user as an observer to this object.
- It then notifies the observer with the userNotification.
- It removes the movie from the user's list of rated, purchased, watched and liked movies if it exists there.

In class ProcessActions I checked if the action type is database and call the methods.




-------------------------------

------------------------------
<span style="color:red;">

## Back Action
</span>

For implementing the back "button" I used command pattern. 
I created a stack of pages named pagesStack that stores all the pages that the user went to successfully when the action is change page.
So every time the action is change page, I add the current page to the stack, if the user changed the page successfully.
I created a new package named back_command and there I have an interface Command and 3 classes BackActions, BackCommand and Command Invoker.
Command interface: 
It has a method execute that is implemented in BackActions.
CommandInvoker: 
It sets the command and executes them.
BackActions: It creates a new CommandInvoker object and a new BackCommand object. It assigns the BackCommand object to the CommandInvoker object by calling the setCommand method on it. It then calls the executeCommand method on the CommandInvoker object. This code is using the Command pattern which is a behavioral design pattern that turns a request into a stand-alone object that contains all information about the request. The BackActions class is a client of the Command pattern and it uses the CommandInvoker to execute the BackCommand to go back to the previous page and update the currentPage accordingly.
BackCommand: The class has a constructor that takes in all of these instance variables as arguments and assigns them to the corresponding class variables. The execute method is the main method of this class. It does the following:
- It checks if the user is not logged in and if so, it sets an error output and returns.
- It checks if the pagesStack is empty, and if so, it sets an error output and returns.
- It checks if the current page is "neautentificat" and if so it returns.
- It pops the top page from the pagesStack.
- It checks if the popped page is "login" or "register" and if so, it sets the current page to that value and sets an error output and returns.
- It checks if the popped page is "neautentificat" and if so, it sets the current page to that value and returns.
  It creates a new ChangePageActions object and call the changePage method on it, because back action is actually the reverse of change page, and it does approximately the same thing.



-----------------

-------------------
<span style="color:red;">

## Recommendations for premium users
</span>

i created a class Recommendation which has a single method called recommend movie. The method does the following:
- It checks if the user is not a premium user and if not, it returns.
- It checks if the user has no liked movies and if so, it sends a notification indicating that there is no recommendation and returns, because it cannot recommend something based on nothing.
- It calculates the total likes of each genre based on the movies the user liked.
- It sorts the genres by the total likes in descending order.
- It filters the list of all movies by the movies that the user has not watched.
- It filters the list of not watched movies by the user's country, because we can't recommend a movie that the user is banned to see.
- It recommends a movie based on the sorted genres and filtered list of not watched movies. In the list of genres it takes the first genre and looks for the first most liked movie that has this genre, if no movie has the genre, it goes to the next genre and so on.
- It sends a notification with the recommended movie to the user.

This method essentially is recommending a movie to a user based on the movies they liked and haven't watched yet, filtered by their country, and sorting the genres based on the total likes the user has given to that genre.

---------------------

-------------------------
<span style="color:red;">

## OuputCommands - Builder Pattern
</span>

I changed the OutputCommands code by using builder pattern, because I needed more than one constructor and it was easier to use Output Commands in other classes. 
I created a class Builder that has the constructor for each field. 

---------------------

-------------------------
<span style="color:red;">

## Change Page - State and Factory Patterns
</span>

I changed the code in Change Page Actions using State and Factory patterns.
Because initially I had switch cases and the code wasn't easy to read. So I decided to separate each switch case in different states (specifically in different classes).
I created an interface PageState which contains a method that change the page and it's implemented differently in each of the states. The State pattern allows the "ChangePageActions" class to change its behavior based on the state it is in, without having to know the implementation details of the concrete states.
Then I used Factory Pattern in Change Page Actions class to create states.
The method creates a new instance of PageState by calling the PageStateFactory's createPageState method, passing in the pageName and movieName as arguments. Then it sets the currentPageState to the newly created state and calls the changePage method on the currentPageState.
It uses the PageStateFactory to get the appropriate state according to the pageName and movieName, this way the class doesn't need to know the concrete states.

---------------------

-------------------------
<span style="color:red;">

## Patterns 
</span>

In the first part of the project I used two design patterns: Singleton pattern and Strategy pattern. 
Singleton pattern for: Input, actions.UserActions, actions.MovieActions, actions.BuyActions, outputdata.ErrorOutput, actions.FilterActions.
Strategy pattern for filter and sort. 

Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance. I used it because those classes had just a single intance and I needed stricter control over global variables.

Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable. I used this pattern because I needed to use different variants of an algorithm within an object and be able to switch from one algorithm to another during runtime (sort and filter). The classes I created in package filter have a lot of similarities, and they just differ in the way they execute some behavior, that's why for this case strategy pattern was very useful.



In the second part of the project I used four design patterns: Command pattern, Builder pattern, State Pattern, Factory Pattern Observer Pattern.
Command Pattern for Back Action, Builder Pattern for OutputCommands, Observer pattern for Database, and State and Factory Patterns for Change Page Actions.

Command pattern is a data driven design pattern and falls under behavioral pattern category. A request is wrapped under an object as command and passed to invoker object. Invoker object looks for the appropriate object which can handle this command and passes the command to the corresponding object which executes the command.

Builder pattern builds a complex object using simple objects and using a step by step approach. This type of design pattern comes under creational pattern as this pattern provides one of the best ways to create an object. A Builder class builds the final object step by step. This builder is independent of other objects.

In State pattern a class behavior changes based on its state. This type of design pattern comes under behavior pattern. In State pattern, we create objects which represent various states and a context object whose behavior varies as its state object changes.

Factory pattern is one of the most used design patterns in Java. This type of design pattern comes under creational pattern as this pattern provides one of the best ways to create an object. In Factory pattern, we create object without exposing the creation logic to the client and refer to newly created object using a common interface.

Observer is a behavioral design pattern. It specifies communication between objects: observable and observers. An observable is an object which notifies observers about the changes in its state. 

---------------------

-------------------------

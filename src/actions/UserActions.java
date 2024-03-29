package actions;

import inputdata.Action;
import inputdata.Credentials;
import inputdata.Input;
import inputdata.User;
import outputdata.ErrorOutput;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;

import java.util.ArrayList;

public final class UserActions {
    private static UserActions instance;
    private UserActions() {
    }

    /**
     * @return
     * Singleton pattern
     */
    public static UserActions getInstance() {
        if (instance == null) {
            instance = new UserActions();
        }

        return instance;
    }


    /**
     * @param currentPage
     * @param output
     * @param pageName
     * @param errorOutput
     * I check if there is no user authenticated and the current
     * page is the homepage, then I change the current page name
     * and set movie list to a new list.
     */
    public void loginRegisterChangePage(final CurrentPage currentPage, final Output output,
                                        final String pageName,
                                        final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("neautentificat")
                || currentPage.getCurrentUser() != null) {
            errorOutput.setError(output);
            return;
        }
        currentPage.setPageName(pageName);
        currentPage.setCurrentMoviesList(new ArrayList<>());
        ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
    }


    /**
     * @param currentPage
     * @param input
     * @param output
     * @param action
     * @param outputCommands
     * @param errorOutput
     * I check if the current page is login and the user is not
     * authenticated, then I check if the login and password given
     * by him is correct, I set the current user.
     */
    public void loginOnPage(final CurrentPage currentPage, final Input input, final Output output,
                            final Action action, final OutputCommands outputCommands,
                            final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("login")
                && currentPage.getCurrentUser() != null) {
            errorOutput.errorAuthenticate(currentPage, output);
            return;
        }
        if (checkUser(input, action.getCredentials()) == null) {
            errorOutput.errorAuthenticate(currentPage, output);
            return;
        }
        currentPage.setCurrentUser(checkUser(input, action.getCredentials()));
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                currentPage.getCurrentUser());
    }


    /**
     * @param currentPage
     * @param input
     * @param output
     * @param action
     * @param outputCommands
     * @param errorOutput
     * I check if the current page is register and there is no user
     * authenticated and also if there is no such user in the database.
     * I create a new user with given credentials and add him to the input database.
     */
    public void registerOnPage(final CurrentPage currentPage, final Input input,
                               final Output output, final Action action,
                               final OutputCommands outputCommands, final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("register")
                || currentPage.getCurrentUser() != null) {
            errorOutput.errorAuthenticate(currentPage, output);
            return;
        }
        if (checkUser(input, action.getCredentials()) != null) {
            errorOutput.errorAuthenticate(currentPage, output);
            return;
        }
        User newUser = new User(action.getCredentials());
        currentPage.setCurrentUser(newUser);
        input.getUsers().add(newUser);
        errorOutput.outputSuccess(output, new ArrayList<>(), newUser);
    }


    /**
     * @param input
     * @param credentials
     * @return
     * Method that checks if the user is in the input database and returns that
     * user if it finds it and null if not.
     */
    private User checkUser(final Input input, final Credentials credentials) {
        for (User user: input.getUsers()) {
            if (user.getCredentials().equals(credentials)) {
                return user;
            }
        }
        return null;
    }
}

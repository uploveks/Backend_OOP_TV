package inputdata;

import java.util.ArrayList;

public class Input {
    private static Input instance = new Input();
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;

    private Input(){
    }

    public static Input getInstance(){
        return instance;
    }

    public Input(final ArrayList<User> users, final ArrayList<Movie> movies,
                 final ArrayList<Action> actions) {
        this.users = users;
        this.movies = movies;
        this.actions = actions;
    }

    /**
     * @return
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * @param users
     */
    public void setUsers(final ArrayList<User> users) {

        this.users = users;
    }

    /**
     * @return
     */
    public ArrayList<Movie> getMovies() {

        return movies;
    }

    /**
     * @param movies
     */
    public void setMovies(final ArrayList<Movie> movies) {

        this.movies = movies;
    }

    /**
     * @return
     */
    public ArrayList<Action> getActions() {

        return actions;
    }

    /**
     * @param actions
     */
    public void setActions(final ArrayList<Action> actions) {

        this.actions = actions;
    }
}

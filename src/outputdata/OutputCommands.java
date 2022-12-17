package outputdata;

import inputdata.Movie;
import inputdata.User;

import java.util.List;

public class OutputCommands {
    private String error;
    private List<Movie> currentMoviesList;
    private User currentUser;

    public OutputCommands() {
    }


    /**
     * @return
     */
    public String getError() {

        return error;
    }

    /**
     * @param error
     */
    public void setError(final String error) {

        this.error = error;
    }

    /**
     * @return
     */
    public List<Movie> getCurrentMoviesList() {

        return currentMoviesList;
    }

    /**
     * @param currentMoviesList
     */
    public void setCurrentMoviesList(final List<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    /**
     * @return
     */
    public User getCurrentUser() {

        return currentUser;
    }

    /**
     * @param currentUser
     */
    public void setCurrentUser(final User currentUser) {

        this.currentUser = currentUser;
    }
}

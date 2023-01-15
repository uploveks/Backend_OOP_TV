package outputdata;

import inputdata.Movie;
import inputdata.User;
import page.CurrentPage;

import java.util.ArrayList;
import java.util.List;

public final class Error {

    private static Error instance;

    private Error() {
    }

    /**
     * @return
     */
    public static Error getInstance() {
        if (instance == null) {
            instance = new Error();
        }

        return instance;
    }


    /**
     * @param output
     *  Sets the error as outputdata.Error, movie list as a new list and user as null
     *                       and shows it in output.
     */
    public void setError(final Output output) {
        OutputCommands outputCommands = new OutputCommands.Builder()
                .error("Error")
                .currentMoviesList(new ArrayList<>())
                .build();
        output.addOutputCommands(outputCommands);
    }


    /**
     * @param currentPage
     * @param output
     * Sets the error as outputdata.Error, movie list as a new list and user as null
     *                       and shows it in output and also sets the current page to homepage
     *                       not authenticated.
     */
    public void errorAuthenticate(final CurrentPage currentPage, final Output output) {
        OutputCommands outputCommands = new OutputCommands.Builder()
                .error("Error")
                .currentMoviesList(new ArrayList<>())
                .build();
        currentPage.setPageName("neautentificat");
        output.addOutputCommands(outputCommands);
    }


    /**
     * @param output
     * @param movieList
     * @param user           Sets error to null and shows in output the user and his movie list.
     */
    public void outputSuccess(final Output output,
                              final List<Movie> movieList, final User user) {
        OutputCommands outputCommands = new OutputCommands.Builder()
                .currentMoviesList(movieList)
                .currentUser(user)
                .build();
        output.addOutputCommands(outputCommands);
    }
}

import inputdata.Movie;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

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
     * @param outputCommands
     * Sets the error as Error, movie list as a new list and user as null
     * and shows it in output.
     */
    public void setError(final Output output, final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        output.addOutputCommands(outputCommands);
    }


    /**
     * @param currentPage
     * @param output
     * @param outputCommands
     * Sets the error as Error, movie list as a new list and user as null
     * and shows it in output and also sets the current page to homepage
     * not authenticated.
     */
    public void errorAuthenticate(final CurrentPage currentPage, final Output output,
                                  final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        currentPage.setPageName("neautentificat");
        output.addOutputCommands(outputCommands);
    }


    /**
     * @param output
     * @param outputCommands
     * @param movieList
     * @param user
     * Sets error to null and shows in output the user and his movie list.
     */
    public void outputSuccess(final Output output, final OutputCommands outputCommands,
                              final List<Movie> movieList, final User user) {
        outputCommands.setError(null);
        outputCommands.setCurrentMoviesList(movieList);
        outputCommands.setCurrentUser(user);
        output.addOutputCommands(outputCommands);
    }
}

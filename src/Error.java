import inputdata.Input;
import inputdata.Movie;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;
import java.util.List;

public class Error {

    private static Error instance;

    private Error() {
    }

    public static Error getInstance() {
        if (instance == null) {
            instance = new Error();
        }

        return instance;
    }

    /**
     * @param outputCommands
     */
    public void setError(final Output output, final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        output.getOutput().add(new OutputCommands(outputCommands));
    }

    /**
     * @param outputCommands
     */
    public void errorAuthenticate(final CurrentPage currentPage, final Output output, final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        currentPage.setPageName("neautentificat");
        output.getOutput().add(new OutputCommands(outputCommands));
    }

    /**
     * @param outputCommands
     * @param movieList
     * @param user
     */
    public void outputSuccess(final Output output, final OutputCommands outputCommands, final List<Movie> movieList,
                              final User user) {
        outputCommands.setError(null);
        outputCommands.setCurrentMoviesList(movieList);
        outputCommands.setCurrentUser(user);
        output.getOutput().add(new OutputCommands(outputCommands));
    }
}

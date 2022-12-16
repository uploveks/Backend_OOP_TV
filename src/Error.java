import inputdata.Input;
import inputdata.Movie;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;
import java.util.List;

public class Error {

    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public Error(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     * @param outputCommands
     */
    public void setError(final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        output.getOutput().add(new OutputCommands(outputCommands));
    }

    /**
     * @param outputCommands
     */
    public void errorAuthenticate(final OutputCommands outputCommands) {
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
    public void outputSuccess(final OutputCommands outputCommands, final List<Movie> movieList,
                              final User user) {
        outputCommands.setError(null);
        outputCommands.setCurrentMoviesList(movieList);
        outputCommands.setCurrentUser(user);
        output.getOutput().add(new OutputCommands(outputCommands));
    }
}

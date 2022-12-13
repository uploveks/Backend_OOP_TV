package outputData;

import inputData.Movie;
import inputData.User;

import java.util.List;

public class OutputCommands {
    private String error;
    private List<Movie> currentMoviesList;
    private User currentUser;

    public OutputCommands() {
    }

    public OutputCommands(OutputCommands outputCommands) {
        this.error = outputCommands.getError();
        this.currentMoviesList = outputCommands.getCurrentMoviesList();
        this.currentUser = outputCommands.getCurrentUser();
    }

    public OutputCommands(String error, List<Movie> currentMoviesList, User currentUser) {
        this.error = error;
        this.currentMoviesList = currentMoviesList;
        this.currentUser = currentUser;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(List<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

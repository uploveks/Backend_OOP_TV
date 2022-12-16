import inputdata.Input;
import inputdata.Movie;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;
import java.util.List;

public class MovieActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public MovieActions(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     * @param movie
     * @param outputCommands
     * @param error
     */
    public void purchaseMovie(final Movie movie, final OutputCommands outputCommands,
                              final Error error) {
        // aici contains posibil sa nu mearga intotdeauna
        if (currentPage.getCurrentUser().getPurchasedMovies().contains(movie)) {
            error.setError(outputCommands);
        } else if (currentPage.getCurrentUser().getCredentials().getAccountType().equals(
                "premium") && currentPage.getCurrentUser().getNumFreePremiumMovies() > 0) {
            currentPage.getCurrentUser().setNumFreePremiumMovies(currentPage.getCurrentUser().
                    getNumFreePremiumMovies() - 1);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            ArrayList<Movie> purchasedMovie = new ArrayList<>(List.of(movie));
            error.outputSuccess(outputCommands, purchasedMovie, currentPage.getCurrentUser());
        } else if (currentPage.getCurrentUser().getTokensCount() < 2) {
            error.setError(outputCommands);
        } else {
            currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().
                    getTokensCount() - 2);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            ArrayList<Movie> purchasedMovie = new ArrayList<>(List.of(movie));
            error.outputSuccess(outputCommands, purchasedMovie, currentPage.getCurrentUser());
        }
    }

    /**
     * @param outputCommands
     * @param error
     */
    public void watchMovie(final OutputCommands outputCommands, final Error error) {
        if (currentPage.getPageName().equals("see details")) {
            if (!currentPage.getCurrentUser().getPurchasedMovies().contains(
                    currentPage.getSeenMoviedetails())) {
                error.setError(outputCommands);
            } else if (!currentPage.getCurrentUser().getWatchedMovies().contains(
                    currentPage.getSeenMoviedetails())) {
                currentPage.getCurrentUser().getWatchedMovies().add(
                        currentPage.getSeenMoviedetails());
                ArrayList<Movie> seenMovie = new ArrayList<>(List.of(
                        currentPage.getSeenMoviedetails()));
                currentPage.setCurrentMoviesList(seenMovie);
                error.outputSuccess(outputCommands, seenMovie, currentPage.getCurrentUser());
            }
        } else {
            error.setError(outputCommands);
        }
    }
}

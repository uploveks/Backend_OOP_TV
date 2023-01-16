package actions;

import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Movie;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;
import utils.MagicNumbers;


import java.util.Collections;

public final class MovieActions {
    private static MovieActions instance;

    private MovieActions() {
    }

    /**
     * @return
     * Singleton pattern
     */
    public static MovieActions getInstance() {
        if (instance == null) {
            instance = new MovieActions();
        }

        return instance;
    }


    /**
     * @param currentPage
     * @param output
     * @param movie
     * @param errorOutput
     * Method that checks if the movie already purchased, then if the user
     * has premium account, and it subtracts 1 from his free movies and adds
     * to user's purchased movies the movie that he purchased. If user has
     * standard account, it checks if he has more than 2 tokens and if true
     * subtracts 2 from tokens, adds movie in purchased and print the output.
     */
    public void executePurchaseMovie(final CurrentPage currentPage, final Output output,
                                     final Movie movie, final ErrorOutput errorOutput) {
        if (currentPage.getCurrentUser().getPurchasedMovies().contains(movie)) {
            errorOutput.setError(output);
        } else if (currentPage.getCurrentUser().getCredentials().getAccountType().equals(
                "premium") && currentPage.getCurrentUser().getNumFreePremiumMovies() > 0) {
            currentPage.getCurrentUser().setNumFreePremiumMovies(currentPage.getCurrentUser().
                    getNumFreePremiumMovies() - 1);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                    currentPage.getCurrentUser());
        } else if (currentPage.getCurrentUser().getTokensCount() < 2) {
            errorOutput.setError(output);
        } else {
            currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().
                    getTokensCount() - 2);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                    currentPage.getCurrentUser());
        }
    }

    /**
     * @param currentPage
     * @param action
     * @param errorOutput
     * @param output
     * Method that actually checks if the current page is upgrades
     * or see details. If the page is see details, I just purchase the movie
     * using executePurchaseMovie method. If the page is upgrades, firstly
     * I check if there is a movie with that name using FilterByName, then
     * purchase the movie using executePurchaseMovie method.
     */
    public void purchaseMovie(final CurrentPage currentPage, final Action action,
                              final ErrorOutput errorOutput, final Output output) {
        if (!currentPage.getPageName().equals("upgrades")
                && !currentPage.getPageName().equals("see details")) {
            errorOutput.setError(output);
            return;
        }
        if (currentPage.getPageName().equals("see details")) {
            executePurchaseMovie(currentPage, output, currentPage.getSeenMoviedetails(),
                    errorOutput);
        } else {
            FilterExecutable filterExecutable1 =
                    new FilterExecutable(new FilterByName());
            var filteredList1 = filterExecutable1.executeFilter(
                    currentPage.getCurrentMoviesList(),
                    Collections.singletonList(action.getMovie()));
            Movie foundMovie = filteredList1.get(0);
            executePurchaseMovie(currentPage, output, foundMovie, errorOutput);
        }
    }


    /**
     * @param currentPage
     * @param output
     * @param errorOutput
     * I check if current page is see details, if the movie was purchased and
     * not watched yet. I add to user's watched movie the movie that he saw details for
     * (saved in an object in page.CurrentPage class) and then print the output.
     */
    public void watchMovie(final CurrentPage currentPage, final Output output,
                           final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("see details")) {
            errorOutput.setError(output);
            return;
        }
        if (!currentPage.getCurrentUser().getPurchasedMovies().contains(
                currentPage.getSeenMoviedetails())) {
            errorOutput.setError(output);
            return;
        }
        if (!currentPage.getCurrentUser().getWatchedMovies().contains(
                currentPage.getSeenMoviedetails())) {
            currentPage.getCurrentUser().getWatchedMovies().add(currentPage.getSeenMoviedetails());
        }
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                currentPage.getCurrentUser());
    }

    /**
     * @param currentPage
     * @param output
     * @param errorOutput
     * I check if the current page is see details, if the movie was watched
     * and not liked yet by this user, then I increase his number of likes and add the movie
     * to user's liked movies.
     */
    public void likeMovie(final CurrentPage currentPage, final Output output,
                          final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("see details")) {
            errorOutput.setError(output);
            return;
        }
        if (!currentPage.getCurrentUser().getWatchedMovies().contains(
                    currentPage.getSeenMoviedetails()) || currentPage.getCurrentUser().
                    getLikedMovies().contains(currentPage.getSeenMoviedetails())) {
            errorOutput.setError(output);
            return;
        }
        currentPage.getSeenMoviedetails().setNumLikes(currentPage.getSeenMoviedetails().
                getNumLikes() + 1);
        currentPage.getCurrentUser().getLikedMovies().add(currentPage.
                getSeenMoviedetails());
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                currentPage.getCurrentUser());
    }

    /**
     * @param currentPage
     * @param action
     * @param output
     * @param errorOutput
     * I check if the current page is see details, if the movie was watched,
     * if the user didn't rate it already, and if the rate he wants to give is between 1 and 5.
     * To determine the rating of the movie I multiply his initial rating
     * with the number of ratings and add the rate of the user, then I divide it by number
     * of ratings plus 1. I increment movie's number of ratings and add the movie to
     * user's rated movies.
     */
    public void rateMovie(final CurrentPage currentPage, final Action action, final Output output,
                          final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("see details")) {
            errorOutput.setError(output);
            return;
        }
        if (!(currentPage.getCurrentUser().getWatchedMovies().contains(currentPage.
                getSeenMoviedetails()) && action.getRate()
                        <= MagicNumbers.MAX_RATING && action.getRate() >= 1)) {
            errorOutput.setError(output);
            return;
        }

        double movieRating = currentPage.getSeenMoviedetails().getRating();
        int movieNumRatings = currentPage.getSeenMoviedetails().getNumRatings();
        if (currentPage.getCurrentUser().getRatedMovies().contains(
                currentPage.getSeenMoviedetails())) {
            currentPage.getSeenMoviedetails().setRating((movieRating * movieNumRatings
                    + action.getRate()) / (movieNumRatings + 1));
            errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                    currentPage.getCurrentUser());
            return;
        }
        currentPage.getSeenMoviedetails().setRating((movieRating * movieNumRatings
                + action.getRate()) / (movieNumRatings + 1));
        currentPage.getSeenMoviedetails().setNumRatings(movieNumRatings + 1);
        currentPage.getCurrentUser().getRatedMovies().add(currentPage.getSeenMoviedetails());
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                currentPage.getCurrentUser());
    }

    /**
     * @param currentPage
     * @param action
     * @param output
     * @param errorOutput
     */
    public void subscribe(final CurrentPage currentPage, final Action action, final Output output,
                          final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("see details")) {
            errorOutput.setError(output);
            return;
        }
        if (!currentPage.getSeenMoviedetails().getGenres().contains(action.getSubscribedGenre())) {
            errorOutput.setError(output);
            return;
        }
        if (currentPage.getCurrentUser().getSubscribedGenres().contains(action
                .getSubscribedGenre())) {
            errorOutput.setError(output);
            return;
        }
        currentPage.getCurrentUser().getSubscribedGenres().add(action.getSubscribedGenre());

    }
}

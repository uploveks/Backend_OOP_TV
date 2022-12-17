import filter.*;
import inputdata.*;
import outputdata.Output;
import outputdata.OutputCommands;


public class OnPageActions {

    public OnPageActions() {

    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     * @param buyActions
     * @param userActions
     * @param movieActions
     */
    public void onPage(final CurrentPage currentPage, final Input input, final Output output, final Action action, final OutputCommands outputCommands,
                       final Error error, final BuyActions buyActions,
                       final UserActions userActions, final MovieActions movieActions) {
        if (action.getFeature().equals("login")) {
            userActions.loginOnPage(currentPage, input, output, action, outputCommands, error);
        } else if (action.getFeature().equals("register")) {
            userActions.registerOnPage(currentPage, input, output, action, outputCommands, error);
        } else if (action.getFeature().equals("search")) {
            if (currentPage.getPageName().equals("movies")) {
                FilterExecutable filterExecutable =
                        new FilterExecutable(new FilterByName());
                var filteredList = filterExecutable.executeFilter(input.getMovies(),
                        action.getStartsWith());
//                currentPage.setCurrentMoviesList(filteredList);
                error.outputSuccess(output, outputCommands, filteredList, currentPage.getCurrentUser());
            } else {
                error.setError(output, outputCommands);
            }
        } else if (action.getFeature().equals("filter")) {
            if (currentPage.getPageName().equals("movies")) {
                FilterExecutable filterExecutableCountry =
                        new FilterExecutable(new FilterByCountry());
                var filteredCountryList = filterExecutableCountry.executeFilter(currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser().getCredentials().getCountry());
                currentPage.setCurrentMoviesList(filteredCountryList);
                if (action.getFilters().getSort() != null) {
                    if (action.getFilters().getSort().getDuration() != null
                            && action.getFilters().getSort().getRating() != null) {
                        SortExecutable sortExecutableDuration =
                                new SortExecutable(new SortByDuration());
                        var sortedListDuration = sortExecutableDuration.executeSort(
                                currentPage.getCurrentMoviesList(),
                                action.getFilters().getSort().getDuration());
                        SortExecutable sortExecutableRating =
                                new SortExecutable(new SortByRatings());
                        var sortedListRating = sortExecutableRating.executeSort(
                                sortedListDuration, action.getFilters().getSort().getRating());
                        currentPage.setCurrentMoviesList(sortedListRating);
                    } else if (action.getFilters().getSort().getDuration() != null) {
                        SortExecutable sortExecutableDuration =
                                new SortExecutable(new SortByDuration());
                        var sortedListDuration = sortExecutableDuration.executeSort(
                                currentPage.getCurrentMoviesList(), action.getFilters().getSort().
                                        getDuration());
                        currentPage.setCurrentMoviesList(sortedListDuration);
                    } else if (action.getFilters().getSort().getRating() != null) {
                        SortExecutable sortExecutableRating =
                                new SortExecutable(new SortByRatings());
                        var sortedListRating = sortExecutableRating.executeSort(
                                currentPage.getCurrentMoviesList(),
                                action.getFilters().getSort().getRating());
                        currentPage.setCurrentMoviesList(sortedListRating);
                    }
                }
                    if (action.getFilters().getContains() != null) {
                        if (action.getFilters().getContains().getActors() != null) {
                            FilterExecutable filterExecutable =
                                    new FilterExecutable(new FilterByActors());
                            var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                                    action.getFilters().getContains().getActors().get(0));
                            currentPage.setCurrentMoviesList(filteredList);
                        }
                        if (action.getFilters().getContains().getGenre() != null) {
                            FilterExecutable filterExecutable =
                                    new FilterExecutable(new FilterByGenre());
                            var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                                    action.getFilters().getContains().getGenre().get(0));
                            currentPage.setCurrentMoviesList(filteredList);
                        }
                    }
                error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
            } else {
                error.setError(output, outputCommands);
            }
        } else if (action.getFeature().equals("buy tokens")) {
            buyActions.buyTokens(currentPage, output, action, outputCommands, error);
        } else if (action.getFeature().equals("buy premium account")) {
            buyActions.buyPremiumAccount(currentPage, output, outputCommands, error);
        } else if (action.getFeature().equals("purchase")) {
            if (currentPage.getPageName().equals("upgrades")) {
                FilterExecutable filterExecutable =
                        new FilterExecutable(new FilterByName());
                var filteredList = filterExecutable.executeFilter(
                        currentPage.getCurrentMoviesList(),
                        action.getMovie());
                Movie foundMovie = filteredList.get(0);
                movieActions.purchaseMovie(currentPage, output, foundMovie, outputCommands, error);
            } else if (currentPage.getPageName().equals("see details")) {
                movieActions.purchaseMovie(currentPage, output, currentPage.getSeenMoviedetails(),
                        outputCommands, error);
            } else {
                error.setError(output, outputCommands);
            }
        } else if (action.getFeature().equals("watch")) {
            movieActions.watchMovie(currentPage, output, outputCommands, error);
        } else if (action.getFeature().equals("like")) {
                    if (currentPage.getPageName().equals("see details")) {
                        if (currentPage.getCurrentUser().getWatchedMovies().contains(currentPage.getSeenMoviedetails())
                                && !currentPage.getCurrentUser().getLikedMovies().contains(currentPage.getSeenMoviedetails())
                                ) {
                            currentPage.getSeenMoviedetails().setNumLikes(currentPage.getSeenMoviedetails().getNumLikes() + 1);
                            for (User user: input.getUsers()) {
                                for (Movie watchedMovie: user.getWatchedMovies()) {
                                    if (watchedMovie.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                        user.getWatchedMovies().set(user.getWatchedMovies().indexOf(currentPage.getSeenMoviedetails()), currentPage.getSeenMoviedetails());
                                    }
                                }
                                for (Movie likedMovies: user.getLikedMovies()) {
                                    if (likedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                        user.getLikedMovies().set(user.getLikedMovies().indexOf(currentPage.getSeenMoviedetails()), currentPage.getSeenMoviedetails());
                                    }
                                }
                                for (Movie purchasedMovies: user.getLikedMovies()) {
                                    if (purchasedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                        user.getPurchasedMovies().set(user.getPurchasedMovies().indexOf(currentPage.getSeenMoviedetails()), currentPage.getSeenMoviedetails());
                                    }
                                }
                                for (Movie ratedMovies: user.getLikedMovies()) {
                                    if (ratedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                        user.getRatedMovies().set(user.getRatedMovies().indexOf(currentPage.getSeenMoviedetails()), currentPage.getSeenMoviedetails());
                                    }
                                }
                            }
                        error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(), currentPage.getCurrentUser());
                        } else {
                            error.setError(output, outputCommands);
                        }
                    } else {
                        error.setError(output, outputCommands);
                    }
        } else if (action.getFeature().equals("rate")) {
            if (currentPage.getPageName().equals("see details")) {
                if (currentPage.getCurrentUser().getWatchedMovies().contains(currentPage.getSeenMoviedetails()) && !currentPage.getCurrentUser().getRatedMovies().contains(currentPage.getSeenMoviedetails()) && action.getRate() <= 5 && action.getRate() >= 1) {
                    double movieRating = currentPage.getSeenMoviedetails().getRating();
                    int movieNumRatings = currentPage.getSeenMoviedetails().getNumRatings();
                    currentPage.getSeenMoviedetails().setRating((movieRating * movieNumRatings + action.getRate()) / (movieNumRatings + 1));
                    currentPage.getSeenMoviedetails().setNumRatings(movieNumRatings + 1);
                    for (User user: input.getUsers()) {
                        for (Movie watchedMovie : user.getWatchedMovies()) {
                            if (watchedMovie.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                watchedMovie.setNumRatings(currentPage.getSeenMoviedetails().getNumRatings());
                                watchedMovie.setRating(currentPage.getSeenMoviedetails().getRating());
                            }
                        }
                        for (Movie likedMovies : user.getLikedMovies()) {
                            if (likedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                likedMovies.setNumRatings(currentPage.getSeenMoviedetails().getNumRatings());
                                likedMovies.setRating(currentPage.getSeenMoviedetails().getRating());
                            }
                        }
                        for (Movie purchasedMovies : user.getLikedMovies()) {
                            if (purchasedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                purchasedMovies.setNumRatings(currentPage.getSeenMoviedetails().getNumRatings());
                                purchasedMovies.setRating(currentPage.getSeenMoviedetails().getRating());
                            }
                        }
                        for (Movie ratedMovies : user.getLikedMovies()) {
                            if (ratedMovies.getName().equals(currentPage.getSeenMoviedetails().getName())) {
                                ratedMovies.setNumRatings(currentPage.getSeenMoviedetails().getNumRatings());
                                ratedMovies.setRating(currentPage.getSeenMoviedetails().getRating());
                            }
                        }
                    }
                    error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(), currentPage.getCurrentUser());
                } else {
                    error.setError(output, outputCommands);
                }
            } else {
                error.setError(output, outputCommands);
            }
        }
    }


}

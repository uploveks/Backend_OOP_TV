import filter.*;
import inputdata.*;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;
import java.util.List;

public class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public ProcessActions(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     *
     */
    public void readActions() {
        OutputCommands outputCommands = new OutputCommands();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("login")) {
                    executeAction(currentPage, "login", outputCommands);
                }
                if (action.getPage().equals("register")) {
                    executeAction(currentPage, "register", outputCommands);
                }
                if (action.getPage().equals("logout")) {
                    if (currentPage.getCurrentUser() != null) {
                        currentPage.setPageName("neautentificat");
                        currentPage.setCurrentMoviesList(new ArrayList<>());
                        currentPage.setCurrentUser(null);
                    } else {
                        setError(outputCommands);
                    }
                }
                if (action.getPage().equals("movies")) {
                    if (currentPage.getCurrentUser() != null) {
                        currentPage.setPageName("movies");
                        FilterExecutable filterExecutable =
                                new FilterExecutable(new FilterByCountry());
                        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                                currentPage.getCurrentUser().getCredentials().getCountry());
                        currentPage.setCurrentMoviesList(filteredList);
                        outputCommands.setError(null);
                        outputCommands.setCurrentMoviesList(currentPage.getCurrentMoviesList());
                        outputCommands.setCurrentUser(currentPage.getCurrentUser());
                        output.getOutput().add(new OutputCommands(outputCommands));
                    } else {
                        setError(outputCommands);
                    }
                }
                if (action.getPage().equals("see details")) {
                    if (currentPage.getPageName().equals("movies")) {
                        FilterExecutable filterExecutable =
                                new FilterExecutable(new FilterByName());
                        var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                                action.getMovie());
                            if (filteredList.size() != 0) {
                                currentPage.setPageName("see details");
                                currentPage.setSeenMoviedetails(filteredList.get(0));
                            } else {
                                setError(outputCommands);
                            }

                    } else {
                        setError(outputCommands);
                    }

                }
                if (action.getPage().equals("upgrades")) {
                    if (currentPage.getCurrentUser() != null) {
                        currentPage.setPageName("upgrades");
                        currentPage.setCurrentMoviesList(new ArrayList<>());
                    } else {
                        setError(outputCommands);
                    }
                }
            }
            if (action.getType().equals("on page")) {
                if (action.getFeature().equals("login")) {
                    if (currentPage.getPageName().equals("login")
                            && currentPage.getCurrentUser() == null) {
                        if (checkUser(action.getCredentials()) != null) {
                            currentPage.setCurrentUser(checkUser(action.getCredentials()));
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(checkUser(action.getCredentials()));
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else {
                            outputCommands.setError("Error");
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(null);
                            currentPage.setPageName("neautentificat");
                            output.getOutput().add(new OutputCommands(outputCommands));
                        }
                    } else {
                        outputCommands.setError("Error");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        outputCommands.setCurrentUser(null);
                        currentPage.setPageName("neautentificat");
                        output.getOutput().add(new OutputCommands(outputCommands));
                    }
                } else if (action.getFeature().equals("register")) {
                    if (currentPage.getPageName().equals("register")
                            && currentPage.getCurrentUser() == null) {
                        if (checkUser(action.getCredentials()) == null) {
                            User newUser = new User(action.getCredentials());
                            currentPage.setCurrentUser(newUser);
                            input.getUsers().add(newUser);
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(newUser);
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else {
                            outputCommands.setError("Error");
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(null);
                            currentPage.setPageName("neautentificat");
                            output.getOutput().add(new OutputCommands(outputCommands));
                        }
                    } else {
                        outputCommands.setError("Error");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        outputCommands.setCurrentUser(null);
                        currentPage.setPageName("neautentificat");
                        output.getOutput().add(new OutputCommands(outputCommands));
                    }
                } else if (action.getFeature().equals("search")) {
                    if (currentPage.getPageName().equals("movies")) {
                        FilterExecutable filterExecutable =
                                new FilterExecutable(new FilterByName());
                        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                                action.getStartsWith());
                        currentPage.setCurrentMoviesList(filteredList);
                        outputCommands.setError(null);
                        outputCommands.setCurrentMoviesList(filteredList);
                        outputCommands.setCurrentUser(currentPage.getCurrentUser());
                        output.getOutput().add(new OutputCommands(outputCommands));
                    } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("filter")) {
                    if (currentPage.getPageName().equals("movies")) {
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
                                    sortedListDuration,
                                            action.getFilters().getSort().getRating());
                            currentPage.setCurrentMoviesList(sortedListRating);
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(sortedListRating);
                            outputCommands.setCurrentUser(currentPage.getCurrentUser());
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else if (action.getFilters().getSort().getDuration() != null) {
                            SortExecutable sortExecutableDuration =
                                    new SortExecutable(new SortByDuration());
                            var sortedListDuration = sortExecutableDuration.executeSort(
                                    currentPage.getCurrentMoviesList(),
                                            action.getFilters().getSort().getDuration());
                            currentPage.setCurrentMoviesList(sortedListDuration);
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(sortedListDuration);
                            outputCommands.setCurrentUser(currentPage.getCurrentUser());
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else if (action.getFilters().getSort().getRating() != null) {
                            SortExecutable sortExecutableRating =
                                    new SortExecutable(new SortByRatings());
                            var sortedListRating = sortExecutableRating.executeSort(
                                    currentPage.getCurrentMoviesList(),
                                            action.getFilters().getSort().getRating());
                            currentPage.setCurrentMoviesList(sortedListRating);
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(sortedListRating);
                            outputCommands.setCurrentUser(currentPage.getCurrentUser());
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else {
                            setError(outputCommands);
                        }
                    }
                } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("buy tokens")) {
                    if (currentPage.getPageName().equals("upgrades")) {
                        if (Integer.parseInt(currentPage.getCurrentUser().getCredentials().getBalance()) >= Integer.parseInt(action.getCount())) {
                            currentPage.getCurrentUser().setTokensCount(Integer.parseInt(action.getCount()));
                            int substractBalance = Integer.parseInt(currentPage.getCurrentUser().getCredentials().getBalance()) - Integer.parseInt(action.getCount());
                            currentPage.getCurrentUser().getCredentials().setBalance(Integer.toString(substractBalance));
                        } else {
                            setError(outputCommands);
                        }
                    } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("buy premium account")) {
                    if (currentPage.getPageName().equals("upgrades")) {
                        if (currentPage.getCurrentUser().getTokensCount() >= 10) {
                            currentPage.getCurrentUser().getCredentials().setAccountType("premium");
                            currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().getTokensCount() - 10);
                        } else {
                            setError(outputCommands);
                        }
                    } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("purchase")) {
                    if (currentPage.getPageName().equals("upgrades")) {
                        FilterExecutable filterExecutable =
                                new FilterExecutable(new FilterByName());
                        var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                                action.getMovie());
                        Movie foundMovie = filteredList.get(0);
                        purchaseMovie(currentPage, foundMovie, outputCommands);
                    } else if (currentPage.getPageName().equals("see details")) {
                        purchaseMovie(currentPage, currentPage.getSeenMoviedetails(), outputCommands);
                    } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("watch")) {
                    if (currentPage.getPageName().equals("see details")) {
                        if (!currentPage.getCurrentUser().getPurchasedMovies().contains(currentPage.getSeenMoviedetails())) {
                            setError(outputCommands);
                        } else if (!currentPage.getCurrentUser().getWatchedMovies().contains(currentPage.getSeenMoviedetails())) {
                            currentPage.getCurrentUser().getWatchedMovies().add(currentPage.getSeenMoviedetails());
                            outputCommands.setError(null);
                            ArrayList<Movie> purchasedMovie = new ArrayList<>(List.of(currentPage.getSeenMoviedetails()));
                            outputCommands.setCurrentMoviesList(purchasedMovie);
                            outputCommands.setCurrentUser(currentPage.getCurrentUser());
                            output.getOutput().add(new OutputCommands(outputCommands));
                        }
                    } else {
                        setError(outputCommands);
                    }
                } else if (action.getFeature().equals("like")) {
                    if (currentPage.getPageName().equals("see details")) {
                        if (currentPage.getCurrentUser().getWatchedMovies().contains(currentPage.getSeenMoviedetails()) && !currentPage.getCurrentUser().getLikedMovies().contains(currentPage.getSeenMoviedetails())) {
                            currentPage.getSeenMoviedetails().setNumLikes(currentPage.getSeenMoviedetails().getNumLikes() + 1);

                        }
                    } else {
                        setError(outputCommands);
                    }
                }
            }

        }

    }

    private void purchaseMovie(CurrentPage currentPage, Movie movie, OutputCommands outputCommands) {
        if (currentPage.getCurrentUser().getPurchasedMovies().contains(movie.getName())) {
            setError(outputCommands);
        } else if (currentPage.getCurrentUser().getCredentials().getAccountType().equals("premium") && currentPage.getCurrentUser().getNumFreePremiumMovies() > 0) {
            currentPage.getCurrentUser().setNumFreePremiumMovies(currentPage.getCurrentUser().getNumFreePremiumMovies() - 1);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            outputCommands.setError(null);
            ArrayList<Movie> purchasedMovie = new ArrayList<>(List.of(movie));
            outputCommands.setCurrentMoviesList(purchasedMovie);
            outputCommands.setCurrentUser(currentPage.getCurrentUser());
            output.getOutput().add(new OutputCommands(outputCommands));
        } else if (currentPage.getCurrentUser().getTokensCount() < 2) {
            setError(outputCommands);
        } else {
            currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().getTokensCount() - 2);
            currentPage.getCurrentUser().getPurchasedMovies().add(movie);
            outputCommands.setError(null);
            ArrayList<Movie> purchasedMovie = new ArrayList<>(List.of(movie));
            outputCommands.setCurrentMoviesList(purchasedMovie);
            outputCommands.setCurrentUser(currentPage.getCurrentUser());
            output.getOutput().add(new OutputCommands(outputCommands));
        }
    }
    private void setError(final OutputCommands outputCommands) {
        outputCommands.setError("Error");
        outputCommands.setCurrentMoviesList(new ArrayList<>());
        outputCommands.setCurrentUser(null);
        output.getOutput().add(new OutputCommands(outputCommands));
    }

    private User checkUser(final Credentials credentials) {
        for (User user: input.getUsers()) {
            if (user.getCredentials().equals(credentials)) {
                return new User(user);
            }
        }
        return null;
    }

    private void executeAction(final CurrentPage currentPage, final String pageName,
                               final OutputCommands outputCommands) {
        if (currentPage.getPageName().equals("neautentificat")
                && currentPage.getCurrentUser() == null) {
            currentPage.setPageName(pageName);
            currentPage.setCurrentMoviesList(null);
        } else {
            setError(outputCommands);
        }
    }
}

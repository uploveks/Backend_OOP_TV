package actions;

import filter.FilterByCountry;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;
    private Stack<CurrentPage> pagesStack;
    private static ProcessActions instance = new ProcessActions();

    private ProcessActions(){

    }
    public static ProcessActions getInstance(){
        return instance;
    }

    public CurrentPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Stack<CurrentPage> getPagesStack() {
        return pagesStack;
    }

    public void setPagesStack(Stack<CurrentPage> pagesStack) {
        this.pagesStack = pagesStack;
    }

    /**
     * Process all the actions from change page and from on page.
     */
    public void readActions() {
        currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        OutputCommands outputCommands = new OutputCommands.Builder().build();
        BuyActions buyActions = BuyActions.getInstance();
        Error error = Error.getInstance();
        UserActions userActions =  UserActions.getInstance();
        ChangePageActions changePageActions = new ChangePageActions();
        OnPageActions onPageActions = new OnPageActions();
        BackActions backActions = new BackActions();
        DatabaseActions databaseActions = new DatabaseActions();
        MovieActions movieActions = MovieActions.getInstance();
        FilterActions filterActions = FilterActions.getInstance();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(currentPage, input, output, action.getPage(), action.getMovie(), userActions,
                        outputCommands, error);
            } else if (action.getType().equals("on page")) {
                onPageActions.onPage(currentPage, input, output, action, outputCommands, error,
                        buyActions, userActions, movieActions, filterActions);
            } else if (action.getType().equals("back")) {
                backActions.goBack(pagesStack,currentPage, input, output, userActions,
                        outputCommands, error);
            } else if (action.getType().equals("database")) {
                if (action.getFeature().equals("add")) {
                    databaseActions.databaseAdd(input, output, outputCommands, action, error);
                }
                if (action.getFeature().equals("delete")) {
                    databaseActions.databaseDelete(input, output, outputCommands, action, error);
                }
            }
        }

        if (currentPage.getCurrentUser() != null) {
            if (currentPage.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
                if (currentPage.getCurrentUser().getLikedMovies().isEmpty()) {
                    Notifications recommendation = new Notifications("No recommendation", "Recommendation");
                    currentPage.getCurrentUser().getNotifications().add(recommendation);
                    error.outputSuccess(output, null, currentPage.getCurrentUser());
                } else {
                    Map<String, Integer> genreLikes = new HashMap<>();
                    for (Movie movie : currentPage.getCurrentUser().getLikedMovies()) {
                        for (String genre : movie.getGenres()) {
                            int totalLikes = genreLikes.getOrDefault(genre, 0) + movie.getNumLikes();
                            genreLikes.put(genre, totalLikes);
                        }
                    }
                    List<String> genres = new ArrayList<>(genreLikes.keySet());
                    genres.sort((genre1, genre2) -> {
                        if (genreLikes.get(genre1).equals(genreLikes.get(genre2))) {
                            return genre1.compareTo(genre2);
                        }
                        return genreLikes.get(genre2) - genreLikes.get(genre1);
                    });
                    List<Movie> notWatchedMovies = input.getMovies().stream().filter(movie -> !currentPage.getCurrentUser().getWatchedMovies().contains(movie)).toList();
                    FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
                    var filteredByCountry = filterExecutable.executeFilter(notWatchedMovies,
                            Collections.singletonList(currentPage.getCurrentUser().getCredentials().
                                    getCountry()));
                    Collections.sort(filteredByCountry, Comparator.comparingInt(Movie::getNumLikes));
                    while (!genres.isEmpty()) {
                        String likedGenre = genres.get(0);
                        genres.remove(0);
                        List<Movie> likedGenreMovies = new ArrayList<>(filteredByCountry.stream().filter(movie -> movie.getGenres().contains(likedGenre)).toList());
                        likedGenreMovies.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());
                        if (!likedGenreMovies.isEmpty()) {
                            Movie recommendedMovie = likedGenreMovies.get(0);
                            Notifications recommendation = new Notifications(recommendedMovie.getName(), "Recommendation");
                            currentPage.getCurrentUser().getNotifications().add(recommendation);
                            error.outputSuccess(output, null, currentPage.getCurrentUser());
                            break;
                        }
                    }


                }
            }
        }
    }

}

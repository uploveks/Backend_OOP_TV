import filter.FilterByCountry;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;

public class ChangePageActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public ChangePageActions(final CurrentPage currentPage, final Input input,
                             final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     * @param action
     * @param userActions
     * @param outputCommands
     * @param error
     */
    public void changePage(final Action action, final UserActions userActions,
                           final OutputCommands outputCommands, final Error error) {
        if (action.getPage().equals("login")) {
            userActions.executeChangePage(currentPage, "login", outputCommands, error);
        }
        if (action.getPage().equals("register")) {
            userActions.executeChangePage(currentPage, "register", outputCommands, error);
        }
        if (action.getPage().equals("logout")) {
            if (currentPage.getCurrentUser() != null) {
                currentPage.setPageName("neautentificat");
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setCurrentUser(null);
            } else {
                error.setError(outputCommands);
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
                error.setError(outputCommands);
            }
        }
        if (action.getPage().equals("see details")) {
            if (currentPage.getPageName().equals("movies")) {
                FilterExecutable filterExecutable =
                        new FilterExecutable(new FilterByName());
                var filteredList = filterExecutable.executeFilter(currentPage.
                        getCurrentMoviesList(), action.getMovie());
                if (filteredList.size() != 0) {
                    currentPage.setPageName("see details");
                    currentPage.setSeenMovieDetails(filteredList.get(0));
                } else {
                    error.setError(outputCommands);
                }

            } else {
                error.setError(outputCommands);
            }

        }
        if (action.getPage().equals("upgrades")) {
            if (currentPage.getCurrentUser() != null) {
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setPageName("upgrades");
            } else {
                error.setError(outputCommands);
            }
        }
    }
}

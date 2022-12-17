import filter.FilterByCountry;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;
import java.util.Collections;

public class ChangePageActions {

    public ChangePageActions() {
    }


    /**
     * @param currentPage
     * @param input
     * @param output
     * @param action
     * @param userActions
     * @param outputCommands
     * @param error
     * Method that implements all the actions for "change page"
     */
    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final Action action, final UserActions userActions,
                           final OutputCommands outputCommands, final Error error) {
        switch (action.getPage()) {
            case "login":
                userActions.loginRegisterChangePage(currentPage, output, "login", outputCommands, error);
                break;
            case "register":
                userActions.loginRegisterChangePage(currentPage, output, "register", outputCommands,
                        error);
                break;
            case "logout":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output, outputCommands);
                    return;
                }
                currentPage.setPageName("neautentificat");
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setCurrentUser(null);
                break;
            case "movies":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output, outputCommands);
                    return;
                }
                currentPage.setPageName("movies");
                FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
                var filteredList = filterExecutable.executeFilter(input.getMovies(),
                        Collections.singletonList(currentPage.getCurrentUser().getCredentials().
                                getCountry()));
                currentPage.setCurrentMoviesList(filteredList);
                error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
                break;
            case "see details":
                if (!currentPage.getPageName().equals("movies")) {
                    error.setError(output, outputCommands);
                    return;
                }
                FilterExecutable filterExecutable1 = new FilterExecutable(new FilterByName());
                var filteredList1 = filterExecutable1.executeFilter(currentPage.
                        getCurrentMoviesList(), Collections.singletonList(action.getMovie()));
                if (filteredList1.size() == 0) {
                    error.setError(output, outputCommands);
                    return;
                }
                currentPage.setPageName("see details");
                currentPage.setSeenMovieDetails(filteredList1.get(0));
                currentPage.setCurrentMoviesList(filteredList1);
                error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
                break;
            case "upgrades":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output, outputCommands);
                    return;
                }
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setPageName("upgrades");
                break;
            default:
                break;
        }
    }
}

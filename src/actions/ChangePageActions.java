package actions;

import filter.FilterByCountry;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Input;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;

import java.util.ArrayList;
import java.util.Collections;

public class ChangePageActions {

    public ChangePageActions() {
    }


    /**
     * @param currentPage
     * @param input
     * @param output
     * @param userActions
     * @param outputCommands
     * @param error
     * Method that implements all the actions for "change page"
     */
    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final String pageName, final String movieName, final UserActions userActions,
                           final OutputCommands outputCommands, final Error error) {
        switch (pageName) {
            case "login":
                userActions.loginRegisterChangePage(currentPage, output, "login",
                        error);
                break;
            case "register":
                userActions.loginRegisterChangePage(currentPage, output, "register",
                        error);
                break;
            case "logout":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output);
                    return;
                }
                currentPage.setPageName("neautentificat");
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setCurrentUser(null);
                ProcessActions.getInstance().getPagesStack().clear();
                ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
                break;
            case "movies":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output);
                    return;
                }
                currentPage.setPageName("movies");
                FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
                var filteredList = filterExecutable.executeFilter(input.getMovies(),
                        Collections.singletonList(currentPage.getCurrentUser().getCredentials().
                                getCountry()));
                currentPage.setCurrentMoviesList(filteredList);
                ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
                error.outputSuccess(output, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
                break;
            case "see details":
                if (!currentPage.getPageName().equals("movies")) {
                    error.setError(output);
                    return;
                }
                FilterExecutable filterExecutable1 = new FilterExecutable(new FilterByName());
                var filteredList1 = filterExecutable1.executeFilter(currentPage.
                        getCurrentMoviesList(), Collections.singletonList(movieName));
                if (filteredList1.size() == 0) {
                    error.setError(output);
                    return;
                }
                currentPage.setPageName("see details");
                currentPage.setSeenMovieDetails(filteredList1.get(0));
                currentPage.setCurrentMoviesList(filteredList1);
                ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
                error.outputSuccess(output, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
                break;
            case "upgrades":
                if (currentPage.getCurrentUser() == null) {
                    error.setError(output);
                    return;
                }
                currentPage.setCurrentMoviesList(new ArrayList<>());
                currentPage.setPageName("upgrades");
                ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
                break;
            default:
                break;
        }
    }
}

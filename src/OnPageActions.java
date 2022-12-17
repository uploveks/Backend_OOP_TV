
import filter.FilterByCountry;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.Collections;


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
     * Method that implements all the actions for "on page"
     */
    public void onPage(final CurrentPage currentPage, final Input input, final Output output,
                       final Action action, final OutputCommands outputCommands,
                       final Error error, final BuyActions buyActions,
                       final UserActions userActions, final MovieActions movieActions,
                       final FilterActions filterActions) {
        switch (action.getFeature()) {
            case "login":
                userActions.loginOnPage(currentPage, input, output, action, outputCommands, error);
                break;
            case "register":
                userActions.registerOnPage(currentPage, input, output, action, outputCommands,
                        error);
                break;
            case "search":
                if (!currentPage.getPageName().equals("movies")) {
                    error.setError(output, outputCommands);
                    return;
                }
                FilterExecutable filterExecutableCountry = new FilterExecutable(
                        new FilterByCountry());
                var filteredCountryList = filterExecutableCountry.executeFilter(input.getMovies(),
                        Collections.singletonList(currentPage.getCurrentUser().getCredentials().
                                getCountry()));
                FilterExecutable filterExecutable = new FilterExecutable(new FilterByName());
                var filteredList = filterExecutable.executeFilter(filteredCountryList,
                        Collections.singletonList(action.getStartsWith()));
                currentPage.setCurrentMoviesList(filteredList);
                error.outputSuccess(output, outputCommands, filteredList, currentPage.
                        getCurrentUser());
                break;
            case "filter":
                if (!currentPage.getPageName().equals("movies")) {
                    error.setError(output, outputCommands);
                    return;
                }
                FilterExecutable filterExecutableCountry1 =
                        new FilterExecutable(new FilterByCountry());
                var filteredCountryList1 = filterExecutableCountry1.executeFilter(
                        input.getMovies(), Collections.singletonList(currentPage.getCurrentUser().
                                getCredentials().getCountry()));
                currentPage.setCurrentMoviesList(filteredCountryList1);
                if (action.getFilters().getSort() != null) {
                    filterActions.sortMovies(currentPage, action);
                }
                if (action.getFilters().getContains() != null) {
                    filterActions.containsInMovies(currentPage, action);
                }
                error.outputSuccess(output, outputCommands, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
                break;
            case "buy tokens":
                buyActions.buyTokens(currentPage, output, action, outputCommands, error);
                break;
            case "buy premium account":
                buyActions.buyPremiumAccount(currentPage, output, outputCommands, error);
                break;
            case "purchase":
                movieActions.purchaseMovie(currentPage, action, error, outputCommands, output);
                break;
            case "watch":
                movieActions.watchMovie(currentPage, output, outputCommands, error);
                break;
            case "like":
                movieActions.likeMovie(currentPage, output, error, outputCommands);
                break;
            case "rate":
                movieActions.rateMovie(currentPage, action, output, error, outputCommands);
                break;
            default:
                break;
        }
    }
}

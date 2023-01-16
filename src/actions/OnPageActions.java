package actions;

import filter.FilterByCountry;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;

import java.util.Collections;


public class OnPageActions {

    public OnPageActions() {

    }

    /**
     * @param action
     * @param outputCommands
     * @param errorOutput
     * @param buyActions
     * @param userActions
     * @param movieActions
     * Method that implements all the actions for "on page"
     */
    public void onPage(final CurrentPage currentPage, final Input input, final Output output,
                       final Action action, final OutputCommands outputCommands,
                       final ErrorOutput errorOutput, final BuyActions buyActions,
                       final UserActions userActions, final MovieActions movieActions,
                       final FilterActions filterActions) {
        switch (action.getFeature()) {
            case "login" -> userActions.loginOnPage(currentPage, input, output, action, outputCommands,
                    errorOutput);
            case "register" -> userActions.registerOnPage(currentPage, input, output, action, outputCommands,
                    errorOutput);
            case "search" -> {
                if (!currentPage.getPageName().equals("movies")) {
                    errorOutput.setError(output);
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
                errorOutput.outputSuccess(output, filteredList, currentPage.
                        getCurrentUser());
            }
            case "filter" -> {
                if (!currentPage.getPageName().equals("movies")) {
                    errorOutput.setError(output);
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
                errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                        currentPage.getCurrentUser());
            }
            case "buy tokens" -> buyActions.buyTokens(currentPage, output, action, errorOutput);
            case "buy premium account" -> buyActions.buyPremiumAccount(currentPage, output, errorOutput);
            case "purchase" -> movieActions.purchaseMovie(currentPage, action, errorOutput, output);
            case "watch" -> movieActions.watchMovie(currentPage, output, errorOutput);
            case "like" -> movieActions.likeMovie(currentPage, output, errorOutput);
            case "rate" -> movieActions.rateMovie(currentPage, action, output, errorOutput);
            case "subscribe" -> movieActions.subscribe(currentPage, action, output, errorOutput);
            default -> {
            }
        }
    }
}

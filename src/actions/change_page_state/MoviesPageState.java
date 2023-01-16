package actions.change_page_state;

import actions.ProcessActions;
import actions.UserActions;
import filter.FilterByCountry;
import filter.FilterExecutable;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.Collections;

public class MoviesPageState implements PageState {
    @Override
    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final UserActions userActions, final ErrorOutput errorOutput) {
        if (currentPage.getCurrentUser() == null) {
            errorOutput.setError(output);
            return;
        }
        currentPage.setPageName("movies");
        FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                Collections.singletonList(currentPage.getCurrentUser().getCredentials().getCountry()));
        currentPage.setCurrentMoviesList(filteredList);
        ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(), currentPage.getCurrentUser());
    }
}

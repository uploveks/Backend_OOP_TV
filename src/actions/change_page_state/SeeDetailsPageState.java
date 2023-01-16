package actions.change_page_state;

import actions.ProcessActions;
import actions.UserActions;
import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.Collections;

public class SeeDetailsPageState implements PageState {
    private final String movieName;

    public SeeDetailsPageState(String movieName) {
        this.movieName = movieName;
    }

    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final UserActions userActions, final ErrorOutput errorOutput) {
        if (!currentPage.getPageName().equals("movies")) {
            errorOutput.setError(output);
            return;
        }
        FilterExecutable filterExecutable1 = new FilterExecutable(new FilterByName());
        var filteredList1 = filterExecutable1.executeFilter(currentPage.
                getCurrentMoviesList(), Collections.singletonList(movieName));
        if (filteredList1.size() == 0) {
            errorOutput.setError(output);
            return;
        }
        currentPage.setPageName("see details");
        currentPage.setSeenMovieDetails(filteredList1.get(0));
        currentPage.setCurrentMoviesList(filteredList1);
        ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
        errorOutput.outputSuccess(output, currentPage.getCurrentMoviesList(),
                currentPage.getCurrentUser());
    }
}

package actions.change_page_state;

import actions.ProcessActions;
import actions.UserActions;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.ArrayList;

public class UpgradesPageState implements PageState {
    @Override
    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final UserActions userActions, final ErrorOutput errorOutput) {
        if (currentPage.getCurrentUser() == null) {
            errorOutput.setError(output);
            return;
        }
        currentPage.setCurrentMoviesList(new ArrayList<>());
        currentPage.setPageName("upgrades");
        ProcessActions.getInstance().getPagesStack().push(new CurrentPage(currentPage));
    }
}

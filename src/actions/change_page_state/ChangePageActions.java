package actions.change_page_state;

import actions.UserActions;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

public class ChangePageActions {
    private PageState currentPageState;

    public ChangePageActions() {
        currentPageState = new LoginPageState();
    }

    /**
     * @param currentPage
     * @param input
     * @param output
     * @param pageName
     * @param movieName
     * @param userActions
     * @param errorOutput
     */
    public void changePage(final CurrentPage currentPage, final Input input, final Output output,
                           final String pageName, final String movieName,
                           final UserActions userActions, final ErrorOutput errorOutput) {
        currentPageState = PageStateFactory.createPageState(pageName, movieName);
        currentPageState.changePage(currentPage, input, output, userActions, errorOutput);
    }
}

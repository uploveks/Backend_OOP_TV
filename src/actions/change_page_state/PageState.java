package actions.change_page_state;

import actions.UserActions;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

public interface PageState {
    void changePage(final CurrentPage currentPage, final Input input, final Output output,
                    final UserActions userActions, final ErrorOutput errorOutput);
}

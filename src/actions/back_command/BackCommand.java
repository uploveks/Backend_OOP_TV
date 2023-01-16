package actions.back_command;

import actions.change_page_state.ChangePageActions;
import actions.UserActions;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.Stack;

class BackCommand implements Command {
    private Stack<CurrentPage> pagesStack;
    private CurrentPage currentPage;
    private Input input;
    private Output output;
    private UserActions userActions;
    private ErrorOutput errorOutput;

    BackCommand(final Stack<CurrentPage> pagesStack, final CurrentPage currentPage,
                       final Input input, final Output output, final UserActions userActions,
                        final ErrorOutput errorOutput) {
        this.pagesStack = pagesStack;
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
        this.userActions = userActions;
        this.errorOutput = errorOutput;
    }

    @Override
    public void execute() {
        if (currentPage.getCurrentUser() == null) {
            errorOutput.setError(output);
            return;
        }
        if (pagesStack.isEmpty()) {
            errorOutput.setError(output);
            return;
        }
        if (currentPage.getPageName().equals("neautentificat")) {
            return;
        }
        pagesStack.pop();
        if (!pagesStack.isEmpty()) {
            CurrentPage backPage = pagesStack.pop();
            if (backPage.getPageName().equals("login") || backPage.getPageName()
                    .equals("register")) {
                currentPage.setPageName(backPage.getPageName());
                errorOutput.setError(output);
                return;
            }
            if (backPage.getPageName().equals("neautentificat")) {
                currentPage.setPageName("neautentificat");
                return;
            }
            ChangePageActions changePage = new ChangePageActions();
            if (backPage.getSeenMoviedetails() != null) {
                changePage.changePage(currentPage, input, output, backPage.getPageName(),
                        backPage.getSeenMoviedetails().getName(), userActions,
                        errorOutput);
            } else {
                changePage.changePage(currentPage, input, output, backPage.getPageName(),
                        null, userActions, errorOutput);
            }
        }
    }
}


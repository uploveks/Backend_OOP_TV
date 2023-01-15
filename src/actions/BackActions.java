package actions;

import inputdata.Input;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;

import java.util.Stack;

public class BackActions {
    public BackActions() {
    }
    public void goBack(final Stack<CurrentPage> pagesStack, final CurrentPage currentPage, final Input input, final Output output,
                       final UserActions userActions,
                       final OutputCommands outputCommands, final Error error) {
        if (currentPage.getCurrentUser() == null) {
            error.setError(output, outputCommands);
            return;
        }
        if (pagesStack.isEmpty()) {
            error.setError(output, outputCommands);
            return;
        }
        if (currentPage.getPageName().equals("neautentificat")) {
            return;
        }
        pagesStack.pop();
        if (!pagesStack.isEmpty()) {
            CurrentPage backPage = pagesStack.pop();
            if (backPage.getPageName().equals("login") || backPage.getPageName().equals("register")) {
                currentPage.setPageName(backPage.getPageName());
                error.setError(output, outputCommands);
                return;
            }
            if (backPage.getPageName().equals("neautentificat")) {
                currentPage.setPageName("neautentificat");
                return;
            }
            ChangePageActions changePage = new ChangePageActions();
            if (backPage.getSeenMoviedetails() != null) {
                changePage.changePage(currentPage, input, output, backPage.getPageName(), backPage.getSeenMoviedetails().getName(), userActions, outputCommands, error);
            } else {
                changePage.changePage(currentPage, input, output, backPage.getPageName(), null, userActions, outputCommands, error);
            }
        }
    }
}

import inputdata.Action;
import inputdata.Credentials;
import inputdata.Input;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;

public class UserActions {
    private CurrentPage currentPage;
    private Input input;

    private Output output;

    public UserActions(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     * @param currentPage
     * @param pageName
     * @param outputCommands
     * @param error
     */
    public void executeChangePage(final CurrentPage currentPage, final String pageName,
                                  final OutputCommands outputCommands, final Error error) {
        if (currentPage.getPageName().equals("neautentificat")
                && currentPage.getCurrentUser() == null) {
            currentPage.setPageName(pageName);
            currentPage.setCurrentMoviesList(null);
        } else {
            error.setError(outputCommands);
        }
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void loginOnPage(final Action action, final OutputCommands outputCommands,
                            final Error error) {
        if (currentPage.getPageName().equals("login")
                && currentPage.getCurrentUser() == null) {
            if (checkUser(action.getCredentials()) != null) {
                currentPage.setCurrentUser(checkUser(action.getCredentials()));
                outputCommands.setError(null);
                outputCommands.setCurrentMoviesList(new ArrayList<>());
                outputCommands.setCurrentUser(checkUser(action.getCredentials()));
                output.getOutput().add(new OutputCommands(outputCommands));
            } else {
                error.errorAuthenticate(outputCommands);
            }
        } else {
            error.errorAuthenticate(outputCommands);
        }
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void registerOnPage(final Action action, final OutputCommands outputCommands,
                               final Error error) {
        if (currentPage.getPageName().equals("register")
                && currentPage.getCurrentUser() == null) {
            if (checkUser(action.getCredentials()) == null) {
                User newUser = new User(action.getCredentials());
                currentPage.setCurrentUser(newUser);
                input.getUsers().add(newUser);
                outputCommands.setError(null);
                outputCommands.setCurrentMoviesList(new ArrayList<>());
                outputCommands.setCurrentUser(newUser);
                output.getOutput().add(new OutputCommands(outputCommands));
            } else {
                error.errorAuthenticate(outputCommands);
            }
        } else {
            error.errorAuthenticate(outputCommands);
        }
    }

    /**
     * @param credentials
     * @return
     */
    private User checkUser(final Credentials credentials) {
        for (User user: input.getUsers()) {
            if (user.getCredentials().equals(credentials)) {
                return new User(user);
            }
        }
        return null;
    }
}

import inputdata.Action;
import inputdata.Credentials;
import inputdata.Input;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;

public class UserActions {
    private static UserActions instance;
    private UserActions() {
    }

    public static UserActions getInstance() {
        if (instance == null) {
            instance = new UserActions();
        }

        return instance;
    }

    /**
     * @param currentPage
     * @param pageName
     * @param outputCommands
     * @param error
     */
    public void executeChangePage(final CurrentPage currentPage, final Output output, final String pageName,
                                  final OutputCommands outputCommands, final Error error) {
        if (currentPage.getPageName().equals("neautentificat")
                && currentPage.getCurrentUser() == null) {
            currentPage.setPageName(pageName);
            currentPage.setCurrentMoviesList(null);
        } else {
            error.setError(output, outputCommands);
        }
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void loginOnPage(final CurrentPage currentPage, final Input input, final Output output, final Action action, final OutputCommands outputCommands,
                            final Error error) {
        if (currentPage.getPageName().equals("login")
                && currentPage.getCurrentUser() == null) {
            if (checkUser(input, action.getCredentials()) != null) {
                currentPage.setCurrentUser(checkUser(input, action.getCredentials()));
                outputCommands.setError(null);
                outputCommands.setCurrentMoviesList(new ArrayList<>());
                outputCommands.setCurrentUser(checkUser(input, action.getCredentials()));
                output.getOutput().add(new OutputCommands(outputCommands));
            } else {
                error.errorAuthenticate(currentPage, output, outputCommands);
            }
        } else {
            error.errorAuthenticate(currentPage, output, outputCommands);
        }
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void registerOnPage(final CurrentPage currentPage, final Input input, final Output output, final Action action, final OutputCommands outputCommands,
                               final Error error) {
        if (currentPage.getPageName().equals("register")
                && currentPage.getCurrentUser() == null) {
            if (checkUser(input, action.getCredentials()) == null) {
                User newUser = new User(action.getCredentials());
                currentPage.setCurrentUser(newUser);
                input.getUsers().add(newUser);
                outputCommands.setError(null);
                outputCommands.setCurrentMoviesList(new ArrayList<>());
                outputCommands.setCurrentUser(newUser);
                output.getOutput().add(new OutputCommands(outputCommands));
            } else {
                error.errorAuthenticate(currentPage, output, outputCommands);
            }
        } else {
            error.errorAuthenticate(currentPage, output, outputCommands);
        }
    }

    /**
     * @param credentials
     * @return
     */
    private User checkUser(final Input input, final Credentials credentials) {
        for (User user: input.getUsers()) {
            if (user.getCredentials().equals(credentials)) {
                return new User(user);
            }
        }
        return null;
    }
}

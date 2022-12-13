import inputData.Action;
import inputData.Credentials;
import inputData.Input;
import inputData.User;
import outputData.Output;
import outputData.OutputCommands;

import java.util.ArrayList;

public class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public ProcessActions(CurrentPage currentPage, Input input, Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    public void readActions() {
        OutputCommands outputCommands = new OutputCommands();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                if (action.getPage().equals("login")) {
                    executeAction(currentPage, "login", outputCommands);
                }
                if (action.getPage().equals("register")) {
                    executeAction(currentPage, "register", outputCommands);
                }
                if (action.getPage().equals("logout")) {
                    if (currentPage.getCurrentUser() != null) {
                        currentPage.setPageName("neautentificat");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        currentPage.setCurrentUser(null);
                    } else {
                        outputCommands.setError("Error");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        outputCommands.setCurrentUser(null);
                        output.getOutput().add(new OutputCommands(outputCommands));
                    }
                }
            }
            if (action.getType().equals("on page")) {
                if (action.getFeature().equals("login")) {
                    if (currentPage.getPageName().equals("login") && currentPage.getCurrentUser() == null) {
                        if (checkUser(action.getCredentials()) != null) {
                            currentPage.setCurrentUser(checkUser(action.getCredentials()));
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(checkUser(action.getCredentials()));
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else {
                            outputCommands.setError("Error");
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(null);
                            currentPage.setPageName("neautentificat");
                            output.getOutput().add(new OutputCommands(outputCommands));
                        }
                    } else {
                        outputCommands.setError("Error");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        outputCommands.setCurrentUser(null);
                        currentPage.setPageName("neautentificat");
                        output.getOutput().add(new OutputCommands(outputCommands));
                    }
                }
                if (action.getFeature().equals("register")) {
                    if (currentPage.getPageName().equals("register") && currentPage.getCurrentUser() == null) {
                        if (checkUser(action.getCredentials()) == null) {
                            User newUser = new User(action.getCredentials());
                            currentPage.setCurrentUser(newUser);
                            input.getUsers().add(newUser);
                            outputCommands.setError(null);
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(newUser);
                            output.getOutput().add(new OutputCommands(outputCommands));
                        } else {
                            outputCommands.setError("Error");
                            outputCommands.setCurrentMoviesList(new ArrayList<>());
                            outputCommands.setCurrentUser(null);
                            currentPage.setPageName("neautentificat");
                            output.getOutput().add(new OutputCommands(outputCommands));
                        }
                    } else {
                        outputCommands.setError("Error");
                        outputCommands.setCurrentMoviesList(new ArrayList<>());
                        outputCommands.setCurrentUser(null);
                        currentPage.setPageName("neautentificat");
                        output.getOutput().add(new OutputCommands(outputCommands));
                    }
                }
            }

        }

    }
    private User checkUser(Credentials credentials) {
        for (User user: input.getUsers()) {
            if (user.getCredentials().equals(credentials)) {
                return new User(user);
            }
        }
        return null;
    }

    private void executeAction(CurrentPage currentPage, String pageName, OutputCommands outputCommands) {
        if (currentPage.getPageName().equals("neautentificat") && currentPage.getCurrentUser() == null) {
            currentPage.setPageName(pageName);
            currentPage.setCurrentMoviesList(null);
        } else {
            outputCommands.setError("Error");
            outputCommands.setCurrentMoviesList(new ArrayList<>());
            outputCommands.setCurrentUser(null);
            output.getOutput().add(new OutputCommands(outputCommands));
        }
    }
}

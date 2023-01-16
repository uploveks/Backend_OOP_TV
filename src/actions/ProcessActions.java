package actions;

import actions.back_command.BackActions;
import actions.change_page_state.ChangePageActions;
import inputdata.Action;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;
import recommendation.Recommendation;

import java.util.ArrayList;
import java.util.Stack;

public final class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;
    private Stack<CurrentPage> pagesStack;
    private static ProcessActions instance = new ProcessActions();

    private ProcessActions() {
    }
    public static ProcessActions getInstance(){
        return instance;
    }

    public CurrentPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(final Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(final Output output) {
        this.output = output;
    }

    public Stack<CurrentPage> getPagesStack() {
        return pagesStack;
    }

    public void setPagesStack(final Stack<CurrentPage> pagesStack) {
        this.pagesStack = pagesStack;
    }

    /**
     * Process all the actions from change page and from on page.
     */
    public void readActions() {
        currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        OutputCommands outputCommands = new OutputCommands.Builder().build();
        BuyActions buyActions = BuyActions.getInstance();
        ErrorOutput errorOutput = ErrorOutput.getInstance();
        UserActions userActions =  UserActions.getInstance();
        ChangePageActions changePageActions = new ChangePageActions();
        OnPageActions onPageActions = new OnPageActions();
        BackActions backActions = new BackActions();
        DatabaseActions databaseActions = new DatabaseActions();
        MovieActions movieActions = MovieActions.getInstance();
        FilterActions filterActions = FilterActions.getInstance();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(currentPage, input, output, action.getPage(),
                        action.getMovie(), userActions, errorOutput);
            } else if (action.getType().equals("on page")) {
                onPageActions.onPage(currentPage, input, output, action, outputCommands,
                        errorOutput, buyActions, userActions, movieActions, filterActions);
            } else if (action.getType().equals("back")) {
                backActions.goBack(pagesStack, currentPage, input, output, userActions,
                        errorOutput);
            } else if (action.getType().equals("database")) {
                if (action.getFeature().equals("add")) {
                    databaseActions.databaseAdd(input, output, action, errorOutput);
                }
                if (action.getFeature().equals("delete")) {
                    databaseActions.databaseDelete(input, output, action, errorOutput);
                }
            }
        }

        if (currentPage.getCurrentUser() != null) {
            new Recommendation().recommendMovie(currentPage, input, output, errorOutput);
        }
    }

}

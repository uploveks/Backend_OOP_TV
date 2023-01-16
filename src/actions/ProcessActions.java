package actions;

import actions.back_command.BackActions;
import filter.FilterByCountry;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;
import recommendation.Recommendation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;
    private Stack<CurrentPage> pagesStack;
    private static ProcessActions instance = new ProcessActions();

    private ProcessActions(){

    }
    public static ProcessActions getInstance(){
        return instance;
    }

    public CurrentPage getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(CurrentPage currentPage) {
        this.currentPage = currentPage;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public Stack<CurrentPage> getPagesStack() {
        return pagesStack;
    }

    public void setPagesStack(Stack<CurrentPage> pagesStack) {
        this.pagesStack = pagesStack;
    }

    /**
     * Process all the actions from change page and from on page.
     */
    public void readActions() {
        currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        OutputCommands outputCommands = new OutputCommands.Builder().build();
        BuyActions buyActions = BuyActions.getInstance();
        Error error = Error.getInstance();
        UserActions userActions =  UserActions.getInstance();
        ChangePageActions changePageActions = new ChangePageActions();
        OnPageActions onPageActions = new OnPageActions();
        BackActions backActions = new BackActions();
        DatabaseActions databaseActions = new DatabaseActions();
        MovieActions movieActions = MovieActions.getInstance();
        FilterActions filterActions = FilterActions.getInstance();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(currentPage, input, output, action.getPage(), action.getMovie(), userActions,
                        outputCommands, error);
            } else if (action.getType().equals("on page")) {
                onPageActions.onPage(currentPage, input, output, action, outputCommands, error,
                        buyActions, userActions, movieActions, filterActions);
            } else if (action.getType().equals("back")) {
                backActions.goBack(pagesStack,currentPage, input, output, userActions,
                        outputCommands, error);
            } else if (action.getType().equals("database")) {
                if (action.getFeature().equals("add")) {
                    databaseActions.databaseAdd(input, output, outputCommands, action, error);
                }
                if (action.getFeature().equals("delete")) {
                    databaseActions.databaseDelete(input, output, outputCommands, action, error);
                }
            }
        }

        if (currentPage.getCurrentUser() != null) {
            new Recommendation().recommendMovie(currentPage, input, output, error);
        }
    }

}

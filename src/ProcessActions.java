import inputdata.Action;
import inputdata.Input;
import inputdata.Movie;
import inputdata.User;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.ArrayList;

public class ProcessActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public ProcessActions(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }


    /**
     * Process all the actions from change page and from on page.
     */
    public void readActions() {
        currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        OutputCommands outputCommands = new OutputCommands();
        BuyActions buyActions = BuyActions.getInstance();
        Error error = Error.getInstance();
        UserActions userActions =  UserActions.getInstance();
        ChangePageActions changePageActions = new ChangePageActions();
        OnPageActions onPageActions = new OnPageActions();
        MovieActions movieActions = MovieActions.getInstance();
        FilterActions filterActions = FilterActions.getInstance();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(currentPage, input, output, action, userActions,
                        outputCommands, error);
            } else if (action.getType().equals("on page")) {
                onPageActions.onPage(currentPage, input, output, action, outputCommands, error,
                        buyActions, userActions, movieActions, filterActions);
            }

        }

    }

}

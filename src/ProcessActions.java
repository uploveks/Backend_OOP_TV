import filter.*;
import inputdata.*;
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
     *
     */
    public void readActions() {
        currentPage = new CurrentPage("neautentificat", new ArrayList<>(), null, null);
        input.getUsers().forEach(User::reinitializeTest);
        input.getMovies().forEach(Movie::reinitializeTest);
        OutputCommands outputCommands = new OutputCommands();
        BuyActions buyActions = new BuyActions(currentPage, input, output);
        Error error = new Error(currentPage, input, output);
        UserActions userActions = new UserActions(currentPage, input, output);
        ChangePageActions changePageActions = new ChangePageActions(currentPage, input, output);
        OnPageActions onPageActions = new OnPageActions(currentPage, input, output);
        MovieActions movieActions = new MovieActions(currentPage, input, output);
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(action, userActions, outputCommands, error);
            }else if (action.getType().equals("on page")) {
                onPageActions.onPage(action, outputCommands, error, buyActions, userActions,
                        movieActions);
            }

        }

    }

}

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
        BuyActions buyActions = BuyActions.getInstance();
        Error error = Error.getInstance();
        UserActions userActions =  UserActions.getInstance();
        ChangePageActions changePageActions = new ChangePageActions();
        OnPageActions onPageActions = new OnPageActions();
        MovieActions movieActions = MovieActions.getInstance();
        for (Action action: input.getActions()) {
            if (action.getType().equals("change page")) {
                changePageActions.changePage(currentPage, input, output, action, userActions, outputCommands, error);
            }else if (action.getType().equals("on page")) {
                onPageActions.onPage(currentPage, input, output, action, outputCommands, error, buyActions, userActions,
                        movieActions);
            }

        }

    }

}

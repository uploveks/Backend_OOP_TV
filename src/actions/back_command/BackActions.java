package actions.back_command;

import actions.ChangePageActions;
import actions.UserActions;
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
        CommandInvoker invoker = new CommandInvoker();
        Command command = new BackCommand(pagesStack, currentPage, input, output, userActions, outputCommands, error);
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}

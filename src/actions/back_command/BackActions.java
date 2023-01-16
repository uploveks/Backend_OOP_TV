package actions.back_command;

import actions.UserActions;
import inputdata.Input;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.Stack;

public class BackActions {
    public BackActions() {
    }

    /**
     * @param pagesStack
     * @param currentPage
     * @param input
     * @param output
     * @param userActions
     * @param errorOutput
     */
    public void goBack(final Stack<CurrentPage> pagesStack, final CurrentPage currentPage,
                       final Input input, final Output output, final UserActions userActions,
                       final ErrorOutput errorOutput) {
        CommandInvoker invoker = new CommandInvoker();
        Command command = new BackCommand(pagesStack, currentPage, input, output, userActions,
                errorOutput);
        invoker.setCommand(command);
        invoker.executeCommand();
    }
}

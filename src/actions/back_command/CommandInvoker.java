package actions.back_command;

public class CommandInvoker {
    private Command command;

    /**
     * @param command
     */
    public void setCommand(final Command command) {
        this.command = command;
    }

    /**
     *
     */
    public void executeCommand() {
        command.execute();
    }
}

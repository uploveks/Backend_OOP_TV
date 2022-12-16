import inputdata.Action;
import inputdata.Input;
import outputdata.Output;
import outputdata.OutputCommands;

public class BuyActions {
    private CurrentPage currentPage;
    private Input input;
    private Output output;

    public BuyActions(final CurrentPage currentPage, final Input input, final Output output) {
        this.currentPage = currentPage;
        this.input = input;
        this.output = output;
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void buyTokens(final Action action, final OutputCommands outputCommands,
                          final Error error) {
        if (currentPage.getPageName().equals("upgrades")) {
            if (Integer.parseInt(currentPage.getCurrentUser().getCredentials().getBalance())
                    >= Integer.parseInt(action.getCount())) {
                currentPage.getCurrentUser().setTokensCount(Integer.parseInt(action.getCount()));
                int previousBalance = Integer.parseInt(currentPage.getCurrentUser().
                        getCredentials().getBalance());
                int substractBalance = previousBalance
                        - Integer.parseInt(action.getCount());
                outputCommands.getCurrentUser().getCredentials().setBalance(currentPage.getCurrentUser().
                        getCredentials().getBalance());
                currentPage.getCurrentUser().getCredentials().setBalance(
                        String.valueOf(substractBalance));
            } else {
                error.setError(outputCommands);
            }
        } else {
            error.setError(outputCommands);
        }
    }

    /**
     * @param outputCommands
     * @param error
     */
    public void buyPremiumAccount(final OutputCommands outputCommands, final Error error) {
        if (currentPage.getPageName().equals("upgrades")) {
                        if (currentPage.getCurrentUser().getTokensCount() >= 10) {
                            currentPage.getCurrentUser().getCredentials().
                                    setAccountType("premium");
                            currentPage.getCurrentUser().setTokensCount(currentPage.
                                    getCurrentUser().getTokensCount() - 10);
                        } else {
                            error.setError(outputCommands);
                        }
                    } else {
                        error.setError(outputCommands);
                    }
    }
}

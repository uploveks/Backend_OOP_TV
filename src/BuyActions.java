import inputdata.Action;
import inputdata.Input;
import outputdata.Output;
import outputdata.OutputCommands;

public class BuyActions {
    private static BuyActions instance;

    private BuyActions() {
    }

    public static BuyActions getInstance() {
        if (instance == null) {
            instance = new BuyActions();
        }

        return instance;
    }

    /**
     * @param action
     * @param outputCommands
     * @param error
     */
    public void buyTokens(final CurrentPage currentPage, final Output output, final Action action, final OutputCommands outputCommands,
                          final Error error) {
        if (currentPage.getPageName().equals("upgrades")) {
            if (Integer.parseInt(currentPage.getCurrentUser().getCredentials().getBalance())
                    >= Integer.parseInt(action.getCount())) {
                currentPage.getCurrentUser().setTokensCount(Integer.parseInt(action.getCount()));
                int previousBalance = Integer.parseInt(currentPage.getCurrentUser().
                        getCredentials().getBalance());
                int subtractBalance = previousBalance
                        - Integer.parseInt(action.getCount());
                currentPage.getCurrentUser().getCredentials().setBalance(
                        String.valueOf(subtractBalance));
                outputCommands.getCurrentUser().getCredentials().setBalance(currentPage.getCurrentUser().
                        getCredentials().getBalance());
            } else {
                error.setError(output, outputCommands);
            }
        } else {
            error.setError(output, outputCommands);
        }
    }

    /**
     * @param outputCommands
     * @param error
     */
    public void buyPremiumAccount(final CurrentPage currentPage, final Output output, final OutputCommands outputCommands, final Error error) {
        if (currentPage.getPageName().equals("upgrades")) {
                        if (currentPage.getCurrentUser().getTokensCount() > 10) {
                            currentPage.getCurrentUser().getCredentials().
                                    setAccountType("premium");
                            currentPage.getCurrentUser().setTokensCount(currentPage.
                                    getCurrentUser().getTokensCount() - 10);
                        } else {
                            error.setError(output, outputCommands);
                        }
                    } else {
                        error.setError(output, outputCommands);
                    }
    }
}

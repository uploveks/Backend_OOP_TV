package actions;

import inputdata.Action;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;
import page.CurrentPage;
import utils.MagicNumbers;

public final class BuyActions {
    private static BuyActions instance;

    private BuyActions() {
    }

    /**
     * @return
     * Used Singleton pattern
     */
    public static BuyActions getInstance() {
        if (instance == null) {
            instance = new BuyActions();
        }

        return instance;
    }


    /**
     * @param currentPage Used to get information about current user and movies.
     * @param output
     * @param action Used to get commands from input
     * @param outputCommands
     * @param error Used to print output
     *  Method that implements feature "buy tokens" only if the current page
     *  if "upgrades" and if user has enough money on his balance to buy tokens.
     *  In currentPage I changed number of tokens for the user, then subtracted
     *  the price from his balance and set the new balance.
     */
    public void buyTokens(final CurrentPage currentPage, final Output output, final Action action,
                          final OutputCommands outputCommands, final Error error) {
        if (!currentPage.getPageName().equals("upgrades")) {
            error.setError(output, outputCommands);
            return;
        }
        if (!(Integer.parseInt(currentPage.getCurrentUser().getCredentials().getBalance())
                    >= Integer.parseInt(action.getCount()))) {
            error.setError(output, outputCommands);
            return;
        }
        currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().getTokensCount() +
                Integer.parseInt(action.getCount()));
        int previousBalance = Integer.parseInt(currentPage.getCurrentUser().
                getCredentials().getBalance());
        int subtractBalance = previousBalance - Integer.parseInt(action.getCount());
        currentPage.getCurrentUser().getCredentials().setBalance(String.valueOf(subtractBalance));
    }


    /**
     * @param currentPage
     * @param output
     * @param outputCommands
     * @param error
     * Method that implements feature "buy premium account" only if the current page
     * if "upgrades" and if user has enough tokens to buy premium account.
     * I set the current user's account type to premium and subtract 10 tokens from his
     * initial number of tokens.
     */
    public void buyPremiumAccount(final CurrentPage currentPage, final Output output,
                                  final OutputCommands outputCommands, final Error error) {
        if (!currentPage.getPageName().equals("upgrades")) {
            error.setError(output, outputCommands);
            return;
        }
        if (!(currentPage.getCurrentUser().getTokensCount() > MagicNumbers.PREMIUM_ACCOUNT_PRICE)) {
            error.setError(output, outputCommands);
            return;
        }
        currentPage.getCurrentUser().getCredentials().setAccountType("premium");
        currentPage.getCurrentUser().setTokensCount(currentPage.getCurrentUser().
                getTokensCount() - MagicNumbers.PREMIUM_ACCOUNT_PRICE);
    }
}

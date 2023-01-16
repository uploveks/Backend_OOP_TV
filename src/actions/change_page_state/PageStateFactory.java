package actions.change_page_state;

public final class PageStateFactory {
    private PageStateFactory() {
    }

    /**
     * @param pageName
     * @param movieName
     * @return
     */
    public static PageState createPageState(final String pageName, final String movieName) {
        return switch (pageName) {
            case "login" -> new LoginPageState();
            case "register" -> new RegisterPageState();
            case "logout" -> new LogoutPageState();
            case "movies" -> new MoviesPageState();
            case "see details" -> new SeeDetailsPageState(movieName);
            case "upgrades" -> new UpgradesPageState();
            default -> throw new IllegalArgumentException("Invalid page name: " + pageName);
        };
    }
}

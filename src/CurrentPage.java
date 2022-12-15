import inputdata.Movie;
import inputdata.User;

import java.util.List;

public final class CurrentPage {
    private String pageName;
    private List<Movie> currentMoviesList;
    private User currentUser;

    public CurrentPage(final String pageName, final List<Movie> currentMoviesList,
                       final User currentUser) {
        this.pageName = pageName;
        this.currentMoviesList = currentMoviesList;
        this.currentUser = currentUser;
    }

    public String getPageName() {

        return pageName;
    }

    public void setPageName(final String pageName) {

        this.pageName = pageName;
    }

    public List<Movie> getCurrentMoviesList() {

        return currentMoviesList;
    }

    public void setCurrentMoviesList(final List<Movie> currentMoviesList) {

        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {

        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {

        this.currentUser = currentUser;
    }
}

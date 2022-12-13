import inputData.Movie;
import inputData.User;

import java.util.List;

public final class CurrentPage {
    private String pageName;
    private List<Movie> currentMoviesList;
    private User currentUser;

    public CurrentPage(String pageName, List<Movie> currentMoviesList, User currentUser) {
        this.pageName = pageName;
        this.currentMoviesList = currentMoviesList;
        this.currentUser = currentUser;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<Movie> getCurrentMoviesList() {
        return currentMoviesList;
    }

    public void setCurrentMoviesList(List<Movie> currentMoviesList) {
        this.currentMoviesList = currentMoviesList;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}

import inputdata.Movie;
import inputdata.User;

import java.util.List;

public final class CurrentPage {
    private String pageName;
    private List<Movie> currentMoviesList;
    private User currentUser;
    private Movie seenMoviedetails;

    public CurrentPage(String pageName, List<Movie> currentMoviesList, User currentUser, Movie seenMoviedetails) {
        this.pageName = pageName;
        this.currentMoviesList = currentMoviesList;
        this.currentUser = currentUser;
        this.seenMoviedetails = seenMoviedetails;
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

    public Movie getSeenMoviedetails() {
        return seenMoviedetails;
    }

    public void setSeenMoviedetails(Movie seenMoviedetails) {
        this.seenMoviedetails = seenMoviedetails;
    }
}

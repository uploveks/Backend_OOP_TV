package inputdata;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = 15;
    private List<Movie> purchasedMovies = new ArrayList();
    private List<Movie> watchedMovies = new ArrayList();
    private List<Movie> likedMovies = new ArrayList();
    private List<Movie> ratedMovies = new ArrayList();

    public User() {
    }

    public User(final Credentials credentials) {
        this.credentials = credentials;
        this.tokensCount = 0;
        this.numFreePremiumMovies = 15;
        this.purchasedMovies = new ArrayList();
        this.watchedMovies = new ArrayList();
        this.likedMovies = new ArrayList();
        this.ratedMovies = new ArrayList();
    }

    public User(final Credentials credentials, final int tokensCount,
                final int numFreePremiumMovies, final List<Movie> purchasedMovies,
                final List<Movie> watchedMovies, final List<Movie> likedMovies,
                final List<Movie> ratedMovies) {
        this.credentials = credentials;
        this.tokensCount = tokensCount;
        this.numFreePremiumMovies = numFreePremiumMovies;
        this.purchasedMovies = purchasedMovies;
        this.watchedMovies = watchedMovies;
        this.likedMovies = likedMovies;
        this.ratedMovies = ratedMovies;
    }

    public User(final User user) {
        this.credentials = user.getCredentials();
        this.tokensCount = user.getTokensCount();
        this.numFreePremiumMovies = user.getNumFreePremiumMovies();
        this.purchasedMovies = user.getPurchasedMovies();
        this.watchedMovies = user.getWatchedMovies();
        this.likedMovies = user.getLikedMovies();
        this.ratedMovies = user.getRatedMovies();
    }

    /**
     * @return
     */
    public Credentials getCredentials() {

        return credentials;
    }

    /**
     * @param credentials
     */
    public void setCredentials(final Credentials credentials) {

        this.credentials = credentials;
    }

    /**
     * @return
     */
    public int getTokensCount() {

        return tokensCount;
    }

    /**
     * @param tokensCount
     */
    public void setTokensCount(final int tokensCount) {

        this.tokensCount = tokensCount;
    }

    /**
     * @return
     */
    public int getNumFreePremiumMovies() {

        return numFreePremiumMovies;
    }

    /**
     * @param numFreePremiumMovies
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {

        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * @return
     */
    public List<Movie> getPurchasedMovies() {

        return purchasedMovies;
    }

    /**
     * @param purchasedMovies
     */
    public void setPurchasedMovies(final List<Movie> purchasedMovies) {

        this.purchasedMovies = purchasedMovies;
    }

    /**
     * @return
     */
    public List<Movie> getWatchedMovies() {

        return watchedMovies;
    }

    /**
     * @param watchedMovies
     */
    public void setWatchedMovies(final List<Movie> watchedMovies) {

        this.watchedMovies = watchedMovies;
    }

    /**
     * @return
     */
    public List<Movie> getLikedMovies() {

        return likedMovies;
    }

    /**
     * @param likedMovies
     */
    public void setLikedMovies(final List<Movie> likedMovies) {

        this.likedMovies = likedMovies;
    }

    /**
     * @return
     */
    public List<Movie> getRatedMovies() {

        return ratedMovies;
    }

    /**
     * @param ratedMovies
     */
    public void setRatedMovies(final List<Movie> ratedMovies) {

        this.ratedMovies = ratedMovies;
    }
}

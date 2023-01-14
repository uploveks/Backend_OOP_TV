package inputdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import utils.MagicNumbers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class User {
    private Credentials credentials;
    private int tokensCount;
    private int numFreePremiumMovies = MagicNumbers.NUMBER_FREE_PREMIUM_MOVIES;
    private List<Movie> purchasedMovies = new ArrayList();
    private List<Movie> watchedMovies = new ArrayList();
    private List<Movie> likedMovies = new ArrayList();
    private List<Movie> ratedMovies = new ArrayList();
    private Queue<Notifications> notifications = new LinkedList<>();
    @JsonIgnore
    private List<String> subscribedGenres = new ArrayList<>();

    public User() {
    }

    public User(final Credentials credentials) {
        this.credentials = credentials;
        this.tokensCount = 0;
        this.numFreePremiumMovies = MagicNumbers.NUMBER_FREE_PREMIUM_MOVIES;
        this.purchasedMovies = new ArrayList();
        this.watchedMovies = new ArrayList();
        this.likedMovies = new ArrayList();
        this.ratedMovies = new ArrayList();
        this.notifications = new LinkedList<>();
    }

    public User(final Credentials credentials, final int tokensCount,
                final int numFreePremiumMovies, final List<Movie> purchasedMovies,
                final List<Movie> watchedMovies, final List<Movie> likedMovies,
                final List<Movie> ratedMovies, final Queue<Notifications> notifications,
                final List<String> subscribedGenres) {
        this.credentials = credentials;
        this.tokensCount = tokensCount;
        this.numFreePremiumMovies = numFreePremiumMovies;
        this.purchasedMovies = purchasedMovies;
        this.watchedMovies = watchedMovies;
        this.likedMovies = likedMovies;
        this.ratedMovies = ratedMovies;
        this.notifications = notifications;
        this.subscribedGenres = subscribedGenres;
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

    public Queue<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(final Queue<Notifications> notifications) {
        this.notifications = notifications;
    }

    public List<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public void setSubscribedGenres(final List<String> subscribedGenres) {
        this.subscribedGenres = subscribedGenres;
    }
}

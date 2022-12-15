package inputdata;

import java.util.List;

public final class Action {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private String startsWith;

    private Filter filters;
    private String objectType;
    private List<Movie> movies;
    private String count;
    private String movie;
    private Integer rate;

    public Action() {
    }

    public Action(final String type, final String page, final Credentials credentials,
                  final String feature, final String startsWith, final Filter filters,
                  final String objectType, final List<Movie> movies, final String count,
                  final String movie, final Integer rate) {
        this.type = type;
        this.page = page;
        this.credentials = credentials;
        this.feature = feature;
        this.startsWith = startsWith;
        this.filters = filters;
        this.objectType = objectType;
        this.movies = movies;
        this.count = count;
        this.movie = movie;
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(final Filter filters) {
        this.filters = filters;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final List<Movie> movies) {
        this.movies = movies;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(final Integer rate) {
        this.rate = rate;
    }
}

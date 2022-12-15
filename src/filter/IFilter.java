package filter;

import inputdata.Movie;

import java.util.List;

/**
 *
 */
public interface IFilter {
    /**
     * @param movies
     * @param feature
     * @return
     */
    List<Movie> filterMovies(List<Movie> movies, String feature);
}

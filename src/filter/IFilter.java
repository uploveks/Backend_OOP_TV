package filter;

import inputdata.Movie;

import java.util.List;

/**
 *
 */
public interface IFilter {
    /**
     * @param movies
     * @param features
     * @return
     */
    List<Movie> filterMovies(List<Movie> movies, List<String> features);
}

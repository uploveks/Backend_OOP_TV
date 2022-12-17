package filter;

import inputdata.Movie;
import inputdata.Sort;

import java.util.List;

public interface ISort {
    /**
     * @param movies
     * @param sort
     * @return
     */
    List<Movie> sortMovies(List<Movie> movies, Sort sort);
}

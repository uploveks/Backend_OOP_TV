package filter;

import inputdata.Movie;

import java.util.List;

public interface ISort {
    /**
     * @param movies
     * @param listOrder
     * @return
     */
    List<Movie> sortMovies(List<Movie> movies, String listOrder);
}

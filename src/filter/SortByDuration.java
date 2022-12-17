package filter;

import inputdata.Movie;
import inputdata.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByDuration implements ISort {
    /**
     * @param movies
     * @param sort
     * @return
     * Method that sorts movies only by duration using Comparator
     * increasing or decreasing and puts it in a new list and returns it.
     */
    public List<Movie> sortMovies(final List<Movie> movies, final Sort sort) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        if (sort.getDuration().equals("increasing")) {
            sortedMovies.sort(Comparator.comparing(Movie::getDuration));
        } else if (sort.getDuration().equals("decreasing")) {
            sortedMovies.sort(Comparator.comparing(Movie::getDuration).reversed());
        }
        return sortedMovies;
    }
}

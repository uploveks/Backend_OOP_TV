package filter;

import inputdata.Movie;
import inputdata.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByRatings implements ISort {
    /**
     * @param movies
     * @param sort
     * @return
     * Method that sorts movies only by rating using Comparator
     * increasing or decreasing and puts it in a new list and returns it.
     */
    @Override
    public List<Movie> sortMovies(final List<Movie> movies, final Sort sort) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        if (sort.getRating().equals("increasing")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating));
        } else if (sort.getRating().equals("decreasing")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating).reversed());
        }

        return sortedMovies;
    }
}

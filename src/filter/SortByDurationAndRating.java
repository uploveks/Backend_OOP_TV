package filter;

import inputdata.Movie;
import inputdata.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByDurationAndRating implements ISort  {
    /**
     * @param movies
     * @param sort
     * @return
     * Method that sorts movies by both, duration and rating. It sorts it firstly by
     * duration, then only if it's equal it sorts it by rating.
     */
    public List<Movie> sortMovies(final List<Movie> movies, final Sort sort) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        Comparator<Movie> comparator = Comparator.comparing(Movie::getDuration);
        if (sort.getDuration().equals("decreasing")) {
            comparator = Comparator.comparing(Movie::getDuration).reversed();
        }

        if (sort.getRating().equals("increasing")) {
            comparator = comparator.thenComparing(Movie::getRating);
        } else if (sort.getRating().equals("decreasing")) {
            comparator = comparator.thenComparing(Movie::getRating).reversed();
        }

        sortedMovies.sort(comparator);

        return sortedMovies;
    }
}

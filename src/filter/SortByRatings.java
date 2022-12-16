package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByRatings implements ISort {
    /**
     * @param movies
     * @param listOrder
     * @return
     */
    @Override
    public List<Movie> sortMovies(final List<Movie> movies, final String listOrder) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        if (listOrder.equals("ascending")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating));
        } else if (listOrder.equals("descending")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating).reversed());
        }
        return sortedMovies;
    }
}

package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByRatings implements ISort{
    @Override
    public List<Movie> sortMovies(List<Movie> movies, String listOrder) {
        List<Movie> sortedMovies = new ArrayList<>(movies);
        if (listOrder.equals("ascending")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating));
        } else if (listOrder.equals("descending")) {
            sortedMovies.sort(Comparator.comparing(Movie::getRating).reversed());
        }
        return sortedMovies;
    }
}
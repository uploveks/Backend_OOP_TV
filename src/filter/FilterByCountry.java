package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.List;

public class FilterByCountry implements IFilter{
    public List<Movie> filterMovies(final List<Movie> movies, final String feature) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(feature)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}


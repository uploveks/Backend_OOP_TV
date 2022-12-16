package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.List;

public class FilterByActors implements IFilter {
    @Override
    public List<Movie> filterMovies(List<Movie> movies, String feature) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getActors().contains(feature)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}


package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.List;

public class FilterByName implements IFilter {
    /**
     * @param movies
     * @param feature
     * @return
     */
    @Override
    public List<Movie> filterMovies(final List<Movie> movies, final String feature) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getName().startsWith(feature)) {
                filteredMovies.add(movie);
            }
        }
        return filteredMovies;
    }
}

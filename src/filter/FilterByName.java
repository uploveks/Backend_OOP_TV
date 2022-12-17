package filter;

import inputdata.Movie;

import java.util.ArrayList;
import java.util.List;

public class FilterByName implements IFilter {
    /**
     * @param movies
     * @param features
     * @return
     * Method that checks in the movie list given movies that start with the name
     * given and adds it to a list that is returned at the end.
     */
    @Override
    public List<Movie> filterMovies(final List<Movie> movies, final List<String> features) {
        List<Movie> filteredMovies = new ArrayList<>();
        for (Movie movie : movies) {
            for (String feature : features) {
                if (movie.getName().startsWith(feature)) {
                    filteredMovies.add(movie);
                    break;
                }
            }
        }
        return filteredMovies;
    }
}

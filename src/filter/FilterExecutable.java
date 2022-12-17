package filter;

import inputdata.Movie;

import java.util.List;

public final class FilterExecutable {
    private IFilter iFilter;

    public FilterExecutable(final IFilter iFilter) {

        this.iFilter = iFilter;
    }

    /**
     * @param movies
     * @param features
     * @return
     * Executes filters given in constructor.
     */
    public List<Movie> executeFilter(final List<Movie> movies, final List<String> features) {
        return iFilter.filterMovies(movies, features);
    }
}

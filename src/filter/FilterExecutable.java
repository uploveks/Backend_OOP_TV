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
     * @param feature
     * @return
     */
    public List<Movie> executeFilter(final List<Movie> movies, final String feature) {
        return iFilter.filterMovies(movies, feature);
    }
}

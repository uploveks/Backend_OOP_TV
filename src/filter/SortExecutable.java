package filter;


import inputdata.Movie;
import inputdata.Sort;

import java.util.List;

public class SortExecutable {
    private final ISort iSort;

    public SortExecutable(final ISort iSort) {
        this.iSort = iSort;
    }

    /**
     * @param movies
     * @param sort
     * @return
     * Executes sort given in constructor.
     */
    public List<Movie> executeSort(final List<Movie> movies, final Sort sort) {
        return iSort.sortMovies(movies, sort);
    }
}

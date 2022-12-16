package filter;

import inputdata.Movie;

import java.util.List;

public class SortExecutable {
    private final ISort iSort;

    public SortExecutable(final ISort iSort) {

        this.iSort = iSort;
    }

    /**
     * @param movies
     * @param listOrder
     * @return
     */
    public List<Movie> executeSort(final List<Movie> movies, final String listOrder) {
        return iSort.sortMovies(movies, listOrder);
    }
}

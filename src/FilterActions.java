import filter.FilterByActors;
import filter.FilterByGenre;
import filter.FilterExecutable;
import filter.SortByDuration;
import filter.SortByDurationAndRating;
import filter.SortByRatings;
import filter.SortExecutable;
import inputdata.Action;

public final class FilterActions {
    private static FilterActions instance;

    private FilterActions() {
    }

    /**
     * @return
     * Singleton pattern
     */
    public static FilterActions getInstance() {
        if (instance == null) {
            instance = new FilterActions();
        }

        return instance;
    }

    /**
     * @param currentPage
     * @param action
     * Method that sorts movies by duration and rating.
     */
    public void sortMovies(final CurrentPage currentPage, final Action action) {
        if (action.getFilters().getSort().getDuration() != null
                && action.getFilters().getSort().getRating() != null) {
            SortExecutable sortExecutable =
                    new SortExecutable(new SortByDurationAndRating());
            var sortedList = sortExecutable.executeSort(
                    currentPage.getCurrentMoviesList(),
                    action.getFilters().getSort());
            currentPage.setCurrentMoviesList(sortedList);
        } else if (action.getFilters().getSort().getDuration() != null) {
            SortExecutable sortExecutableDuration =
                    new SortExecutable(new SortByDuration());
            var sortedListDuration = sortExecutableDuration.executeSort(
                    currentPage.getCurrentMoviesList(), action.getFilters().getSort());
            currentPage.setCurrentMoviesList(sortedListDuration);
        } else if (action.getFilters().getSort().getRating() != null) {
            SortExecutable sortExecutableRating =
                    new SortExecutable(new SortByRatings());
            var sortedListRating = sortExecutableRating.executeSort(
                    currentPage.getCurrentMoviesList(),
                    action.getFilters().getSort());
            currentPage.setCurrentMoviesList(sortedListRating);
        }
    }

    /**
     * @param currentPage
     * @param action
     * Method that checks if in movie list are movies with specific
     * actor or genre.
     */
    public void containsInMovies(final CurrentPage currentPage, final Action action) {
        if (action.getFilters().getContains().getActors() != null) {
            FilterExecutable filterExecutable =
                    new FilterExecutable(new FilterByActors());
            var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                    action.getFilters().getContains().getActors());
            currentPage.setCurrentMoviesList(filteredList);
        }
        if (action.getFilters().getContains().getGenre() != null) {
            FilterExecutable filterExecutable =
                    new FilterExecutable(new FilterByGenre());
            var filteredList = filterExecutable.executeFilter(currentPage.getCurrentMoviesList(),
                    action.getFilters().getContains().getGenre());
            currentPage.setCurrentMoviesList(filteredList);
        }
    }
}

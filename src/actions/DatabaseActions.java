package actions;

import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import inputdata.User;
import outputdata.ErrorOutput;
import outputdata.Output;

import java.util.Collections;

public class DatabaseActions {
    /**
     * @param input
     * @param output
     * @param action
     * @param errorOutput
     */
    public void databaseAdd(final Input input, final Output output,
                            final Action action, final ErrorOutput errorOutput) {
        FilterExecutable filterExecutable = new FilterExecutable(new FilterByName());
        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                Collections.singletonList(action.getAddedMovie().getName()));
        if (!filteredList.isEmpty()) {
            errorOutput.setError(output);
            return;
        }
        input.getMovies().add(action.getAddedMovie());
        for (User user: input.getUsers()) {
            if (!action.getAddedMovie().getCountriesBanned().contains(user.getCredentials()
                    .getCountry()) && user.getSubscribedGenres().stream().anyMatch(action
                    .getAddedMovie().getGenres()::contains)) {
                Notifications userNotification = new Notifications(action.getAddedMovie()
                        .getName(), "ADD");
                user.addObserver(user);
                user.notifyObservers(userNotification);
            }
        }
    }

    /**
     * @param input
     * @param output
     * @param action
     * @param errorOutput
     */
    public void databaseDelete(final Input input, final Output output, final Action action,
                            final ErrorOutput errorOutput) {

        FilterExecutable filterExecutable = new FilterExecutable(new FilterByName());
        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                Collections.singletonList(action.getDeletedMovie()));
        if (filteredList.isEmpty()) {
            errorOutput.setError(output);
            return;
        }
        Movie deletedMovie = filteredList.get(0);
        input.getMovies().remove(deletedMovie);
        for (User user: input.getUsers()) {
            if (user.getPurchasedMovies().contains(deletedMovie)) {
                if (user.getCredentials().getAccountType().equals("premium")) {
                    user.setNumFreePremiumMovies(user.getNumFreePremiumMovies() + 1);
                } else {
                    user.setTokensCount(user.getTokensCount() + 2);
                }
                Notifications userNotification = new Notifications(deletedMovie.getName(),
                        "DELETE");
                user.addObserver(user);
                user.notifyObservers(userNotification);
                user.getRatedMovies().remove(deletedMovie);
                user.getPurchasedMovies().remove(deletedMovie);
                user.getWatchedMovies().remove(deletedMovie);
                user.getLikedMovies().remove(deletedMovie);
            }
        }
    }
}

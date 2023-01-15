package actions;

import filter.FilterByName;
import filter.FilterExecutable;
import inputdata.Action;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import inputdata.User;
import outputdata.Error;
import outputdata.Output;
import outputdata.OutputCommands;

import java.util.Collections;

public class DatabaseActions {
    public void databaseAdd(final Input input, final Output output,
                            final OutputCommands outputCommands, final Action action,
                            final Error error) {
        FilterExecutable filterExecutable = new FilterExecutable(new FilterByName());
        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                Collections.singletonList(action.getAddedMovie().getName()));
        if (!filteredList.isEmpty()) {
            error.setError(output, outputCommands);
            return;
        }
        input.getMovies().add(action.getAddedMovie());
        for (User user: input.getUsers()) {
            if (!action.getAddedMovie().getCountriesBanned().contains(user.getCredentials().getCountry()) &&
                user.getSubscribedGenres().stream().anyMatch(action.getAddedMovie().getGenres()::contains)) {
                Notifications userNotification = new Notifications(action.getAddedMovie().getName(), "ADD");
                user.addObserver(user);
                user.notifyObservers(userNotification);
            }
        }
    }

    public void databaseDelete(final Input input, final Output output,
                            final OutputCommands outputCommands, final Action action,
                            final Error error) {

        FilterExecutable filterExecutable = new FilterExecutable(new FilterByName());
        var filteredList = filterExecutable.executeFilter(input.getMovies(),
                Collections.singletonList(action.getDeletedMovie()));
        if (filteredList.isEmpty()) {
            error.setError(output, outputCommands);
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
                Notifications userNotification = new Notifications(deletedMovie.getName(), "DELETE");
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

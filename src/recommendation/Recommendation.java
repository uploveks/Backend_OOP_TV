package recommendation;

import filter.FilterByCountry;
import filter.FilterExecutable;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import outputdata.Error;
import outputdata.Output;
import page.CurrentPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommendation {
    public void recommendMovie(CurrentPage currentPage, Input input, Output output, Error error) {
        if (currentPage.getCurrentUser().getCredentials().getAccountType().equals("premium")) {
            if (currentPage.getCurrentUser().getLikedMovies().isEmpty()) {
                Notifications recommendation = new Notifications("No recommendation", "Recommendation");
                currentPage.getCurrentUser().getNotifications().add(recommendation);
                error.outputSuccess(output, null, currentPage.getCurrentUser());
            } else {
                Map<String, Integer> genreLikes = new HashMap<>();
                for (Movie movie : currentPage.getCurrentUser().getLikedMovies()) {
                    for (String genre : movie.getGenres()) {
                        int totalLikes = genreLikes.getOrDefault(genre, 0) + movie.getNumLikes();
                        genreLikes.put(genre, totalLikes);
                    }
                }
                List<String> genres = new ArrayList<>(genreLikes.keySet());
                genres.sort((genre1, genre2) -> {
                    if (genreLikes.get(genre1).equals(genreLikes.get(genre2))) {
                        return genre1.compareTo(genre2);
                    }
                    return genreLikes.get(genre2) - genreLikes.get(genre1);
                });
                List<Movie> notWatchedMovies = input.getMovies().stream().filter(movie -> !currentPage.getCurrentUser().getWatchedMovies().contains(movie)).toList();
                FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
                var filteredByCountry = filterExecutable.executeFilter(notWatchedMovies,
                        Collections.singletonList(currentPage.getCurrentUser().getCredentials().
                                getCountry()));
                Collections.sort(filteredByCountry, Comparator.comparingInt(Movie::getNumLikes));
                while (!genres.isEmpty()) {
                    String likedGenre = genres.get(0);
                    genres.remove(0);
                    List<Movie> likedGenreMovies = new ArrayList<>(filteredByCountry.stream().filter(movie -> movie.getGenres().contains(likedGenre)).toList());
                    likedGenreMovies.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());
                    if (!likedGenreMovies.isEmpty()) {
                        Movie recommendedMovie = likedGenreMovies.get(0);
                        Notifications recommendation = new Notifications(recommendedMovie.getName(), "Recommendation");
                        currentPage.getCurrentUser().getNotifications().add(recommendation);
                        error.outputSuccess(output, null, currentPage.getCurrentUser());
                        break;
                    }
                }


            }
        }
    }
}

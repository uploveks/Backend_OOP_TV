package recommendation;

import filter.FilterByCountry;
import filter.FilterExecutable;
import inputdata.Input;
import inputdata.Movie;
import inputdata.Notifications;
import outputdata.ErrorOutput;
import outputdata.Output;
import page.CurrentPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recommendation {
    /**
     * @param currentPage
     * @param input
     * @param output
     * @param errorOutput
     */
    public void recommendMovie(final CurrentPage currentPage, final Input input,
                               final Output output, final ErrorOutput errorOutput) {
        if (!isPremiumUser(currentPage)) {
            return;
        }
        if (userHasNoLikedMovies(currentPage)) {
            sendNoRecommendationNotification(currentPage, errorOutput, output);
            return;
        }
        Map<String, Integer> genreLikes = calculateGenreLikes(currentPage);
        List<String> sortedGenres = sortGenresByLikes(genreLikes);
        List<Movie> notWatchedMovies = getNotWatchedMovies(currentPage, input);
        List<Movie> filteredByCountry = filterMoviesByCountry(currentPage, notWatchedMovies);
        Movie recommendedMovie = getRecommendedMovie(filteredByCountry, sortedGenres);
        sendRecommendationNotification(currentPage, recommendedMovie, errorOutput, output);
    }

    /**
     * @param currentPage
     * @return
     */
    private boolean isPremiumUser(final CurrentPage currentPage) {
        return "premium".equals(currentPage.getCurrentUser().getCredentials().getAccountType());
    }

    /**
     * @param currentPage
     * @return
     */
    private boolean userHasNoLikedMovies(final CurrentPage currentPage) {
        return currentPage.getCurrentUser().getLikedMovies().isEmpty();
    }

    /**
     * @param currentPage
     * @param errorOutput
     * @param output
     */
    private void sendNoRecommendationNotification(final CurrentPage currentPage,
                                                  final ErrorOutput errorOutput,
                                                  final Output output) {
        Notifications recommendation = new Notifications("No recommendation",
                "Recommendation");
        currentPage.getCurrentUser().getNotifications().add(recommendation);
        errorOutput.outputSuccess(output, null, currentPage.getCurrentUser());
    }

    /**
     * @param currentPage
     * @return
     */
    private Map<String, Integer> calculateGenreLikes(final CurrentPage currentPage) {
        Map<String, Integer> genreLikes = new HashMap<>();
        for (Movie movie : currentPage.getCurrentUser().getLikedMovies()) {
            for (String genre : movie.getGenres()) {
                int totalLikes = genreLikes.getOrDefault(genre, 0) + movie.getNumLikes();
                genreLikes.put(genre, totalLikes);
            }
        }
        return genreLikes;
    }

    /**
     * @param genreLikes
     * @return
     */
    private List<String> sortGenresByLikes(final Map<String, Integer> genreLikes) {
        List<String> genres = new ArrayList<>(genreLikes.keySet());
        genres.sort((genre1, genre2) -> {
            if (genreLikes.get(genre1).equals(genreLikes.get(genre2))) {
                return genre1.compareTo(genre2);
            }
            return genreLikes.get(genre2) - genreLikes.get(genre1);
        });
        return genres;
    }

    /**
     * @param currentPage
     * @param input
     * @return
     */
    private List<Movie> getNotWatchedMovies(final CurrentPage currentPage, final Input input) {
        return input.getMovies().stream()
                .filter(movie -> !currentPage.getCurrentUser().getWatchedMovies().contains(movie))
                .toList();
    }

    /**
     * @param currentPage
     * @param notWatchedMovies
     * @return
     */
    private List<Movie> filterMoviesByCountry(final CurrentPage currentPage,
                                              final List<Movie> notWatchedMovies) {
        FilterExecutable filterExecutable = new FilterExecutable(new FilterByCountry());
        return filterExecutable.executeFilter(notWatchedMovies,
                Collections.singletonList(currentPage.getCurrentUser().getCredentials()
                        .getCountry()));
    }

    /**
     * @param filteredByCountry
     * @param sortedGenres
     * @return
     */
    private Movie getRecommendedMovie(final List<Movie> filteredByCountry,
                                      final List<String> sortedGenres) {
        while (!sortedGenres.isEmpty()) {
            String likedGenre = sortedGenres.get(0);
            sortedGenres.remove(0);
            List<Movie> likedGenreMovies = new ArrayList<>(filteredByCountry.stream()
                    .filter(movie -> movie.getGenres().contains(likedGenre)).toList());
            likedGenreMovies.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());
            if (!likedGenreMovies.isEmpty()) {
                return likedGenreMovies.get(0);
            }
        }
        return null;
    }

    /**
     * @param currentPage
     * @param recommendedMovie
     * @param errorOutput
     * @param output
     */
    private void sendRecommendationNotification(final CurrentPage currentPage, final Movie
            recommendedMovie, final ErrorOutput errorOutput, final Output output) {
        if (recommendedMovie == null) {
            sendNoRecommendationNotification(currentPage, errorOutput, output);
        } else {
            Notifications recommendation = new Notifications(recommendedMovie.getName(),
                    "Recommendation");
            currentPage.getCurrentUser().getNotifications().add(recommendation);
            errorOutput.outputSuccess(output, null, currentPage.getCurrentUser());
        }
    }
}

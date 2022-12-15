package filter;

import inputdata.Movie;

import java.util.List;

public interface ISort {
    List<Movie> sortMovies(List<Movie> movies, String listOrder);
}

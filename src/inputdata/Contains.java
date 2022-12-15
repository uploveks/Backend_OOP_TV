package inputdata;

import java.util.List;

public final class Contains {
    private List<String> actors;
    private List<String> genre;
    private List<String> country;

    public Contains() {
    }

    public Contains(final List<String> actors, final List<String> genre,
                    final List<String> country) {
        this.actors = actors;
        this.genre = genre;
        this.country = country;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(final List<String> actors) {
        this.actors = actors;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(final List<String> genre) {
        this.genre = genre;
    }

    public List<String> getCountry() {
        return country;
    }

    public void setCountry(final List<String> country) {
        this.country = country;
    }
}

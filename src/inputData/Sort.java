package inputData;

public final class Sort {
    private String rating;
    private String duration;

    public Sort() {
    }

    public Sort(final String rating, final String duration) {
        this.rating = rating;
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }
}

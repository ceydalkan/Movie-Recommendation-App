package movies;

public class ComparableMovie implements Comparable<ComparableMovie> {
    private final int score;
    private final Movie movie;

    public ComparableMovie(Movie movie, int score){
        this.score = score;
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getScore() {
        return score;
    }

    public int compareTo(ComparableMovie other) {
        return Integer.compare(other.score,this.score);
    }
}
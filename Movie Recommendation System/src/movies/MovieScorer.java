package movies;
import java.util.*;

public class MovieScorer {
    private final MovieDatabase database;
    private List<String> desiredGenres;
    private List<String> desiredActors;
    private String desiredDirector;
    private int startYear = 0;
    private int endYear = 0;
    private int maxDuration = 0;
    private Set<Movie> candidateSet = new HashSet<>();

    public MovieScorer(MovieDatabase database) {
        this.database = database;
    }

    public Set<Movie> getCandidateSet() {
        return candidateSet;
    }

    public void setDesiredGenre(List<String> desiredGenre) {
        this.desiredGenres = desiredGenre;
    }

    public void setDesiredActors(List<String> desiredActors) {
        this.desiredActors = desiredActors;
    }

    public void setDesiredDirector(String desiredDirector) {
        this.desiredDirector = desiredDirector;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public void addToSetByGenre() {
        if (desiredGenres != null) {
            for (String genre : desiredGenres) {
                genre = genre.trim().toLowerCase();
                if (database.getGenreToMovies().containsKey(genre)) {
                    candidateSet.addAll(database.getGenreToMovies().get(genre));
                }
            }
        }
    }

    public void addToSetByActors() {
        if (desiredActors != null) {
            for (String actor : desiredActors) {
                actor = actor.trim().toLowerCase();
                if (database.getActorsToMovies().containsKey(actor)) {
                    candidateSet.addAll(database.getActorsToMovies().get(actor));
                }
            }
        }
    }

    public void addToSetByDirector() {
        if (desiredDirector != null) {
            String director = desiredDirector.trim().toLowerCase();
            if (database.getDirectorToMovies().containsKey(director)) {
                candidateSet.addAll(database.getDirectorToMovies().get(director));
            }
        }
    }

    public PriorityQueue<ComparableMovie> scoreMovies() {
        PriorityQueue<ComparableMovie> queue = new PriorityQueue<>();

        for (Movie movie : candidateSet) {
            int score = 0;

            if (desiredGenres != null) {
                for (String genre : desiredGenres) {
                    genre = genre.trim().toLowerCase();
                    for (String movieGenre : movie.getGenres()) {
                        movieGenre = movieGenre.trim().toLowerCase();
                        if (movieGenre.equals(genre)) {
                            score += 3;
                        }

                    }
                }
            }

            if (desiredActors != null) {
                for (String actor : desiredActors) {
                    actor = actor.trim().toLowerCase();
                    for (String movieActor : movie.getActors()) {
                        movieActor = movieActor.trim().toLowerCase();
                        if (movieActor.equals(actor)) {
                            score += 2;
                        }
                    }
                }
            }

            if (desiredDirector != null) {
                String director = desiredDirector.trim().toLowerCase();
                String movieDirector = movie.getDirector().trim().toLowerCase();
                if (movieDirector.equals(director)) {
                    score += 2;
                }
            }

            if (startYear != 0 && endYear !=0) {
               int year = movie.getYear();
               if(year >= startYear && year <= endYear){
                   score += 1;
               }
            }

            if (maxDuration != 0) {
                if (movie.getRuntimeMinutes() <= maxDuration) {
                    score += 1;
                }
            }

            if (movie.getRating() > 7.5) {
                score += 1;
            }

            queue.offer(new ComparableMovie(movie, score));
        }
        return queue;
    }

    public MovieNode createDoubleLinkedList(PriorityQueue<ComparableMovie> queue){
        MovieNode head = null;
        MovieNode tail = null;

        int count = 0;

        while (!queue.isEmpty() && count<20) {
            ComparableMovie cm = queue.poll();
            MovieNode newNode = new MovieNode(cm);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.setNext(newNode);
                newNode.setPrev(tail);
                tail = newNode;
            }
            count++;
        }
        return head;
    }
}
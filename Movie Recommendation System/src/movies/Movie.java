package movies;
import java.util.*;

public class Movie {
        private String title;
        private List<String> genres;
        private String description;
        private String director;
        private List<String> actors;
        private Integer year;
        private Integer runtimeMinutes;
        private Double rating;
        private Integer votes;
        private Double revenueMillions;
        private Integer metascore;

        public Movie(){}

        public Movie(String title, List<String> genres, String description, String director, List<String> actors, Integer year, Integer runtimeMinutes, Double rating, Integer votes, Double revenueMillions, Integer metascore) {
            this.title = title;
            this.genres = genres;
            this.description = description;
            this.director = director;
            this.actors = actors;
            this.year = year;
            this.runtimeMinutes = runtimeMinutes;
            this.rating = rating;
            this.votes = votes;
            this.revenueMillions = revenueMillions;
            this.metascore = metascore;
        }

        public String getTitle() { return title; }
        public List<String> getGenres() { return genres; }
        public String getDescription() { return description; }
        public String getDirector() { return director; }
        public List<String> getActors() { return actors; }
        public Integer getYear() { return year; }
        public Integer getRuntimeMinutes() { return runtimeMinutes; }
        public Double getRating() { return rating; }
        public Integer getVotes() { return votes; }
        public Double getRevenueMillions() { return revenueMillions; }
        public Integer getMetascore() { return metascore; }

        public void showAllDetails() {
            System.out.println("Title: " + getTitle());
            System.out.println("Genre: " + getGenres());
            System.out.println("Description: " + getDescription());
            System.out.println("Director: " + getDirector());
            System.out.println("Actors: " + getActors());
            System.out.println("Year: " + getYear());
            System.out.println("Runtime: " + getRuntimeMinutes());
            System.out.println("Rating: " + getRating());
            System.out.println("Votes: " + getVotes());
            System.out.println("Revenue: " + getRevenueMillions());
            System.out.println("Metascore: " + getMetascore());
        }

        public String AlltoHtml() {
            return String.format(
                    "<html><b>Description:</b> %s<br><b>Genres:</b> %s<br><b>Director:</b> %s<br>" +
                            "<b>Actors:</b> %s<br><b>Year:</b> %d<br><b>Runtime:</b> %d min<br><b>Rating:</b> %.1f<br>" +
                            "<b>Votes:</b> %,d<br><b>Revenue:</b> $%.2fM<br><b>Metascore:</b> %d</html>",
                    getDescription(),
                    String.join(", ", getGenres()),
                    getDirector(),
                    String.join(", ", getActors()),
                    getYear(),
                    getRuntimeMinutes(),
                    getRating(),
                    getVotes(),
                    getRevenueMillions(),
                    getMetascore()
            );
        }

        public String LimitedToHtml() {
            return String.format(
                    "<html><b>Description:</b> %s<br><b>Genres:</b> %s<br><b>Director:</b> %s<br>" +
                            "<b>Actors:</b> %s<br><b>Rating:</b> %.1f</html>",
                    getDescription(),
                    String.join(", ", getGenres()),
                    getDirector(),
                    String.join(", ", getActors()),
                    getRating()
            );
        }
}
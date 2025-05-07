package movies;
import com.opencsv.CSVReader;
import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MovieDatabase {
    private final List<Movie> movies = new ArrayList<Movie>();
    private final HashMap<String,List<Movie>> genreToMovies = new HashMap<>();
    private final HashMap<String,List<Movie>>  actorsToMovies = new HashMap<>();
    private final HashMap<String,List<Movie>>  directorToMovies = new HashMap<>();

    public List<Movie> getMovies() {
        return movies;
    }

    public HashMap<String, List<Movie>> getGenreToMovies() {
        return genreToMovies;
    }

    public HashMap<String,List<Movie>> getActorsToMovies() {
        return actorsToMovies;
    }

    public HashMap<String, List<Movie>> getDirectorToMovies() {
        return directorToMovies;
    }

    public void loadFromCsv(String filename) {
        try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            String[] parts = reader.readNext();
            parts = reader.readNext();

            while (parts != null) {
                String title = parts[1];
                List<String> genres = List.of(parts[2].split(","));
                String description = parts[3];
                String director = parts[4];
                List<String> actors = List.of(parts[5].split(","));
                Integer year = Integer.parseInt(parts[6]);
                Integer runtimeMinutes = Integer.parseInt(parts[7]);
                Double rating = Double.parseDouble(parts[8]);
                Integer votes = Integer.parseInt(parts[9]);
                Double revenueMillions = parts[10].isEmpty() ? null : Double.parseDouble(parts[10]);
                Integer metascore = parts[11].isEmpty() ? null : Integer.parseInt(parts[11]);

                Movie movie = new Movie(title,genres,description, director, actors, year, runtimeMinutes, rating, votes, revenueMillions, metascore);
                movies.add(movie);
                addMovieToGenreMap(movie);
                addMovieToActorMap(movie);
                addMovieToDirectorMap(movie);
                parts = reader.readNext();
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void saveMoviesToJson(String filename) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(filename);
            gson.toJson(movies, writer);
            writer.close();
            System.out.println("Movies saved to JSON successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMovieToGenreMap(Movie movie) {
        for (String genre : movie.getGenres()) {
            genre = genre.trim().toLowerCase();
            genreToMovies.putIfAbsent(genre, new ArrayList<>());
            genreToMovies.get(genre).add(movie);
        }
    }

    private void addMovieToActorMap(Movie movie) {
        for (String actor : movie.getActors()) {
            actor = actor.trim().toLowerCase();
            actorsToMovies.putIfAbsent(actor, new ArrayList<>());
            actorsToMovies.get(actor).add(movie);
        }
    }

    private void addMovieToDirectorMap(Movie movie) {
        String director = movie.getDirector().trim().toLowerCase();
        directorToMovies.putIfAbsent(director, new ArrayList<>());
        directorToMovies.get(director).add(movie);
    }
}
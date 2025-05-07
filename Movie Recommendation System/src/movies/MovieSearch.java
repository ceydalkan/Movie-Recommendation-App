package movies;
import java.util.*;
import java.util.regex.Pattern;

public class MovieSearch {
    private final MovieDatabase database;

    public MovieSearch(MovieDatabase database) {
        this.database = database;
    }

    public PriorityQueue<ComparableMovie> searchByTitle(String input, int limit) {
        if (input == null || input.isBlank()) {
            return new PriorityQueue<>();
        }

        PriorityQueue<ComparableMovie> result = new PriorityQueue<>();
        int count = 0;

        input = input.toLowerCase(Locale.ROOT).trim();
        String[] searchTerms = input.split("\\s+");

        for (Movie movie : database.getMovies()) {
            String title = movie.getTitle().toLowerCase(Locale.ROOT);
            int score = 0;

            if (title.equals(input)) {
                score += 5;
            }

            for (String term : searchTerms) {
                boolean termMatched = false;
                String[] titleWords = title.split("\\s+");

                for (int i = 0; i < titleWords.length; i++) {
                    String word = titleWords[i];

                    if (word.equals(term)) {
                        int positionBonus = Math.max(2, 4 - i);
                        score += 4 + positionBonus;
                        termMatched = true;
                        break;
                    }

                    if (word.startsWith(term)) {
                        int positionBonus = Math.max(1, 3 - i);
                        int lengthPenalty = word.length() - term.length();
                        score += Math.max(2, 3 - lengthPenalty / 2) + positionBonus;
                        termMatched = true;
                        break;
                    }

                    if (word.contains(term)) {
                        int positionBonus = Math.max(0, 1 - i/3);
                        score += 1 + positionBonus;
                        termMatched = true;
                        break;
                    }
                }

                if (!termMatched) {
                    if (title.startsWith(term)) {
                        score += 4;
                    } else if (title.matches(".*\\b" + Pattern.quote(term) + "\\b.*")) {
                        score += 3;
                    } else if (title.contains(term)) {
                        score += 1;
                    }
                }
            }

            if (score > 0) {
                result.add(new ComparableMovie(movie, score));
                count++;
                if (count >= limit) break;
            }
        }
        return result;
    }

    public MovieNode createDoubleLinkedList(PriorityQueue<ComparableMovie> result, int limit) {
        MovieNode head = null;
        MovieNode tail = null;
        int count = 0;

        while (!result.isEmpty() && count < limit) {
            ComparableMovie cm = result.poll();
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
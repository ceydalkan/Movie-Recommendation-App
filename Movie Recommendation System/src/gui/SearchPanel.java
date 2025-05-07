package gui;
import movies.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class SearchPanel {
    private static final Color BABY_BLUE = new Color(240, 248, 255);
    private static final Font BASE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 12);

    private final JPanel panel;
    private final JTextField searchField;

    public SearchPanel(MovieDatabase database, Consumer<ResultsPanel> onSearchComplete) {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(BABY_BLUE);

        JPanel center = new JPanel();
        center.setBackground(BABY_BLUE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("Search Movies");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(titleLabel);
        center.add(Box.createVerticalStrut(20));

        JLabel usageLabel = new JLabel("Enter movie name and click search button");
        usageLabel.setFont(HINT_FONT);
        usageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(usageLabel);
        center.add(Box.createVerticalStrut(20));

        searchField = new JTextField();
        searchField.setFont(BASE_FONT);
        searchField.setHorizontalAlignment(JTextField.CENTER);
        searchField.setPreferredSize(new Dimension(500, 50));
        searchField.setMaximumSize(new Dimension(500, 50));
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(searchField);
        center.add(Box.createVerticalStrut(20));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 20));
        searchButton.setPreferredSize(new Dimension(80, 40));
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(searchButton);
        center.add(Box.createVerticalGlue());

        searchButton.addActionListener(e -> performSearch(database, onSearchComplete));
        searchField.addActionListener(e -> searchButton.doClick());

        panel.add(center, BorderLayout.CENTER);
    }

    private void performSearch(MovieDatabase database, Consumer<ResultsPanel> onSearchComplete) {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a movie title.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        MovieSearch searcher = new MovieSearch(database);
        PriorityQueue<ComparableMovie> results = searcher.searchByTitle(query, 10);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "No movies found.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        MovieScorer dummyScorer = new MovieScorer(database); // only for linked list creation
        ResultsPanel resultsPanel = new ResultsPanel(results, dummyScorer);
        onSearchComplete.accept(resultsPanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
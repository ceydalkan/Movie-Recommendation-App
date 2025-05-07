package gui;
import javax.swing.*;
import movies.*;
import java.awt.*;
import java.util.PriorityQueue;

public class MovieRecommenderGUI extends JFrame {
    private final MovieDatabase database;
    private final JPanel rightPanel;
    private final SidePanel sidePanel;

    public MovieRecommenderGUI() {
        super("Movie Recommender");

        database = new MovieDatabase();
        database.loadFromCsv("src/IMDB-Movie-Data.csv");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sidePanel = new SidePanel();
        JPanel leftPanel = sidePanel.getPanel();
        add(leftPanel, BorderLayout.WEST);

        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(240, 248, 255));
        add(rightPanel, BorderLayout.CENTER);

        showWelcomePanel();

        sidePanel.getRecommendButton().addActionListener(e -> showRecommendStep1());
        sidePanel.getSearchButton().addActionListener(e -> showSearchPanel());
    }

    private void switchPanel(JPanel newPanel) {
        rightPanel.removeAll();
        rightPanel.add(newPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void showWelcomePanel() {
        WelcomePanel wp = new WelcomePanel();
        switchPanel(wp.getPanel());
    }

    private void showRecommendStep1() {
        MovieScorer scorer = new MovieScorer(database);
        RecommendPanelStep1 step1 = new RecommendPanelStep1(scorer, () -> showRecommendStep2(scorer));
        switchPanel(step1.getPanel());
    }
    private void showRecommendStep2(MovieScorer scorer) {
        RecommendPanelStep2 step2 = new RecommendPanelStep2(
                scorer,
                () -> showRecommendStep1(),
                queue -> showResultsPanel(queue, scorer)
        );
        switchPanel(step2.getPanel());
    }

    private void showResultsPanel(PriorityQueue<ComparableMovie> queue, MovieScorer scorer) {
        if (queue == null || queue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No movies found for your criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            showRecommendStep1();
            return;
        }

        ResultsPanel resultsPanel = new ResultsPanel(queue, scorer);
        switchPanel(resultsPanel.getPanel());
    }
    private void showSearchPanel() {
        SearchPanel searchPanel = new SearchPanel(database, resultsPanel -> switchPanel(resultsPanel.getPanel()));
        switchPanel(searchPanel.getPanel());
    }
}
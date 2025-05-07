package gui;
import movies.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.PriorityQueue;

public class ResultsPanel {
    private final JPanel panel;
    private final JLabel movieLabel;
    private final JLabel detailsLabel;
    private final JButton prevButton, nextButton, detailButton;

    private static final Color BABY_BLUE = new Color(240, 248, 255);

    private MovieNode current;
    private boolean detailsVisible = false;

    public ResultsPanel(PriorityQueue<ComparableMovie> queue, MovieScorer scorer) {
        current = scorer.createDoubleLinkedList(queue);

        panel = new JPanel(new BorderLayout());
        panel.setBackground(BABY_BLUE);

        movieLabel = new JLabel();
        movieLabel.setFont(new Font("Arial", Font.BOLD, 28));
        movieLabel.setHorizontalAlignment(SwingConstants.CENTER);

        detailsLabel = new JLabel();
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        detailButton = new JButton("Detail");
        detailButton.setPreferredSize(new Dimension(100, 30));
        detailButton.setFont(new Font("Arial", Font.BOLD, 14));
        detailButton.addActionListener(e -> {
            detailsVisible = !detailsVisible;
            updateMovieDisplay();
        });

        prevButton = new JButton("<");
        nextButton = new JButton(">");
        prevButton.setPreferredSize(new Dimension(60, 35));
        nextButton.setPreferredSize(new Dimension(60, 35));
        prevButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));

        prevButton.addActionListener(e -> {
            if (current != null && current.getPrev() != null) {
                current = current.getPrev();
                updateMovieDisplay();
            }
        });

        nextButton.addActionListener(e -> {
            if (current != null && current.getNext() != null) {
                current = current.getNext();
                updateMovieDisplay();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BABY_BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(movieLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(detailsLabel);

        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.setBackground(BABY_BLUE);
        navPanel.add(prevButton);
        navPanel.add(detailButton);
        navPanel.add(nextButton);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(navPanel, BorderLayout.SOUTH);

        updateMovieDisplay();
    }

    private void updateMovieDisplay() {
        if (current == null || current.getData() == null) {
            return;
        }

        Movie movie = current.getData().getMovie();
        movieLabel.setText(movie.getTitle());
        detailsLabel.setText(detailsVisible ? movie.AlltoHtml() : movie.LimitedToHtml());
        detailButton.setText(detailsVisible ? "Summary" : "Detail");
    }

    public JPanel getPanel() {
        return panel;
    }
}
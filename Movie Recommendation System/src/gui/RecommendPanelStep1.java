package gui;
import movies.MovieScorer;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RecommendPanelStep1 {
    private final Color BABY_BLUE = new Color(240, 248, 255);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 22);
    private static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 12);

    private final JPanel panel;
    private final JTextField genreField, actorField, directorField;

    private final MovieScorer scorer;
    private final Runnable onNext;

    public RecommendPanelStep1(MovieScorer scorer,Runnable onNext){
        this.scorer = scorer;
        this.onNext = onNext;

        panel=new JPanel(new BorderLayout());
        panel.setBackground(BABY_BLUE);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBackground(BABY_BLUE);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel titleLabel= new JLabel("Enter Criteria For Movie Recommendation");
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.add(titleLabel);
        fieldsPanel.add(Box.createVerticalStrut(30));

        genreField = new JTextField(20);
        fieldsPanel.add(createInputFieldWithHint("Genres:", genreField, "Example: Drama, Comedy, Action"));
        fieldsPanel.add(Box.createVerticalStrut(30));

        actorField = new JTextField(20);
        fieldsPanel.add(createInputFieldWithHint("Actors:", actorField, "Example: Tom Hanks, Scarlett Johansson"));
        fieldsPanel.add(Box.createVerticalStrut(30));

        directorField = new JTextField(20);
        fieldsPanel.add(createInputFieldWithHint("Director:", directorField, "Example: Christopher Nolan"));

        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 30));
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));

        nextButton.addActionListener(e -> {
            String genreText = genreField.getText().trim().toLowerCase();
            String actorText = actorField.getText().trim().toLowerCase();
            String directorText = directorField.getText().trim().toLowerCase();

            if (genreText.isEmpty() && actorText.isEmpty() && directorText.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter at least one field.", "Input Required", JOptionPane.WARNING_MESSAGE);
            } else {
                if (!genreText.isEmpty())
                    scorer.setDesiredGenre(Arrays.asList(genreText.split(",")));

                if (!actorText.isEmpty())
                    scorer.setDesiredActors(Arrays.asList(actorText.split(",")));

                if (!directorText.isEmpty())
                    scorer.setDesiredDirector(directorText);

                scorer.addToSetByGenre();
                scorer.addToSetByActors();
                scorer.addToSetByDirector();

                if (scorer.getCandidateSet().isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No movies matched your criteria.", "No Results", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    onNext.run();
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BABY_BLUE);
        bottomPanel.add(nextButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createInputFieldWithHint(String label, JTextField field, String hintText){
        JPanel wrapper = new JPanel();
        wrapper.setBackground(BABY_BLUE);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(lbl);

        wrapper.add(Box.createVerticalStrut(10));
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setMaximumSize(new Dimension(400, 40));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(field);

        JLabel hint = new JLabel(hintText);
        hint.setFont(HINT_FONT);
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(hint);

        return wrapper;
    }
}
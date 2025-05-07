package gui;
import movies.ComparableMovie;
import movies.MovieScorer;
import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.stream.IntStream;
import java.util.function.Consumer;

public class RecommendPanelStep2 {
    private static final Color BABY_BLUE = new Color(240, 248, 255);
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 22);
    private static final Font HINT_FONT = new Font("Arial", Font.ITALIC, 12);

    private final JPanel panel;
    private final JComboBox<Integer> startYearBox, endYearBox;
    private final JSlider runtimeSlider;
    private final JCheckBox allTimeCheckBox;

    private final MovieScorer scorer;
    private final Runnable onBack;

    private final Consumer<PriorityQueue<ComparableMovie>> onShowResults;

    public RecommendPanelStep2(MovieScorer scorer, Runnable onBack, Consumer<PriorityQueue<ComparableMovie>> onShowResults) {
        this.scorer = scorer;
        this.onBack = onBack;
        this.onShowResults = onShowResults;

        panel = new JPanel(new BorderLayout());
        panel.setBackground(BABY_BLUE);

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setBackground(BABY_BLUE);
        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JLabel titleLabel = new JLabel("Additional Criteria");
        titleLabel.setFont(LABEL_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        fieldsPanel.add(titleLabel);
        fieldsPanel.add(Box.createVerticalStrut(30));

        Vector<Integer> yearOptions = new Vector<>();
        IntStream.rangeClosed(2005, 2017).forEach(yearOptions::add);
        startYearBox = new JComboBox<>(yearOptions);
        endYearBox = new JComboBox<>(yearOptions);
        allTimeCheckBox = new JCheckBox("All Time");
        allTimeCheckBox.setBackground(BABY_BLUE);
        allTimeCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        allTimeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        allTimeCheckBox.addActionListener(e -> {
            boolean isSelected = allTimeCheckBox.isSelected();
            startYearBox.setEnabled(!isSelected);
            endYearBox.setEnabled(!isSelected);
        });

        fieldsPanel.add(createComboBoxGroupWithCheckbox("Start Year:", startYearBox, "End Year:", endYearBox, allTimeCheckBox));
        fieldsPanel.add(Box.createVerticalStrut(30));

        runtimeSlider = new JSlider(0, 200, 0);
        runtimeSlider.setMajorTickSpacing(30);
        runtimeSlider.setPaintLabels(true);
        runtimeSlider.setPaintTicks(true);
        runtimeSlider.setFont(new Font("Arial", Font.PLAIN, 12));

        fieldsPanel.add(createSliderWithHint("Duration (minutes):", runtimeSlider, "0 = All Durations, up to 200 minutes"));

        JButton showResultsButton = new JButton("Show Results");
        showResultsButton.setPreferredSize(new Dimension(120, 30));
        showResultsButton.setFont(new Font("Arial", Font.BOLD, 14));

        showResultsButton.addActionListener(e -> {
            if (allTimeCheckBox.isSelected()) {
                scorer.setStartYear(0);
                scorer.setEndYear(0);
            } else {
                Integer startValue = (Integer) startYearBox.getSelectedItem();
                Integer endValue = (Integer) endYearBox.getSelectedItem();

                int startYear = (startValue != null) ? startValue : 0;
                int endYear = (endValue != null) ? endValue : 0;

                scorer.setStartYear(startYear);
                scorer.setEndYear(endYear);
            }

            int maxDuration = runtimeSlider.getValue();
            scorer.setMaxDuration(maxDuration > 0 ? maxDuration : 0);

            PriorityQueue<ComparableMovie> queue = scorer.scoreMovies();
            onShowResults.accept(queue);
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> onBack.run());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BABY_BLUE);
        bottomPanel.add(backButton);
        bottomPanel.add(showResultsButton);

        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createComboBoxGroupWithCheckbox(String label1, JComboBox<Integer> box1, String label2, JComboBox<Integer> box2, JCheckBox allTimeBox) {
        JPanel wrapper = new JPanel();
        wrapper.setBackground(BABY_BLUE);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JLabel lbl1 = new JLabel(label1);
        lbl1.setFont(new Font("Arial", Font.BOLD, 16));
        lbl1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl2 = new JLabel(label2);
        lbl2.setFont(new Font("Arial", Font.BOLD, 16));
        lbl2.setAlignmentX(Component.LEFT_ALIGNMENT);

        box1.setMaximumSize(new Dimension(400, 40));
        box1.setFont(new Font("Arial", Font.PLAIN, 16));
        box1.setAlignmentX(Component.LEFT_ALIGNMENT);

        box2.setMaximumSize(new Dimension(400, 40));
        box2.setFont(new Font("Arial", Font.PLAIN, 16));
        box2.setAlignmentX(Component.LEFT_ALIGNMENT);

        allTimeBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        wrapper.add(lbl1);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(box1);
        wrapper.add(Box.createVerticalStrut(15));
        wrapper.add(lbl2);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(box2);
        wrapper.add(Box.createVerticalStrut(15));
        wrapper.add(allTimeBox);

        return wrapper;
    }

    private JPanel createSliderWithHint(String label, JSlider slider, String hintText) {
        JPanel wrapper = new JPanel();
        wrapper.setBackground(BABY_BLUE);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(lbl);

        wrapper.add(Box.createVerticalStrut(10));
        slider.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(slider);

        JLabel hint = new JLabel(hintText);
        hint.setFont(HINT_FONT);
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);
        wrapper.add(Box.createVerticalStrut(5));
        wrapper.add(hint);

        return wrapper;
    }
}
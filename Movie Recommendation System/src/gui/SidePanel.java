package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SidePanel {
    private final JPanel panel;
    private final JButton recommendButton;
    private final JButton searchButton;

    private static final Color DEFAULT_BLUE = new Color(0, 122, 204);
    private static final Color SELECTED_BLUE = new Color(0, 102, 170);
    private static final Color SELECTED_RED = new Color(153, 0, 0);
    private static final Font BASE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font HOVER_FONT = new Font("Arial", Font.BOLD, 20);

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getRecommendButton() {
        return recommendButton;
    }

    public SidePanel() {
        panel = new JPanel();
        panel.setBackground(DEFAULT_BLUE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, 600));

        String recText = "<html><div style='text-align:center'>Recommend<br>Movie</div></html>";
        recommendButton = createButton(recText);
        searchButton = createButton("Search");

        panel.add(Box.createVerticalGlue());
        panel.add(recommendButton);
        panel.add(Box.createVerticalGlue());
        panel.add(searchButton);
        panel.add(Box.createVerticalGlue());

        recommendButton.addActionListener(e -> {
            setPanelColor(SELECTED_BLUE);
        });

        searchButton.addActionListener(e -> {
            setPanelColor(SELECTED_RED);
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(DEFAULT_BLUE);
        button.setForeground(Color.WHITE);
        button.setFont(BASE_FONT);

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setFont(HOVER_FONT); }
            public void mouseExited(MouseEvent e)  { button.setFont(BASE_FONT); }
        });

        return button;
    }

    private void setPanelColor(Color color) {
        panel.setBackground(color);
        recommendButton.setBackground(color);
        searchButton.setBackground(color);
    }

    public JPanel getPanel() {
        return panel;
    }
}
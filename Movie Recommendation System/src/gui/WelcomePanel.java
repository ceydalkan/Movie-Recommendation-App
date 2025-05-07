package gui;
import javax.swing.*;
import java.awt.*;

public class WelcomePanel {
    private final Color BABY_BLUE = new Color(240, 248, 255);
    private final JPanel panel;

    public WelcomePanel(){
        panel = new JPanel();
        panel.setBackground(BABY_BLUE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        JLabel title = new JLabel("Welcome!");
        title.setFont(new Font("Arial",Font.BOLD,32));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));
        JLabel desc = new JLabel(
                "<html>Use '<b>Recommend Movie</b>' to get personalized movie suggestions<br>" +
                        "and '<b>Search</b>' to find a movie by its exact name.</html>"
        );

        desc.setFont(new Font("Arial", Font.PLAIN, 16));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(desc);
    }

    public JPanel getPanel(){
        return panel;
    }
}
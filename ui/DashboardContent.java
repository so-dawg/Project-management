package ui;

import logic.IUser;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class DashboardContent extends JPanel {
    private IUser currentUser;

    public DashboardContent(IUser currentUser) {
        this.currentUser = currentUser;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel headerLabel = new JLabel("Welcome back, " + currentUser.getFirstName() + "!");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerLabel.setForeground(new Color(45, 55, 75));

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(245, 245, 250));
        statsPanel.setBorder(new EmptyBorder(30, 0, 30, 0));

        statsPanel.add(createStatCard("Projects", "0", new Color(70, 130, 180)));
        statsPanel.add(createStatCard("Tasks", "0", new Color(100, 180, 100)));
        statsPanel.add(createStatCard("Pending", "0", new Color(255, 180, 80)));
        statsPanel.add(createStatCard("Completed", "0", new Color(180, 100, 180)));

        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel activityTitle = new JLabel("Recent Activity");
        activityTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        activityTitle.setForeground(new Color(45, 55, 75));
        activityTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel noActivity = new JLabel("No recent activity");
        noActivity.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        noActivity.setForeground(new Color(150, 150, 160));
        noActivity.setAlignmentX(Component.LEFT_ALIGNMENT);

        activityPanel.add(activityTitle);
        activityPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        activityPanel.add(noActivity);

        add(headerLabel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(activityPanel, BorderLayout.SOUTH);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
            new LineBorder(color, 2),
            new EmptyBorder(20, 15, 20, 15)
        ));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(100, 100, 120));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);

        return card;
    }
}

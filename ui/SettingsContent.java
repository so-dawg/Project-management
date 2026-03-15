package ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SettingsContent extends JPanel {

    public SettingsContent() {
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;

        JPanel appearancePanel = createSettingsSection("Appearance",
            new String[]{"Theme", "Font Size", "Language"});
        JPanel notificationPanel = createSettingsSection("Notifications",
            new String[]{"Email Notifications", "Desktop Alerts", "Sound"});
        JPanel securityPanel = createSettingsSection("Security",
            new String[]{"Change Password", "Two-Factor Auth", "Session Timeout"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        add(appearancePanel, gbc);

        gbc.gridx = 1;
        add(notificationPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(securityPanel, gbc);
    }

    private JPanel createSettingsSection(String title, String[] options) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setMaximumSize(new Dimension(400, 250));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(45, 55, 75));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        for (String option : options) {
            JCheckBox checkBox = new JCheckBox(option);
            checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            checkBox.setBackground(Color.WHITE);
            checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(checkBox);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return panel;
    }
}

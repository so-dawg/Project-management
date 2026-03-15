package ui;

import logic.IUser;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ProfileContent extends JPanel {
    private IUser currentUser;

    public ProfileContent(IUser currentUser) {
        this.currentUser = currentUser;
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(Color.WHITE);
        profileCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));
        profileCard.setMaximumSize(new Dimension(500, 400));

        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("Segoe UI", Font.PLAIN, 64));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(currentUser.getFirstName() + " " + currentUser.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(new Color(45, 55, 75));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel roleLabel = new JLabel(currentUser.getRole());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        roleLabel.setForeground(new Color(100, 100, 120));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        infoPanel.add(createInfoLabel("Email:"));
        infoPanel.add(createInfoLabel(currentUser.getEmail()));
        infoPanel.add(createInfoLabel("Username:"));
        infoPanel.add(createInfoLabel(currentUser.getUsername()));
        infoPanel.add(createInfoLabel("ID:"));
        infoPanel.add(createInfoLabel(currentUser.getId()));

        profileCard.add(avatarLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 15)));
        profileCard.add(nameLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 5)));
        profileCard.add(roleLabel);
        profileCard.add(infoPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(profileCard, gbc);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(100, 100, 120));
        return label;
    }
}

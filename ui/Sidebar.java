package ui;

import logic.IUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sidebar extends JPanel {
    private JButton dashboardBtn;
    private JButton projectsBtn;
    private JButton tasksBtn;
    private JButton profileBtn;
    private JButton settingsBtn;
    private JButton logoutBtn;

    private Runnable onDashboardClick;
    private Runnable onProjectsClick;
    private Runnable onTasksClick;
    private Runnable onProfileClick;
    private Runnable onSettingsClick;
    private Runnable onLogoutClick;

    public Sidebar(IUser currentUser) {
        initUI(currentUser);
    }

    private void initUI(IUser currentUser) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(45, 55, 75));
        setPreferredSize(new Dimension(220, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 55, 75));
        titlePanel.setMaximumSize(new Dimension(220, 80));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel("  Project Mgmt");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        JPanel userPanel = createUserPanel(currentUser);

        dashboardBtn = createSidebarButton("📊 Dashboard", true);
        projectsBtn = createSidebarButton("📁 Projects", false);
        tasksBtn = createSidebarButton("✓ Tasks", false);
        profileBtn = createSidebarButton("👤 Profile", false);
        settingsBtn = createSidebarButton("⚙️ Settings", false);

        add(titlePanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(userPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(dashboardBtn);
        add(projectsBtn);
        add(tasksBtn);
        add(profileBtn);
        add(settingsBtn);
        add(Box.createVerticalGlue());

        logoutBtn = createSidebarButton("🚪 Logout", false);
        logoutBtn.setForeground(new Color(255, 150, 150));
        add(logoutBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));

        setupActions();
    }

    private JPanel createUserPanel(IUser currentUser) {
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        userPanel.setBackground(new Color(55, 65, 85));
        userPanel.setMaximumSize(new Dimension(220, 60));
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI", Font.PLAIN, 24));

        JPanel userInfo = new JPanel();
        userInfo.setLayout(new BoxLayout(userInfo, BoxLayout.Y_AXIS));
        userInfo.setBackground(new Color(55, 65, 85));
        userInfo.setOpaque(true);

        JLabel userName = new JLabel(currentUser.getFirstName() + " " + currentUser.getLastName());
        userName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userName.setForeground(Color.WHITE);
        userName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userRole = new JLabel(currentUser.getRole());
        userRole.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        userRole.setForeground(new Color(180, 180, 200));
        userRole.setAlignmentX(Component.LEFT_ALIGNMENT);

        userInfo.add(userName);
        userInfo.add(Box.createRigidArea(new Dimension(0, 3)));
        userInfo.add(userRole);

        userPanel.add(userIcon);
        userPanel.add(userInfo);

        return userPanel;
    }

    private JButton createSidebarButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(200, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBorder(new EmptyBorder(0, 20, 0, 0));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (active) {
            button.setBackground(new Color(70, 85, 110));
            button.setForeground(Color.WHITE);
        } else {
            button.setBackground(new Color(45, 55, 75));
            button.setForeground(new Color(180, 180, 200));
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 75, 100));
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != dashboardBtn && button != projectsBtn &&
                    button != tasksBtn && button != profileBtn &&
                    button != settingsBtn) {
                    button.setBackground(new Color(45, 55, 75));
                    button.setForeground(new Color(180, 180, 200));
                }
            }
        });

        return button;
    }

    private void setupActions() {
        dashboardBtn.addActionListener(e -> {
            if (onDashboardClick != null) onDashboardClick.run();
        });
        projectsBtn.addActionListener(e -> {
            if (onProjectsClick != null) onProjectsClick.run();
        });
        tasksBtn.addActionListener(e -> {
            if (onTasksClick != null) onTasksClick.run();
        });
        profileBtn.addActionListener(e -> {
            if (onProfileClick != null) onProfileClick.run();
        });
        settingsBtn.addActionListener(e -> {
            if (onSettingsClick != null) onSettingsClick.run();
        });
        logoutBtn.addActionListener(e -> {
            if (onLogoutClick != null) onLogoutClick.run();
        });
    }

    public void setOnDashboardClick(Runnable action) {
        this.onDashboardClick = action;
    }

    public void setOnProjectsClick(Runnable action) {
        this.onProjectsClick = action;
    }

    public void setOnTasksClick(Runnable action) {
        this.onTasksClick = action;
    }

    public void setOnProfileClick(Runnable action) {
        this.onProfileClick = action;
    }

    public void setOnSettingsClick(Runnable action) {
        this.onSettingsClick = action;
    }

    public void setOnLogoutClick(Runnable action) {
        this.onLogoutClick = action;
    }

    public void setActiveButton(String buttonName) {
        resetButtons();
        switch (buttonName) {
            case "dashboard":
                dashboardBtn.setBackground(new Color(70, 85, 110));
                dashboardBtn.setForeground(Color.WHITE);
                break;
            case "projects":
                projectsBtn.setBackground(new Color(70, 85, 110));
                projectsBtn.setForeground(Color.WHITE);
                break;
            case "tasks":
                tasksBtn.setBackground(new Color(70, 85, 110));
                tasksBtn.setForeground(Color.WHITE);
                break;
            case "profile":
                profileBtn.setBackground(new Color(70, 85, 110));
                profileBtn.setForeground(Color.WHITE);
                break;
            case "settings":
                settingsBtn.setBackground(new Color(70, 85, 110));
                settingsBtn.setForeground(Color.WHITE);
                break;
        }
    }

    private void resetButtons() {
        dashboardBtn.setBackground(new Color(45, 55, 75));
        dashboardBtn.setForeground(new Color(180, 180, 200));
        projectsBtn.setBackground(new Color(45, 55, 75));
        projectsBtn.setForeground(new Color(180, 180, 200));
        tasksBtn.setBackground(new Color(45, 55, 75));
        tasksBtn.setForeground(new Color(180, 180, 200));
        profileBtn.setBackground(new Color(45, 55, 75));
        profileBtn.setForeground(new Color(180, 180, 200));
        settingsBtn.setBackground(new Color(45, 55, 75));
        settingsBtn.setForeground(new Color(180, 180, 200));
    }
}

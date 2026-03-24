package ui;

import logic.IUser;
import logic.UserRegistry;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private IUser currentUser;
    private UserRegistry userRegistry;
    private AppFrame appFrame;

    private Sidebar sidebar;
    private CardLayout cardLayout;
    private JPanel contentPanel;

    public DashboardPanel(AppFrame appFrame, IUser currentUser, UserRegistry userRegistry) {
        this.appFrame = appFrame;
        this.currentUser = currentUser;
        this.userRegistry = userRegistry;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        sidebar = new Sidebar(currentUser);
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new DashboardContent(currentUser), "dashboard");
        contentPanel.add(new ProjectsContent(currentUser), "projects");
        contentPanel.add(new TasksContent(currentUser), "tasks");
        contentPanel.add(new ProfileContent(currentUser), "profile");
        contentPanel.add(new SettingsContent(), "settings");

        setupSidebarActions();

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        cardLayout.show(contentPanel, "dashboard");
    }

    private void setupSidebarActions() {
        sidebar.setOnDashboardClick(() -> {
            try {
                cardLayout.show(contentPanel, "dashboard");
                sidebar.setActiveButton("dashboard");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading dashboard: " + e.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        sidebar.setOnProjectsClick(() -> {
            try {
                cardLayout.show(contentPanel, "projects");
                sidebar.setActiveButton("projects");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading projects: " + e.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        sidebar.setOnTasksClick(() -> {
            try {
                cardLayout.show(contentPanel, "tasks");
                sidebar.setActiveButton("tasks");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading tasks: " + e.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        sidebar.setOnProfileClick(() -> {
            try {
                cardLayout.show(contentPanel, "profile");
                sidebar.setActiveButton("profile");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading profile: " + e.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        sidebar.setOnSettingsClick(() -> {
            try {
                cardLayout.show(contentPanel, "settings");
                sidebar.setActiveButton("settings");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading settings: " + e.getMessage(),
                    "Navigation Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        sidebar.setOnLogoutClick(() -> {
            try {
                int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Confirm Logout",
                    JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    appFrame.showLogin();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error during logout: " + e.getMessage(),
                    "Logout Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

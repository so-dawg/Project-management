package ui;

import logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserRegistry userRegistry;

    public AppFrame() {
        setTitle("Project Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userRegistry = new UserRegistry();

        // Add default test users
        Owner admin = new Owner("Admin", "User", "admin", "admin@example.com", "Password1!");
        userRegistry.addUser(admin);

        Member member = new Member("John", "Doe", "john@example.com", "johndoe", "Password1!");
        userRegistry.addUser(member);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create login and dashboard panels
        LoginPanel loginPanel = new LoginPanel(this, userRegistry);
        mainPanel.add(loginPanel, "login");

        mainPanel.add(new JPanel(), "dashboard"); // Placeholder, will be replaced

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
    }

    public void showDashboard(IUser user) {
        DashboardPanel dashboardPanel = new DashboardPanel(this, user, userRegistry);
        mainPanel.add(dashboardPanel, "dashboard");
        cardLayout.show(mainPanel, "dashboard");
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "login");
    }
}

package ui;

import logic.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;

public class DashboardPanel extends JPanel {
    private IUser currentUser;
    private UserRegistry userRegistry;
    private AppFrame appFrame;
    private ProjectManager projectManager;

    // UI Components
    private JPanel mainPanel;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    // Sidebar buttons
    private JButton dashboardBtn;
    private JButton projectsBtn;
    private JButton tasksBtn;
    private JButton profileBtn;
    private JButton settingsBtn;
    private JButton logoutBtn;

    // Content panels
    private JPanel dashboardContent;
    private JPanel projectsContent;
    private JPanel tasksContent;
    private JPanel profileContent;
    private JPanel settingsContent;

    public DashboardPanel(AppFrame appFrame, IUser currentUser, UserRegistry userRegistry) {
        this.appFrame = appFrame;
        this.currentUser = currentUser;
        this.userRegistry = userRegistry;
        this.projectManager = new ProjectManager();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));

        createSidebar();
        createContentPanels();

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        showDashboard();
    }

    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 55, 75));
        sidebarPanel.setPreferredSize(new Dimension(220, 0));

        // Logo/Title area
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 55, 75));
        titlePanel.setMaximumSize(new Dimension(220, 80));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("  Project Mgmt");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // User info panel
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

        // Navigation buttons
        dashboardBtn = createSidebarButton("📊 Dashboard", true);
        projectsBtn = createSidebarButton("📁 Projects", false);
        tasksBtn = createSidebarButton("✓ Tasks", false);
        profileBtn = createSidebarButton("👤 Profile", false);
        settingsBtn = createSidebarButton("⚙️ Settings", false);

        sidebarPanel.add(titlePanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(userPanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarPanel.add(dashboardBtn);
        sidebarPanel.add(projectsBtn);
        sidebarPanel.add(tasksBtn);
        sidebarPanel.add(profileBtn);
        sidebarPanel.add(settingsBtn);
        sidebarPanel.add(Box.createVerticalGlue());

        // Logout button at bottom
        logoutBtn = createSidebarButton("🚪 Logout", false);
        logoutBtn.setForeground(new Color(255, 150, 150));
        sidebarPanel.add(logoutBtn);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Button actions
        dashboardBtn.addActionListener(e -> showDashboard());
        projectsBtn.addActionListener(e -> showProjects());
        tasksBtn.addActionListener(e -> showTasks());
        profileBtn.addActionListener(e -> showProfile());
        settingsBtn.addActionListener(e -> showSettings());
        logoutBtn.addActionListener(e -> logout());
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

    private void createContentPanels() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        dashboardContent = createDashboardPanel();
        projectsContent = createProjectsPanel();
        tasksContent = createTasksPanel();
        profileContent = createProfilePanel();
        settingsContent = createSettingsPanel();

        contentPanel.add(dashboardContent, "dashboard");
        contentPanel.add(projectsContent, "projects");
        contentPanel.add(tasksContent, "tasks");
        contentPanel.add(profileContent, "profile");
        contentPanel.add(settingsContent, "settings");
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

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

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        panel.add(activityPanel, BorderLayout.SOUTH);

        return panel;
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

    private JPanel createProjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 250));

        JLabel titleLabel = new JLabel("My Projects");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(45, 55, 75));

        JButton addProjectBtn = new JButton("+ New Project");
        addProjectBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addProjectBtn.setBackground(new Color(70, 130, 180));
        addProjectBtn.setForeground(Color.WHITE);
        addProjectBtn.setFocusPainted(false);
        addProjectBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addProjectBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addProjectBtn.addActionListener(e -> showAddProjectDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addProjectBtn, BorderLayout.EAST);

        JPanel projectsListPanel = new JPanel();
        projectsListPanel.setLayout(new BoxLayout(projectsListPanel, BoxLayout.Y_AXIS));
        projectsListPanel.setBackground(Color.WHITE);
        projectsListPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel noProjects = new JLabel("No projects yet. Create your first project!");
        noProjects.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noProjects.setForeground(new Color(150, 150, 160));
        noProjects.setAlignmentX(Component.CENTER_ALIGNMENT);

        projectsListPanel.add(Box.createVerticalGlue());
        projectsListPanel.add(noProjects);
        projectsListPanel.add(Box.createVerticalGlue());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(projectsListPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTasksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 250));

        JLabel titleLabel = new JLabel("My Tasks");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(45, 55, 75));

        JButton addTaskBtn = new JButton("+ New Task");
        addTaskBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addTaskBtn.setBackground(new Color(100, 180, 100));
        addTaskBtn.setForeground(Color.WHITE);
        addTaskBtn.setFocusPainted(false);
        addTaskBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addTaskBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addTaskBtn.addActionListener(e -> showAddTaskDialog());

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(addTaskBtn, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(new Color(245, 245, 250));

        JButton allBtn = createFilterButton("All", true);
        JButton pendingBtn = createFilterButton("Pending", false);
        JButton completedBtn = createFilterButton("Completed", false);

        filterPanel.add(allBtn);
        filterPanel.add(pendingBtn);
        filterPanel.add(completedBtn);

        JPanel tasksListPanel = new JPanel();
        tasksListPanel.setLayout(new BoxLayout(tasksListPanel, BoxLayout.Y_AXIS));
        tasksListPanel.setBackground(Color.WHITE);
        tasksListPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel noTasks = new JLabel("No tasks assigned");
        noTasks.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noTasks.setForeground(new Color(150, 150, 160));
        noTasks.setAlignmentX(Component.CENTER_ALIGNMENT);

        tasksListPanel.add(Box.createVerticalGlue());
        tasksListPanel.add(noTasks);
        tasksListPanel.add(Box.createVerticalGlue());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(filterPanel, BorderLayout.CENTER);
        panel.add(tasksListPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createFilterButton(String text, boolean active) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (active) {
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        } else {
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(100, 100, 120));
            button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 230), 1));
        }
        
        return button;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

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
        panel.add(profileCard, gbc);

        return panel;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(100, 100, 120));
        return label;
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

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
        panel.add(appearancePanel, gbc);

        gbc.gridx = 1;
        panel.add(notificationPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(securityPanel, gbc);

        return panel;
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

    private void showAddProjectDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create New Project", true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Project Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(25);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel descLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(descLabel, gbc);

        JTextArea descArea = new JTextArea(5, 25);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createBtn = new JButton("Create");
        createBtn.setBackground(new Color(70, 130, 180));
        createBtn.setForeground(Color.WHITE);
        JButton cancelBtn = new JButton("Cancel");

        createBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descArea.getText().trim();
            
            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a project title");
                return;
            }

            if (currentUser instanceof Owner) {
                Project project = new Project(title, description, (Owner) currentUser);
                JOptionPane.showMessageDialog(dialog, "Project created successfully!");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Only Owners can create projects");
            }
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAddTaskDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Create New Task", true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Task Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(25);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        JLabel priorityLabel = new JLabel("Priority:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(priorityLabel, gbc);

        JComboBox<String> priorityCombo = new JComboBox<>(
            new String[]{"LOW", "MEDIUM", "HIGH", "URGENT"});
        gbc.gridx = 1;
        panel.add(priorityCombo, gbc);

        JLabel deadlineLabel = new JLabel("Deadline (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(deadlineLabel, gbc);

        JTextField deadlineField = new JTextField(25);
        deadlineField.setText(LocalDate.now().plusDays(7).toString());
        gbc.gridx = 1;
        panel.add(deadlineField, gbc);

        JLabel descLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(descLabel, gbc);

        JTextArea descArea = new JTextArea(5, 25);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descArea);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createBtn = new JButton("Create");
        createBtn.setBackground(new Color(100, 180, 100));
        createBtn.setForeground(Color.WHITE);
        JButton cancelBtn = new JButton("Cancel");

        createBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String priority = (String) priorityCombo.getSelectedItem();
            String deadlineStr = deadlineField.getText().trim();
            String description = descArea.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a task title");
                return;
            }

            Task.TaskPriority taskPriority = Task.TaskPriority.valueOf(priority);
            LocalDate deadline = null;
            try {
                if (!deadlineStr.isEmpty()) {
                    deadline = LocalDate.parse(deadlineStr);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid date format. Use YYYY-MM-DD");
                return;
            }

            Task task = new Task(title, taskPriority, deadline, description);
            JOptionPane.showMessageDialog(dialog, "Task created successfully!");
            dialog.dispose();
        });

        cancelBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(createBtn);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showDashboard() {
        cardLayout.show(contentPanel, "dashboard");
        resetSidebarButtons();
        dashboardBtn.setBackground(new Color(70, 85, 110));
        dashboardBtn.setForeground(Color.WHITE);
    }

    private void showProjects() {
        cardLayout.show(contentPanel, "projects");
        resetSidebarButtons();
        projectsBtn.setBackground(new Color(70, 85, 110));
        projectsBtn.setForeground(Color.WHITE);
    }

    private void showTasks() {
        cardLayout.show(contentPanel, "tasks");
        resetSidebarButtons();
        tasksBtn.setBackground(new Color(70, 85, 110));
        tasksBtn.setForeground(Color.WHITE);
    }

    private void showProfile() {
        cardLayout.show(contentPanel, "profile");
        resetSidebarButtons();
        profileBtn.setBackground(new Color(70, 85, 110));
        profileBtn.setForeground(Color.WHITE);
    }

    private void showSettings() {
        cardLayout.show(contentPanel, "settings");
        resetSidebarButtons();
        settingsBtn.setBackground(new Color(70, 85, 110));
        settingsBtn.setForeground(Color.WHITE);
    }

    private void resetSidebarButtons() {
        JButton[] buttons = {dashboardBtn, projectsBtn, tasksBtn, profileBtn, settingsBtn};
        for (JButton btn : buttons) {
            btn.setBackground(new Color(45, 55, 75));
            btn.setForeground(new Color(180, 180, 200));
        }
    }

    private void logout() {
        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            appFrame.showLogin();
        }
    }
}

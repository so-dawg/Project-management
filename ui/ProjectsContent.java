package ui;

import logic.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ProjectsContent extends JPanel {
    private IUser currentUser;

    public ProjectsContent(IUser currentUser) {
        this.currentUser = currentUser;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

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

        add(headerPanel, BorderLayout.NORTH);
        add(projectsListPanel, BorderLayout.CENTER);
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
}

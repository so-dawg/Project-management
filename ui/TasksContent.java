package ui;

import logic.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;

public class TasksContent extends JPanel {
    private IUser currentUser;

    public TasksContent(IUser currentUser) {
        this.currentUser = currentUser;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));
        setBorder(new EmptyBorder(30, 30, 30, 30));

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

        add(headerPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.CENTER);
        add(tasksListPanel, BorderLayout.SOUTH);
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
}

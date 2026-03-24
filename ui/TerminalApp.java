package ui;

import logic.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class TerminalApp {
    private UserRegistry userRegistry;
    private Scanner scanner;
    private IUser currentUser;
    private boolean running;

    public TerminalApp() {
        this.userRegistry = new UserRegistry();
        this.scanner = new Scanner(System.in);
        this.running = true;
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        Owner admin = new Owner("Admin", "User", "admin", "admin@example.com", "Password1!");
        userRegistry.addUser(admin);

        Member member = new Member("John", "Doe", "john@example.com", "johndoe", "Password1!");
        userRegistry.addUser(member);

        System.out.println("Default users created:");
        System.out.println("  Admin: admin@example.com / Password1!");
        System.out.println("  Member: john@example.com / Password1!");
    }

    public void start() {
        System.out.println("\n========================================");
        System.out.println("   PROJECT MANAGEMENT SYSTEM           ");
        System.out.println("   (Terminal Version)                  ");
        System.out.println("========================================\n");

        while (running) {
            showLoginMenu();
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    private void showLoginMenu() {
        System.out.println("\n=== LOGIN MENU ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                register();
                break;
            case "3":
                running = false;
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    private void login() {
        try {
            System.out.print("Email/Username: ");
            String emailOrUsername = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (emailOrUsername.isEmpty() || password.isEmpty()) {
                System.out.println("Error: All fields are required!");
                return;
            }

            currentUser = userRegistry.login(emailOrUsername, password);

            if (currentUser != null) {
                System.out.println("\n✓ Login successful! Welcome, " + currentUser.getFirstName() + "!");
                showMainMenu();
            } else {
                System.out.println("✗ Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private void register() {
        try {
            System.out.println("\n=== REGISTER NEW USER ===");

            System.out.print("First Name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            System.out.print("Confirm Password: ");
            String confirmPassword = scanner.nextLine().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
                username.isEmpty() || password.isEmpty()) {
                System.out.println("Error: All fields are required!");
                return;
            }

            if (!password.equals(confirmPassword)) {
                System.out.println("Error: Passwords do not match!");
                return;
            }

            if (!User.isValidEmail(email)) {
                System.out.println("Error: Invalid email format!");
                return;
            }

            if (!User.isValidPassword(password)) {
                System.out.println("Error: Password must be 8+ chars with uppercase, lowercase, number, and special char!");
                return;
            }

            if (userRegistry.searchUserByUsername(username) != null) {
                System.out.println("Error: Username already taken!");
                return;
            }

            if (userRegistry.searchUserByEmail(email) != null) {
                System.out.println("Error: Email already registered!");
                return;
            }

            Member newMember = new Member(firstName, lastName, email, username, password);
            userRegistry.addUser(newMember);

            System.out.println("\n✓ Registration successful! Please login.");
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private void showMainMenu() {
        while (currentUser != null) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName() + 
                             " (" + currentUser.getRole() + ")");
            System.out.println("1. View Profile");
            System.out.println("2. View Projects");
            System.out.println("3. Create Project");
            System.out.println("4. View Tasks");
            System.out.println("5. Create Task");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    viewProfile();
                    break;
                case "2":
                    viewProjects();
                    break;
                case "3":
                    createProject();
                    break;
                case "4":
                    viewTasks();
                    break;
                case "5":
                    createTask();
                    break;
                case "6":
                    currentUser = null;
                    System.out.println("Logged out successfully!");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void viewProfile() {
        try {
            System.out.println("\n=== YOUR PROFILE ===");
            System.out.println("ID: " + currentUser.getId());
            System.out.println("Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
            System.out.println("Email: " + currentUser.getEmail());
            System.out.println("Username: " + currentUser.getUsername());
            System.out.println("Role: " + currentUser.getRole());
        } catch (Exception e) {
            System.out.println("Error viewing profile: " + e.getMessage());
        }
    }

    private void viewProjects() {
        try {
            System.out.println("\n=== YOUR PROJECTS ===");
            // Note: Projects are not persisted in this version
            System.out.println("No projects yet. Create your first project!");
        } catch (Exception e) {
            System.out.println("Error viewing projects: " + e.getMessage());
        }
    }

    private void createProject() {
        try {
            System.out.println("\n=== CREATE PROJECT ===");

            if (!(currentUser instanceof Owner)) {
                System.out.println("Error: Only Owners can create projects!");
                return;
            }

            System.out.print("Project Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Description: ");
            String description = scanner.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Error: Title is required!");
                return;
            }

            Owner owner = (Owner) currentUser;
            Project project = new Project(title, description, owner);

            System.out.println("\n✓ Project created successfully!");
            System.out.println("Project ID: " + project.getProjectID());
            System.out.println("Title: " + project.getTitle());
        } catch (Exception e) {
            System.out.println("Error creating project: " + e.getMessage());
        }
    }

    private void viewTasks() {
        try {
            System.out.println("\n=== YOUR TASKS ===");
            // Note: Tasks are not persisted in this version
            System.out.println("No tasks assigned yet.");
        } catch (Exception e) {
            System.out.println("Error viewing tasks: " + e.getMessage());
        }
    }

    private void createTask() {
        try {
            System.out.println("\n=== CREATE TASK ===");

            System.out.print("Task Title: ");
            String title = scanner.nextLine().trim();

            System.out.print("Priority (LOW/MEDIUM/HIGH/URGENT): ");
            String priorityStr = scanner.nextLine().trim().toUpperCase();

            System.out.print("Deadline (YYYY-MM-DD) or empty for none: ");
            String deadlineStr = scanner.nextLine().trim();

            System.out.print("Description: ");
            String description = scanner.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println("Error: Title is required!");
                return;
            }

            Task.TaskPriority priority;
            try {
                priority = Task.TaskPriority.valueOf(priorityStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid priority! Using MEDIUM.");
                priority = Task.TaskPriority.MEDIUM;
            }

            LocalDate deadline = null;
            if (!deadlineStr.isEmpty()) {
                try {
                    deadline = LocalDate.parse(deadlineStr);
                } catch (Exception e) {
                    System.out.println("Error: Invalid date format! No deadline set.");
                }
            }

            Task task = new Task(title, priority, deadline, description);

            System.out.println("\n✓ Task created successfully!");
            System.out.println("Task ID: " + task.getTaskId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Priority: " + task.getPriority());
            System.out.println("Deadline: " + (deadline != null ? deadline.toString() : "Not set"));
        } catch (Exception e) {
            System.out.println("Error creating task: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TerminalApp app = new TerminalApp();
        app.start();
    }
}

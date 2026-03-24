package ui;

import logic.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.Console;
import java.time.LocalDate;

public class TerminalApp {
  private UserRegistry userRegistry;
  private Scanner scanner;
  private IUser currentUser;
  private boolean running;
  private ProjectManager projectManager;

  public TerminalApp() {
    this.userRegistry = new UserRegistry();
    this.scanner = new Scanner(System.in);
    this.running = true;
    this.projectManager = new ProjectManager();
    initializeDefaultUsers();
  }

  /**
   * Safely read a line from scanner, handling end-of-input gracefully
   * 
   * @return The trimmed input line, or null if end of input
   */
  private String readLine() {
    try {
      return scanner.nextLine().trim();
    } catch (Exception e) {
      running = false;
      return null;
    }
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

    String choice = readLine();
    if (choice == null)
      return;

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

  /**
   * Read password with hidden input (no echo)
   */
  private String readPassword() {
    System.out.print("Password: ");
    Console console = System.console();
    if (console != null) {
      char[] passwordChars = console.readPassword();
      String password = new String(passwordChars);
      return password.trim();
    } else {
      // Fallback for non-interactive environments (piped input, IDEs)
      try {
        return scanner.nextLine().trim();
      } catch (Exception e) {
        running = false;
        return null;
      }
    }
  }

  private void login() {
    try {
      System.out.print("Email/Username: ");
      String emailOrUsername = readLine();
      if (emailOrUsername == null)
        return;

      String password = readPassword();
      if (password == null)
        return;

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
      String firstName = readLine();
      if (firstName == null)
        return;

      System.out.print("Last Name: ");
      String lastName = readLine();
      if (lastName == null)
        return;

      System.out.print("Email: ");
      String email = readLine();
      if (email == null)
        return;

      System.out.print("Username: ");
      String username = readLine();
      if (username == null)
        return;

      String password = readPassword();
      if (password == null)
        return;

      String confirmPassword = readPassword();
      if (confirmPassword == null)
        return;
      System.out.println(); // Add newline after password input

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
      System.out.println("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName());
      System.out.println("1. View Profile");
      System.out.println("2. View Projects");
      System.out.println("3. Create Project");
      System.out.println("4. View Tasks");
      System.out.println("5. Create Task");
      System.out.println("6. Delete Task");
      System.out.println("7. Delete Project");
      System.out.println("8. Join Project");
      System.out.println("9. Logout");
      System.out.print("Choose option: ");

      String choice = readLine();
      if (choice == null)
        return;

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
          deleteTask();
          break;
        case "7":
          deleteProject();
          break;
        case "8":
          joinProject();
          break;
        case "9":
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
    } catch (Exception e) {
      System.out.println("Error viewing profile: " + e.getMessage());
    }
  }

  private void viewProjects() {
    try {
      System.out.println("\n=== YOUR PROJECTS ===");

      ArrayList<Project> userProjects = projectManager.getAllUserProjects(currentUser);

      if (userProjects.isEmpty()) {
        System.out.println("No projects yet. Create your first project!");
        return;
      }

      System.out.printf("%-5s %-30s %-15s %-10s%n", "ID", "Title", "Owner", "Members");
      System.out.println("--------------------------------------------------------------------------------");

      for (Project project : userProjects) {
        System.out.printf("%-5d %-30s %-15s %-10d%n",
            project.getProjectID(),
            project.getTitle(),
            project.getOwner().getFirstName() + " " + project.getOwner().getLastName(),
            project.getNumMember());
      }
    } catch (Exception e) {
      System.out.println("Error viewing projects: " + e.getMessage());
    }
  }

  private void createProject() {
    try {
      System.out.println("\n=== CREATE PROJECT ===");

      if (!currentUser.can("CREATE_PROJECT")) {
        System.out.println("Error: You don't have permission to create projects!");
        return;
      }

      System.out.print("Project Title: ");
      String title = readLine();
      if (title == null)
        return;

      System.out.print("Description: ");
      String description = readLine();
      if (description == null)
        return;

      if (title.isEmpty()) {
        System.out.println("Error: Title is required!");
        return;
      }

      Project project = projectManager.createProject(title, description, currentUser);

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

      boolean foundTasks = false;

      // Get all projects the user is involved with
      ArrayList<Project> userProjects = projectManager.getAllUserProjects(currentUser);

      if (userProjects.isEmpty()) {
        System.out.println("No projects found. Create or join a project first!");
        return;
      }

      // Collect all tasks from user's projects
      ArrayList<Task> userTasks = new ArrayList<>();
      for (Project project : userProjects) {
        ArrayList<Task> tasks = project.getTasks();
        for (Task task : tasks) {
          // Show all tasks in user's projects, or filter by assignment
          userTasks.add(task);
        }
      }

      if (userTasks.isEmpty()) {
        System.out.println("No tasks found in your projects.");
        return;
      }

      System.out.printf("%-5s %-30s %-10s %-12s %-10s %-15s%n", "ID", "Title", "Priority", "Deadline", "Status",
          "Assigned To");
      System.out.println(
          "--------------------------------------------------------------------------------------------------------");

      for (Task task : userTasks) {
        String status = task.isCompleted() ? "Completed" : "Pending";
        String deadline = task.getDeadline() != null ? task.getDeadline().toString() : "Not set";
        String assignee = getTaskAssigneeName(task, userProjects);
        System.out.printf("%-5d %-30s %-10s %-12s %-10s %-15s%n",
            task.getTaskId(),
            task.getTitle(),
            task.getPriority(),
            deadline,
            status,
            assignee);
      }
    } catch (Exception e) {
      System.out.println("Error viewing tasks: " + e.getMessage());
    }
  }

  private void createTask() {
    try {
      System.out.println("\n=== CREATE TASK ===");

      // Get all projects the user is involved with
      ArrayList<Project> userProjects = projectManager.getAllUserProjects(currentUser);

      if (userProjects.isEmpty()) {
        System.out.println("Error: No projects found. Create or join a project first!");
        return;
      }

      // Display available projects
      System.out.println("\nAvailable Projects:");
      for (Project project : userProjects) {
        System.out.println("  " + project.getProjectID() + ". " + project.getTitle());
      }

      System.out.print("\nSelect Project ID: ");
      String projectIdStr = readLine();
      if (projectIdStr == null)
        return;
      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("Error: Invalid project ID!");
        return;
      }

      Project selectedProject = projectManager.getProjectById(projectId);
      if (selectedProject == null) {
        System.out.println("Error: Project not found!");
        return;
      }

      // Check if user has permission to add tasks to this project
      if (!currentUser.can("CREATE_TASK")) {
        System.out.println("Error: You don't have permission to create tasks in this project!");
        return;
      }

      System.out.print("Task Title: ");
      String title = readLine();
      if (title == null)
        return;

      System.out.print("Priority (LOW/MEDIUM/HIGH/URGENT): ");
      String priorityStr = readLine();
      if (priorityStr == null)
        return;
      priorityStr = priorityStr.toUpperCase();

      System.out.print("Deadline (YYYY-MM-DD) or empty for none: ");
      String deadlineStr = readLine();
      if (deadlineStr == null)
        return;

      System.out.print("Description: ");
      String description = readLine();
      if (description == null)
        return;

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

      // Add task to the selected project
      selectedProject.addTask(currentUser, title, priority,
          deadline != null ? deadline.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null,
          description);

      // Get the created task (last task in the list)
      ArrayList<Task> tasks = selectedProject.getTasks();
      Task newTask = tasks.get(tasks.size() - 1);

      System.out.println("\n✓ Task created successfully!");
      System.out.println("Task ID: " + newTask.getTaskId());
      System.out.println("Title: " + newTask.getTitle());
      System.out.println("Priority: " + newTask.getPriority());
      System.out.println("Deadline: " + (deadline != null ? deadline.toString() : "Not set"));
      System.out.println("Project: " + selectedProject.getTitle());

      // Only project owner can assign tasks to members
      if (selectedProject.getOwner().getId().equals(currentUser.getId())) {
        System.out.println("\n--- Assign Task ---");
        System.out.println("Project Members:");
        System.out.println("  0. Unassigned");
        System.out.println("  1. " + selectedProject.getOwner().getFirstName() + " "
            + selectedProject.getOwner().getLastName() + " (Owner)");

        int memberNum = 2;
        ArrayList<Member> members = selectedProject.getMembers();
        for (Member member : members) {
          System.out.println("  " + memberNum + ". " + member.getFirstName() + " " + member.getLastName());
          memberNum++;
        }

        System.out.print("\nAssign to (enter number): ");
        String assignChoice = readLine();
        if (assignChoice != null) {
          try {
            int choice = Integer.parseInt(assignChoice);
            if (choice == 0) {
              // Unassigned
              System.out.println("Assigned To: Unassigned");
            } else if (choice == 1) {
              // Assign to owner
              int ownerId = Integer.parseInt(selectedProject.getOwner().getId());
              newTask.setAssignToDirect(ownerId);
              System.out.println("Assigned To: " + selectedProject.getOwner().getFirstName() + " "
                  + selectedProject.getOwner().getLastName());
            } else if (choice >= 2 && choice <= members.size() + 1) {
              // Assign to member
              Member assignee = members.get(choice - 2);
              int memberId = Integer.parseInt(assignee.getId());
              newTask.setAssignToDirect(memberId);
              System.out.println("Assigned To: " + assignee.getFirstName() + " " + assignee.getLastName());
            } else {
              System.out.println("Assigned To: Unassigned (invalid choice)");
            }
          } catch (NumberFormatException e) {
            System.out.println("Assigned To: Unassigned (invalid input)");
          }
        }
      } else {
        // Members create tasks but they remain unassigned
        System.out.println("Assigned To: Unassigned (Only project owner can assign)");
      }
    } catch (Exception e) {
      System.out.println("Error creating task: " + e.getMessage());
    }
  }

  private void deleteTask() {
    try {
      System.out.println("\n=== DELETE TASK ===");

      // Get all projects the user is involved with
      ArrayList<Project> userProjects = projectManager.getAllUserProjects(currentUser);

      if (userProjects.isEmpty()) {
        System.out.println("Error: No projects found. Create or join a project first!");
        return;
      }

      // Collect all tasks from user's projects
      ArrayList<Task> userTasks = new ArrayList<>();
      System.out.println("\nAvailable Tasks:");
      for (Project project : userProjects) {
        for (Task task : project.getTasks()) {
          userTasks.add(task);
          String status = task.isCompleted() ? "Completed" : "Pending";
          String deadline = task.getDeadline() != null ? task.getDeadline().toString() : "Not set";
          System.out.printf("  [%d] %s (Project: %s, Priority: %s, Deadline: %s, Status: %s)%n",
              task.getTaskId(), task.getTitle(), project.getTitle(),
              task.getPriority(), deadline, status);
        }
      }

      if (userTasks.isEmpty()) {
        System.out.println("No tasks found in your projects.");
        return;
      }

      System.out.print("\nEnter Task ID to delete (or -1 to cancel): ");
      String taskIdStr = readLine();
      if (taskIdStr == null)
        return;

      int taskId;
      try {
        taskId = Integer.parseInt(taskIdStr);
      } catch (NumberFormatException e) {
        System.out.println("Error: Invalid task ID!");
        return;
      }

      if (taskId == -1) {
        System.out.println("Delete task cancelled.");
        return;
      }

      // Find and delete the task
      boolean deleted = false;
      for (Project project : userProjects) {
        // Check if user has permission to delete tasks in this project
        if (project.getOwner().getId().equals(currentUser.getId()) || currentUser.can("DELETE_TASK")) {
          if (project.removeTaskByID(currentUser, taskId)) {
            deleted = true;
            break;
          }
        }
      }

      if (deleted) {
        System.out.println("\n✓ Task deleted successfully!");
      } else {
        System.out.println("\n✗ Failed to delete task. You may not have permission or task not found.");
      }
    } catch (Exception e) {
      System.out.println("Error deleting task: " + e.getMessage());
    }
  }

  private void deleteProject() {
    try {
      System.out.println("\n=== DELETE PROJECT ===");

      ArrayList<Project> userProjects = projectManager.getAllUserProjects(currentUser);

      if (userProjects.isEmpty()) {
        System.out.println("No projects found.");
        return;
      }

      // Show only projects owned by the current user
      ArrayList<Project> ownedProjects = new ArrayList<>();
      System.out.println("\nYour Projects (that you can delete):");
      for (Project project : userProjects) {
        if (project.getOwner().getId().equals(currentUser.getId())) {
          ownedProjects.add(project);
          System.out.printf("  [%d] %s - %d members, %d tasks%n",
              project.getProjectID(), project.getTitle(),
              project.getNumMember(), project.getTaskCount());
        }
      }

      if (ownedProjects.isEmpty()) {
        System.out.println("You don't own any projects to delete.");
        return;
      }

      System.out.print("\nEnter Project ID to delete (or -1 to cancel): ");
      String projectIdStr = readLine();
      if (projectIdStr == null)
        return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("Delete project cancelled.");
        return;
      }

      Project projectToDelete = projectManager.getProjectById(projectId);
      if (projectToDelete == null) {
        System.out.println("Error: Project not found!");
        return;
      }

      if (!projectToDelete.getOwner().getId().equals(currentUser.getId())) {
        System.out.println("Error: You can only delete projects you own!");
        return;
      }

      if (projectManager.removeProject(projectId)) {
        System.out.println("\n✓ Project deleted successfully!");
      } else {
        System.out.println("\n✗ Failed to delete project.");
      }
    } catch (Exception e) {
      System.out.println("Error deleting project: " + e.getMessage());
    }
  }

  private void joinProject() {
    try {
      System.out.println("\n=== JOIN PROJECT ===");

      // Only Members can join projects (Owners already own their projects)
      if (!(currentUser instanceof Member)) {
        System.out.println("Error: Only Members can join projects!");
        return;
      }

      // Get all projects
      ArrayList<Project> allProjects = projectManager.getAllProjects();

      if (allProjects.isEmpty()) {
        System.out.println("No projects available. Create a project first!");
        return;
      }

      // Show available projects (excluding ones user already joined)
      ArrayList<Project> availableProjects = new ArrayList<>();
      System.out.println("\nAvailable Projects:");
      for (Project project : allProjects) {
        // Check if user is already in this project
        if (!project.getOwner().getId().equals(currentUser.getId()) &&
            project.searchMemberById(currentUser.getId()) == null) {
          availableProjects.add(project);
          System.out.printf("  [%d] %s - Owner: %s, %d members, %d tasks%n",
              project.getProjectID(), project.getTitle(),
              project.getOwner().getFirstName() + " " + project.getOwner().getLastName(),
              project.getNumMember(), project.getTaskCount());
        }
      }

      if (availableProjects.isEmpty()) {
        System.out.println("No available projects to join. You may already be in all projects.");
        return;
      }

      System.out.print("\nEnter Project ID to join (or -1 to cancel): ");
      String projectIdStr = readLine();
      if (projectIdStr == null)
        return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("Join project cancelled.");
        return;
      }

      Project projectToJoin = projectManager.getProjectById(projectId);
      if (projectToJoin == null) {
        System.out.println("Error: Project not found!");
        return;
      }

      // Check if already a member
      if (projectToJoin.searchMemberById(currentUser.getId()) != null) {
        System.out.println("Error: You are already a member of this project!");
        return;
      }

      // Check if user is the owner
      if (projectToJoin.getOwner().getId().equals(currentUser.getId())) {
        System.out.println("Error: You already own this project!");
        return;
      }

      // Use the currentUser (which is a Member) to add to project
      // The addMemberById needs a User object with the users list populated
      // We'll use a Member as a workaround to hold the registry
      Member memberContainer = new Member("", "", "", "", "");
      for (IUser u : userRegistry.getAllUsers()) {
        memberContainer.addUser(u);
      }

      if (projectToJoin.addMemberById(currentUser.getId(), memberContainer)) {
        System.out.println("\n✓ Successfully joined the project!");
      } else {
        System.out.println("\n✗ Failed to join the project.");
      }
    } catch (Exception e) {
      System.out.println("Error joining project: " + e.getMessage());
    }
  }

  /**
   * Get the assignee name for a task
   */
  private String getTaskAssigneeName(Task task, ArrayList<Project> userProjects) {
    int assignToId = task.getAssignToId();
    if (assignToId == 0) {
      return "Unassigned";
    }
    // Search for the member in all projects
    for (Project project : userProjects) {
      Member member = project.searchMemberById(String.valueOf(assignToId));
      if (member != null) {
        return member.getFirstName() + " " + member.getLastName();
      }
    }
    // Check if it's the project owner
    for (Project project : userProjects) {
      if (project.getOwner().getId().equals(String.valueOf(assignToId))) {
        return project.getOwner().getFirstName() + " " + project.getOwner().getLastName();
      }
    }
    return "Unknown (ID: " + assignToId + ")";
  }

  public static void main(String[] args) {
    TerminalApp app = new TerminalApp();
    app.start();
  }
}

package cli;

import logic.*;

import java.util.Scanner;
import java.io.Console;

public class TerminalApp {

  private UserRegistry userRegistry;
  private Scanner scanner;
  private IUser currentUser;
  private boolean running;
  private ProjectManager projectManager;
  private DatabaseManager dbManager;
  private boolean useDatabase;

  public TerminalApp() {
    this.userRegistry = new UserRegistry();
    this.scanner = new Scanner(System.in);
    this.running = true;
    this.projectManager = new ProjectManager();
    this.dbManager = new DatabaseManager();
    this.useDatabase = false;
  }

  private String readLine() {
    try {
      return scanner.nextLine().trim();
    } catch (Exception e) {
      running = false;
      return null;
    }
  }

  public String readLinePublic() {
    return readLine();
  }

  private String readPassword() {
    Console console = System.console();
    if (console != null) {
      char[] passwordChars = console.readPassword();
      return new String(passwordChars).trim();
    } else {
      try {
        return scanner.nextLine().trim();
      } catch (Exception e) {
        running = false;
        return null;
      }
    }
  }

  public String readPasswordPublic() {
    return readPassword();
  }

  private void initGuestUser() {
    Member member = new Member("Guest", "00", "Guest@example.com", "Guest-00", "1234");
    userRegistry.addUser(member);
    System.out.println("[SYSTEM] Guest user created:");
    System.out.println("[INFO] Guest@example.com / 1234");
  }

  public void start() {
    System.out.println("\n========================================");
    System.out.println("   PROJECT MANAGEMENT SYSTEM");
    System.out.println("========================================\n");

    // Auto-connect to database, fallback to offline
    System.out.println("[SYSTEM] Connecting to database...");
    dbManager.connect();
    if (dbManager.isConnected()) {
      useDatabase = true;
      dbManager.loadUsersToRegistry(userRegistry);
      System.out.println("[SYSTEM] ✓ Connected to database!");
    } else {
      useDatabase = false;
      initGuestUser();
      System.out.println("[SYSTEM] Running in offline mode (data will NOT be saved)");
    }
    System.out.println();

    while (running) {
      showLoginMenu();
    }

    if (useDatabase && dbManager != null) {
      dbManager.disconnect();
    }

    scanner.close();
    System.out.println("Goodbye!");
  }

  private void showLoginMenu() {
    System.out.println("\n[===== LOGIN MENU =====]");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Exit");
    System.out.print("Choose option: ");

    String choice = readLine();
    if (choice == null) {
      return;
    }

    switch (choice) {
      case "1":
        login();
        break;
      case "2":
        registerUser();
        break;
      case "3":
        running = false;
        break;
      default:
        System.out.println("[SYSTEM] Invalid option!");
    }
  }

  private void login() {
    try {
      System.out.print("Email/Username: ");
      String emailOrUsername = readLine();
      if (emailOrUsername == null || emailOrUsername.isEmpty()) {
        System.out.println("[SYSTEM] Login cancelled.");
        return;
      }

      System.out.print("Password: ");
      String password = readPassword();
      if (password == null) {
        System.out.println("[SYSTEM] Login cancelled.");
        return;
      }

      if (useDatabase && dbManager != null && dbManager.isConnected()) {
        currentUser = dbManager.login(emailOrUsername, password);
      } else {
        currentUser = userRegistry.login(emailOrUsername, password);
      }

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

  private void registerUser() {
    try {
      System.out.println("\n=== REGISTER NEW USER ===");

      System.out.print("First Name: ");
      String firstName = readLine();
      if (firstName == null || firstName.isEmpty()) {
        System.out.println("Registration cancelled.");
        return;
      }

      System.out.print("Last Name: ");
      String lastName = readLine();
      if (lastName == null || lastName.isEmpty()) {
        System.out.println("Registration cancelled.");
        return;
      }

      System.out.print("Email: ");
      String email = readLine();
      if (email == null || email.isEmpty()) {
        System.out.println("Registration cancelled.");
        return;
      }

      System.out.print("Username: ");
      String username = readLine();
      if (username == null || username.isEmpty()) {
        System.out.println("Registration cancelled.");
        return;
      }

      System.out.print("Password (min 4 characters): ");
      String password = readPassword();
      if (password == null || password.isEmpty()) {
        System.out.println("Registration cancelled.");
        return;
      }

      System.out.print("Confirm Password: ");
      String confirmPassword = readPassword();
      if (confirmPassword == null) {
        System.out.println("Registration cancelled.");
        return;
      }
      System.out.println();

      if (!password.equals(confirmPassword)) {
        System.out.println("Error: Passwords do not match!");
        return;
      }

      if (!User.isValidEmail(email)) {
        System.out.println("Error: Invalid email format!");
        return;
      }

      if (!User.isValidPassword(password)) {
        System.out.println("Error: Password must be at least 4 characters!");
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

      if (useDatabase && dbManager != null && dbManager.isConnected()) {
        int userId = dbManager.insertUser(firstName, lastName, email, username, password);
        if (userId != -1) {
          newMember.setIdDirect(String.valueOf(userId));
          System.out.println("User saved to database with ID: " + userId);
        } else {
          System.out.println("Warning: Failed to save user to database. Continuing in-memory only.");
        }
      }

      userRegistry.addUser(newMember);
      System.out.println("\n✓ Registration successful! Please login.");
    } catch (Exception e) {
      System.out.println("Error during registration: " + e.getMessage());
    }
  }

  private void showMainMenu() {
    while (currentUser != null) {
      System.out.println("\n[===== MAIN MENU =====]");
      System.out.println("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName());
      System.out.println("1. Projects");
      System.out.println("2. Tasks");
      System.out.println("3. Members");
      System.out.println("4. View Profile");
      System.out.println("5. Edit Profile");
      System.out.println("6. Exit");
      System.out.print("Choose option: ");

      String choice = readLine();
      if (choice == null) {
        return;
      }

      switch (choice) {
        case "1":
          new ProjectsMenu(this).show();
          break;
        case "2":
          new TasksMenu(this).show();
          break;
        case "3":
          new MembersMenu(this).show();
          break;
        case "4":
          new ProfileMenu(this).viewProfile();
          break;
        case "5":
          new ProfileMenu(this).editProfile();
          break;
        case "6":
          System.out.println("\n[SYSTEM] Goodbye!");
          if (useDatabase && dbManager != null) {
            dbManager.disconnect();
          }
          scanner.close();
          running = false;
          return;
        default:
          System.out.println("[SYSTEM] Invalid option!");
      }
    }
  }

  // Getters for menu classes
  public UserRegistry getUserRegistry() { return userRegistry; }
  public Scanner getScanner() { return scanner; }
  public IUser getCurrentUser() { return currentUser; }
  public ProjectManager getProjectManager() { return projectManager; }
  public DatabaseManager getDbManager() { return dbManager; }
  public boolean isUseDatabase() { return useDatabase; }

  public boolean isDbReady() {
    return useDatabase && dbManager != null && dbManager.isConnected();
  }

  public void logout() {
    currentUser = null;
  }
}

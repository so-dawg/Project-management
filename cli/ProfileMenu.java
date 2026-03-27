package cli;

import logic.*;

public class ProfileMenu {
  private TerminalApp app;

  public ProfileMenu(TerminalApp app) {
    this.app = app;
  }

  public void viewProfile() {
    try {
      System.out.println("\n[===== YOUR PROFILE =====]");
      System.out.println("ID: " + app.getCurrentUser().getId());
      System.out.println("Name: " + app.getCurrentUser().getFirstName() + " " + app.getCurrentUser().getLastName());
      System.out.println("Email: " + app.getCurrentUser().getEmail());
      System.out.println("Username: " + app.getCurrentUser().getUsername());
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error viewing profile: " + e.getMessage());
    }
  }

  public void editProfile() {
    try {
      System.out.println("\n[===== EDIT PROFILE =====]");

      System.out.print("Enter your current password: ");
      String currentPassword = app.readPasswordPublic();
      if (currentPassword == null || !currentPassword.equals(app.getCurrentUser().getPassword())) {
        System.out.println("[SYSTEM] ✗ Wrong password!");
        return;
      }

      System.out.println("\nCurrent Information:");
      System.out.println("  Name: " + app.getCurrentUser().getFirstName() + " " + app.getCurrentUser().getLastName());
      System.out.println("  Email: " + app.getCurrentUser().getEmail());
      System.out.println("  Username: " + app.getCurrentUser().getUsername());

      System.out.println("\n1. Change First Name");
      System.out.println("2. Change Last Name");
      System.out.println("3. Change Username");
      System.out.println("4. Change Email");
      System.out.println("5. Change Password");
      System.out.print("Choose what to edit: ");

      String choice = app.readLinePublic();
      if (choice == null) return;

      switch (choice) {
        case "1":
          System.out.print("New First Name: ");
          String firstName = app.readLinePublic();
          if (firstName != null && !firstName.isEmpty()) {
            ((User) app.getCurrentUser()).setFirstname(firstName);
            System.out.println("[SYSTEM] ✓ First name updated!");
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              app.getDbManager().updateUser(app.getCurrentUser());
              System.out.println("[SYSTEM] ✓ Saved to database!");
            }
          }
          break;
        case "2":
          System.out.print("New Last Name: ");
          String lastName = app.readLinePublic();
          if (lastName != null && !lastName.isEmpty()) {
            ((User) app.getCurrentUser()).setLastname(lastName);
            System.out.println("[SYSTEM] ✓ Last name updated!");
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              app.getDbManager().updateUser(app.getCurrentUser());
              System.out.println("[SYSTEM] ✓ Saved to database!");
            }
          }
          break;
        case "3":
          System.out.print("New Username: ");
          String newUsername = app.readLinePublic();
          if (newUsername != null && !newUsername.isEmpty()) {
            if (app.getUserRegistry().searchUserByUsername(newUsername) != null) {
              System.out.println("[SYSTEM] ✗ Username already taken!");
            } else {
              ((User) app.getCurrentUser()).setUsername(newUsername);
              System.out.println("[SYSTEM] ✓ Username updated!");
              if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                app.getDbManager().updateUsername(app.getCurrentUser().getId(), newUsername);
                System.out.println("[SYSTEM] ✓ Saved to database!");
              }
            }
          }
          break;
        case "4":
          System.out.print("New Email: ");
          String email = app.readLinePublic();
          if (email != null && !email.isEmpty() && User.isValidEmail(email)) {
            if (app.getUserRegistry().searchUserByEmail(email) != null) {
              System.out.println("[SYSTEM] ✗ Email already registered!");
            } else {
              ((User) app.getCurrentUser()).setEmail(email);
              System.out.println("[SYSTEM] ✓ Email updated!");
              if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                app.getDbManager().updateUserEmail(app.getCurrentUser().getId(), email);
                System.out.println("[SYSTEM] ✓ Saved to database!");
              }
            }
          } else {
            System.out.println("[SYSTEM] Invalid email!");
          }
          break;
        case "5":
          System.out.print("New Password: ");
          String newPassword = app.readPasswordPublic();
          if (newPassword != null && User.isValidPassword(newPassword)) {
            ((User) app.getCurrentUser()).setPassword(newPassword);
            System.out.println("[SYSTEM] ✓ Password updated!");
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              app.getDbManager().updateUserPassword(app.getCurrentUser().getId(), newPassword);
              System.out.println("[SYSTEM] ✓ Saved to database!");
            }
          } else {
            System.out.println("[SYSTEM] Password must be at least 4 characters!");
          }
          break;
        default:
          System.out.println("[SYSTEM] Invalid option!");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error: " + e.getMessage());
    }
  }
}

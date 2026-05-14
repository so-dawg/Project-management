package cli;

import logic.*;

public class MembersMenu {
  private TerminalApp app;

  public MembersMenu(TerminalApp app) {
    this.app = app;
  }

  public void show() {
    while (true) {
      System.out.println("\n[===== MEMBERS MENU =====]");
      System.out.println("1. Add Member to Project (Owner)");
      System.out.println("2. View Project Members");
      System.out.println("0. Back to Main Menu");
      System.out.println("9. Logout");
      System.out.print("Choose option: ");

      String choice = app.readLinePublic();
      if (choice == null) return;

      switch (choice) {
        case "1": addMemberToProject(); break;
        case "2": viewProjectMembers(); break;
        case "9": doLogout(); return;
        case "0": return;
        default: System.out.println("[SYSTEM] Invalid option!");
      }
    }
  }

  private void addMemberToProject() {
    try {
      System.out.println("\n[===== ADD MEMBER TO PROJECT =====]");

      java.util.ArrayList<Project> allProjects;
      
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getProjectsByOwnerId(userId);
        for (Project project : allProjects) {
          java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(project.getProjectID());
          for (Member member : members) {
            project.addMember(member);
          }
        }
      } else {
        allProjects = app.getProjectManager().getProjectsByOwner(app.getCurrentUser());
      }

      if (allProjects.isEmpty()) {
        System.out.println("[SYSTEM] Error: Only project owners can add members!");
        System.out.println("[INFO] You don't own any projects.");
        return;
      }

      System.out.println("\nYour Projects:");
      for (Project project : allProjects) {
        System.out.printf("  [%d] %s - %d members%n",
            project.getProjectID(), project.getTitle(), project.getNumMember());
      }

      System.out.print("\nSelect Project ID: ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      Project selectedProject = null;
      for (Project p : allProjects) {
        if (p.getProjectID() == projectId) {
          selectedProject = p;
          break;
        }
      }
      if (selectedProject == null) {
        System.out.println("[SYSTEM] Error: Project not found!");
        return;
      }

      java.util.ArrayList<Integer> memberIds = new java.util.ArrayList<>();
      if (app.isDbReady()) {
        java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(projectId);
        for (Member m : members) {
          memberIds.add(Integer.parseInt(m.getId()));
        }
      }

      java.util.ArrayList<IUser> allUsers = app.getUserRegistry().getAllUsers();
      java.util.ArrayList<IUser> availableUsers = new java.util.ArrayList<>();

      System.out.println("\nAvailable Users:");
      System.out.println("  0. Cancel");
      int userNum = 1;
      for (IUser user : allUsers) {
        if (user.getId().equals(app.getCurrentUser().getId())) {
          continue;
        }
        int userIdInt = Integer.parseInt(user.getId());
        if (memberIds.contains(userIdInt) || 
            (selectedProject.searchMemberById(user.getId()) != null)) {
          System.out.printf("  [%d] %s %s (%s) [Already in project]%n",
              userNum, user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
          availableUsers.add(user);
          System.out.printf("  [%d] %s %s (%s)%n",
              userNum, user.getFirstName(), user.getLastName(), user.getEmail());
          userNum++;
        }
      }

      if (availableUsers.isEmpty()) {
        System.out.println("[SYSTEM] No available users to add!");
        return;
      }

      System.out.print("\nSelect User ID to add: ");
      String userIdStr = app.readLinePublic();
      if (userIdStr == null) return;

      int userIdChoice;
      try {
        userIdChoice = Integer.parseInt(userIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid user ID!");
        return;
      }

      if (userIdChoice == 0) {
        System.out.println("[SYSTEM] Add member cancelled.");
        return;
      }

      if (userIdChoice < 1 || userIdChoice > availableUsers.size()) {
        System.out.println("[SYSTEM] Error: Invalid user selection!");
        return;
      }

      IUser userToAdd = availableUsers.get(userIdChoice - 1);

      boolean added = false;
      if (selectedProject.addMemberById(userToAdd.getId(), app.getUserRegistry())) {
        added = true;
      }
      
      if (app.isDbReady()) {
        int memberId = Integer.parseInt(userToAdd.getId());
        if (app.getDbManager().addMemberToProject(projectId, memberId)) {
          added = true;
        }
      }
      
      if (added) {
        System.out.println("\n[SYSTEM] ✓ Successfully added " + userToAdd.getUsername() + " to the project!");
      } else {
        System.out.println("\n[SYSTEM] ✗ Failed to add member.");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error adding member: " + e.getMessage());
    }
  }

  private void viewProjectMembers() {
    try {
      System.out.println("\n[===== VIEW PROJECT MEMBERS =====]");

      java.util.ArrayList<Project> allProjects;
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getUserProjects(userId);
      } else {
        allProjects = app.getProjectManager().getAllUserProjects(app.getCurrentUser());
      }

      if (allProjects.isEmpty()) {
        System.out.println("[SYSTEM] No projects found!");
        return;
      }

      System.out.println("\nYour Projects:");
      for (Project project : allProjects) {
        System.out.printf("  [%d] %s - Owner: %s%n",
            project.getProjectID(), project.getTitle(),
            project.getOwner().getFirstName() + " " + project.getOwner().getLastName());
      }

      System.out.print("\nEnter Project ID to view members: ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      Project selectedProject = null;
      for (Project p : allProjects) {
        if (p.getProjectID() == projectId) {
          selectedProject = p;
          break;
        }
      }

      if (selectedProject == null) {
        System.out.println("[SYSTEM] Project not found!");
        return;
      }

      if (app.isDbReady()) {
        java.util.ArrayList<Member> dbMembers = app.getDbManager().getProjectMembers(projectId);
        for (Member member : dbMembers) {
          selectedProject.addMember(member);
        }
      }

      System.out.println("\n=== Members of " + selectedProject.getTitle() + " ===");
      System.out.println("Owner: " + selectedProject.getOwner().getFirstName() + " "
          + selectedProject.getOwner().getLastName());

      java.util.ArrayList<Member> members = selectedProject.getMembers();
      if (members.isEmpty()) {
        System.out.println("No members yet.");
      } else {
        System.out.println("Members (" + members.size() + "):");
        for (Member member : members) {
          System.out.printf("  - %s %s (%s)%n",
              member.getFirstName(), member.getLastName(), member.getEmail());
        }
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error: " + e.getMessage());
    }
  }

  private void doLogout() {
    app.logout();
    System.out.println("\n[SYSTEM] Logged out successfully!");
  }
}

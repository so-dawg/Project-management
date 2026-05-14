package cli;

import logic.*;

public class ProjectsMenu {
  private TerminalApp app;

  public ProjectsMenu(TerminalApp app) {
    this.app = app;
  }

  public void show() {
    while (true) {
      System.out.println("\n[===== PROJECTS MENU =====]");
      System.out.println("1. View Projects");
      System.out.println("2. Create Project");
      System.out.println("3. Edit Project");
      System.out.println("4. Delete Project");
      System.out.println("5. Join Project");
      System.out.println("6. Leave Project");
      System.out.println("0. Back to Main Menu");
      System.out.println("9. Logout");
      System.out.print("Choose option: ");

      String choice = app.readLinePublic();
      if (choice == null) return;

      switch (choice) {
        case "1": viewProjects(); break;
        case "2": createProject(); break;
        case "3": editProject(); break;
        case "4": deleteProject(); break;
        case "5": joinProject(); break;
        case "6": leaveProject(); break;
        case "9": doLogout(); return;
        case "0": return;
        default: System.out.println("[SYSTEM] Invalid option!");
      }
    }
  }

  private void viewProjects() {
    try {
      System.out.println("\n[===== YOUR PROJECTS =====]");

      java.util.ArrayList<Project> userProjects;
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          int projectId = project.getProjectID();
          java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(projectId);
          for (Member member : members) {
            project.addMember(member);
          }
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(projectId);
          for (Task task : tasks) {
            project.getTasks().add(task);
          }
        }
      } else {
        java.util.ArrayList<Project> allProjects = app.getProjectManager().getAllProjects();
        userProjects = new java.util.ArrayList<>();
        for (Project project : allProjects) {
          if (project.getOwner().getId().equals(app.getCurrentUser().getId()) ||
              project.searchMemberById(app.getCurrentUser().getId()) != null) {
            userProjects.add(project);
          }
        }
      }

      if (userProjects.isEmpty()) {
        System.out.println("No projects yet. Create your first project!");
        return;
      }

      System.out.printf("%-5s %-30s %-15s %-10s %-10s%n", "ID", "Title", "Owner", "Members", "Tasks");
      System.out.println("-----------------------------------------------------------------------------------------");

      for (Project project : userProjects) {
        int taskCount = project.getTaskCount();
        if (app.isDbReady()) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          taskCount = tasks.size();
        }
        System.out.printf("%-5d %-30s %-15s %-10d %-10d%n",
            project.getProjectID(),
            project.getTitle(),
            project.getOwner().getFirstName() + " " + project.getOwner().getLastName(),
            project.getNumMember(),
            taskCount);
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error viewing projects: " + e.getMessage());
    }
  }

  private void createProject() {
    try {
      System.out.println("\n[===== CREATE PROJECT =====]");

      if (!app.getCurrentUser().can("CREATE_PROJECT")) {
        System.out.println("[SYSTEM] Error: You don't have permission to create projects!");
        return;
      }

      System.out.print("Project Title: ");
      String title = app.readLinePublic();
      if (title == null) return;

      System.out.print("Description: ");
      String description = app.readLinePublic();
      if (description == null) return;

      if (title.isEmpty()) {
        System.out.println("[SYSTEM] Error: Title is required!");
        return;
      }

      Project project = app.getProjectManager().createProject(title, description, app.getCurrentUser());

      if (app.isDbReady()) {
        int ownerId = Integer.parseInt(app.getCurrentUser().getId());
        app.getDbManager().insertProject(title, description, ownerId);
      }

      System.out.println("\n✓ Project created successfully!");
      System.out.println("Project ID: " + project.getProjectID());
      System.out.println("Title: " + project.getTitle());
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error creating project: " + e.getMessage());
    }
  }

  private void editProject() {
    try {
      System.out.println("\n[===== EDIT PROJECT =====]");

      java.util.ArrayList<Project> allProjects;
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getProjectsByOwnerId(userId);
      } else {
        allProjects = app.getProjectManager().getProjectsByOwner(app.getCurrentUser());
      }

      if (allProjects.isEmpty()) {
        System.out.println("[SYSTEM] You don't own any projects!");
        return;
      }

      System.out.println("\nYour Projects:");
      for (Project project : allProjects) {
        System.out.printf("  [%d] %s - %d members, %d tasks%n",
            project.getProjectID(), project.getTitle(),
            project.getNumMember(), project.getTaskCount());
      }

      System.out.print("\nEnter Project ID to edit (or -1 to cancel): ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("[SYSTEM] Edit cancelled.");
        return;
      }

      Project projectToEdit = null;
      for (Project p : allProjects) {
        if (p.getProjectID() == projectId) {
          projectToEdit = p;
          break;
        }
      }

      if (projectToEdit == null) {
        System.out.println("[SYSTEM] Project not found!");
        return;
      }

      System.out.println("\n1. Change Title");
      System.out.println("2. Change Description");
      System.out.print("Choose what to edit: ");

      String choice = app.readLinePublic();
      if (choice == null) return;

      switch (choice) {
        case "1":
          System.out.println("\nCurrent Title: " + projectToEdit.getTitle());
          System.out.print("New Title (or empty to keep): ");
          String newTitle = app.readLinePublic();
          if (newTitle != null && !newTitle.isEmpty()) {
            if (app.isDbReady()) {
              if (app.getDbManager().updateProjectTitle(projectId, newTitle)) {
                System.out.println("[SYSTEM] ✓ Title updated in database!");
              } else {
                System.out.println("[SYSTEM] ✗ Failed to update title");
              }
            } else {
              System.out.println("[SYSTEM] Offline mode: Title change not saved");
            }
          }
          break;
        case "2":
          System.out.println("\nCurrent Description: " + projectToEdit.getProjectDescription());
          System.out.print("New Description (or empty to keep): ");
          String newDesc = app.readLinePublic();
          if (newDesc != null && !newDesc.isEmpty()) {
            if (app.isDbReady()) {
              if (app.getDbManager().updateProjectDescription(projectId, newDesc)) {
                System.out.println("[SYSTEM] ✓ Description updated in database!");
              } else {
                System.out.println("[SYSTEM] ✗ Failed to update description");
              }
            } else {
              System.out.println("[SYSTEM] Offline mode: Description change not saved");
            }
          }
          break;
        default:
          System.out.println("[SYSTEM] Invalid option!");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error editing project: " + e.getMessage());
    }
  }

  private void deleteProject() {
    try {
      System.out.println("\n[===== DELETE PROJECT =====]");

      java.util.ArrayList<Project> allProjects;
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getUserProjects(userId);
      } else {
        allProjects = app.getProjectManager().getAllProjects();
      }

      java.util.ArrayList<Project> ownedProjects = new java.util.ArrayList<>();
      System.out.println("\nYour Projects (that you can delete):");
      for (Project project : allProjects) {
        if (project.getOwner().getId().equals(app.getCurrentUser().getId())) {
          ownedProjects.add(project);
          System.out.printf("  [%d] %s - %d members, %d tasks%n",
              project.getProjectID(), project.getTitle(),
              project.getNumMember(), project.getTaskCount());
        }
      }

      if (ownedProjects.isEmpty()) {
        System.out.println("[SYSTEM] You don't own any projects to delete.");
        return;
      }

      System.out.print("\nEnter Project ID to delete (or -1 to cancel): ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("[SYSTEM] Delete project cancelled.");
        return;
      }

      Project projectToDelete = null;
      for (Project p : allProjects) {
        if (p.getProjectID() == projectId) {
          projectToDelete = p;
          break;
        }
      }

      if (projectToDelete == null) {
        System.out.println("[SYSTEM] Error: Project not found!");
        return;
      }

      if (!projectToDelete.getOwner().getId().equals(app.getCurrentUser().getId())) {
        System.out.println("[SYSTEM] Error: You can only delete projects you own!");
        return;
      }

      app.getProjectManager().removeProject(projectId);
      
      if (app.isDbReady()) {
        app.getDbManager().removeProject(projectId);
      }

      System.out.println("\n[SYSTEM] ✓ Project deleted successfully!");
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error deleting project: " + e.getMessage());
    }
  }

  private void joinProject() {
    try {
      System.out.println("\n[===== JOIN PROJECT =====]");

      if (!(app.getCurrentUser() instanceof Member)) {
        System.out.println("[SYSTEM] Error: Only Members can join projects!");
        return;
      }

      java.util.ArrayList<Project> allProjects;
      java.util.ArrayList<Integer> joinedProjectIds = new java.util.ArrayList<>();
      
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getAllProjects();
        java.util.ArrayList<Project> userProjects = app.getDbManager().getUserProjects(userId);
        for (Project p : userProjects) {
          joinedProjectIds.add(p.getProjectID());
        }
      } else {
        allProjects = app.getProjectManager().getAllProjects();
      }

      if (allProjects.isEmpty()) {
        System.out.println("[SYSTEM] No projects available. Create a project first!");
        return;
      }

      java.util.ArrayList<Project> availableProjects = new java.util.ArrayList<>();
      System.out.println("\nAvailable Projects:");
      for (Project project : allProjects) {
        if (project.getOwner().getId().equals(app.getCurrentUser().getId())) {
          continue;
        }
        boolean alreadyJoined = joinedProjectIds.contains(project.getProjectID()) ||
            project.searchMemberById(app.getCurrentUser().getId()) != null;
        
        if (alreadyJoined) {
          System.out.printf("  [%d] %s - Owner: %s (Already joined)%n",
              project.getProjectID(), project.getTitle(),
              project.getOwner().getFirstName() + " " + project.getOwner().getLastName());
        } else {
          availableProjects.add(project);
          System.out.printf("  [%d] %s - Owner: %s, %d members, %d tasks%n",
              project.getProjectID(), project.getTitle(),
              project.getOwner().getFirstName() + " " + project.getOwner().getLastName(),
              project.getNumMember(), project.getTaskCount());
        }
      }

      if (availableProjects.isEmpty()) {
        System.out.println("[SYSTEM] No available projects to join.");
        return;
      }

      System.out.print("\nEnter Project ID to join (or -1 to cancel): ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("[SYSTEM] Join project cancelled.");
        return;
      }

      if (joinedProjectIds.contains(projectId)) {
        System.out.println("[SYSTEM] Error: You have already joined this project!");
        return;
      }

      Project projectToJoin = null;
      for (Project p : allProjects) {
        if (p.getProjectID() == projectId) {
          projectToJoin = p;
          break;
        }
      }

      if (projectToJoin == null) {
        System.out.println("[SYSTEM] Error: Project not found!");
        return;
      }

      if (projectToJoin.searchMemberById(app.getCurrentUser().getId()) != null) {
        System.out.println("[SYSTEM] Error: You are already a member of this project!");
        return;
      }

      if (projectToJoin.getOwner().getId().equals(app.getCurrentUser().getId())) {
        System.out.println("[SYSTEM] Error: You already own this project!");
        return;
      }

      if (projectToJoin.addMemberById(app.getCurrentUser().getId(), app.getUserRegistry())) {
        if (app.isDbReady()) {
          int memberId = Integer.parseInt(app.getCurrentUser().getId());
          app.getDbManager().addMemberToProject(projectId, memberId);
        }
        System.out.println("\n[SYSTEM] ✓ Successfully joined the project!");
      } else {
        System.out.println("\n[SYSTEM] ✗ Failed to join the project.");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error joining project: " + e.getMessage());
    }
  }

  private void leaveProject() {
    try {
      System.out.println("\n[===== LEAVE PROJECT =====]");

      java.util.ArrayList<Project> allProjects;
      java.util.ArrayList<Integer> memberProjectIds = new java.util.ArrayList<>();
      
      if (app.isDbReady()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        allProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : allProjects) {
          if (!project.getOwner().getId().equals(app.getCurrentUser().getId())) {
            memberProjectIds.add(project.getProjectID());
          }
        }
      } else {
        allProjects = app.getProjectManager().getAllUserProjects(app.getCurrentUser());
      }

      java.util.ArrayList<Project> memberProjects = new java.util.ArrayList<>();
      System.out.println("\nProjects you are a member of:");
      for (Project project : allProjects) {
        if (project.getOwner().getId().equals(app.getCurrentUser().getId())) {
          continue;
        }
        if (memberProjectIds.contains(project.getProjectID()) ||
            project.searchMemberById(app.getCurrentUser().getId()) != null) {
          memberProjects.add(project);
          System.out.printf("  [%d] %s - Owner: %s%n",
              project.getProjectID(), project.getTitle(),
              project.getOwner().getFirstName() + " " + project.getOwner().getLastName());
        }
      }

      if (memberProjects.isEmpty()) {
        System.out.println("[SYSTEM] You are not a member of any projects!");
        return;
      }

      System.out.print("\nEnter Project ID to leave (or -1 to cancel): ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null) return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      if (projectId == -1) {
        System.out.println("[SYSTEM] Leave project cancelled.");
        return;
      }

      Project projectToLeave = null;
      for (Project p : memberProjects) {
        if (p.getProjectID() == projectId) {
          projectToLeave = p;
          break;
        }
      }

      if (projectToLeave == null) {
        System.out.println("[SYSTEM] Error: Project not found!");
        return;
      }

      boolean removed = false;
      if (app.isDbReady()) {
        int memberId = Integer.parseInt(app.getCurrentUser().getId());
        removed = app.getDbManager().removeMemberFromProject(projectId, memberId);
      }
      
      if (projectToLeave.removeMemberById(app.getCurrentUser().getId())) {
        removed = true;
      }

      if (removed) {
        System.out.println("\n[SYSTEM] ✓ Successfully left the project!");
      } else {
        System.out.println("\n[SYSTEM] ✗ Failed to leave project.");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error leaving project: " + e.getMessage());
    }
  }

  private void doLogout() {
    app.logout();
    System.out.println("\n[SYSTEM] Logged out successfully!");
  }
}

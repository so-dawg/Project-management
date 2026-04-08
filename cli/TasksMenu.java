package cli;

import logic.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TasksMenu {
  private TerminalApp app;

  public TasksMenu(TerminalApp app) {
    this.app = app;
  }

  public void show() {
    while (true) {
      System.out.println("\n[===== TASKS MENU =====]");
      System.out.println("1. View Tasks");
      System.out.println("2. Create Task");
      System.out.println("3. Edit Task");
      System.out.println("4. Delete Task");
      System.out.println("5. Mark Complete/Incomplete");
      System.out.println("6. Unassign Task");
      System.out.println("0. Back to Main Menu");
      System.out.println("9. Logout");
      System.out.print("Choose option: ");

      String choice = app.readLinePublic();
      if (choice == null)
        return;

      switch (choice) {
        case "1":
          viewTasks();
          break;
        case "2":
          createTask();
          break;
        case "3":
          editTask();
          break;
        case "4":
          deleteTask();
          break;
        case "5":
          markTaskStatus();
          break;
        case "6":
          unassignTask();
          break;
        case "9":
          doLogout();
          return;
        case "0":
          return;
        default:
          System.out.println("[SYSTEM] Invalid option!");
      }
    }
  }

  private void viewTasks() {
    try {
      System.out.println("\n[===== YOUR TASKS =====]");

      java.util.ArrayList<Project> userProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          project.getTasks().clear();
          project.getTasks().addAll(tasks);
          java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(project.getProjectID());
          for (Member member : members) {
            project.addMember(member);
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
        System.out.println("[SYSTEM] No projects found. Create or join a project first!");
        return;
      }

      System.out.println("\n--- Filter Tasks ---");
      System.out.println("1. All Tasks");
      System.out.println("2. To Do (Pending)");
      System.out.println("3. Completed");
      System.out.println("4. Past Deadline");
      System.out.print("Choose filter (or Enter for All): ");

      String filterChoice = app.readLinePublic();
      if (filterChoice == null)
        filterChoice = "1";

      java.util.ArrayList<Task> allUserTasks = new java.util.ArrayList<>();
      for (Project project : userProjects) {
        allUserTasks.addAll(project.getTasks());
      }

      if (allUserTasks.isEmpty()) {
        System.out.println("[SYSTEM] No tasks found in your projects.");
        return;
      }

      java.util.ArrayList<Task> userTasks = new java.util.ArrayList<>();
      for (Task task : allUserTasks) {
        switch (filterChoice) {
          case "2":
            if (!task.isCompleted())
              userTasks.add(task);
            break;
          case "3":
            if (task.isCompleted())
              userTasks.add(task);
            break;
          case "4":
            if (task.isPastDeadline() && !task.isCompleted())
              userTasks.add(task);
            break;
          default:
            userTasks.add(task);
        }
      }

      if (userTasks.isEmpty()) {
        System.out.println("[SYSTEM] No tasks match the selected filter.");
        return;
      }

      System.out.printf("%-5s %-25s %-30s %-10s %-12s %-10s %-15s%n", "ID", "Project", "Title", "Priority", "Deadline",
          "Status", "Assigned To");
      System.out.println(
          "---------------------------------------------------------------------------------------------------------------");

      for (Task task : userTasks) {
        String status = task.isCompleted() ? "Completed" : "Pending";
        String deadline = task.getDeadline() != null ? task.getDeadline().toString() : "Not set";
        String assignee = getTaskAssigneeName(task, userProjects);

        String projectName = "Unknown";
        for (Project project : userProjects) {
          if (project.getTasks().contains(task)) {
            projectName = project.getTitle();
            break;
          }
        }

        if (task.isPastDeadline() && !task.isCompleted()) {
          System.out.printf("%-5d %-25s %-30s %-10s %-12s %-10s %-15s [OVERDUE]%n",
              task.getTaskId(), projectName, task.getTitle(), task.getPriority(),
              deadline, status, assignee);
        } else {
          System.out.printf("%-5d %-25s %-30s %-10s %-12s %-10s %-15s%n",
              task.getTaskId(), projectName, task.getTitle(), task.getPriority(),
              deadline, status, assignee);
        }
      }

      System.out.println("\n--- Summary ---");
      int completed = 0, pending = 0, overdue = 0;
      for (Task task : allUserTasks) {
        if (task.isCompleted())
          completed++;
        else if (task.isPastDeadline())
          overdue++;
        else
          pending++;
      }
      System.out.printf("Total: %d | To Do: %d | Completed: %d | Overdue: %d%n",
          allUserTasks.size(), pending, completed, overdue);
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error viewing tasks: " + e.getMessage());
    }
  }

  private void createTask() {
    try {
      System.out.println("\n[===== CREATE TASK =====]");

      java.util.ArrayList<Project> allProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        allProjects = app.getDbManager().getAllProjects();
      } else {
        allProjects = app.getProjectManager().getAllProjects();
      }

      java.util.ArrayList<Project> userProjects = new java.util.ArrayList<>();
      for (Project project : allProjects) {
        if (project.getOwner().getId().equals(app.getCurrentUser().getId()) ||
            project.searchMemberById(app.getCurrentUser().getId()) != null) {
          userProjects.add(project);
        }
      }

      if (userProjects.isEmpty()) {
        System.out.println("[SYSTEM] Error: No projects found. Create or join a project first!");
        return;
      }

      System.out.println("\nAvailable Projects:");
      for (Project project : userProjects) {
        System.out.println("  " + project.getProjectID() + ". " + project.getTitle());
      }

      System.out.print("\nSelect Project ID: ");
      String projectIdStr = app.readLinePublic();
      if (projectIdStr == null)
        return;

      int projectId;
      try {
        projectId = Integer.parseInt(projectIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid project ID!");
        return;
      }

      Project selectedProject = null;
      for (Project p : userProjects) {
        if (p.getProjectID() == projectId) {
          selectedProject = p;
          break;
        }
      }
      if (selectedProject == null) {
        System.out.println("[SYSTEM] Error: Project not found!");
        return;
      }

      if (!app.getCurrentUser().can("CREATE_TASK")) {
        System.out.println("[SYSTEM] Error: You don't have permission to create tasks in this project!");
        return;
      }

      System.out.print("Task Title: ");
      String title = app.readLinePublic();
      if (title == null)
        return;

      System.out.print("Priority (LOW/MEDIUM/HIGH/URGENT): ");
      String priorityStr = app.readLinePublic();
      if (priorityStr == null)
        return;
      priorityStr = priorityStr.toUpperCase();

      System.out.print("Deadline (YYYY-MM-DD) or empty for none: ");
      String deadlineStr = readDeadline();

      System.out.print("Description: ");
      String description = app.readLinePublic();
      if (description == null)
        return;

      if (title.isEmpty()) {
        System.out.println("[SYSTEM] Error: Title is required!");
        return;
      }

      Task.TaskPriority priority;
      try {
        priority = Task.TaskPriority.valueOf(priorityStr);
      } catch (IllegalArgumentException e) {
        System.out.println("[SYSTEM] Error: Invalid priority! Using MEDIUM.");
        priority = Task.TaskPriority.MEDIUM;
      }

      LocalDate deadline = null;
      if (deadlineStr != null && !deadlineStr.isEmpty()) {
        try {
          deadline = LocalDate.parse(deadlineStr);
        } catch (Exception e) {
          System.out.println("[SYSTEM] Error: Invalid date format! No deadline set.");
        }
      }

      selectedProject.addTask(app.getCurrentUser(), title, priority,
          deadline != null ? deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null,
          description);

      java.util.ArrayList<Task> tasks = selectedProject.getTasks();
      Task newTask = tasks.get(tasks.size() - 1);

      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int dbProjectId = selectedProject.getProjectID();
        boolean saved = app.getDbManager().insertTaskAndSetId(newTask, dbProjectId);
        if (saved) {
          System.out.println("[DEBUG] Task saved to database with ID: " + newTask.getTaskId());
        }
      }

      System.out.println("\n[SYSTEM] ✓ Task created successfully!");
      System.out.println("Task ID: " + newTask.getTaskId());
      System.out.println("Title: " + newTask.getTitle());
      System.out.println("Priority: " + newTask.getPriority());
      System.out.println("Deadline: " + (deadline != null ? deadline.toString() : "Not set"));
      System.out.println("Project: " + selectedProject.getTitle());

      if (selectedProject.getOwner().getId().equals(app.getCurrentUser().getId())) {
        // load data from database
        if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
          java.util.ArrayList<Member> dbMembers = app.getDbManager().getProjectMembers(selectedProject.getProjectID());
          for (Member member : dbMembers) {
            selectedProject.addMember(member);
          }
        }

        System.out.println("\n--- Assign Task ---");
        System.out.println("Project Members:");
        System.out.println("  0. Unassigned");
        System.out.println("  1. " + selectedProject.getOwner().getFirstName() + " "
            + selectedProject.getOwner().getLastName() + " (Owner)");

        int memberNum = 2;
        java.util.ArrayList<Member> members = selectedProject.getMembers();
        for (Member member : members) {
          System.out.println("  " + memberNum + ". " + member.getFirstName() + " " + member.getLastName());
          memberNum++;
        }

        System.out.print("\nAssign to (enter number): ");
        String assignChoice = app.readLinePublic();
        if (assignChoice != null) {
          try {
            int choice = Integer.parseInt(assignChoice);
            if (choice == 0) {
              System.out.println("Assigned To: Unassigned");
            } else if (choice == 1) {
              int ownerId = Integer.parseInt(selectedProject.getOwner().getId());
              newTask.setAssignToDirect(ownerId);
              System.out.println("Assigned To: " + selectedProject.getOwner().getFirstName() + " "
                  + selectedProject.getOwner().getLastName());
              if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                app.getDbManager().updateTaskAssignedTo(newTask.getTaskId(), ownerId);
              }
            } else if (choice >= 2 && choice <= members.size() + 1) {
              Member assignee = members.get(choice - 2);
              int memberId = Integer.parseInt(assignee.getId());
              newTask.setAssignToDirect(memberId);
              System.out.println("Assigned To: " + assignee.getFirstName() + " " + assignee.getLastName());
              if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                app.getDbManager().updateTaskAssignedTo(newTask.getTaskId(), memberId);
              }
            } else {
              System.out.println("[SYSTEM] Assigned To: Unassigned (invalid choice)");
            }
          } catch (NumberFormatException e) {
            System.out.println("[SYSTEM] Assigned To: Unassigned (invalid input)");
          }
        }
      } else {
        System.out.println("[SYSTEM] Assigned To: Unassigned (Only project owner can assign)");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error creating task: " + e.getMessage());
    }
  }

  private void editTask() {
    try {
      System.out.println("\n[===== EDIT TASK =====]");

      java.util.ArrayList<Project> userProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          project.getTasks().clear();
          project.getTasks().addAll(tasks);
          java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(project.getProjectID());
          for (Member member : members) {
            project.addMember(member);
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

      java.util.ArrayList<Task> allTasks = new java.util.ArrayList<>();
      System.out.println("\nYour Tasks:");
      for (Project project : userProjects) {
        for (Task task : project.getTasks()) {
          allTasks.add(task);
          String status = task.isCompleted() ? "Completed" : "Pending";
          System.out.printf("  [%d] %s (Project: %s, Status: %s)%n",
              task.getTaskId(), task.getTitle(), project.getTitle(), status);
        }
      }

      if (allTasks.isEmpty()) {
        System.out.println("[SYSTEM] No tasks found!");
        return;
      }

      System.out.print("\nEnter Task ID to edit (or -1 to cancel): ");
      String taskIdStr = app.readLinePublic();
      if (taskIdStr == null)
        return;

      int taskId;
      try {
        taskId = Integer.parseInt(taskIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid task ID!");
        return;
      }

      if (taskId == -1) {
        System.out.println("[SYSTEM] Edit cancelled.");
        return;
      }

      Task taskToEdit = null;
      for (Task task : allTasks) {
        if (task.getTaskId() == taskId) {
          taskToEdit = task;
          break;
        }
      }

      if (taskToEdit == null) {
        System.out.println("[SYSTEM] Task not found!");
        return;
      }

      System.out.println("\n1. Change Title");
      System.out.println("2. Change Priority");
      System.out.println("3. Change Deadline");
      System.out.println("4. View/Change Description");
      System.out.println("5. Change Assigned To");
      System.out.print("Choose what to edit: ");

      String choice = app.readLinePublic();
      if (choice == null)
        return;

      switch (choice) {
        case "1":
          System.out.print("New Title: ");
          String newTitle = app.readLinePublic();
          if (newTitle != null && !newTitle.isEmpty()) {
            taskToEdit.setNewTitle(newTitle);
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              if (app.getDbManager().updateTaskTitle(taskToEdit.getTaskId(), newTitle)) {
                System.out.println("[SYSTEM] ✓ Title updated in database!");
              } else {
                System.out.println("[SYSTEM] ✗ Failed to update title");
              }
            } else {
              System.out.println("[SYSTEM] Title updated (offline mode)");
            }
          }
          break;
        case "2":
          System.out.print("New Priority (LOW/MEDIUM/HIGH/URGENT): ");
          String newPriority = app.readLinePublic();
          if (newPriority != null) {
            try {
              taskToEdit.setNewPriority(Task.TaskPriority.valueOf(newPriority.toUpperCase()));
              if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                if (app.getDbManager().updateTaskPriority(taskToEdit.getTaskId(), newPriority.toLowerCase())) {
                  System.out.println("[SYSTEM] ✓ Priority updated in database!");
                } else {
                  System.out.println("[SYSTEM] ✗ Failed to update priority");
                }
              } else {
                System.out.println("[SYSTEM] Priority updated (offline mode)");
              }
            } catch (IllegalArgumentException e) {
              System.out.println("[SYSTEM] Invalid priority!");
            }
          }
          break;
        case "3":
          System.out.print("New Deadline (YYYY-MM-DD or empty): ");
          String newDeadline = readDeadline();
          if (newDeadline != null && !newDeadline.isEmpty()) {
            taskToEdit.setDeadline(LocalDate.parse(newDeadline));
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              if (app.getDbManager().updateTaskDeadline(taskToEdit.getTaskId(), newDeadline)) {
                System.out.println("[SYSTEM] ✓ Deadline updated in database!");
              } else {
                System.out.println("[SYSTEM] ✗ Failed to update deadline");
              }
            } else {
              System.out.println("[SYSTEM] Deadline updated (offline mode)");
            }
          }
          break;
        case "4":
          System.out.println("\n=== Current Description ===");
          String currentDesc = taskToEdit.getTaskDescription();
          if (currentDesc == null || currentDesc.isEmpty()) {
            System.out.println("(No description)");
          } else {
            System.out.println(currentDesc);
          }
          System.out.print("\nNew Description (or empty to keep): ");
          String newDesc = app.readLinePublic();
          if (newDesc != null && !newDesc.isEmpty()) {
            taskToEdit.setNewTaskDescription(newDesc);
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              if (app.getDbManager().updateTaskDescription(taskToEdit.getTaskId(), newDesc)) {
                System.out.println("[SYSTEM] ✓ Description updated in database!");
              } else {
                System.out.println("[SYSTEM] ✗ Failed to update description");
              }
            } else {
              System.out.println("[SYSTEM] Description updated (offline mode)");
            }
          }
          break;
        case "5":
          int currentAssignee = taskToEdit.getAssignToId();
          if (currentAssignee == 0) {
            System.out.println("Currently Assigned To: Unassigned");
          } else {
            System.out.println("Currently Assigned To: " + getTaskAssigneeName(taskToEdit, userProjects));
          }

          System.out.println("\nAssign to:");
          System.out.println("  0. Unassigned");

          Project taskProject = null;
          for (Project p : userProjects) {
            if (p.getTasks().contains(taskToEdit)) {
              taskProject = p;
              break;
            }
          }

          if (taskProject != null) {
            System.out.printf("  1. %s %s (Owner)%n",
                taskProject.getOwner().getFirstName(),
                taskProject.getOwner().getLastName());

            int memberNum = 2;
            for (Member member : taskProject.getMembers()) {
              System.out.printf("  %d. %s %s%n",
                  memberNum,
                  member.getFirstName(),
                  member.getLastName());
              memberNum++;
            }

            System.out.print("\nEnter number: ");
            String assignChoice = app.readLinePublic();
            if (assignChoice != null) {
              try {
                int choice2 = Integer.parseInt(assignChoice);
                if (choice2 == 0) {
                  taskToEdit.setAssignToDirect(0);
                  System.out.println("[SYSTEM] Task set to Unassigned");
                  if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                    app.getDbManager().updateTaskAssignedTo(taskToEdit.getTaskId(), 0);
                    System.out.println("[SYSTEM] ✓ Assignment updated in database!");
                  }
                } else if (choice2 == 1) {
                  int ownerId = Integer.parseInt(taskProject.getOwner().getId());
                  taskToEdit.setAssignToDirect(ownerId);
                  System.out.println("[SYSTEM] Assigned to: " + taskProject.getOwner().getFirstName() + " "
                      + taskProject.getOwner().getLastName());
                  if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                    app.getDbManager().updateTaskAssignedTo(taskToEdit.getTaskId(), ownerId);
                    System.out.println("[SYSTEM] ✓ Assignment updated in database!");
                  }
                } else if (choice2 >= 2 && choice2 <= taskProject.getMembers().size() + 1) {
                  Member assignee = taskProject.getMembers().get(choice2 - 2);
                  int memberId = Integer.parseInt(assignee.getId());
                  taskToEdit.setAssignToDirect(memberId);
                  System.out.println("[SYSTEM] Assigned to: " + assignee.getFirstName() + " "
                      + assignee.getLastName());
                  if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
                    app.getDbManager().updateTaskAssignedTo(taskToEdit.getTaskId(), memberId);
                    System.out.println("[SYSTEM] ✓ Assignment updated in database!");
                  }
                } else {
                  System.out.println("[SYSTEM] Invalid selection!");
                }
              } catch (NumberFormatException e) {
                System.out.println("[SYSTEM] Invalid selection!");
              }
            }
          } else {
            System.out.println("[SYSTEM] Could not find project for this task");
          }
          break;
        default:
          System.out.println("[SYSTEM] Invalid option!");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error editing task: " + e.getMessage());
    }
  }

  private void deleteTask() {
    try {
      System.out.println("\n[===== DELETE TASK =====]");

      java.util.ArrayList<Project> userProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          project.getTasks().clear();
          project.getTasks().addAll(tasks);
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
        System.out.println("[SYSTEM] Error: No projects found. Create or join a project first!");
        return;
      }

      java.util.ArrayList<Task> userTasks = new java.util.ArrayList<>();
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
        System.out.println("[SYSTEM] No tasks found in your projects.");
        return;
      }

      System.out.print("\nEnter Task ID to delete (or -1 to cancel): ");
      String taskIdStr = app.readLinePublic();
      if (taskIdStr == null)
        return;

      int taskId;
      try {
        taskId = Integer.parseInt(taskIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid task ID!");
        return;
      }

      if (taskId == -1) {
        System.out.println("[SYSTEM] Delete task cancelled.");
        return;
      }

      boolean deleted = false;
      for (Project project : userProjects) {
        if (project.getOwner().getId().equals(app.getCurrentUser().getId())
            || app.getCurrentUser().can("DELETE_TASK")) {
          if (project.removeTaskByID(app.getCurrentUser(), taskId)) {
            deleted = true;
            if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
              app.getDbManager().removeTask(taskId);
            }
            break;
          }
        }
      }

      if (deleted) {
        System.out.println("\n[SYSTEM] ✓ Task deleted successfully!");
      } else {
        System.out.println("\n[SYSTEM] ✗ Failed to delete task. Task not found.");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error deleting task: " + e.getMessage());
    }
  }

  private void markTaskStatus() {
    try {
      System.out.println("\n[===== MARK TASK STATUS =====]");

      java.util.ArrayList<Project> userProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          project.getTasks().clear();
          project.getTasks().addAll(tasks);
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

      java.util.ArrayList<Task> allTasks = new java.util.ArrayList<>();
      System.out.println("\nYour Tasks:");
      for (Project project : userProjects) {
        for (Task task : project.getTasks()) {
          allTasks.add(task);
          String status = task.isCompleted() ? "Completed" : "Pending";
          System.out.printf("  [%d] %s - %s%n",
              task.getTaskId(), task.getTitle(), status);
        }
      }

      if (allTasks.isEmpty()) {
        System.out.println("[SYSTEM] No tasks found!");
        return;
      }

      System.out.print("\nEnter Task ID to toggle status (or -1 to cancel): ");
      String taskIdStr = app.readLinePublic();
      if (taskIdStr == null)
        return;

      int taskId;
      try {
        taskId = Integer.parseInt(taskIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid task ID!");
        return;
      }

      if (taskId == -1) {
        System.out.println("[SYSTEM] Cancelled.");
        return;
      }

      Task taskToToggle = null;
      for (Task task : allTasks) {
        if (task.getTaskId() == taskId) {
          taskToToggle = task;
          break;
        }
      }

      if (taskToToggle == null) {
        System.out.println("[SYSTEM] Task not found!");
        return;
      }

      if (taskToToggle.isCompleted()) {
        taskToToggle.markIncomplete(app.getCurrentUser());
        System.out.println("\n[SYSTEM] ✓ Task marked as Pending");
        if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
          app.getDbManager().updateTaskStatus(taskToToggle.getTaskId(), "todo");
          System.out.println("[SYSTEM] ✓ Status updated in database!");
        }
      } else {
        taskToToggle.markCompleted(app.getCurrentUser());
        System.out.println("\n[SYSTEM] ✓ Task marked as Completed");
        if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
          app.getDbManager().updateTaskStatus(taskToToggle.getTaskId(), "done");
          System.out.println("[SYSTEM] ✓ Status updated in database!");
        }
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error: " + e.getMessage());
    }
  }

  private String readDeadline() {
    String deadlineStr;
    do {
      deadlineStr = app.readLinePublic();
      if (deadlineStr == null || deadlineStr.isEmpty()) {
        return "";
      }
      if (!deadlineStr.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
        System.out.println("[SYSTEM] Invalid format. Please use YYYY-MM-DD or empty to cancel: ");
        continue;
      }
      try {
        LocalDate deadline = LocalDate.parse(deadlineStr);
        if (deadline.isBefore(LocalDate.now())) {
          System.out
              .println("[SYSTEM] Deadline cannot be in the past. Please enter a future date or empty to cancel: ");
          continue;
        }
        return deadlineStr;
      } catch (Exception e) {
        System.out.println("[SYSTEM] Invalid date. Please use YYYY-MM-DD or empty to cancel: ");
      }
    } while (true);
  }

  private String getTaskAssigneeName(Task task, java.util.ArrayList<Project> userProjects) {
    int assignToId = task.getAssignToId();
    if (assignToId == 0) {
      return "Unassigned";
    }
    String assignToStr = String.valueOf(assignToId);

    for (Project project : userProjects) {
      Member member = project.searchMemberById(assignToStr);
      if (member != null) {
        return member.getFirstName() + " " + member.getLastName();
      }
    }

    for (Project project : userProjects) {
      try {
        int ownerId = Integer.parseInt(project.getOwner().getId());
        if (ownerId == assignToId) {
          return project.getOwner().getFirstName() + " " + project.getOwner().getLastName();
        }
      } catch (NumberFormatException e) {
        if (project.getOwner().getId().equals(assignToStr)) {
          return project.getOwner().getFirstName() + " " + project.getOwner().getLastName();
        }
      }
    }

    return "Unknown (ID: " + assignToId + ")";
  }

  private void unassignTask() {
    try {
      System.out.println("\n[===== UNASSIGN TASK =====]");

      if (!app.getCurrentUser().can("ASSIGN_TASK")) {
        System.out.println("[SYSTEM] Error: Only project owners can unassign tasks!");
        return;
      }

      java.util.ArrayList<Project> userProjects;
      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        int userId = Integer.parseInt(app.getCurrentUser().getId());
        userProjects = app.getDbManager().getUserProjects(userId);
        for (Project project : userProjects) {
          java.util.ArrayList<Task> tasks = app.getDbManager().getProjectTasks(project.getProjectID());
          project.getTasks().clear();
          project.getTasks().addAll(tasks);
          java.util.ArrayList<Member> members = app.getDbManager().getProjectMembers(project.getProjectID());
          for (Member member : members) {
            project.addMember(member);
          }
        }
      } else {
        java.util.ArrayList<Project> allProjects = app.getProjectManager().getAllProjects();
        userProjects = new java.util.ArrayList<>();
        for (Project project : allProjects) {
          if (project.getOwner().getId().equals(app.getCurrentUser().getId())) {
            userProjects.add(project);
          }
        }
      }

      if (userProjects.isEmpty()) {
        System.out.println("[SYSTEM] Error: No projects found!");
        return;
      }

      java.util.ArrayList<Task> allTasks = new java.util.ArrayList<>();
      System.out.println("\nAssigned Tasks:");
      for (Project project : userProjects) {
        for (Task task : project.getTasks()) {
          if (task.getAssignToId() != 0) {
            allTasks.add(task);
            String assignee = getTaskAssigneeName(task, userProjects);
            System.out.printf("  [%d] %s (Project: %s, Assigned to: %s)%n",
                task.getTaskId(), task.getTitle(), project.getTitle(), assignee);
          }
        }
      }

      if (allTasks.isEmpty()) {
        System.out.println("[SYSTEM] No assigned tasks found!");
        return;
      }

      System.out.print("\nEnter Task ID to unassign (or -1 to cancel): ");
      String taskIdStr = app.readLinePublic();
      if (taskIdStr == null)
        return;

      int taskId;
      try {
        taskId = Integer.parseInt(taskIdStr);
      } catch (NumberFormatException e) {
        System.out.println("[SYSTEM] Error: Invalid task ID!");
        return;
      }

      if (taskId == -1) {
        System.out.println("[SYSTEM] Unassign cancelled.");
        return;
      }

      Task taskToUnassign = null;
      for (Task task : allTasks) {
        if (task.getTaskId() == taskId) {
          taskToUnassign = task;
          break;
        }
      }

      if (taskToUnassign == null) {
        System.out.println("[SYSTEM] Task not found!");
        return;
      }

      String oldAssignee = getTaskAssigneeName(taskToUnassign, userProjects);
      taskToUnassign.setAssignToDirect(0);
      System.out.println("[SYSTEM] ✓ Task unassigned from: " + oldAssignee);

      if (app.isUseDatabase() && app.getDbManager() != null && app.getDbManager().isConnected()) {
        app.getDbManager().updateTaskAssignedTo(taskToUnassign.getTaskId(), 0);
        System.out.println("[SYSTEM] ✓ Database updated!");
      }
    } catch (Exception e) {
      System.out.println("[SYSTEM] Error unassigning task: " + e.getMessage());
    }
  }

  private void doLogout() {
    app.logout();
    System.out.println("\n[SYSTEM] Logged out successfully!");
  }
}

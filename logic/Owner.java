package logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Owner extends Member {

  private static int totalOwners = 0;
  private Project project;

  public Owner(String firstName, String lastName, String username, String email, String password) {
    super(firstName, lastName, email, username, password);
    totalOwners++;
  }

  public Owner(int id, String firstName, String lastName, String email, String username, String password) {
    super(id, firstName, lastName, email, username, password);
    totalOwners++;
  }

  public static int getTotalOwners() {
    return totalOwners;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public String getRole() {
    return "Owner";
  }

  @Override
  public boolean can(String action) {
    switch (action) {
      case "VIEW_TASK":
      case "UPDATE_OWN_TASK":
      case "CREATE_TASK":
      case "CREATE_PROJECT":
      case "DELETE_TASK":
      case "ASSIGN_TASK":
      case "CREATE_USER":
      case "VIEW_REPORT":
        return true;
      default:
        return true;
    }
  }

  public void assignTask(Task task, Project project, int memberId, LocalDate deadline, LocalTime time, IUser user) {
    if (task == null || project == null || deadline == null || memberId <= 0) {
      System.out.println("Invalid task assignment parameters");
      return;
    }

    if (!user.can("ASSIGN_TASK")) {
      System.out.println("User does not have permission to assign tasks");
      return;
    }

    if (deadline.isBefore(LocalDate.now())) {
      System.out.println("Cannot assign task: deadline is in the past");
      return;
    }

    task.setDeadline(deadline);
    task.setAssignTo(memberId, user);
    project.getTasks().add(task);
  }

  public void unassignTask(Task task, IUser user) {
    if (task == null) {
      System.out.println("Task cannot be null");
      return;
    }
    task.setAssignTo(0, user);
  }

  @Override
  public String toString() {
    return super.toString() + "owned" + project;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Owner && super.equals(obj);
  }
}

package logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Owner extends Member {

  private static int totalOwners = 0;
  private Project project;

  public Owner(String firstName, String lastName, String username, String email, String password) {
    super(firstName, lastName, email, username, password);
    ++totalOwners;
  }

  // Constructor for database users (with existing ID)
  // public Owner(int id, Member m) {
  // this.id = id;
  // super(m.getFirstName(), m.getLastName(), m.getEmail(), m.getUsername(),
  // m.getPassword());
  // }

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
  public boolean can(String action) {
    switch (action) {
      case "VIEW_TASK":
        return true;
      case "UPDATE_OWN_TASK":
        return true;
      case "CREATE_TASK":
        return true; // All users can create tasks
      case "CREATE_PROJECT":
        return true; // All users can create projects
      case "DELETE_TASK":
        return true;
      case "ASSIGN_TASK":
        return true;
      case "CREATE_USER":
        return true;
      case "VIEW_REPORT":
        return true;
      default:
        return true;
    }

  }

  public void assignTask(Task task, Project project, int memberId, LocalDate deadline, LocalTime time, IUser user) {
    if (task == null) {
      System.out.println("Task cannot be null");
      return;
    }

    if (project == null) {
      System.out.println("Project cannot be null");
      return;
    }

    if (!user.can("ASSIGN_TASK")) {
      System.out.println("User does not have permission to assign tasks");
      return;
    }

    if (deadline == null) {
      System.out.println("Deadline cannot be null");
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
    if (obj instanceof Owner) {
      return super.equals(obj);
    }
    return false;
  }
}

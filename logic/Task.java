package logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

  private static int nextTaskId = 1;

  public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
  }

  private int taskID;
  private int assignTo;
  private LocalDate deadline;
  private String title;
  private String taskDescription;
  private TaskPriority priority;
  private boolean completed;

  public Task(String title, TaskPriority priority, LocalDate deadline, String taskDescription) {
    this.taskID = nextTaskId++;
    setNewTitle(title);
    setNewPriority(priority);
    setDeadline(deadline);
    setNewTaskDescription(taskDescription);
    this.completed = false;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public int getTaskId() {
    return taskID;
  }

  public void setTaskId(int taskId) {
    this.taskID = taskId;
  }

  public int getAssignTo() {
    return assignTo;
  }

  public int getAssignToId() {
    return assignTo;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public String getDeadlineString() {
    return deadline != null ? deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "Not set";
  }

  public String getTitle() {
    return title;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public TaskPriority getPriority() {
    return priority;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setNewPriority(TaskPriority p) {
    if (p != null) {
      this.priority = p;
    }
  }

  public void setNewTitle(String t) {
    if (t == null || t.trim().isEmpty()) {
      System.out.println("Title cannot be null or empty");
      return;
    }
    String sanitizedTitle = t.trim();
    if (sanitizedTitle.length() > 255) {
      System.out.println("Title exceeds maximum length of 255 characters");
      return;
    }
    this.title = sanitizedTitle;
  }

  public void setNewTaskDescription(String description) {
    if (description == null) {
      System.out.println("Task description cannot be null");
      return;
    }
    String sanitizedDescrip = description.trim();
    if (sanitizedDescrip.length() > 10000) {
      System.out.println("Task description exceeds maximum length of 10000 characters");
      return;
    }
    this.taskDescription = sanitizedDescrip;
  }

  public void setAssignTo(int memberId, IUser user) {
    if (!user.can("ASSIGN_TASK")) {
      System.out.println("User does not have permission to assign tasks");
      return;
    }
    if (memberId <= 0) {
      System.out.println("Invalid member ID");
      return;
    }
    this.assignTo = memberId;
  }

  public void setAssignToDirect(int memberId) {
    this.assignTo = memberId;
  }

  public void unassign() {
    this.assignTo = 0;
  }

  public void markCompleted(IUser user) {
    if (user.can("UPDATE_OWN_TASK")) {
      this.completed = true;
    }
  }

  public void markIncomplete(IUser user) {
    if (user.can("UPDATE_OWN_TASK")) {
      this.completed = false;
    }
  }

  public boolean isPastDeadline() {
    return deadline != null && deadline.isBefore(LocalDate.now());
  }

  @Override
  public String toString() {
    return "Title: " + title + "\n" +
        "Priority: " + priority + "\n" +
        "Deadline: " + getDeadlineString() + "\n" +
        "Assigned To: " + (assignTo > 0 ? assignTo : "Unassigned") + "\n" +
        "Status: " + (completed ? "Completed" : "Pending") + "\n" +
        "Description: " + taskDescription;
  }
}

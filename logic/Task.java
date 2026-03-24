package logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Task - Represents a task in the project management system
 */
public class Task {

  private static int nextTaskId = 1;

  /**
   * TaskPriority - Enum representing the priority levels of a task
   */
  public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
  }

  private int taskID;
  private int assignTo; // Member ID (from database)
  private LocalDate deadline;
  private String title;
  private String taskDescription;
  private TaskPriority priority;
  private boolean completed;

  /**
   * Constructor for Task
   */
  public Task(String title, TaskPriority priority, LocalDate deadline, String taskDescription) {
    this.taskID = nextTaskId++;
    setNewTitle(title);
    setNewPriority(priority);
    setDeadline(deadline);
    setNewTaskDescription(taskDescription);
    this.completed = false;
  }

  /**
   * Set deadline for the task
   */
  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  // ==================== Getters ====================

  public int getTaskId() {
    return this.taskID;
  }

  public void setTaskId(int taskId) {
    this.taskID = taskId;
  }

  public int getAssignTo() {
    if (this.assignTo == 0) {
      System.out.println("Task Assigned to no one!");
      return 0;
    } else {
      return this.assignTo;
    }
  }

  public LocalDate getDeadline() {
    return this.deadline;
  }

  public String getDeadlineString() {
    if (this.deadline == null) {
      return "Not set";
    }
    return this.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  public String getTitle() {
    return this.title;
  }

  public String getTaskDescription() {
    return this.taskDescription;
  }

  public TaskPriority getPriority() {
    return this.priority;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  // ==================== Setters ====================

  public void setNewPriority(TaskPriority p) {
    try {
      if (p == null) {
        System.out.println("Priority cannot be null");
        return;
      }
      this.priority = p;
    } catch (Exception e) {
      System.out.println("Error setting priority: " + e.getMessage());
    }
  }

  public void setNewTitle(String t) {
    try {
      if (t == null) {
        System.out.println("Title cannot be null");
        return;
      }

      String sanitizedTitle = t.trim();

      if (sanitizedTitle.length() > 255) {
        System.out.println("Title exceeds maximum length of 255 characters");
        return;
      }

      if (sanitizedTitle.isEmpty()) {
        System.out.println("Title cannot be empty");
        return;
      }

      this.title = sanitizedTitle;
    } catch (Exception e) {
      System.out.println("Error setting title: " + e.getMessage());
    }
  }

  public void setNewTaskDescription(String description) {
    try {
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
    } catch (Exception e) {
      System.out.println("Error setting task description: " + e.getMessage());
    }
  }

  public void setAssignTo(int memberId, IUser user) {
    try {
      if (!user.can("ASSIGN_TASK")) {
        System.out.println("User does not have permission to assign tasks");
        return;
      }
      if (memberId <= 0) {
        System.out.println("Invalid member ID");
        return;
      }
      this.assignTo = memberId;
    } catch (Exception e) {
      System.out.println("Error assigning task: " + e.getMessage());
    }
  }

  public int getAssignToId() {
    return this.assignTo;
  }

  public void setAssignToDirect(int memberId) {
    this.assignTo = memberId;
  }

  public void unassign() {
    try {
      this.assignTo = 0;
    } catch (Exception e) {
      System.out.println("Error unassigning task: " + e.getMessage());
    }
  }

  public void markCompleted(IUser user) {
    try {
      if (user.can("UPDATE_OWN_TASK"))
        this.completed = true;
    } catch (Exception e) {
      System.out.println("Error marking task completed: " + e.getMessage());
    }
  }

  public void markIncomplete(IUser user) {
    try {
      if (user.can("UPDATE_OWN_TASK"))
        this.completed = false;
    } catch (Exception e) {
      System.out.println("Error marking task incomplete: " + e.getMessage());
    }
  }

  // ==================== Utility Methods ====================

  /**
   * Check if the task is past its deadline
   */
  public boolean isPastDeadline() {
    if (this.deadline == null) {
      return false;
    }
    return this.deadline.isBefore(LocalDate.now());
  }

  // ==================== Override ====================

  @Override
  public String toString() {
    return "Title: " + title + "\n" +
        "Priority: " + priority + "\n" +
        "Deadline: " + getDeadlineString() + "\n" +
        "Assigned To: " + (assignTo > 0 ? String.valueOf(assignTo) : "Unassigned") + "\n" +
        "Status: " + (completed ? "Completed" : "Pending") + "\n" +
        "Description: " + taskDescription;
  }
}

package model;

import java.time.LocalDate;

public class Task {
  private int taskId;
  private int projectId;
  private Integer assignedTo;
  private String title;
  private String status;
  private String priority;
  private LocalDate dueDate;

  public Task() {

  }
  // Constructor for creating new task before insert to data basse
  public Task(int projectId, Integer assignedTo, String title, String status, String priority, LocalDate dueDate) {
    this.projectId = projectId;
    this.assignedTo = assignedTo;
    this.title = status;
    this.deadLine = priority;
    this.dueDate = dueDate
  }

  // Getters and Setters
  public int getTaskId() {
    return taskId;
  }

  public void setTaskID(int taskId) {
    this.taskId = taskId;
  }

  public int getProjectId() {
    return projectId;
  }

  public void setProjectId(int projectId) {
    this.projectId;
  }

  public Integer getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(Integer assignedTo) {
    this.assignedTo = assignedTo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPriority() {
    return priority;
  }

  public void setStatus(String status) {
    this.priority = priority;
  }

  public LocalDate dueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

}

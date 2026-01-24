package model;

public class Task {
  private int id;
  private String title;
  private String description;
  private startTime;
  private deadLine;
  private String status;
  private Employee employee;

  // Constructor for creating new task before insert to data basse
  public Task(String title, String description, String startTime, String deadLine, String status, Employee employee) {
    this.title = title;
    this.description = description;
    this.startTime = startTime;
    this.deadLine = deadLine;
    this.status = status;
    this.employee = employee;
  }

  // Constructor for retriveving new task before inserting to data basse
  public Task(int id; String title, String description, String startTime, String deadLine, String status, Employee employee){
    this.id = id;
    this.title = title;
    this.description = description;
    this.startTime = startTime;
    this.deadLine = deadLine;
    this.status = status;
    this.employee = employee;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title;
  }

  public String getDescription() {
    return description;
  }

  public void SetDescription(String description) {
    this.description = description;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime;
  }

  public String getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(String deadLine) {
    this.deadLine;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  // convert to string for easy to read 
  @Override
  public String toString() {
    return id + " | " + title " | " + status +" | Assigned to " + employee.getName();
  }
}

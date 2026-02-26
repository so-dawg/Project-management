package logic;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

enum Task_Priority {
  LOW, MEDIUM, HIGH, URGENT
}

public class Task {
  private int assign_to;
  private String deadline;
  private String title;
  private String task_description;
  private Task_Priority priority;
  private int day;
  private int month;
  private int year;
  private boolean completed = false;


  public Task(String deadline, Task_Priority priority, String title, int assign_to, String task_description, int day, int month, int year){
    this.priority = priority;
    this.day = day;
    this.month = month;
    this.year = year;
    this.title = title;
    this.task_description = task_description;
    this.assign_to =  assign_to;
  }

  //getter
  public String getTitle(){
    return this.title;
  }
  
  public String getAssignTo(){
    //need to check database all member in project to get name
    return String.valueOf(this.assign_to);
  }

  public String getDeadline(){
    return this.deadline;
  }

  public String getTaskDescription(){
    return this.task_description;
  }



  //setter
  public void setNewPriority(Task_Priority p){
    if (p == null) {
      System.out.println("Priority cannot be null");
    }
    this.priority = p;
  }

  public void setNewTitle(String t){
    if (t == null) {
      System.out.println("Title cannot be null");
    }

    String sanitizedTitle = t.trim();

    // Validate length
    if (sanitizedTitle.length() > 255) {
      System.out.println("Title exceeds maximum length of 255 characters");
    }

    // Prevent empty titles
    if (sanitizedTitle.isEmpty()) {
      System.out.println("Title cannot be empty");
    }

    this.title = sanitizedTitle;
  }

  public void completedTask(){
    this.completed = true;
  }

  public void setNewTaskDescrip(String Descrip){
    if (Descrip == null) {
      System.out.println("Task description cannot be null");
    }

    // Sanitize input to prevent XSS and injection attacks
    String sanitizedDescrip = Descrip.trim();

    // Validate length (adjust max length as needed)
    if (sanitizedDescrip.length() > 10000) {
      System.out.println("Task description exceeds maximum length of 10000 characters");
    }

    this.task_description = sanitizedDescrip;
  }


  public static boolean isPastDeadline(int year, int month, int day){
    LocalDate deadlineDate = LocalDate.of(year, month, day);
    LocalDate today = LocalDate.now();

    return deadlineDate.isBefore(today);
  }

  public static String convertIntToString(int[] values){
    return Arrays.stream(values)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("-"));
  }


  // @Override
  // public String toString() {
  //   return "Status: " + status + "\n" +
  //          "Priority: " + priority + "\n" ;
           
  // }

  // public static void main(String[] args) {
  //   Task t = new Task("coding", "tra", Task_Priority.HIGH, task_status.PENDING, "2026-12-14");

  //   t.set_priority(Task_Priority.LOW);
  //   System.out.println(t.toString());
  // }
}

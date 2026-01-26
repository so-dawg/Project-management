<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import java.time.localDate;
=======
import java.time.localDate;
>>>>>>> origin/main
>>>>>>> a7ea37b (clean all files)

enum task_status {
  PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}

enum Task_Priority {
  LOW, MEDIUM, HIGH, URGENT
}

public class task {
  private localDate deadline;
  private int project_id;
  private task_status status;
  private Task_Priority priority;
  private String title;
  private String assigned_to;

  public task(String title, String assigned_to, Task_Priority priority, task_status status, int project_id, localDate deadline){
    this.title = title;
    this.assigned_to = assigned_to;
    this.status = status;
    this.priority = priority;
    this.deadline = deadline;
    this.project_id = project_id;
  }
  
  public void addTask(String title, String assigned_to, Task_Priority priority, task_status status, int project_id, localDate deadline){
    this.title = title;
    this.assigned_to = assigned_to;
    this.status = status;
    this.priority = priority;
    this.deadline = deadline;
    this.project_id = project_id;
  }

  public String get_name() {
    return title;
  }
  public String get_assigned_to() {
    return assigned_to;
  }
  public localDate get_deadline() {
    return deadline;
  }
  public task_status get_status() {
    return status;
  }
  public Task_Priority get_priority() {
    return priority;
  }

  
  public void set_name(String title) {
    this.title = title;
  }
  public void set_assigned_to(String assigned_to) {
    this.assigned_to = assigned_to;
  }
  public void set_deadline(localDate deadline) {
    this.deadline = deadline;
  }
  public void set_status(task_status status) {
    this.status = status;
  }
  public void set_priority(Task_Priority priority) {
    this.priority = priority;
  }
}

=======
=======
>>>>>>> caa7927 (write function)
<<<<<<< HEAD
package logic;

public class task {
    member taskMem = new member(null, 0);
    public static void main(String[] name){
        System.out.println(taskMem.name);
    }
    
}
=======
public class task {
  int deadline;
  int project_id;
  enum status;
  enum priority;
  String title;
  String assigned_to;
=======
import java.time.localDate;
>>>>>>> 200c870 (write function)

enum task_status {
  PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}

enum Task_Priority {
  LOW, MEDIUM, HIGH, URGENT
}

public class task {
  private localDate deadline;
  private int project_id;
  private task_status status;
  private Task_Priority priority;
  private String title;
  private String assigned_to;

  public task(String title, String assigned_to, Task_Priority priority, task_status status, int project_id, localDate deadline){
    this.title = title;
    this.assigned_to = assigned_to;
    this.status = status;
    this.priority = priority;
    this.deadline = deadline;
    this.project_id = project_id;
  }
  
  public void addTask(String title, String assigned_to, Task_Priority priority, task_status status, int project_id, localDate deadline){
    this.title = title;
    this.assigned_to = assigned_to;
    this.status = status;
    this.priority = priority;
    this.deadline = deadline;
    this.project_id = project_id;
  }

  public String get_name() {
    return title;
  }
  public String get_assigned_to() {
    return assigned_to;
  }
  public localDate get_deadline() {
    return deadline;
  }
  public task_status get_status() {
    return status;
  }
  public Task_Priority get_priority() {
    return priority;
  }

  
  public void set_name(String title) {
    this.title = title;
  }
  public void set_assigned_to(String assigned_to) {
    this.assigned_to = assigned_to;
  }
  public void set_deadline(localDate deadline) {
    this.deadline = deadline;
  }
  public void set_status(task_status status) {
    this.status = status;
  }
  public void set_priority(Task_Priority priority) {
    this.priority = priority;
  }
}
>>>>>>> d9cac3c (init commit)
>>>>>>> 56dc88a (init commit)

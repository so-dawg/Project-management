package logic;
enum task_status {
  PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}

enum Task_Priority {
  LOW, MEDIUM, HIGH, URGENT
}

public class Task {
  private int assign_to;
  private String title;
  private String task_description;
  private String deadline;
  private task_status status.PENDING;
  private Task_Priority priority;
  

  public addTask(Task_Priority priority, String deadline, String title, int assign_to, String task_description){
    this.priority = priority;
    this.deadline = deadline;
    this.title = title;
    this.task_description = task_description;
    this.assign_to =  assign_to;
  }

  //getter
  public String getTitle(){
    return this.title;
  }
  
  public String getAssignTo(){
    String name;
    //need to check database all member in project to get name
    return name;
  }

  public String getDeadline(){
    return this.deadline;
  }

  public String getTaskDescription(){
    return this.task_description;
  }



  //setter
  public void setNewPriority(Task_Priority p){
    this.priority = p;
  }

  public void setNewTitle(String t){
    this.title = t;
  }

  public void setNewTaskDescrip(String Descrip){
    this.task_description = Descrip;
  }

  public void changeTaskStatus(task_status s){
    this.status = s;
  }

  private void setAssignTo(int id){
    this.assign_to = id;
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

package logic;
enum task_status {
  PENDING, IN_PROGRESS, COMPLETED, CANCELLED
}

 enum Task_Priority {
  LOW, MEDIUM, HIGH, URGENT
}

public class Task {
  private Owner get_ownerprompt;
  private task_status status;
  private Task_Priority priority;
  

  public Task(Owner get_ownerprompt,Task_Priority priority, task_status status){
    this.get_ownerprompt = get_ownerprompt;
    this.status = status;
    this.priority = priority;
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

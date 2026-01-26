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

  public addTask(String title, String assigned_to, enum priority, enum status, int project_id, int deadline){
    this.title = title;
    this.assigned_to = assigned_to;
    this.status = status;
    this.priority = priority;
    this.deadline = deadline;
    this.project_id = project_id;
  }
  
}
>>>>>>> d9cac3c (init commit)

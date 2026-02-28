package logic;
import logic.Task;
import logic.User;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Project {

  private ArrayList<Task> tasks = new ArrayList<>();
  private User users = new User();
  private String title;
  private String projectDescription;
  private int projectID;
  private int numMember;

  public Project(String title, String projectDescription) {
    this.numMember = 1;
    this.title = title;
    this.projectDescription = projectDescription;
  }

  public void addTask(String title, Task.TaskPriority priority, String deadline, String taskDescription, int assignTo){
    LocalDate date = null;
    if (deadline != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(deadline, formatter);
    }
    Task task = new Task(title, priority, date, assignTo, taskDescription);
    this.tasks.add(task);
  }

  
}

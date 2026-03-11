package logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {

  private static int nextProjectID = 1;
  private ArrayList<Task> tasks = new ArrayList<>();
  private ArrayList<Member> members = new ArrayList<>();
  private Owner owner;
  private String title;
  private String projectDescription;
  private int projectID;

  public Project(String title, String projectDescription, Owner owner) {
    this.projectID = nextProjectID++;
    this.owner = owner;
    this.title = title;
    this.projectDescription = projectDescription;
  }

  public boolean addMemberById(String userId, User userRegistry) {
    IUser user = userRegistry.searchUserById(userId);
    if (user != null) {
      members.add((Member) user);
      return true;
    }
    System.out.println("User with ID '" + userId + "' not found");
    return false;
  }

  public boolean addMemberByName(String username, User userRegistry) {
    IUser user = userRegistry.searchUserByUsername(username);
    if (user != null) {
      members.add((Member) user);
      return true;
    }
    System.out.println("User with username '" + username + "' not found");
    return false;
  }

  public boolean removeMemberById(String memberId) {
    Member member = searchMemberById(memberId);
    if (member != null) {
      members.remove(member);
      return true;
    }
    return false;
  }

  public boolean removeMemberByName(String username) {
    Member member = searchMemberByName(username);
    if (member != null) {
      members.remove(member);
      return true;
    }
    return false;
  }

  public Member searchMemberById(String memberId) {
    for (Member member : members) {
      if (member.getId().equals(memberId)) {
        return member;
      }
    }
    return null;
  }

  public Member searchMemberByName(String username) {
    for (Member member : members) {
      if (member.getUsername().equals(username)) {
        return member;
      }
    }
    return null;
  }

  public ArrayList<Member> getMembers() {
    return members;
  }

  public Owner getOwner() {
    return owner;
  }

  public void addTask(IUser user, String title, Task.TaskPriority priority, String deadline, String taskDescription) {
    LocalDate date = null;
    if (deadline != null && user.can("ASSIGN_TASK")) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      date = LocalDate.parse(deadline, formatter);
    }
    Task task = new Task(title, priority, date, taskDescription);
    this.tasks.add(task);
  }

  public boolean removeTaskByIndex(IUser user, int index) {
    if (index >= 0 && index < tasks.size() && user.can("DELETE_TASK")) {
      tasks.remove(index);
      return true;
    }
    return false;
  }

  public boolean removeTaskByID(IUser user, int id) {
    for (Task task : tasks) {
      if (task.getTaskId() == id && user.can("DELETE_TASK")) {
        tasks.remove(task);
        return true;
      }
    }
    return false;
  }

  public Task getTask(IUser user, int index) {
    if (index >= 0 && index < tasks.size() && user.can("VIEW_TASK")) {
      return tasks.get(index);
    }
    return null;
  }

  public ArrayList<Task> getTasks() {
    return tasks;
  }

  // getter
  public String getTitle() {
    return title;
  }

  public String getProjectDescription() {
    return projectDescription;
  }

  public int getNumMember() {
    return members.size();
  }

  public int getProjectID() {
    return projectID;
  }

  public int getTaskCount() {
    return tasks.size();
  }

  public void setTitle(IUser user, String title) {
    if (title.length() <= 255 && user.can("CREATE_PROJECT")) {
      this.title = title;
    } else {
      System.out.println("Error, invalid input!");
    }
  }

  public void setDescript(IUser user, String des) {
    if (des.length() <= 500 && user.can("CREATE_TASK")) {
      this.projectDescription = des;
    } else {
      System.out.println("Error, invalid input!");
    }
  }
}

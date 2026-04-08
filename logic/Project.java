package logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Project {

  private static int nextProjectID = 1;
  private final ArrayList<Task> tasks = new ArrayList<>();
  private final ArrayList<Member> members = new ArrayList<>();
  private final IUser owner;
  private final String title;
  private final String projectDescription;
  private final int projectID;

  public Project(String title, String projectDescription, IUser owner) {
    this.projectID = nextProjectID++;
    this.owner = owner;
    this.title = title;
    this.projectDescription = projectDescription;
  }

  public boolean addMemberById(String userId, UserRegistry userRegistry) {
    try {
      // Check if already a member
      if (searchMemberById(userId) != null) {
        return false;  // Already a member
      }
      IUser user = userRegistry.searchUserById(userId);
      if (user instanceof Member) {
        members.add((Member) user);
        return true;
      }
      System.out.println("User is not a Member instance");
      return false;
    } catch (ClassCastException e) {
      System.out.println("Error casting user to Member: " + e.getMessage());
      return false;
    }
  }

  public boolean addMemberByName(String username, UserRegistry userRegistry) {
    try {
      // Check if already a member
      if (searchMemberByName(username) != null) {
        return false;  // Already a member
      }
      IUser user = userRegistry.searchUserByUsername(username);
      if (user instanceof Member) {
        members.add((Member) user);
        return true;
      }
      System.out.println("User is not a Member instance");
      return false;
    } catch (ClassCastException e) {
      System.out.println("Error casting user to Member: " + e.getMessage());
      return false;
    }
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

  public boolean addMember(Member member) {
    if (member != null && !members.contains(member)) {
      members.add(member);
      return true;
    }
    return false;
  }

  public IUser getOwner() {
    return owner;
  }

  public void addTask(IUser user, String title, Task.TaskPriority priority, String deadline, String taskDescription) {
    try {
      LocalDate date = null;
      if (deadline != null && user.can("ASSIGN_TASK")) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        date = LocalDate.parse(deadline, formatter);
      }
      Task task = new Task(title, priority, date, taskDescription);
      tasks.add(task);
    } catch (Exception e) {
      System.out.println("Error adding task: " + e.getMessage());
    }
  }

  public boolean removeTaskByIndex(IUser user, int index) {
    if (index >= 0 && index < tasks.size() && user.can("DELETE_TASK")) {
      tasks.remove(index);
      return true;
    }
    return false;
  }

  public boolean removeTaskByID(IUser user, int id) {
    if (!user.can("DELETE_TASK")) {
      return false;
    }
    for (int i = 0; i < tasks.size(); i++) {
      if (tasks.get(i).getTaskId() == id) {
        tasks.remove(i);
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

  /**
   * Set project ID directly (used when loading from database)
   * Note: This uses reflection to modify the final field
   */
  public void setProjectIdDirect(int projectId) {
    try {
      java.lang.reflect.Field field = Project.class.getDeclaredField("projectID");
      field.setAccessible(true);
      field.setInt(this, projectId);
    } catch (Exception e) {
      System.out.println("Error setting project ID: " + e.getMessage());
    }
  }

  public int getTaskCount() {
    return tasks.size();
  }
}

package logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Owner extends Member {

  private static int totalOwners = 0;

  public Owner(Member m) {
    super(m.getFirstName(), m.getLastName(), m.getEmail(), m.getUsername(), m.getPassword());
    ++totalOwners;
  }

  // Constructor for database users (with existing ID)
//   public Owner(int id, Member m) {
//     this.id = id;
//     super(m.getFirstName(), m.getLastName(), m.getEmail(), m.getUsername(), m.getPassword());
//   }

  public static int getTotalOwners() {
    return totalOwners;
  }

  @Override
  public boolean can(String action) {
    // All users have the same permissions
    return true;
  }
  
  public void assignTask(Task task, int memberId, LocalDate deadline, LocalTime time) {
    if (task == null) {
      System.out.println("Task cannot be null");
      return;
    }

    if (deadline == null) {
      System.out.println("Deadline cannot be null");
      return;
    }

    // Check if deadline is in the past
    if (deadline.isBefore(LocalDate.now())) {
      System.out.println("Cannot assign task: deadline is in the past");
      return;
    }

    task.setDeadline(deadline);
    task.setAssignTo(memberId);
  }

  public void assignTask(Task task, int memberId) {
    if (task == null) {
      System.out.println("Task cannot be null");
      return;
    }
    task.setAssignTo(memberId);
  }

  public void unassignTask(Task task) {
    if (task == null) {
      System.out.println("Task cannot be null");
      return;
    }
    task.setAssignTo(0);
  }

  @Override
  public String toString() {
    return super.toString()+"Owner";
  }

  @Override
  public boolean equals(Object obj) {
    Owner other = (Owner) obj;
    
    if (!super.equals(obj)) {
      return false;
    }
    return true;
  }
}

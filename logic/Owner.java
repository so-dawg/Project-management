package logic;

import java.time.LocalDate;
import java.time.LocalTime;

public class Owner extends Member implements IUser {

  public static final boolean IS_OWNER = true;
  private static int totalOwners = 0;

  private int id;

  public Owner(Member m) {
    super(m.getFirstName(), m.getLastName(), m.getEmail(), m.getUsername(), m.getPassword());
    this.id = ++totalOwners;
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
  public String getId() {
    return String.valueOf(id);
  }

  @Override
  public String getRole() {
    return "User"; // All users are equal now
  }

  @Override
  public boolean can(String action) {
    // All users have the same permissions
    switch (action) {
      case "VIEW_TASK":
      case "UPDATE_OWN_TASK":
      case "CREATE_TASK":
      case "CREATE_PROJECT":
        return true;
      default:
        return false;
    }
  }

  

  public void setEmail(String email) {
    if (isValidEmail(email)) {
      this.email = email;
    } else {
      System.out.println("Invalid email!");
    }
  }

  public void setPassword(String password) {
    if (isValidPassword(password)) {
      this.password = password;
    } else {
      System.out.println("Invalid password!");
    }
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

  public static boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }

    int atIndex = email.indexOf("@");
    int dotIndex = email.lastIndexOf(".");

    if (atIndex <= 0) {
      return false;
    }

    if (dotIndex <= atIndex + 1) {
      return false;
    }

    if (dotIndex >= email.length() - 1) {
      return false;
    }
    return true;
  }

  public static boolean isValidPassword(String password) {
    if (password == null) {
      return false;
    }

    if (password.length() < 8) {
      return false;
    }

    String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).+$";
    return password.matches(pattern);
  }

  @Override
  public String toString() {
    return "Owner ID: " + getId() + "\n" +
        "Name: " + getFirstName() + " " + getLastName() + "\n" +
        "Email: " + getEmail() + "\n" +
        "Role: " + getRole();
  }
}

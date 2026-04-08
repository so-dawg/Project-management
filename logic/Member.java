package logic;

import java.util.ArrayList;

public class Member extends User {

  private static int totalMembers = 0;
  private static int nextId = 1;
  private int id;

  public Member(String firstName, String lastName, String email, String username, String password) {
    super(firstName, lastName, email, username, password);
    totalMembers++;
    this.id = nextId++;
  }

  public Member(int id, String firstName, String lastName, String email, String username, String password) {
    super(firstName, lastName, email, username, password);
    this.id = id;
  }

  public static int getTotalMembers() {
    return totalMembers;
  }

  public String getId() {
    return String.valueOf(id);
  }

  public void setIdDirect(String id) {
    try {
      this.id = Integer.parseInt(id);
    } catch (NumberFormatException e) {
      System.out.println("Invalid ID format: " + id);
    }
  }

  @Override
  public String getRole() {
    return "Member";
  }

  @Override
  public boolean can(String action) {
    switch (action) {
      case "VIEW_TASK":
      case "UPDATE_OWN_TASK":
      case "CREATE_TASK":
      case "CREATE_PROJECT":
      case "DELETE_TASK":
        return true;
      case "ASSIGN_TASK":
      case "CREATE_USER":
      case "VIEW_REPORT":
      default:
        return false;
    }
  }

  public void addUser(IUser user) {
    // Placeholder for compatibility
  }

  public IUser searchUserById(String userId) {
    return null;
  }

  public IUser searchUserByUsername(String username) {
    return null;
  }

  @Override
  public String toString() {
    return super.toString() + "Id: " + id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Member other = (Member) obj;
    return this.id == other.id;
  }

  @Override
  public int hashCode() {
    return Integer.hashCode(id);
  }
}

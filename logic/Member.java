package logic;

public class Member extends User {

  private static int totalMembers = 0;
  private static int nextId = 1;
  private int Id;

  public Member(String firstName, String lastName, String email, String username, String password) {
    super(firstName, lastName, email, username, password);
    totalMembers++;
    Id = nextId++;
  }

  // Constructor for database users (with existing ID)
  // public Member(int id, String firstName, String lastName, String email, String
  // username, String password) {
  // this.Id = id;
  // this.firstName = firstName;
  // this.lastName = lastName;
  // setEmail(email);
  // this.username = username;
  // setPassword(password);
  // }

  public static int getTotalMembers() {
    return totalMembers;
  }

  public String getId() {
    return String.valueOf(Id);
  }

  @Override
  public String getRole() {
    return "Member";
  }

  @Override
  public boolean can(String action) {
    // Members can view and manage their own tasks
    switch (action) {
      case "VIEW_TASK":
        return true;
      case "UPDATE_OWN_TASK":
        return true;
      case "CREATE_TASK":
        return true; // Members can create tasks
      case "CREATE_PROJECT":
        return true; // Members can create projects
      case "DELETE_TASK":
        return true; // Members can delete tasks
      case "ASSIGN_TASK":
        return false; // Only owners can assign
      case "CREATE_USER":
        return false;
      case "VIEW_REPORT":
        return false;
      default:
        return false;
    }
  }

  @Override
  public String toString() {
    return super.toString() + "Id: " + Id;
  }

}

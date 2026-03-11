package logic;

public class Member extends User {

  private static int totalMembers = 0;
  private int Id;

  public Member(String firstName, String lastName, String email, String username, String password) {
    super(firstName, lastName, email, username, password);
    totalMembers++;
    Id++;
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
  public boolean can(String action) {
    // All users have the same permissions now
    switch (action) {
      case "VIEW_TASK":
        return true;
      case "UPDATE_OWN_TASK":
        return true;
      case "CREATE_TASK":
        return false; // All users can't create tasks
      case "CREATE_PROJECT":
        return true; // All users can create projects
      case "DELETE_TASK":
        return false;
      case "ASSIGN_TASK":
        return false;
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

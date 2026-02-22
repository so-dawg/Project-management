public class Member implements IUser {

  private static int idCounter = 1;

  public int getTotalMembers() {
    return idCounter - 1;
  }

  private int Id;
  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;


  public Member(String firstName, String lastName, String email, String username, String password) {
    this.Id = idCounter++;
    this.firstName = firstName;
    this.lastName = lastName;
    setEmail(email);
    this.username = username;
    setPassword(password);
  }
  // Getter for Id
  @Override
  public String getId() {
    return String.valueOf(Id);
  }
  // Getter for Username
  @Override
  public String getUsername() {
    return this.username != null ? this.username : this.email;
  }
  // getter for Password
  @Override
  public String getPassword() {
    return this.password;
  }
  // Getter for Role
  @Override
  public String getRole() {
    return "Member";
  }
  // Permission checking - Member can only do limited actions
  @Override
  public boolean can(String action) {
      switch (action) {
          case "VIEW_TASK":
              return true;
          case "UPDATE_OWN_TASK":
              return true;
          case "CREATE_TASK":
              return false;
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

  public String getFirstName() {
    if (firstName == null || firstName.isEmpty()) {
      System.out.println("Warning: First name not set!");
      return "Unknown";
    }
    return firstName;
  }

  public String getLastName() {
    if (lastName == null || lastName.isEmpty()) {
      System.out.println("Warning: Last name not set!");
      return "Unknown";
    }
    return lastName;
  }

  public String getEmail() {
    if (!isValidEmail(email)) {
      return "Warning: Email invalid or not set!";
    }
    return email;
  }
  
  // Setter 
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
    }else {
      System.out.println("Invalid password!");
    }
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

    if (dotIndex <= atIndex +1) {
      return false;
    }

    if (dotIndex >= email.length() - 1) {
      return false ;
    }
    return true;
  }

  public static boolean isValidPassword (String password) {
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
  public  String toString() {
    return "User ID: " + getId() + "\n" +
           "Name: " + getFirstName() + " " + getLastName() + "\n" +
           "Email: " + getEmail() + "\n" +
           "Password: " + "........" + "\n" ;
  }
}

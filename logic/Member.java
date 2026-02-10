public class Member {

  private static int idCounter = 1;

  public static int getTotalMembers() {
    return idCounter - 1;
  }

  private int Id;
  private boolean isSet = false;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String task;
  private final String role = "member";


  public Member(int Id,String firstName, String lastName, String email, String password) {
    this.Id = idCounter++;
    this.firstName = firstName;
    this.lastName = lastName;

  }

  // Getter 
  public int getId() {
    return Id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getRole() {
    return role;
  }
  

  public void setPassword(String password) {
    if (isValidPassword(password)) {
      this.password = password;
    }else {
      System.out.println("Invalid password!");
    }
  }
  boolean isValidEmail(String email) {
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

  boolean isValidPassword (String password) {
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
    return "User ID: " + Id + "\n" +
           "Name: " + firstName + " " + lastName + "\n" +
           "Email: " + email + "\n" +
           "Password: " + password + "\n" +
           "role: " + role;
  }
}

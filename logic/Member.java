public class Member {

  private static int idCounter = 1;

  public static int getTotalMembers() {
    return idCounter - 1;
  }

  private int Id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;


  private Member(String firstName, String lastName, String email, String password) {
    this.Id = idCounter++;
    this.firstName = firstName;
    this.lastName = lastName;
    setEmail(email);
    setPassword(password);
  }
  // constructor overloading for register
  private Member(String firsrName, String lastName) {
    this.Id = idCounter++;
    this.firstName = firsrName;
    this.lastName = lastName;
  }
  // Register method
  public static Member register(String firstName, String lastName, String email, String password) {
    Member member = new Member(firstName, lastName);

    if (!member.isValidEmail(email)) {
      System.out.println("Register fail: Invalid email!");
      return null;
    }

    if (!member.isValidPassword(password)) {
      System.out.println("Register fail : Invalid password!");
      return null;
    }

    member.email = email;
    member.password = password;
    
    System.err.println("Register successfull!");

    return member;
  }

  // Getter 
  public int getId() {
    return Id;
  }

  public String getFirstName() {
    if (firstName == null || firstName.isEmpty()) {
      System.out.println("Warning: First name not set!");
      return "Unknown";
    }
    return firstName;
  }

  public String getLastName() {
    if (lastName == null || firstName.isEmpty()) {
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
           "Password: " + password + "\n" ;
  }
}

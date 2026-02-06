package logic;
public class Member {
  int Id;
  boolean isSet = false;
  String firstName;
  String lastName;
  String email;
  String password;
  String task;
  final String role = "member";


  public Member(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    setEmail(email);
    setPassword(password);
  }

  public void setMemberId(int Id){

    if (!isSet) {
      this.Id = Id;
      isSet = true;
    }
  }

  public void setEmail(String email) {

    if (email == null) {
      System.out.println("Email is empty !");
      return ;
    }

    int atIndex = email.indexOf("@");
    int dotIndex = email.lastIndexOf(".");

    if (!email.contains("@") || !email.contains(".")) {
      System.out.println("Invalid email !");
      return ;
    }

    if (atIndex <= 0) {
      System.out.println("Invalid email !");
      return ;
    }

    if (dotIndex <= atIndex + 1) {
      System.out.println("Invalid email !");
      return ;
    }

    if (dotIndex >= email.length()-1) {
      System.out.println("Invalid email !");
      return ;
    }

    this.email = email;
  }

  public void setPassword (String password) {
    if (password == null || password.isEmpty()) {
      System.out.println("Password is empty!");
      return;
    }

    if (password.length() < 8) {
      System.out.println("Password must have 8 digits or above !");
      return ;
    }

    String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).+$";

    if (!password.matches(pattern)) {
      System.out.println("Password must have lowercase character uppercase character number and special character !");
      return ;
    }

    this.password = password;
  }

  // getter
  public String getFirstName() {
    return firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public String getEmail() {
    return email;
  }
  public String getPassword() {
    return password;
  }
  public String getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "User ID: " + Id + "\n" +
           "Name: " + firstName + " " + lastName + "\n" +
           "Email: " + email + "\n" +
           "Password: " + password + "\n" +
           "role: " + role;
  }

  // Test with temporary data 
  public static void main(String[] args) {
    Member m = new Member(
            "pisal",
            "kun",
            "pisal.kun@example.com",
            "Yeager$hhsggsi1"
        );
    m.setMemberId(1);
    m.setEmail("pisal.kjs@sauco.");
    m.setPassword("141212411");
    System.out.println(m.toString());
  }
}

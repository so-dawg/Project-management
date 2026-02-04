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


  Member(int Id,String firstName, String lastName, String email, String password) {
    if (!isSet) {
      this.Id = Id;
      isSet = true;
    }

    this.firstName = firstName;
    this.lastName = lastName;

    if (isValidEmail(email)) {
      this.email = email;
    }else {
      this.email = "invalid";
    }

    if (isValidPassword(password)) {
      this.password = password;
    }else {
      this.password = "invalid";
    }
  }
  boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }

    int atIndex = email.indexOf("@");
    int dotIndex = email.lastIndexOf(".");

    if (atIndex <= 0) {
      return false
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

    if (password < 8) {
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

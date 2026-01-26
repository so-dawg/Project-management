package logic;

public class member {

  private int userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String role;

  public member(int userId, String firstName, String lastName, String email, String password, String role) {

    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
  }
  
  // getter
  public int getUserId() {
    return userId;
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
  public String getPassword() {
    return password;
  }
  public String getRole() {
    return role;
  }

  //setter 

  public void setUserId(int userId) {
    this.userId = userId;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public void setRole(String role) {
    this.role = role;
  }
  @Override
  public String toString() {
    return "User ID: " + userId + "\n" +
           "Name: " + firstName + " " + lastName + "\n" +
           "Email: " + email + "\n" +
           "Role: " + role;
  }

  // Test with temporary data 
  public static void main(String[] args) {
    member m = new member(
            1,
            "pisal",
            "kun",
            "pisal.kun@example.com",
            "1234",
            "member"
        );
    m.setRole("owner");
    System.out.println(m.toString());
  }
}
>>>>>>> 08319c4 (update member.java)

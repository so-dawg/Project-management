package logic;
<<<<<<< HEAD
<<<<<<< HEAD
public class member {
    String name;
    int age;
    member(String name,int age){
        this.name = name;
        if(age != 10){
            this.age = 10;
        } 
    }
}
=======

enum m_role {
  OWNER, MEMBER
}

enum m_role {
  OWNER, MEMBER
}

public class member {
=======

public class member {
>>>>>>> 283bbd6 (update member.java)

  private int userId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
<<<<<<< HEAD
<<<<<<< HEAD
  private m_role role;

  public member(int userId, String firstName, String lastName, String email, String password, m_role role) {
=======
  private String role;

  public member(int userId, String firstName, String lastName, String email, String password, String role) {
>>>>>>> 283bbd6 (update member.java)
=======
  private m_role role;

  public member(int userId, String firstName, String lastName, String email, String password, m_role role) {
>>>>>>> caa7927 (write function)

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
<<<<<<< HEAD
<<<<<<< HEAD
  public m_role getRole() {
=======
  public String getRole() {
>>>>>>> 283bbd6 (update member.java)
=======
  public m_role getRole() {
>>>>>>> caa7927 (write function)
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
<<<<<<< HEAD
<<<<<<< HEAD
  public void setRole(m_role role) {
=======
  public void setRole(String role) {
>>>>>>> 283bbd6 (update member.java)
=======
  public void setRole(m_role role) {
>>>>>>> caa7927 (write function)
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
<<<<<<< HEAD
<<<<<<< HEAD
            m_role.MEMBER
        );
    m.setRole(m_role.OWNER);
    System.out.println(m.toString());
  }
}
>>>>>>> 200c870f22560b28ffc23cf1a0fad96f5343b59e
=======
            "member"
=======
            m_role.MEMBER
>>>>>>> caa7927 (write function)
        );
    m.setRole(m_role.OWNER);
    System.out.println(m.toString());
  }
}
>>>>>>> 08319c4 (update member.java)
>>>>>>> 283bbd6 (update member.java)

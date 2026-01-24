package model;

 public class Employee {
    private int userId;
    private String firstName;
    private String lastName;
    private int projects;
    private String role;
    private String email;
    private String password;

    public Employee() {

    }

    // Contruction for creating new Employee before insert to data bbase 
    public Employee  (String firstName, String lastName, int projects, String email, String password, String role){
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.role = role;
    }


    // Getters and Setters
    public int getUserId() {
      return userId;
    }

    public void setUserId(int userId) {
      this.userId = userId;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName
    }

    public int getProjects() {
      return projects;
    }

    public void setProjects(int projects) {
      this.projects = projects;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }
}

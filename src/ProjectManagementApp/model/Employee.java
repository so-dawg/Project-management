package model;

 public class Employee {
    private int id;
    private String name;
    private String role;
    private String email;

    // Contruction for creating new Employee before insert to data bbase 
    public Employee  (String name, String role, String email){
      this.name = name;
      this.role = role;
      this.email = email;
    }

    // Contruction for retrieving Employee from data bbase
    public Employee (int id, String name, String role, String email) {
      this.id =  id;
      this.name = name;
      this.role = role;
      this.email = email;
    }

    // Getters and Setters
    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name
    }

    public void getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(int email) {
      this.email = email
    }

    // Convert to string easy for display
    @Override
    public String toString() {
      return id +" | " + name +" | " role " | " + email;
    }
}

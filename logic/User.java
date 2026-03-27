package logic;

public abstract class User implements IUser {

  private String firstName;
  private String lastName;
  protected String email;
  private String username;
  protected String password;

  public User(String firstName, String lastName, String email, String username, String password) {
    setFirstname(firstName);
    setLastname(lastName);
    setEmail(email);
    setUsername(username);
    setPassword(password);
  }

  public User() {
  }

  @Override
  public String getUsername() {
    return this.username != null ? this.username : this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public abstract boolean can(String action);

  @Override
  public String getFirstName() {
    if (firstName == null || firstName.isEmpty()) {
      return "Unknown";
    }
    return firstName;
  }

  @Override
  public String getLastName() {
    if (lastName == null || lastName.isEmpty()) {
      return "Unknown";
    }
    return lastName;
  }

  @Override
  public String getEmail() {
    if (!isValidEmail(email)) {
      return "Invalid email";
    }
    return email;
  }

  public void setEmail(String email) {
    if (isValidEmail(email)) {
      this.email = email;
    }
  }

  public void setPassword(String password) {
    if (isValidPassword(password)) {
      this.password = password;
    }
  }

  public void setUsername(String username) {
    if (username != null && username.length() <= 510) {
      this.username = username;
    }
  }

  public void setFirstname(String firstName) {
    if (firstName != null && firstName.length() <= 255) {
      this.firstName = firstName;
    }
  }

  public void setLastname(String lastName) {
    if (lastName != null && lastName.length() <= 255) {
      this.lastName = lastName;
    }
  }

  public static boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }

    int atIndex = email.indexOf("@");
    int dotIndex = email.lastIndexOf(".");

    return atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1;
  }

  public static boolean isValidPassword(String password) {
    return password != null && password.length() >= 4;
  }

  @Override
  public String toString() {
    return "Name: " + firstName + " " + lastName + "\n" +
        "Email: " + email + "\n" +
        "Password: ........\n";
  }
}

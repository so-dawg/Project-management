package logic;

public abstract class User implements IUser {

  private String firstName;
  private String lastName;
  private String email;
  private String username;
  private String password;

  public User(String firstName, String lastName, String email, String username, String password) {
    setFirstname(firstName);
    setLastname(lastName);
    setEmail(email);
    setUsername(username);
    setPassword(password);
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
      System.out.println("Warning: First name not set!");
      return "Unknown";
    }
    return firstName;
  }

  @Override
  public String getLastName() {
    if (lastName == null || lastName.isEmpty()) {
      System.out.println("Warning: Last name not set!");
      return "Unknown";
    }
    return lastName;
  }

  @Override
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
    } else {
      System.out.println("Invalid password!");
    }
  }

  public void setUsername(String username) {
    if (username != null) {
      if (username.length() <= 510) {
        this.username = username;
      } else {
        System.out.println("Error, invalid input!");
      }
    } else {
      System.out.println("Error, invalid input!");
    }
  }

  public void setFirstname(String firstName) {
    if (firstName != null) {
      if (firstName.length() <= 255) {
        this.firstName = firstName;
      } else {
        System.out.println("Error, invalid input!");
      }
    } else {
      System.out.println("Error, invalid input!");
    }
  }

  public void setLastname(String lastName) {
    if (lastName != null) {
      if (lastName.length() <= 255) {
        this.lastName = lastName;
      } else {
        System.out.println("Error, invalid input!");
      }
    } else {
      System.out.println("Error, invalid input!");
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

    if (dotIndex <= atIndex + 1) {
      return false;
    }

    if (dotIndex >= email.length() - 1) {
      return false;
    }
    return true;
  }

  public static boolean isValidPassword(String password) {
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
    return "Name: " + firstName + " " + lastName + "\n" +
        "Email: " + email + "\n" +
        "Password: " + "........" + "\n";
  }

}

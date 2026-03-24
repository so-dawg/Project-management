package logic;

import java.util.ArrayList;

import logic.Owner;

public abstract class User implements IUser {

  private String firstName;
  private String lastName;
  protected String email;
  private String username;
  protected String password;
  
  // User registry
  private ArrayList<IUser> users = new ArrayList<>();

  public User(String firstName, String lastName, String email, String username, String password) {
    setFirstname(firstName);
    setLastname(lastName);
    setEmail(email);
    setUsername(username);
    setPassword(password);
  }
  
  // Default constructor for registry
  public User() {
  }
  
  public void addUser(IUser user) {
    try {
      users.add(user);
    } catch (Exception e) {
      System.out.println("Error adding user: " + e.getMessage());
    }
  }

  public IUser searchUserById(String userId) {
    try {
      for (IUser user : users) {
        if (user.getId().equals(userId)) {
          return user;
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println("Error searching user by ID: " + e.getMessage());
      return null;
    }
  }

  public IUser searchUserByEmail(String email) {
    try {
      for (IUser user : users) {
        if (user.getEmail().equals(email)) {
          return user;
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println("Error searching user by email: " + e.getMessage());
      return null;
    }
  }

  public IUser searchUserByUsername(String username) {
    try {
      for (IUser user : users) {
        if (user.getUsername().equals(username)) {
          return user;
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println("Error searching user by username: " + e.getMessage());
      return null;
    }
  }

  public IUser login(String emailOrUsername, String password, ArrayList<IUser> user_list) {
    try {
      for (IUser user : user_list) {
        if ((user.getEmail().equals(emailOrUsername) || user.getUsername().equals(emailOrUsername))
            && user.getPassword().equals(password)) {
          return user;
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println("Error during login: " + e.getMessage());
      return null;
    }
  }

  public ArrayList<IUser> getArrayList() {
    try {
      return users;
    } catch (Exception e) {
      System.out.println("Error getting user list: " + e.getMessage());
      return new ArrayList<>();
    }
  }

  @Override
  public String getUsername() {
    try {
      return this.username != null ? this.username : this.email;
    } catch (Exception e) {
      System.out.println("Error getting username: " + e.getMessage());
      return null;
    }
  }

  @Override
  public String getPassword() {
    try {
      return this.password;
    } catch (Exception e) {
      System.out.println("Error getting password: " + e.getMessage());
      return null;
    }
  }

  @Override
  public abstract boolean can(String action);

  @Override
  public String getFirstName() {
    try {
      if (firstName == null || firstName.isEmpty()) {
        System.out.println("Warning: First name not set!");
        return "Unknown";
      }
      return firstName;
    } catch (Exception e) {
      System.out.println("Error getting first name: " + e.getMessage());
      return "Unknown";
    }
  }

  @Override
  public String getLastName() {
    try {
      if (lastName == null || lastName.isEmpty()) {
        System.out.println("Warning: Last name not set!");
        return "Unknown";
      }
      return lastName;
    } catch (Exception e) {
      System.out.println("Error getting last name: " + e.getMessage());
      return "Unknown";
    }
  }

  @Override
  public String getEmail() {
    try {
      if (!isValidEmail(email)) {
        return "Warning: Email invalid or not set!";
      }
      return email;
    } catch (Exception e) {
      System.out.println("Error getting email: " + e.getMessage());
      return "Warning: Email invalid or not set!";
    }
  }

  // Setter
  public void setEmail(String email) {
    try {
      if (isValidEmail(email)) {
        this.email = email;
      } else {
        System.out.println("Invalid email!");
      }
    } catch (Exception e) {
      System.out.println("Error setting email: " + e.getMessage());
    }
  }

  public void setPassword(String password) {
    try {
      if (isValidPassword(password)) {
        this.password = password;
      } else {
        System.out.println("Invalid password!");
      }
    } catch (Exception e) {
      System.out.println("Error setting password: " + e.getMessage());
    }
  }

  public void setUsername(String username) {
    try {
      if (username != null) {
        if (username.length() <= 510) {
          this.username = username;
        } else {
          System.out.println("Error, invalid input!");
        }
      } else {
        System.out.println("Error, invalid input!");
      }
    } catch (Exception e) {
      System.out.println("Error setting username: " + e.getMessage());
    }
  }

  public void setFirstname(String firstName) {
    try {
      if (firstName != null) {
        if (firstName.length() <= 255) {
          this.firstName = firstName;
        } else {
          System.out.println("Error, invalid input!");
        }
      } else {
        System.out.println("Error, invalid input!");
      }
    } catch (Exception e) {
      System.out.println("Error setting first name: " + e.getMessage());
    }
  }

  public void setLastname(String lastName) {
    try {
      if (lastName != null) {
        if (lastName.length() <= 255) {
          this.lastName = lastName;
        } else {
          System.out.println("Error, invalid input!");
        }
      } else {
        System.out.println("Error, invalid input!");
      }
    } catch (Exception e) {
      System.out.println("Error setting last name: " + e.getMessage());
    }
  }

  public static boolean isValidEmail(String email) {
    try {
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
    } catch (Exception e) {
      System.out.println("Error validating email: " + e.getMessage());
      return false;
    }
  }

  public static boolean isValidPassword(String password) {
    try {
      if (password == null) {
        return false;
      }

      if (password.length() < 8) {
        return false;
      }

      String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).+$";

      return password.matches(pattern);
    } catch (Exception e) {
      System.out.println("Error validating password: " + e.getMessage());
      return false;
    }
  }


  @Override
  public String toString() {
    return "Name: " + firstName + " " + lastName + "\n" +
        "Email: " + email + "\n" +
        "Password: " + "........" + "\n";
  }
}

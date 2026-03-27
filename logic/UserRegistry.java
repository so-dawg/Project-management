package logic;

import java.util.ArrayList;

import logic.IUser;

public class UserRegistry {
  private final ArrayList<IUser> users = new ArrayList<>();

  public void addUser(IUser user) {
    users.add(user);
  }

  public IUser createGuestUser() {
    return new User("Guest", "User", "Guest123@gmail.com", "guest", "GuestPassword") {
      @Override
      public boolean can(String action) {
        switch (action) {
          case "VIEW_TASK":
          case "VIEW_REPORT":
            return true;
          default:
            return false;
        }
      }

      @Override
      public String getId() {
        return "Guest-User001";
      }

      @Override
      public String getRole() {
        return "Guest";
      }
    };
  }

  public IUser searchUserById(String userId) {
    for (IUser user : users) {
      if (user.getId().equals(userId)) {
        return user;
      }
    }
    return null;
  }

  public IUser searchUserByEmail(String email) {
    for (IUser user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  public IUser searchUserByUsername(String username) {
    for (IUser user : users) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  public IUser login(String emailOrUsername, String password) {
    for (IUser user : users) {
      if ((user.getEmail().equals(emailOrUsername) || user.getUsername().equals(emailOrUsername))
          && user.getPassword().equals(password)) {
        return user;
      }
    }
    return null;
  }

  public ArrayList<IUser> getAllUsers() {
    return users;
  }
}

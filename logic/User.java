package logic;

import java.util.ArrayList;
import java.util.List;

public class User {
  private ArrayList<IUser> users = new ArrayList<>();

  // public User() {
  // this.users = new ArrayList<>();
  // }

  // Add user to the list of users
  public void addUser(IUser user) {
    users.add(user);
  }

  // Search user by id
  public IUser searchUserById(String id) {
    for (IUser user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  // Search user by email
  public IUser searchUserByEmail(String email) {
    for (IUser user : users) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  // Search user by username
  public IUser searchUserByUsername(String username) {
    for (IUser user : users) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    return null;
  }

  public ArrayList<IUser> getArrayList() {
    return users;
  }

  // Auth login user
  public IUser login(String emailOrUsername, String password, List<IUser> users) {

    for (IUser user : users) {
      if (user.getEmail().equals(emailOrUsername) || user.getUsername().equals(emailOrUsername)) {
        if (user.getPassword().equals(password)) {
          return user;
        }
      }
    }
    return null;
  }

}

package logic;

import java.util.ArrayList;

public class UserRegistry {
    private ArrayList<IUser> users = new ArrayList<>();

    public void addUser(IUser user) {
        users.add(user);
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

    public ArrayList<IUser> getArrayList() {
        return users;
    }
}

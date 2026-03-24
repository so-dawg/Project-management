package logic;

import java.util.ArrayList;

/**
 * UserRegistry - Central registry for managing users in the system
 */
public class UserRegistry {
    private ArrayList<IUser> users;

    public UserRegistry() {
        this.users = new ArrayList<>();
    }

    /**
     * Add a user to the registry
     * @param user The user to add
     */
    public void addUser(IUser user) {
        try {
            users.add(user);
        } catch (Exception e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    /**
     * Search for a user by ID
     * @param userId The user ID to search for
     * @return The user if found, null otherwise
     */
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

    /**
     * Search for a user by email
     * @param email The email to search for
     * @return The user if found, null otherwise
     */
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

    /**
     * Search for a user by username
     * @param username The username to search for
     * @return The user if found, null otherwise
     */
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

    /**
     * Login with email/username and password
     * @param emailOrUsername Email or username
     * @param password Password
     * @return The logged-in user if successful, null otherwise
     */
    public IUser login(String emailOrUsername, String password) {
        try {
            for (IUser user : users) {
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

    /**
     * Get all users in the registry
     * @return ArrayList of all users
     */
    public ArrayList<IUser> getAllUsers() {
        try {
            return users;
        } catch (Exception e) {
            System.out.println("Error getting user list: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

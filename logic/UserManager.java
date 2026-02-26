package logic;
import java.util.ArrayList;


public class UserManager {
    private ArrayList<IUser_Member> users = new ArrayList<>();
    // Add user to the list of users
    public void addUser(IUser_Member user){
        users.add(user);
    }
    // Search user by id
    public IUser_Member searchUserById(String id){
        for (IUser_Member user : users){
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
    // Search user by email 
    public IUser_Member searchUserByEmail(String email){
        for (IUser_Member user : users){
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    // Search user by username
    public IUser_Member searchUserByUsername(String username){
        for (IUser_Member user : users){
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public ArrayList<IUser_Member> getArrayList(){
        return users;
    }
}

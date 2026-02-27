package logic;
import java.util.List;
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
    //Auth login user
    public IUser_Member login(String emailOrUsername, String password, List<IUser_Member> users){
    
        for (IUser_Member user : users){
            if (user.getEmail().equals(emailOrUsername) || user.getUsername().equals(emailOrUsername)){
                if (user.getPassword().equals(password)){
                    return user;
                }
            }
        }
        return null;
    }

}

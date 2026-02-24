import java.util.List;
public class AuthService {
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
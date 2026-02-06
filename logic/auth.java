package AUTH;
public class auth {
    public static void main(String[] args) {

        User baseUser = new User(
            "admin",
            "admin@gmail.com",
            1234   // base password (hidden)
        );

        int userInputPassword = 567;

        if (baseUser.login(userInputPassword)) {
            System.out.println("currect");
        } else {
            System.out.println("incurrect");
        }
        
        System.out.println(baseUser.toString());
    }
}



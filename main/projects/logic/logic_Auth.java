package database.logic_Auth;
public class logic_Auth {
    void isOwner(){
        if(show.email == base_data.email){
            if(show.password == show.password){
                isOwner = true;
                return;
            }
            else{
                System.out.println("fail");
                return;
            }
        }
    }
    
}
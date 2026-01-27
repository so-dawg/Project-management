package AUTH;
class pack{
    String us_name;
    String email;
    int password;
    
    pack(String us_name,String email,int password){
        this.us_name = us_name;
        this.email = email;
        this.password = password;
    }

}
class base_pack{
    int password;
    int encypt_pass(){
        return password;
    }
    int dir_pack(){
        
    }
}
public class logic_Auth {
    boolean isOwner(pack input_us, pack base_data) {
        if (input_us.email == base_data.email && input_us.password == base_data.password) {
            return true;
        }
        return false;
    }
    
}
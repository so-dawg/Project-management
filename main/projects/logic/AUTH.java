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

public class AUTH {    
    public static void main(String[] name){
        pack base_data = new pack();
        pack show = new pack("heng","example@gmail.com",1234);
        boolean isOwner;
        
        
        System.out.println("name:" + show.us_name + "\n email:" + show.email + "\n password:" + show.password);
    }
}

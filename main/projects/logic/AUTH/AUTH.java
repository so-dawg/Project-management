// package database;
package AUTH;

import logic.verify;

public class AUTH {    
    public static void main(String[] name){
        pack us_in = new pack("heng","example@gmail.com",1234);
        pack us_base = new pack("none","Eample@gmail.com",463);
        verify user1;
        
        System.out.println(user1.isOwner(us_base,us_in));
    }
}
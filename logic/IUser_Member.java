public interface IUser_Member {

    String getId();
    String getFirstName();
    String getLastName();
    String getUsername();   
    String getEmail();
    String getPassword();
    String getRole();
    boolean can(String action);
}
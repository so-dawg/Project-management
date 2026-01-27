package AUTH;

public class User {
    private String username;
    private String email;
    private int password;

    User(String username, String email, int password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public boolean verify(int inputPassword) {
        return this.password == inputPassword;
    }

    public boolean login(int inputPassword) {
        return verify(inputPassword);
    }
    public void set_password (int password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    @Override
    public String toString() {
        return username + email + password;
    }
}



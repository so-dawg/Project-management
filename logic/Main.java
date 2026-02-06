class User {
    void accessLevel() {
        System.out.println("Basic user access");
    }
}
class Admin extends User {
    @Override
    void accessLevel() {
        System.out.println("Admin: full access");
    }
}

public class Main {
    public static void main(String[] args) {
        User u = new Admin();
        u.accessLevel();
    }
}

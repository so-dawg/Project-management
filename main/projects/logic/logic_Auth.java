package AUTH;
class Auth {
    private int password;   // hidden

    Auth(int password) {
        this.password = password;
    }

    // ❌ NO getter like getPassword()

    // ✅ Verification method
    public boolean verify(int inputPassword) {
        return this.password == inputPassword;
    }
}

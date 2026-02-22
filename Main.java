public class Main {
    public static void main(String[] args) {
       Member member = new Member("KUN", "PISAL", "examplekunpisal@gmial.com", "lazysal", "Password123@#");

       System.out.println("=== Testing Member.java ===\n");
        // Test 1: Getters
        System.out.println("--- Test 1: Getters ---");
        System.out.println("ID: " + member.getId());
        System.out.println("Username: " + member.getUsername());
        System.out.println("Password: " + member.getPassword());
        System.out.println("Role: " + member.getRole());
        System.out.println("First Name: " + member.getFirstName());
        System.out.println("Last Name: " + member.getLastName());
        System.out.println("Email: " + member.getEmail());
        // Test 2: Permissions
        System.out.println("\n--- Test 2: Permissions ---");
        System.out.println("can(VIEW_TASK): " + member.can("VIEW_TASK"));
        System.out.println("can(UPDATE_OWN_TASK): " + member.can("UPDATE_OWN_TASK"));
        System.out.println("can(CREATE_TASK): " + member.can("CREATE_TASK"));
        System.out.println("can(DELETE_TASK): " + member.can("DELETE_TASK"));
        System.out.println("can(ASSIGN_TASK): " + member.can("ASSIGN_TASK"));
        System.out.println("can(CREATE_USER): " + member.can("CREATE_USER"));
        System.out.println("can(VIEW_REPORT): " + member.can("VIEW_REPORT"));

        // Test 3: Email Validation
        System.out.println("\n--- Test 3: Email Validation ---");
        System.out.println("valid@email.com: " + Member.isValidEmail("valid@email.com"));
        System.out.println("invalid: " + Member.isValidEmail("invalid"));
        System.out.println("null: " + Member.isValidEmail(null));

        // Test 5: toString()
        System.out.println("\n--- Test 5: toString() ---");
        System.out.println(member.toString());

        System.out.println("\n--- Test 6: ID Counter ---");
        Member member2 = new Member("Jane", "Smith", "jane@example.com", "janesmith", "Password2!");
        System.out.println("Member 1 ID: " + member.getId());
        System.out.println("Member 2 ID: " + member2.getId());
        System.out.println("Total Members: " + member.getTotalMembers());

        System.out.println("\n=== All Tests Completed ===");
    }
}
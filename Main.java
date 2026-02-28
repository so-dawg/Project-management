import java.time.LocalDate;
import java.time.LocalTime;
import logic.Task;
import logic.Task.TaskPriority;
import logic.Member;
import logic.IUser_Member;
import logic.User;
import logic.Owner;

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

        // Test 4: toString()
        System.out.println("\n--- Test 4: toString() ---");
        System.out.println(member.toString());

        System.out.println("\n--- Test 5: ID Counter ---");
        Member member2 = new Member("Jane", "Smith", "jane@example.com", "janesmith", "Password2!");
        System.out.println("Member 1 ID: " + member.getId());
        System.out.println("Member 2 ID: " + member2.getId());
        System.out.println("Total Members: " + member.getTotalMembers());

        // Test 6: User
        System.out.println("\n--- Test 6: User ---");
        User manager = new User();
        Member member3 = new Member("John", "Doe", "john@example.com", "johndoe", "Password3!");
        manager.addUser(member);
        manager.addUser(member2);
        manager.addUser(member3);
        System.out.println("----Added Users to User----");
        System.out.println("Total Users: " + manager.getArrayList().size());

        System.out.println("----Searching Users by ID----");
        IUser_Member user = manager.searchUserById("2");
        System.out.println("User Found: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("----Searching Users by Email----");
        user = manager.searchUserByEmail("john@example.com");
        System.out.println("User Found: " + user.getFirstName() + " " + user.getLastName());
        
          
        // Test 8: Owner assigning task
        System.out.println("\n--- Test 8: Owner Assigning Task ---");
        Owner owner = new Owner("Admin", "Owner", "admin@example.com", "admin", "Password123@#");
        System.out.println("Owner: " + owner.getFirstName() + " " + owner.getLastName());
        System.out.println("Owner Role: " + owner.getRole());
        System.out.println("Owner can DELETE_TASK: " + owner.can("DELETE_TASK"));
        
        Task task2 = new Task("Review code", TaskPriority.MEDIUM, LocalDate.of(2026, 3, 15), 0,"Review the submitted code");
        owner.assignTask(task2, Integer.parseInt(member.getId()), LocalDate.of(2026, 3, 15), LocalTime.of(23, 59));
        System.out.println("Task assigned to Member ID: " + task2.getAssignTo());
        System.out.println("Task deadline: " + task2.getDeadlineString());
        
        System.out.println("\n=== All Tests Completed ===");
    }
}

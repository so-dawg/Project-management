import logic.Member;
import logic.User;
import logic.Owner;
import logic.Project;
import logic.ProjectManager;

public class Main {
  public static void main(String[] args) {
    System.out.println("========================================");
    System.out.println("   PROJECT MANAGEMENT SYSTEM           ");
    System.out.println("========================================\n");

    System.out.println("=== Running offline test method ===\n");
    testOfflineMode();

    System.out.println("\n========================================");
    System.out.println("       TEST COMPLETED                  ");
    System.out.println("========================================");
  }

  /**
   * Test with in-memory registry (offline mode)
   */
  public static void testOfflineMode() {
    User userRegistry = new User();
    
    Owner owner = new Owner(new Member("Admin", "User", "admin@example.com", "admin", "Password123@"));
    Member member1 = new Member("John", "Doe", "john@example.com", "johndoe", "Password1!");

    userRegistry.addUser(owner);
    userRegistry.addUser(member1);

    System.out.println("Owner: " + owner.getUsername());
    System.out.println("Total users: " + userRegistry.getArrayList().size());

    // Create project
    ProjectManager pm = new ProjectManager();
    Project project = pm.createProject("Offline Project", "Test without database", owner);
    System.out.println("Created project: " + project.getTitle());

    // Add member
    project.addMemberById(member1.getId(), userRegistry);
    System.out.println("Added member to project");
    System.out.println("Project members: " + project.getNumMember());
  }
}

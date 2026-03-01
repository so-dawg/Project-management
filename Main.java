import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import logic.Task;
import logic.Task.TaskPriority;
import logic.Member;
import logic.IUser_Member;
import logic.User;
import logic.Owner;
import logic.Project;
import logic.ProjectManager;
import logic.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   PROJECT MANAGEMENT SYSTEM           ");
        System.out.println("========================================\n");

        // Initialize database connection
        DatabaseManager db = new DatabaseManager();
        db.connect();

        // Check if connection succeeded
        if (db.getConnection() == null) {
            System.out.println("\n=== Running in OFFLINE mode (no database) ===\n");
            testOfflineMode();
            return;
        }

        // Load users from database into User registry
        User userRegistry = new User();
        db.loadUsersToRegistry(userRegistry);

        // ==================== LOGIN EXAMPLES ====================
        System.out.println("=== Login Examples ===\n");

        // Method 1: Login using User registry (faster, uses in-memory list)
        System.out.println("--- Method 1: Login with User Registry ---");
        IUser_Member user1 = userRegistry.login("sarah.johnson@company.com", "Owner123@", userRegistry.getArrayList());
        if (user1 != null) {
            System.out.println("Login successful!");
            System.out.println("Username: " + user1.getUsername());
            System.out.println("User ID: " + user1.getId());
        } else {
            System.out.println("Login failed - user not found");
        }

        // Method 2: Login directly from database (always fresh data)
        System.out.println("\n--- Method 2: Login with Database ---");
        Member user2 = db.login("sarah.johnson@company.com", "Owner123@");
        if (user2 != null) {
            System.out.println("Login successful!");
            System.out.println("Username: " + user2.getUsername());
            System.out.println("User ID: " + user2.getId());
        } else {
            System.out.println("Login failed - user not found");
        }

        // ==================== PROJECT OWNERSHIP ====================
        System.out.println("\n=== Project Ownership ===\n");

        if (user2 != null) {
            int userId = Integer.parseInt(user2.getId());
            
            // Get projects owned by this user
            ArrayList<Project> ownedProjects = db.getProjectsByOwnerId(userId);
            System.out.println("Projects owned by " + user2.getFirstName() + ":");
            if (ownedProjects.isEmpty()) {
                System.out.println("  (No projects owned yet)");
            } else {
                for (Project p : ownedProjects) {
                    System.out.println("  - " + p.getTitle() + " (ID: " + p.getProjectID() + ")");
                }
            }

            // Get project count
            int projectCount = db.getProjectCountByOwnerId(userId);
            System.out.println("\nTotal projects owned: " + projectCount);

            // Create a new project (any user can be an owner!)
            System.out.println("\n--- Creating New Project ---");
            Owner owner = new Owner(
                user2.getFirstName(),
                user2.getLastName(),
                user2.getEmail(),
                user2.getUsername(),
                user2.getPassword()
            );
            
            ProjectManager pm = new ProjectManager();
            Project newProject = pm.createProject("Test Project", "A test project", owner);
            System.out.println("Created: " + newProject.getTitle());
            System.out.println("Project ID: " + newProject.getProjectID());

            // Add members to the project
            System.out.println("\n--- Adding Members to Project ---");
            newProject.addMemberById("3", userRegistry);  // John Doe
            newProject.addMemberById("4", userRegistry);  // Emily Smith
            
            System.out.println("Project members:");
            for (Member m : newProject.getMembers()) {
                System.out.println("  - " + m.getFirstName() + " " + m.getLastName());
            }

            // Add a task to the project
            System.out.println("\n--- Adding Task to Project ---");
            newProject.addTask("Design mockup", TaskPriority.HIGH, "2026-04-01", "Create initial design", 3);
            System.out.println("Task added: Design mockup");
            System.out.println("Total tasks: " + newProject.getTaskCount());

            // Test: Remove member
            System.out.println("\n--- Test: Remove Member ---");
            System.out.println("Removing member by ID...");
            boolean removed = newProject.removeMemberById("4");
            System.out.println("Member removed: " + removed);
            System.out.println("Remaining members: " + newProject.getNumMember());

            // Test: Remove task by index
            System.out.println("\n--- Test: Remove Task by Index ---");
            System.out.println("Removing task at index 0...");
            boolean taskRemoved = newProject.removeTask(0);
            System.out.println("Task removed: " + taskRemoved);
            System.out.println("Remaining tasks: " + newProject.getTaskCount());
        }

        // ==================== ALL USERS ====================
        System.out.println("\n=== All Users in Registry ===");
        for (IUser_Member u : userRegistry.getArrayList()) {
            System.out.println("ID: " + u.getId() + " | " + u.getFirstName() + " " + u.getLastName() + " | " + u.getEmail());
        }

        // ==================== ALL PROJECTS ====================
        System.out.println("\n=== All Projects ===");
        ProjectManager pm = new ProjectManager();
        for (Project p : pm.getAllProjects()) {
            System.out.println("ID: " + p.getProjectID() + " | " + p.getTitle() + " | Owner: " + p.getOwner().getFirstName() + " " + p.getOwner().getLastName());
        }

        // Disconnect from database
        db.disconnect();

        System.out.println("\n========================================");
        System.out.println("       TEST COMPLETED                  ");
        System.out.println("========================================");
    }

    /**
     * Test with in-memory registry (offline mode)
     */
    public static void testOfflineMode() {
        User userRegistry = new User();
        Owner owner = new Owner("Admin", "User", "admin@example.com", "admin", "Password123@");
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

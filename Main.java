import logic.Member;
import logic.Owner;
import logic.Project;
import logic.Task;
import logic.IUser;
import logic.UserRegistry;
import ui.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    System.out.println("========================================");
    System.out.println("   PROJECT MANAGEMENT SYSTEM           ");
    System.out.println("========================================\n");

    System.out.println("Launching application...\n");

    // Launch the application
    SwingUtilities.invokeLater(() -> {
      AppFrame app = new AppFrame();
      app.setVisible(true);
    });

    System.out.println("========================================");
    System.out.println("       APPLICATION STARTED             ");
    System.out.println("========================================");
    System.out.println("\nTest accounts:");
    System.out.println("  Admin: admin@example.com / Password1!");
    System.out.println("  Member: john@example.com / Password1!\n");
  }

  /**
   * Test Member class methods
   * 
   * public static void testMemberClass() {
   * System.out.println("--- Testing Member Class ---");
   * 
   * // Test constructor and getters
   * Member member = new Member("John", "Doe", "john@example.com", "johndoe",
   * "Password1!");
   * System.out.println("Created member: " + member.getFirstName() + " " +
   * member.getLastName());
   * System.out.println("Member ID: " + member.getId());
   * System.out.println("Member username: " + member.getUsername());
   * System.out.println("Member email: " + member.getEmail());
   * System.out.println("Member role: " + member.getRole());
   * 
   * // Test setters
   * member.setFirstname("Jane");
   * member.setLastname("Smith");
   * member.setEmail("jane.smith@example.com");
   * member.setUsername("janesmith");
   * System.out.println("Updated name: " + member.getFirstName() + " " +
   * member.getLastName());
   * System.out.println("Updated email: " + member.getEmail());
   * System.out.println("Updated username: " + member.getUsername());
   * 
   * // Test validation - invalid email
   * System.out.print("Testing invalid email: ");
   * member.setEmail("invalid-email");
   * 
   * // Test validation - invalid password
   * System.out.print("Testing invalid password: ");
   * member.setPassword("weak");
   * 
   * // Test validation - invalid username (null)
   * System.out.print("Testing null username: ");
   * member.setUsername(null);
   * 
   * // Test validation - too long first name
   * System.out.print("Testing too long first name: ");
   * member.setFirstname("A".repeat(300));
   * 
   * // Test static validation methods
   * System.out.println("\nEmail validation tests:");
   * System.out.println(" valid@example.com: " +
   * Member.isValidEmail("valid@example.com"));
   * System.out.println(" invalid: " + Member.isValidEmail("invalid"));
   * System.out.println(" no-at-sign: " + Member.isValidEmail("no-at-sign"));
   * System.out.println(" @nodomain: " + Member.isValidEmail("@nodomain"));
   * 
   * System.out.println("Password validation tests:");
   * System.out.println(" Password1!: " + Member.isValidPassword("Password1!"));
   * System.out.println(" weak: " + Member.isValidPassword("weak"));
   * System.out.println(" nouppercase1!: " +
   * Member.isValidPassword("nouppercase1!"));
   * System.out.println(" NOLOWERCASE1!: " +
   * Member.isValidPassword("NOLOWERCASE1!"));
   * 
   * // Test can() method for Member
   * System.out.println("\nMember permissions:");
   * System.out.println(" VIEW_TASK: " + member.can("VIEW_TASK"));
   * System.out.println(" UPDATE_OWN_TASK: " + member.can("UPDATE_OWN_TASK"));
   * System.out.println(" CREATE_TASK: " + member.can("CREATE_TASK"));
   * System.out.println(" CREATE_PROJECT: " + member.can("CREATE_PROJECT"));
   * System.out.println(" DELETE_TASK: " + member.can("DELETE_TASK"));
   * System.out.println(" ASSIGN_TASK: " + member.can("ASSIGN_TASK"));
   * System.out.println(" CREATE_USER: " + member.can("CREATE_USER"));
   * System.out.println(" VIEW_REPORT: " + member.can("VIEW_REPORT"));
   * 
   * // Test toString
   * System.out.println("\nMember toString:");
   * System.out.println(member.toString());
   * 
   * // Test getTotalMembers
   * System.out.println("Total members created: " + Member.getTotalMembers());
   * 
   * System.out.println("Member tests completed.\n");
   * }
   * 
   * /**
   * Test Owner class methods
   */
  /*
   * public static void testOwnerClass() {
   * System.out.println("--- Testing Owner Class ---");
   * 
   * Member baseMember = new Member("Admin", "User", "admin@example.com", "admin",
   * "Password123@");
   * Owner owner = new Owner(baseMember);
   * 
   * System.out.println("Created owner: " + owner.getUsername());
   * System.out.println("Owner role: " + owner.getRole());
   * 
   * // Test can() method for Owner (should have all permissions)
   * System.out.println("\nOwner permissions:");
   * System.out.println("  VIEW_TASK: " + owner.can("VIEW_TASK"));
   * System.out.println("  UPDATE_OWN_TASK: " + owner.can("UPDATE_OWN_TASK"));
   * System.out.println("  CREATE_TASK: " + owner.can("CREATE_TASK"));
   * System.out.println("  CREATE_PROJECT: " + owner.can("CREATE_PROJECT"));
   * System.out.println("  DELETE_TASK: " + owner.can("DELETE_TASK"));
   * System.out.println("  ASSIGN_TASK: " + owner.can("ASSIGN_TASK"));
   * System.out.println("  CREATE_USER: " + owner.can("CREATE_USER"));
   * System.out.println("  VIEW_REPORT: " + owner.can("VIEW_REPORT"));
   * 
   * // Test assignTask with deadline
   * Task task = new Task("Test Task", Task.TaskPriority.HIGH,
   * LocalDate.now().plusDays(7), "Test description");
   * LocalDate deadline = LocalDate.now().plusDays(14);
   * LocalTime time = LocalTime.of(17, 0);
   * owner.assignTask(task, 1, deadline, time, owner);
   * System.out.println("\nAssigned task with deadline: " + task.getDeadline());
   * 
   * // Test assignTask without deadline
   * Task task2 = new Task("Task 2", Task.TaskPriority.MEDIUM, null,
   * "Another task");
   * owner.assignTask(task2, 2, owner);
   * System.out.println("Assigned task without deadline");
   * 
   * // Test unassignTask
   * owner.unassignTask(task, owner);
   * System.out.println("Unassigned task, assignTo: " + task.getAssignTo());
   * 
   * // Test assignTask with past deadline (should fail)
   * Task task3 = new Task("Past Task", Task.TaskPriority.LOW, null,
   * "Past deadline test");
   * System.out.print("Testing past deadline assignment: ");
   * owner.assignTask(task3, 1, LocalDate.now().minusDays(1), time, owner);
   * 
   * // Test assignTask with null task
   * System.out.print("Testing null task assignment: ");
   * owner.assignTask(null, 1, deadline, time, owner);
   * 
   * // Test assignTask with null deadline
   * System.out.print("Testing null deadline assignment: ");
   * owner.assignTask(task3, 1, null, time, owner);
   * 
   * // Test toString
   * System.out.println("\nOwner toString: " + owner.toString());
   * 
   * // Test getTotalOwners
   * System.out.println("Total owners created: " + Owner.getTotalOwners());
   * 
   * System.out.println("Owner tests completed.\n");
   * }
   * 
   * Test User
   * 
   * class methods
   * 
   * public static void testUserClass() {
   * System.out.println("--- Testing User Class ---");
   * 
   * User userRegistry = new User();
   * 
   * // Create test users
   * Owner owner = new Owner(new Member("Admin", "User", "admin@example.com",
   * "admin", "Password123@"));
   * Member member1 = new Member("John", "Doe", "john@example.com", "johndoe",
   * "Password1!");
   * Member member2 = new Member("Jane", "Smith", "jane@example.com", "janesmith",
   * "Password1!");
   * 
   * // Test addUser
   * userRegistry.addUser(owner);
   * userRegistry.addUser(member1);
   * userRegistry.addUser(member2);
   * System.out.println("Added 3 users to registry");
   * 
   * // Test getArrayList
   * ArrayList<IUser> users = userRegistry.getArrayList();
   * System.out.println("Total users in registry: " + users.size());
   * 
   * // Test searchUserById
   * System.out.println("\nSearch by ID:");
   * IUser foundById = userRegistry.searchUserById("1");
   * System.out.println("  Found user ID 1: " + (foundById != null ?
   * foundById.getUsername() : "null"));
   * IUser notFoundById = userRegistry.searchUserById("999");
   * System.out.println("  Found user ID 999: " + (notFoundById != null ?
   * notFoundById.getUsername() : "null"));
   * 
   * // Test searchUserByEmail
   * System.out.println("\nSearch by email:");
   * IUser foundByEmail = userRegistry.searchUserByEmail("john@example.com");
   * System.out.println("  Found john@example.com: " + (foundByEmail != null ?
   * foundByEmail.getUsername() : "null"));
   * IUser notFoundByEmail =
   * userRegistry.searchUserByEmail("nonexistent@example.com");
   * System.out.println(
   * "  Found nonexistent@example.com: " + (notFoundByEmail != null ?
   * notFoundByEmail.getUsername() : "null"));
   * 
   * // Test searchUserByUsername
   * System.out.println("\nSearch by username:");
   * IUser foundByUsername = userRegistry.searchUserByUsername("janesmith");
   * System.out.println("  Found janesmith: " + (foundByUsername != null ?
   * foundByUsername.getUsername() : "null"));
   * IUser notFoundByUsername = userRegistry.searchUserByUsername("nonexistent");
   * System.out
   * .println("  Found nonexistent: " + (notFoundByUsername != null ?
   * notFoundByUsername.getUsername() : "null"));
   * 
   * // Test login method
   * System.out.println("\nLogin tests:");
   * IUser loggedIn = userRegistry.login("john@example.com", "Password1!", users);
   * System.out.println("  Login with email: " + (loggedIn != null ?
   * loggedIn.getUsername() : "null"));
   * loggedIn = userRegistry.login("johndoe", "Password1!", users);
   * System.out.println("  Login with username: " + (loggedIn != null ?
   * loggedIn.getUsername() : "null"));
   * loggedIn = userRegistry.login("john@example.com", "WrongPassword!", users);
   * System.out.println("  Login with wrong password: " + (loggedIn != null ?
   * loggedIn.getUsername() : "null"));
   * loggedIn = userRegistry.login("nonexistent", "Password1!", users);
   * System.out.println("  Login with nonexistent user: " + (loggedIn != null ?
   * loggedIn.getUsername() : "null"));
   * 
   * System.out.println("User tests completed.\n");
   * }
   * 
   * 
   * public static void testTaskClass() {
   * System.out.println("--- Testing Task Class ---");
   * 
   * Owner owner = new Owner(new Member("Admin", "User", "admin@example.com",
   * "admin", "Password123@"));
   * LocalDate deadline = LocalDate.now().plusDays(7);
   * 
   * // Test constructor and getters
   * Task task = new Task("Important Task", Task.TaskPriority.HIGH, deadline,
   * "This is a test task description");
   * System.out.println("Created task: " + task.getTitle());
   * System.out.println("Priority: " + task.getPriority());
   * System.out.println("Deadline: " + task.getDeadlineString());
   * System.out.println("Description: " + task.getTaskDescription());
   * System.out.println("Completed: " + task.isCompleted());
   * System.out.println("Task ID: " + task.getTaskId());
   * 
   * // Test setters
   * task.setNewTitle("Updated Task Title");
   * task.setNewPriority(Task.TaskPriority.URGENT);
   * task.setNewTaskDescription("Updated description");
   * System.out.println("\nUpdated task:");
   * System.out.println("  Title: " + task.getTitle());
   * System.out.println("  Priority: " + task.getPriority());
   * System.out.println("  Description: " + task.getTaskDescription());
   * 
   * // Test setDeadline
   * task.setDeadline(LocalDate.now().plusDays(14));
   * System.out.println("  New deadline: " + task.getDeadlineString());
   * 
   * // Test setTaskId
   * task.setTaskId(100);
   * System.out.println("  Task ID: " + task.getTaskId());
   * 
   * // Test setAssignTo (with Owner who has permission)
   * task.setAssignTo(1, owner);
   * System.out.println("  Assigned to: " + task.getAssignTo());
   * 
   * // Test markCompleted
   * task.markCompleted(owner);
   * System.out.println("  Marked completed: " + task.isCompleted());
   * 
   * // Test markIncomplete
   * task.markIncomplete(owner);
   * System.out.println("  Marked incomplete: " + task.isCompleted());
   * 
   * // Test isPastDeadline
   * Task pastTask = new Task("Past Task", Task.TaskPriority.LOW,
   * LocalDate.now().minusDays(1), "Past deadline");
   * System.out.println("\nPast deadline task isPastDeadline: " +
   * pastTask.isPastDeadline());
   * Task futureTask = new Task("Future Task", Task.TaskPriority.LOW,
   * LocalDate.now().plusDays(1), "Future deadline");
   * System.out.println("Future deadline task isPastDeadline: " +
   * futureTask.isPastDeadline());
   * Task nullDeadlineTask = new Task("Null Deadline Task", Task.TaskPriority.LOW,
   * null, "No deadline");
   * System.out.println("Null deadline task isPastDeadline: " +
   * nullDeadlineTask.isPastDeadline());
   * 
   * // Test validation - null title
   * System.out.print("\nTesting null title: ");
   * task.setNewTitle(null);
   * 
   * // Test validation - empty title
   * System.out.print("Testing empty title: ");
   * task.setNewTitle("");
   * 
   * // Test validation - too long title
   * System.out.print("Testing too long title: ");
   * task.setNewTitle("A".repeat(300));
   * 
   * // Test validation - null description
   * System.out.print("Testing null description: ");
   * task.setNewTaskDescription(null);
   * 
   * // Test validation - too long description
   * System.out.print("Testing too long description: ");
   * task.setNewTaskDescription("A".repeat(10001));
   * 
   * // Test validation - null priority
   * System.out.print("Testing null priority: ");
   * task.setNewPriority(null);
   * 
   * // Test toString
   * System.out.println("\n\nTask toString:");
   * System.out.println(task.toString());
   * 
   * System.out.println("Task tests completed.\n");
   * }
   * 
   * 
   * public static void testProjectClass() {
   * System.out.println("--- Testing Project Class ---");
   * 
   * User userRegistry = new User();
   * Owner owner = new Owner(new Member("Admin", "User", "admin@example.com",
   * "admin", "Password123@"));
   * Member member1 = new Member("John", "Doe", "john@example.com", "johndoe",
   * "Password1!");
   * Member member2 = new Member("Jane", "Smith", "jane@example.com", "janesmith",
   * "Password1!");
   * 
   * userRegistry.addUser(owner);
   * userRegistry.addUser(member1);
   * userRegistry.addUser(member2);
   * 
   * // Test constructor and getters
   * Project project = new Project("Test Project", "A test project description",
   * owner);
   * System.out.println("Created project: " + project.getTitle());
   * System.out.println("Description: " + project.getProjectDescription());
   * System.out.println("Project ID: " + project.getProjectID());
   * System.out.println("Owner: " + project.getOwner().getUsername());
   * System.out.println("Members count: " + project.getNumMember());
   * System.out.println("Task count: " + project.getTaskCount());
   * 
   * // Test addMemberById
   * System.out.println("\nAdding members:");
   * boolean added = project.addMemberById(member1.getId(), userRegistry);
   * System.out.println("  Added member by ID '" + member1.getId() + "': " +
   * added);
   * added = project.addMemberById("999", userRegistry);
   * System.out.println("  Added member by ID '999': " + added);
   * 
   * // Test addMemberByName
   * added = project.addMemberByName("janesmith", userRegistry);
   * System.out.println("  Added member by username 'janesmith': " + added);
   * added = project.addMemberByName("nonexistent", userRegistry);
   * System.out.println("  Added member by username 'nonexistent': " + added);
   * 
   * // Test getMembers
   * ArrayList<Member> members = project.getMembers();
   * System.out.println("\nProject members: " + members.size());
   * 
   * // Test searchMemberById
   * Member foundMember = project.searchMemberById(member1.getId());
   * System.out.println("Search member by ID: " + (foundMember != null ?
   * foundMember.getUsername() : "null"));
   * Member notFoundMember = project.searchMemberById("999");
   * System.out
   * .println("Search member by ID '999': " + (notFoundMember != null ?
   * notFoundMember.getUsername() : "null"));
   * 
   * // Test searchMemberByName
   * foundMember = project.searchMemberByName("janesmith");
   * System.out.println(
   * "Search member by username 'janesmith': " + (foundMember != null ?
   * foundMember.getUsername() : "null"));
   * notFoundMember = project.searchMemberByName("nonexistent");
   * System.out.println(
   * "Search member by username 'nonexistent': " + (notFoundMember != null ?
   * notFoundMember.getUsername() : "null"));
   * 
   * // Test addTask
   * System.out.println("\nAdding tasks:");
   * project.addTask(owner, "Task 1", Task.TaskPriority.HIGH, "2026-03-15",
   * "First task", 1);
   * project.addTask(owner, "Task 2", Task.TaskPriority.MEDIUM, "2026-03-20",
   * "Second task", 2);
   * System.out.println("  Added 2 tasks, task count: " + project.getTaskCount());
   * 
   * // Test getTask
   * Task retrievedTask = project.getTask(owner, 0);
   * System.out.println("  Retrieved task at index 0: " + (retrievedTask != null ?
   * retrievedTask.getTitle() : "null"));
   * 
   * // Test getTasks
   * ArrayList<Task> tasks = project.getTasks();
   * System.out.println("  All tasks count: " + tasks.size());
   * 
   * // Test setTitle
   * System.out.println("\nUpdating project:");
   * project.setTitle(owner, "Updated Project Title");
   * System.out.println("  New title: " + project.getTitle());
   * 
   * // Test setDescript
   * project.setDescript(owner, "Updated project description");
   * System.out.println("  New description: " + project.getProjectDescription());
   * 
   * // Test removeMemberById
   * System.out.println("\nRemoving members:");
   * boolean removed = project.removeMemberById(member1.getId());
   * System.out.println("  Removed member by ID: " + removed);
   * System.out.println("  Members count after removal: " +
   * project.getNumMember());
   * 
   * // Test removeMemberByName
   * removed = project.removeMemberByName("janesmith");
   * System.out.println("  Removed member by username: " + removed);
   * System.out.println("  Members count after removal: " +
   * project.getNumMember());
   * 
   * // Test removeTaskByIndex
   * System.out.println("\nRemoving tasks:");
   * boolean taskRemoved = project.removeTaskByIndex(owner, 0);
   * System.out.println("  Removed task at index 0: " + taskRemoved);
   * System.out.println("  Task count after removal: " + project.getTaskCount());
   * 
   * // Test removeTaskByID
   * if (!tasks.isEmpty()) {
   * taskRemoved = project.removeTaskByID(owner, tasks.get(0).getTaskId());
   * System.out.println("  Removed task by ID: " + taskRemoved);
   * System.out.println("  Task count after removal: " + project.getTaskCount());
   * }
   * 
   * // Test permission denied for Member (no DELETE_TASK permission)
   * Project project2 = new Project("Project 2", "Description", owner);
   * project2.addTask(owner, "Task", Task.TaskPriority.LOW, null, "Description",
   * 0);
   * boolean denied = project2.removeTaskByIndex(member1, 0);
   * System.out.println("\nMember trying to delete task (should be false): " +
   * denied);
   * 
   * // Test toString via task
   * System.out.println("\nProject tasks:");
   * for (Task t : project.getTasks()) {
   * System.out.println("  - " + t.getTitle());
   * }
   * 
   * System.out.println("Project tests completed.\n");
   * }
   */
}

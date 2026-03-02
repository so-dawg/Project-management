package logic;

import java.sql.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class DatabaseManager {
  private Connection conn;
  private String url = "jdbc:mariadb://localhost:3306/project_management";
  private String user = "root";
  private String password = "12345";

  /**
   * Connect to database
   */
  public void connect() {
    try {
      conn = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to database!");
    } catch (SQLException e) {
      System.out.println("Connection failed: " + e.getMessage());
      System.out.println("\nFix: Connect to MariaDB and run:");
      System.out.println("CREATE DATABASE IF NOT EXISTS project_management;");
      System.out.println("ALTER USER 'root'@'localhost' IDENTIFIED BY '12345';");
      System.out.println("FLUSH PRIVILEGES;");
    }
  }

  /**
   * Disconnect from database
   */
  public void disconnect() {
    try {
      if (conn != null && !conn.isClosed()) {
        conn.close();
        System.out.println("Disconnected from database");
      }
    } catch (SQLException e) {
      System.out.println("Disconnect failed: " + e.getMessage());
    }
  }

  /**
   * Load all users from database into User registry
   * 
   * @param userRegistry The User registry to populate
   */
  public void loadUsersToRegistry(User userRegistry) {
    if (conn == null) {
      System.out.println("Not connected to database. Call connect() first.");
      return;
    }
    String sql = "SELECT * FROM users";

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        // Create Member for all users (no role distinction)
        Member member = createMemberFromResultSet(rs);
        userRegistry.addUser(member);
      }
      rs.close();
      stmt.close();
      System.out.println("Loaded " + userRegistry.getArrayList().size() + " users from database");
    } catch (SQLException e) {
      System.out.println("Load users failed: " + e.getMessage());
    }
  }

  /**
   * Login user from database (direct query)
   * 
   * @param email    Email address
   * @param password Password
   * @return Member if found, null otherwise
   */
  public Member login(String email, String password) {
    if (conn == null) {
      System.out.println("Not connected to database. Call connect() first.");
      return null;
    }
    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
    Member loggedInUser = null;

    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, email);
      stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        loggedInUser = createMemberFromResultSet(rs);
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Login failed: " + e.getMessage());
    }
    return loggedInUser;
  }

  /**
   * Login using User registry (faster, no database query)
   * 
   * @param email        Email address
   * @param password     Password
   * @param userRegistry The User registry to search in
   * @return IUser_Member if found, null otherwise
   */
  public IUser_Member loginWithRegistry(String email, String password, User userRegistry) {
    return userRegistry.login(email, password, userRegistry.getArrayList());
  }

  /**
   * Get all users from database
   * 
   * @return ArrayList of Member
   */
  public ArrayList<Member> getAllUsers() {
    ArrayList<Member> users = new ArrayList<>();
    String sql = "SELECT * FROM users";

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        users.add(createMemberFromResultSet(rs));
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get users failed: " + e.getMessage());
    }
    return users;
  }

  /**
   * Get user by ID from database
   * 
   * @param userId The user ID
   * @return Member if found, null otherwise
   */
  public Member getUserById(String userId) {
    String sql = "SELECT * FROM users WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, Integer.parseInt(userId));
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return createMemberFromResultSet(rs);
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get user by ID failed: " + e.getMessage());
    }
    return null;
  }

  /**
   * Get all projects from database
   * 
   * @return ArrayList of Project
   */
  public ArrayList<Project> getAllProjects() {
    ArrayList<Project> projects = new ArrayList<>();
    String sql = "SELECT * FROM projects";

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "", // description not in your schema
              owner);
          projects.add(project);
        }
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get projects failed: " + e.getMessage());
    }
    return projects;
  }

  /**
   * Get projects owned by a user
   * 
   * @param userId The user ID
   * @return ArrayList of Project
   */
  public ArrayList<Project> getProjectsByOwnerId(int userId) {
    ArrayList<Project> projects = new ArrayList<>();
    String sql = "SELECT * FROM projects WHERE owner_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          projects.add(project);
        }
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get projects by owner failed: " + e.getMessage());
    }
    return projects;
  }

  /**
   * Get projects where user is a member
   * 
   * @param userId The user ID
   * @return ArrayList of Project
   */
  public ArrayList<Project> getProjectsByMemberId(int userId) {
    ArrayList<Project> projects = new ArrayList<>();
    String sql = "SELECT p.* FROM projects p " +
        "JOIN project_members pm ON p.project_id = pm.project_id " +
        "WHERE pm.user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          projects.add(project);
        }
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      // Table might not exist yet - return empty list
      System.out.println("Note: project_members table may not exist yet");
    }
    return projects;
  }

  /**
   * Get count of projects owned by a user
   * 
   * @param userId The user ID
   * @return Number of projects owned
   */
  public int getProjectCountByOwnerId(int userId) {
    String sql = "SELECT COUNT(*) AS count FROM projects WHERE owner_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, userId);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt("count");
      }
    } catch (SQLException e) {
      System.out.println("Get project count failed: " + e.getMessage());
    }
    return 0;
  }

  /**
   * Get owner by ID
   */
  private Owner getOwnerById(int ownerId) {
    String sql = "SELECT * FROM users WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, ownerId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        return new Owner(
            rs.getInt("user_id"), // Use database ID
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("email"), // Use email as username
            rs.getString("password"));
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get owner failed: " + e.getMessage());
    }
    return null;
  }

  /**
   * Create Member from ResultSet
   */
  private Member createMemberFromResultSet(ResultSet rs) throws SQLException {
    int id = rs.getInt("user_id");
    Member member = new Member(
        id, // Use database ID
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email"),
        rs.getString("email"), // Use email as username since column doesn't exist
        rs.getString("password"));
    return member;
  }

  /**
   * Insert a new user into database
   * 
   * @return The created user ID, or -1 if failed
   */
  public int insertUser(String firstName, String lastName, String email,
      String username, String password) {
    String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.setString(3, email);
      stmt.setString(4, password);
      int rows = stmt.executeUpdate();

      if (rows > 0) {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1); // Return generated ID
        }
      }
      stmt.close();
      return -1;
    } catch (SQLException e) {
      System.out.println("Insert user failed: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Insert a new task into database
   * 
   * @param projectId   The project ID this task belongs to
   * @param assignedTo  User ID assigned to this task
   * @param title       Task title
   * @param status      Task status (todo, in_progress, done)
   * @param priority    Task priority (low, medium, high)
   * @param dueDate     Due date (yyyy-MM-dd)
   * @param description Task description
   * @return The generated task ID, or -1 if failed
   */
  public int insertTask(int projectId, int assignedTo, String title, String status,
      String priority, String dueDate, String description) {
    String sql = "INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES (?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, projectId);
      stmt.setInt(2, assignedTo);
      stmt.setString(3, title);
      stmt.setString(4, status);
      stmt.setString(5, priority.toLowerCase());
      stmt.setString(6, dueDate);
      int rows = stmt.executeUpdate();

      if (rows > 0) {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1); // Return generated task ID
        }
      }
      stmt.close();
      return -1;
    } catch (SQLException e) {
      System.out.println("Insert task failed: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Insert a task and update the Task object with the database ID
   * 
   * @param task      The Task object to save
   * @param projectId The project ID
   * @return true if saved successfully
   */
  public boolean insertTaskAndSetId(Task task, int projectId) {
    int taskId = insertTask(
        projectId,
        task.getAssignTo(),
        task.getTitle(),
        "todo", // default status
        task.getPriority().toString().toLowerCase(),
        task.getDeadline() != null ? task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null,
        task.getTaskDescription());

    if (taskId != -1) {
      task.setTaskId(taskId);
      return true;
    }
    return false;
  }

  /**
   * Update user in database
   */
  public boolean updateUser(IUser_Member user) {
    String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, user.getFirstName());
      stmt.setString(2, user.getLastName());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getPassword());
      stmt.setInt(5, Integer.parseInt(user.getId()));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update user failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Delete user from database
   */
  public boolean deleteUser(String userId) {
    String sql = "DELETE FROM users WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, Integer.parseInt(userId));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Delete user failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Remove a member from a project (from database)
   * Note: This requires a project_members table to track memberships
   * 
   * @param projectId The project ID
   * @param memberId  The member ID to remove
   * @return true if removed, false if failed
   */
  public boolean removeMemberFromProject(int projectId, int memberId) {
    // First check if project_members table exists
    String sql = "DELETE FROM project_members WHERE project_id = ? AND user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      stmt.setInt(2, memberId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Note: project_members table doesn't exist yet. Member tracking is in-memory only.");
      return false;
    }
  }

  /**
   * Remove a task from database by task ID
   * 
   * @param taskId The task ID to delete
   * @return true if removed, false if failed
   */
  public boolean removeTask(int taskId) {
    String sql = "DELETE FROM tasks WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Remove task failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Remove all tasks from a project
   * 
   * @param projectId The project ID
   * @return Number of tasks removed
   */
  public int removeAllTasksFromProject(int projectId) {
    String sql = "DELETE FROM tasks WHERE projects_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows;
    } catch (SQLException e) {
      System.out.println("Remove tasks failed: " + e.getMessage());
      return 0;
    }
  }

  /**
   * Remove a project from database
   * 
   * @param projectId The project ID to delete
   * @return true if removed, false if failed
   */
  public boolean removeProject(int projectId) {
    String sql = "DELETE FROM projects WHERE project_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Remove project failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Get connection (for advanced queries)
   */
  public Connection getConnection() {
    return conn;
  }
}

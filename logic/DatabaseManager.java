package logic;

import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
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
      System.out.println("\n[Database LOG] Connected to database!");
    } catch (SQLException e) {
      System.out.println("[Database LOG] Connection failed: " + e.getMessage());
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
  public void loadUsersToRegistry(UserRegistry userRegistry) {
    if (conn == null) {
      System.out.println("Not connected to database. Call connect() first.");
      return;
    }
    String sql = "SELECT * FROM users";

    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        Member member = createMemberFromResultSet(rs);
        userRegistry.addUser(member);
      }
      rs.close();
      stmt.close();
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
      e.printStackTrace();
    }
    return loggedInUser;
  }

  /**
   * Login using User registry (faster, no database query)
   *
   * @param email        Email address
   * @param password     Password
   * @param userRegistry The User registry to search in
   * @return IUser if found, null otherwise
   */
  public IUser loginWithRegistry(String email, String password, UserRegistry userRegistry) {
    return userRegistry.login(email, password);
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
        int projectId = rs.getInt("project_id");
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          project.setProjectIdDirect(projectId);
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
        int projectId = rs.getInt("project_id");
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          project.setProjectIdDirect(projectId);
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
        int projectId = rs.getInt("project_id");
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          project.setProjectIdDirect(projectId);
          projects.add(project);
        }
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get projects by member failed: " + e.getMessage());
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
    String username = rs.getString("username");
    // If username column doesn't exist or is null, use email prefix
    if (username == null || username.isEmpty()) {
      username = rs.getString("email").substring(0, rs.getString("email").indexOf("@"));
    }
    Member member = new Member(
        id, // Use database ID
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email"),
        username,
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
    // Use INSERT ... ON DUPLICATE KEY UPDATE to handle duplicate emails
    String sql = "INSERT INTO users (first_name, last_name, email, username, password) VALUES (?, ?, ?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE first_name=VALUES(first_name), last_name=VALUES(last_name), username=VALUES(username), password=VALUES(password)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, firstName);
      stmt.setString(2, lastName);
      stmt.setString(3, email);
      stmt.setString(4, username);
      stmt.setString(5, password);
      int rows = stmt.executeUpdate();

      if (rows > 0) {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
          int userId = rs.getInt(1);
          System.out.println("User saved/updated in database with ID: " + userId);
          return userId;
        }
      }
      stmt.close();
      return -1;
    } catch (SQLException e) {
      System.out.println("Insert user failed: " + e.getMessage());
      // Try to get existing user ID if duplicate
      try {
        Member existing = getUserByEmail(email);
        if (existing != null) {
          System.out.println("User already exists with ID: " + existing.getId());
          return Integer.parseInt(existing.getId());
        }
      } catch (Exception ex) {
        // Ignore
      }
      return -1;
    }
  }

  /**
   * Get user by email from database
   */
  public Member getUserByEmail(String email) {
    String sql = "SELECT * FROM users WHERE email = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, email);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return createMemberFromResultSet(rs);
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get user by email failed: " + e.getMessage());
    }
    return null;
  }

  /**
   * Insert a new task into database
   *
   * @param projectId   The project ID this task belongs to
   * @param assignedTo  User ID assigned to this task (0 for unassigned)
   * @param title       Task title
   * @param status      Task status (todo, in_progress, done)
   * @param priority    Task priority (low, medium, high)
   * @param dueDate     Due date (yyyy-MM-dd)
   * @param description Task description
   * @return The generated task ID, or -1 if failed
   */
  public int insertTask(int projectId, int assignedTo, String title, String status,
      String priority, String dueDate, String description) {
    // Use NULL for assigned_to if unassigned (0)
    String sql = "INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES (?, ?, ?, ?, ?, ?)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setInt(1, projectId);
      if (assignedTo <= 0) {
        stmt.setNull(2, java.sql.Types.INTEGER);
      } else {
        stmt.setInt(2, assignedTo);
      }
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
  public boolean updateUser(IUser user) {
    String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, user.getFirstName());
      stmt.setString(2, user.getLastName());
      stmt.setInt(3, Integer.parseInt(user.getId()));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update user failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update username in database
   */
  public boolean updateUsername(String userId, String newUsername) {
    String sql = "UPDATE users SET username = ? WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newUsername);
      stmt.setInt(2, Integer.parseInt(userId));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update username failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update email in database
   */
  public boolean updateUserEmail(String userId, String newEmail) {
    String sql = "UPDATE users SET email = ? WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newEmail);
      stmt.setInt(2, Integer.parseInt(userId));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update email failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update password in database
   */
  public boolean updateUserPassword(String userId, String newPassword) {
    String sql = "UPDATE users SET password = ? WHERE user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newPassword);
      stmt.setInt(2, Integer.parseInt(userId));
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update password failed: " + e.getMessage());
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
   * Update task title in database
   */
  public boolean updateTaskTitle(int taskId, String newTitle) {
    String sql = "UPDATE tasks SET title = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newTitle);
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task title failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update task priority in database
   */
  public boolean updateTaskPriority(int taskId, String newPriority) {
    String sql = "UPDATE tasks SET priority = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newPriority);
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task priority failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update task deadline in database
   */
  public boolean updateTaskDeadline(int taskId, String newDeadline) {
    String sql = "UPDATE tasks SET due_date = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newDeadline);
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task deadline failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update task description in database
   */
  public boolean updateTaskDescription(int taskId, String newDescription) {
    String sql = "UPDATE tasks SET description = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newDescription);
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task description failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update task assigned_to in database
   */
  public boolean updateTaskAssignedTo(int taskId, int assignedTo) {
    String sql = "UPDATE tasks SET assigned_to = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      if (assignedTo <= 0) {
        stmt.setNull(1, java.sql.Types.INTEGER);
      } else {
        stmt.setInt(1, assignedTo);
      }
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task assignment failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update task status in database
   */
  public boolean updateTaskStatus(int taskId, String status) {
    String sql = "UPDATE tasks SET status = ? WHERE task_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, status);
      stmt.setInt(2, taskId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update task status failed: " + e.getMessage());
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

  /**
   * Check if database is connected
   *
   * @return true if connected
   */
  public boolean isConnected() {
    try {
      return conn != null && !conn.isClosed();
    } catch (SQLException e) {
      return false;
    }
  }

  /**
   * Insert a new project into database
   *
   * @param title Project title
   * @param description Project description
   * @param ownerId Owner user ID
   * @return The generated project ID, or -1 if failed
   */
  public int insertProject(String title, String description, int ownerId) {
    String sql = "INSERT INTO projects (pname, owner_id) VALUES (?, ?)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.setString(1, title);
      stmt.setInt(2, ownerId);
      int rows = stmt.executeUpdate();

      if (rows > 0) {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1);
        }
      }
      stmt.close();
      return -1;
    } catch (SQLException e) {
      System.out.println("Insert project failed: " + e.getMessage());
      return -1;
    }
  }

  /**
   * Update project title in database
   *
   * @param projectId Project ID
   * @param newTitle New project title
   * @return true if updated successfully
   */
  public boolean updateProjectTitle(int projectId, String newTitle) {
    String sql = "UPDATE projects SET pname = ? WHERE project_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newTitle);
      stmt.setInt(2, projectId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update project title failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Update project description in database
   *
   * @param projectId Project ID
   * @param newDescription New project description
   * @return true if updated successfully
   */
  public boolean updateProjectDescription(int projectId, String newDescription) {
    String sql = "UPDATE projects SET description = ? WHERE project_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, newDescription);
      stmt.setInt(2, projectId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Update project description failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Add a member to a project in database
   *
   * @param projectId Project ID
   * @param memberId Member user ID
   * @return true if added successfully
   */
  public boolean addMemberToProject(int projectId, int memberId) {
    String sql = "INSERT INTO project_members (project_id, user_id) VALUES (?, ?)";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      stmt.setInt(2, memberId);
      int rows = stmt.executeUpdate();
      stmt.close();
      return rows > 0;
    } catch (SQLException e) {
      System.out.println("Add member to project failed: " + e.getMessage());
      return false;
    }
  }

  /**
   * Get projects for a user (owned or member)
   *
   * @param userId User ID
   * @return ArrayList of Project
   */
  public ArrayList<Project> getUserProjects(int userId) {
    ArrayList<Project> projects = new ArrayList<>();
    String sql = "SELECT DISTINCT p.* FROM projects p " +
        "LEFT JOIN project_members pm ON p.project_id = pm.project_id " +
        "WHERE p.owner_id = ? OR pm.user_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, userId);
      stmt.setInt(2, userId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        int projectId = rs.getInt("project_id");
        Owner owner = getOwnerById(rs.getInt("owner_id"));
        if (owner != null) {
          // Create project with database ID
          Project project = new Project(
              rs.getString("pname"),
              "",
              owner);
          // Set the database project ID
          project.setProjectIdDirect(projectId);
          projects.add(project);
        }
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get user projects failed: " + e.getMessage());
    }
    return projects;
  }

  /**
   * Get project members from database
   *
   * @param projectId Project ID
   * @return ArrayList of Member
   */
  public ArrayList<Member> getProjectMembers(int projectId) {
    ArrayList<Member> members = new ArrayList<>();
    String sql = "SELECT u.* FROM users u " +
        "JOIN project_members pm ON u.user_id = pm.user_id " +
        "WHERE pm.project_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Member member = new Member(
            rs.getInt("user_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email"),
            rs.getString("email"),
            rs.getString("password"));
        members.add(member);
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get project members failed: " + e.getMessage());
    }
    return members;
  }

  /**
   * Get tasks for a project from database
   *
   * @param projectId Project ID
   * @return ArrayList of Task
   */
  public ArrayList<Task> getProjectTasks(int projectId) {
    ArrayList<Task> tasks = new ArrayList<>();
    String sql = "SELECT * FROM tasks WHERE projects_id = ?";
    try {
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, projectId);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Task.TaskPriority priority;
        try {
          priority = Task.TaskPriority.valueOf(rs.getString("priority").toUpperCase());
        } catch (IllegalArgumentException e) {
          priority = Task.TaskPriority.MEDIUM;
        }

        LocalDate deadline = null;
        String dueDate = rs.getString("due_date");
        if (dueDate != null && !dueDate.isEmpty()) {
          try {
            deadline = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          } catch (Exception e) {
            // Ignore invalid dates
          }
        }

        // Get description if column exists, otherwise use empty string
        String description = "";
        try {
          description = rs.getString("description");
          if (description == null) description = "";
        } catch (SQLException e) {
          // description column doesn't exist, use empty string
        }

        Task task = new Task(
            rs.getString("title"),
            priority,
            deadline,
            description);
        task.setTaskId(rs.getInt("task_id"));
        
        // Handle NULL assigned_to (unassigned tasks)
        int assignedTo = rs.getInt("assigned_to");
        if (rs.wasNull()) {
          assignedTo = 0; // Unassigned
        }
        task.setAssignToDirect(assignedTo);

        String status = rs.getString("status");
        if ("done".equals(status)) {
          task.markCompleted(new Member("Temp", "User", "temp@example.com", "temp", "temp"));
        }

        tasks.add(task);
      }
      rs.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println("Get project tasks failed: " + e.getMessage());
    }
    return tasks;
  }
}

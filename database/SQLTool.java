import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQLTool - A utility class for extracting data from MariaDB databases
 */
public class SQLTool {
    
    private String url = "jdbc:mariadb://localhost:3306/mysql";
    private String username = "testing";
    private String password = "12345";
    private Connection connection;
    
    /**
     * Constructor for SQLTool
     * @param url The database URL
     * @param username The database username
     * @param password The database password
     */
    public SQLTool(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Establish a connection to the database
     * @return true if connection is successful, false otherwise
     */
    public boolean connect() {
        try {
            // Load MariaDB JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");
            
            // Establish connection
            this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MariaDB database successfully!");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("MariaDB JDBC Driver not found: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close the database connection
     */
    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    /**
     * Execute a SELECT query and return the results as a list of maps
     * @param query The SQL SELECT query to execute
     * @return List of maps representing rows in the result set
     */
    public List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        
        if (connection == null || isConnectionClosed()) {
            System.err.println("Not connected to database. Please connect first.");
            return results;
        }
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }
                
                results.add(row);
            }
            
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }
        
        return results;
    }
    
    /**
     * Execute a SELECT query and print the results to console
     * @param query The SQL SELECT query to execute
     */
    public void printQueryResults(String query) {
        List<Map<String, Object>> results = executeQuery(query);
        
        if (results.isEmpty()) {
            System.out.println("No results found for query: " + query);
            return;
        }
        
        // Print headers
        Map<String, Object> firstRow = results.get(0);
        System.out.println("\nResults for query: " + query);
        System.out.println("-".repeat(50));
        
        for (String columnName : firstRow.keySet()) {
            System.out.printf("%-15s", columnName);
        }
        System.out.println();
        System.out.println("-".repeat(50));
        
        // Print rows
        for (Map<String, Object> row : results) {
            for (String columnName : firstRow.keySet()) {
                Object value = row.get(columnName);
                System.out.printf("%-15s", value != null ? value.toString() : "NULL");
            }
            System.out.println();
        }
        System.out.println("-".repeat(50));
    }
    
    /**
     * Count the number of rows returned by a query
     * @param query The SQL query to count rows for
     * @return The number of rows in the result set
     */
    public int getRowCount(String query) {
        int count = 0;
        
        if (connection == null || isConnectionClosed()) {
            System.err.println("Not connected to database. Please connect first.");
            return count;
        }
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                count++;
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting rows: " + e.getMessage());
        }
        
        return count;
    }
    
    /**
     * Check if the connection is closed
     * @return true if connection is closed, false otherwise
     */
    private boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true; // If we can't check, assume it's closed
        }
    }
    
    /**
     * Get the database connection
     * @return The current database connection
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Main method for testing the SQLTool class
     */
    public static void main(String[] args) {
        // Example usage
        // Replace with your actual database details
        String dbUrl = "jdbc:mariadb://localhost:3306/mysql";  // Changed to mysql system DB
        String dbUsername = "testuser";  // Changed to test user
        String dbPassword = "testpass";  // Changed to test password
        
        SQLTool sqlTool = new SQLTool(dbUrl, dbUsername, dbPassword);
        
        if (sqlTool.connect()) {
            // Example query - this will work if connected to mysql system DB
            String query = "SELECT User, Host FROM mysql.user LIMIT 5;";
            sqlTool.printQueryResults(query);
            
            // Count rows example
            int rowCount = sqlTool.getRowCount("SELECT * FROM mysql.user;");
            System.out.println("Total users in database: " + rowCount);
            
            sqlTool.disconnect();
        } else {
            System.out.println("Failed to connect to the database.");
            System.out.println("Make sure you have:");
            System.out.println("1. MariaDB server running");
            System.out.println("2. Correct username and password");
            System.out.println("3. Created a database user with proper privileges");
            System.out.println("\nTo create a test user, run in MariaDB console:");
            System.out.println("CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'testpass';");
            System.out.println("GRANT ALL PRIVILEGES ON *.* TO 'testuser'@'localhost';");
            System.out.println("FLUSH PRIVILEGES;");
        }
    }
}

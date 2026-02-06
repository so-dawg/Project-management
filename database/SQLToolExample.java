import java.util.List;
import java.util.Map;

/**
 * Example demonstrating how to use the SQLTool class
 */
public class SQLToolExample {
    public static void main(String[] args) {
        // Database connection details - replace with your actual database info
        String dbUrl = "jdbc:mariadb://localhost:3306/testdb";  // Change to your database
        String dbUsername = "root";                             // Change to your username
        String dbPassword = "password";                         // Change to your password
        
        // Create an instance of SQLTool
        SQLTool sqlTool = new SQLTool(dbUrl, dbUsername, dbPassword);
        
        // Connect to the database
        if (sqlTool.connect()) {
            System.out.println("Successfully connected to the database!\n");
            
            // Example 1: Query to show all tables in the current database
            System.out.println("Example 1: Showing tables in the database");
            String showTablesQuery = "SHOW TABLES;";
            sqlTool.printQueryResults(showTablesQuery);
            
            // Example 2: Query to get information about columns in a table
            // Note: Replace 'your_table_name' with an actual table name in your database
            System.out.println("\nExample 2: Getting column information (replace 'your_table_name')");
            String describeTableQuery = "DESCRIBE your_table_name;";  // Replace with actual table name
            sqlTool.printQueryResults(describeTableQuery);
            
            // Example 3: Query to count records in a table
            System.out.println("\nExample 3: Counting records (replace 'your_table_name')");
            String countQuery = "SELECT COUNT(*) AS total_records FROM your_table_name;";  // Replace with actual table name
            sqlTool.printQueryResults(countQuery);
            
            // Example 4: A more complex query showing how to retrieve data
            System.out.println("\nExample 4: Sample SELECT query (replace 'your_table_name')");
            String sampleSelectQuery = "SELECT * FROM your_table_name LIMIT 5;";  // Replace with actual table name
            List<Map<String, Object>> results = sqlTool.executeQuery(sampleSelectQuery);
            
            if (!results.isEmpty()) {
                System.out.println("Retrieved " + results.size() + " rows:");
                for (int i = 0; i < results.size(); i++) {
                    System.out.println("Row " + (i+1) + ": " + results.get(i));
                }
            } else {
                System.out.println("No results found or table doesn't exist.");
            }
            
            // Close the connection
            sqlTool.disconnect();
        } else {
            System.out.println("Failed to connect to the database. Please check your credentials and connection settings.");
        }
    }
}
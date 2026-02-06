# SQLTool for MariaDB

SQLTool is a Java utility class for extracting data from MariaDB databases.

## Features

- Connect to MariaDB databases
- Execute SELECT queries
- Retrieve results as a list of maps
- Print formatted query results to console
- Count rows in result sets
- Proper connection management

## Prerequisites

- Java 8 or higher
- MariaDB JDBC driver (mariadb-java-client-x.x.x.jar)

## Usage

### 1. Compilation

```bash
javac -cp ".:mariadb-java-client-*.jar" SQLTool.java
```

### 2. Running

```bash
java -cp ".:mariadb-java-client-*.jar" SQLTool
```

### 3. Using in your own code

```java
// Create an instance of SQLTool
String dbUrl = "jdbc:mariadb://localhost:3306/your_database";
String dbUsername = "your_username";
String dbPassword = "your_password";

SQLTool sqlTool = new SQLTool(dbUrl, dbUsername, dbPassword);

// Connect to the database
if (sqlTool.connect()) {
    // Execute a query
    String query = "SELECT * FROM your_table LIMIT 10;";
    
    // Print results to console
    sqlTool.printQueryResults(query);
    
    // Or get results as a list of maps
    List<Map<String, Object>> results = sqlTool.executeQuery(query);
    
    // Count rows in a result set
    int rowCount = sqlTool.getRowCount("SELECT COUNT(*) FROM your_table;");
    
    // Close the connection when done
    sqlTool.disconnect();
}
```

## Methods

- `connect()` - Establishes a connection to the database
- `disconnect()` - Closes the database connection
- `executeQuery(String query)` - Executes a SELECT query and returns results as a list of maps
- `printQueryResults(String query)` - Executes a query and prints formatted results to console
- `getRowCount(String query)` - Returns the number of rows in the result set
- `getConnection()` - Returns the current database connection

## Dependencies

This project requires the MariaDB JDBC driver. You can download it from:
https://mariadb.com/downloads/connectors/

## Example Queries

The main method in the class includes example queries that work with the information_schema database to demonstrate functionality.
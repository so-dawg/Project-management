# Maven Build Guide

## Prerequisites

### Install Maven on Windows

1. **Download Maven:**
   - Go to https://maven.apache.org/download.cgi
   - Download `apache-maven-3.9.x-bin.zip`

2. **Extract:**
   - Extract to `C:\Program Files\Apache\maven`

3. **Add to PATH:**
   - Open System Properties → Environment Variables
   - Add `C:\Program Files\Apache\maven\bin` to PATH

4. **Verify:**
   ```cmd
   mvn --version
   ```

---

## Database Setup

### Option 1: MariaDB (Recommended)

1. **Install MariaDB:**
   - Download from https://mariadb.org/download/
   - Install MariaDB Server

2. **Create Database:**
   ```sql
   CREATE DATABASE project_management;
   USE project_management;
   ```

3. **Create User:**
   ```sql
   CREATE USER 'testing'@'localhost' IDENTIFIED BY '12345';
   GRANT ALL PRIVILEGES ON project_management.* TO 'testing'@'localhost';
   FLUSH PRIVILEGES;
   ```

### Option 2: MySQL

1. **Install MySQL:**
   - Download from https://dev.mysql.com/downloads/mysql/
   - Install MySQL Server

2. **Create Database:**
   ```sql
   CREATE DATABASE project_management;
   USE project_management;
   ```

3. **Create User:**
   ```sql
   CREATE USER 'testing'@'localhost' IDENTIFIED BY '12345';
   GRANT ALL PRIVILEGES ON project_management.* TO 'testing'@'localhost';
   FLUSH PRIVILEGES;
   ```

---

## Maven Commands

### First Time Setup (Download Dependencies)

```cmd
mvn clean install
```

This will:
- Download MariaDB driver
- Download MySQL driver
- Download JUnit for testing
- Download all other dependencies

### Compile Project

```cmd
mvn compile
```

### Run Main Class

```cmd
mvn exec:java
```

### Run Tests

```cmd
mvn test
```

### Build JAR File

```cmd
mvn package
```

Output: `target/project-management-1.0.0.jar`

### Run JAR File

```cmd
java -jar target/project-management-1.0.0.jar
```

### Clean Build

```cmd
mvn clean
```

---

## Database Connection

### For MariaDB (Default)

The `pom.xml` is configured for MariaDB by default:
- URL: `jdbc:mariadb://localhost:3306/project_management`
- Driver: `org.mariadb.jdbc.Driver`

### For MySQL

Use MySQL profile:
```cmd
mvn clean install -Pmysql
```

This switches to:
- URL: `jdbc:mysql://localhost:3306/project_management`
- Driver: `com.mysql.cj.jdbc.Driver`

---

## Troubleshooting

### "Driver not found" Error

Make sure dependencies are downloaded:
```cmd
mvn clean install
```

### "Connection refused" Error

1. Check database server is running
2. Check username/password in `SQLTool.java`
3. Check database exists

### Maven not recognized

1. Check PATH environment variable
2. Restart command prompt
3. Run `mvn --version`

---

## Project Structure

```
Project-management/
├── pom.xml                    ← Maven configuration
├── Main.java                  ← Entry point
├── logic/                     ← Business logic
│   ├── Member.java
│   ├── Owner.java
│   ├── Staff.java
│   ├── User.java
│   ├── Task.java
│   ├── TaskPriority.java
│   └── IUser_Member.java
├── gui/                       ← User interface
│   ├── login_screen.java
│   └── dashboard_ui.java
├── database/                  ← Database utilities
│   ├── SQLTool.java
│   └── mariadb-java-client-3.1.4.jar  ← Maven downloads this
└── target/                    ← Build output (generated)
    ├── classes/
    ├── lib/                   ← Dependencies copied here
    └── project-management-1.0.0.jar
```

---

## Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| MariaDB Java Client | 3.1.4 | Connect to MariaDB |
| MySQL Connector/J | 8.0.33 | Connect to MySQL |
| JUnit | 4.13.2 | Unit testing |

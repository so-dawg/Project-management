# Project Management System

A comprehensive Java-based project management application with terminal UI and database persistence.

## Features

### User Management

- User Registration & Login
- Edit Profile (Name, Email, Password)
- Role-based Permissions (Owner, Member)
- Guest User for Testing

### Project Management

- Create Projects
- Edit Projects
- View Projects (Owned & Member)
- Delete Projects
- Join Projects
- Leave Projects
- Add Members to Projects (Owners)

### Task Management

- Create Tasks
- Edit Tasks (Title, Priority, Deadline, Description)
- View Tasks with Project Names
- Delete Tasks
- Mark Tasks Complete/Incomplete
- Task Filtering (All, To Do, Completed, Overdue)
- Task Assignment
- Deadline Validation (No past dates)
- Priority Levels (LOW, MEDIUM, HIGH, URGENT)

### Database Features

- MariaDB/MySQL Persistence
- Offline Mode (In-memory)
- Auto-sync Changes

---

## Main Menu Options

**Main Menu:**

```
[===== MAIN MENU =====]
1. Projects       (All project operations)
2. Tasks          (All task operations)
3. Members        (Manage project members)
4. View Profile
5. Edit Profile
6. Exit
```

**Projects Menu:**

```
[===== PROJECTS MENU =====]
1. View Projects
2. Create Project
3. Edit Project
4. Delete Project
5. Join Project
6. Leave Project
0. Back to Main Menu
9. Logout
```

**Tasks Menu:**

```
[===== TASKS MENU =====]
1. View Tasks (with filters)
2. Create Task
3. Edit Task
4. Delete Task
5. Mark Complete/Incomplete
0. Back to Main Menu
9. Logout
```

**Members Menu:**

```
[===== MEMBERS MENU =====]
1. Add Member to Project (Owner)
2. View Project Members
0. Back to Main Menu
9. Logout
```

---

## Installation & Setup

### Prerequisites

- Java JDK 8 or higher
- MariaDB or MySQL (optional, for database persistence)
- MariaDB JDBC Driver (included in `database/` folder)

### Step 1: Database Setup

**One Command Setup (Windows & Linux):**

```bash
# Windows (Command Prompt or PowerShell)
mysql -u root -p < database/src/init_database.sql

# Linux (MariaDB/MySQL)
sudo mariadb < database/src/init_database.sql
```

The script will:

- Create the `project_management` database
- Create all tables (users, projects, tasks, etc.)
- Insert test users and sample data
- Show you the login credentials

### Step 2: Compile the Application

```bash
# Windows & Linux
javac -d . logic/*.java Main.java
```

### Step 3: Run the Application

**Auto-connects to database (fallback to offline):**

```bash
# Windows (Command Prompt or PowerShell)
java -cp ".;database\mariadb-java-client-3.1.4.jar" Main

# Linux
java -cp .:database/mariadb-java-client-3.1.4.jar Main
```

**Note:** The application automatically:

- Tries to connect to MySQL/MariaDB database
- Falls back to offline mode if database is unavailable
- Offline mode: data is NOT saved
- Database mode: all data is persisted

**Quick Start (All Platforms):**

```bash
# Setup database
mysql -u root -p < database/src/init_database.sql

# Compile
javac -d . logic/*.java Main.java

# Run (Windows)
java -cp ".;database\mariadb-java-client-3.1.4.jar" Main

# Run (Linux)
java -cp .:database/mariadb-java-client-3.1.4.jar Main
```

---

## Default Test Accounts

After running the database setup, these accounts are available:

| Role    | Email                     | Password     | Projects | Tasks |
| ------- | ------------------------- | ------------ | -------- | ----- |
| Admin   | admin@example.com         | Password123! | 0        | 0     |
| John    | john@example.com          | Password1!   | 0        | 0     |
| Sarah   | sarah.johnson@company.com | Owner123@    | 5        | 20+   |
| Michael | michael.chen@company.com  | Owner123@    | 5        | 20+   |
| Emily   | emily.smith@company.com   | Member123@   | Member   | 8+    |
| David   | david.wilson@company.com  | Member123@   | Member   | 8+    |

**Recommended for Testing:**

- **Sarah Johnson** - Owns 5 projects with 20+ tasks
- **Michael Chen** - Owns 5 projects with 20+ tasks

---

## Usage Guide

### Starting the Application

1. **Login Menu**
   ```
   [=== LOGIN MENU ===]
   1. Login
   2. Register
   3. Exit
   ```

### Task Filtering

When viewing tasks, you can filter by:

- **1. All Tasks** - Shows every task
- **2. To Do (Pending)** - Shows incomplete tasks
- **3. Completed** - Shows finished tasks
- **4. Past Deadline** - Shows overdue tasks (marked with `[OVERDUE]`)

### Task Status

Use the "Mark Complete/Incomplete" option to toggle task completion:

- Completed to Pending
- Pending to Completed

### Edit Profile

Use the "Edit Profile" option to update:

- First Name
- Last Name
- Username
- Email
- Password

---

## System Architecture

```
+-------------------------------------------------------------+
|                      Main.java (UI Layer)                    |
|  - Terminal menu system                                      |
|  - User input handling                                       |
|  - Display formatting                                        |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                   Logic Layer (Business Logic)               |
|  +--------------+  +--------------+  +------------------+   |
|  |   User.java  |  |  Project.java |  |    Task.java     |   |
|  |  (Base User) |  |  (Projects)  |  |    (Tasks)       |   |
|  +--------------+  +--------------+  +------------------+   |
|  +--------------+  +--------------+  +------------------+   |
|  |  Member.java |  |ProjectManager|  | UserRegistry.java|   |
|  |  (Member)    |  |  (Manager)   |  |   (Registry)     |   |
|  +--------------+  +--------------+  +------------------+   |
|  +--------------+  +--------------+                        |
|  |  Owner.java  |  |DatabaseManager|                        |
|  |  (Admin)     |  |   (Database)  |                        |
|  +--------------+  +--------------+                        |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                  MariaDB/MySQL Database                      |
|  - users (with username column)                              |
|  - projects                                                  |
|  - project_members                                           |
|  - tasks                                                     |
+-------------------------------------------------------------+
```

---

## Database Schema

### Users Table

```sql
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    username VARCHAR(100) UNIQUE,
    email VARCHAR(250) UNIQUE,
    password VARCHAR(250)
);
```

### Projects Table

```sql
CREATE TABLE projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    pname VARCHAR(250) NOT NULL,
    description TEXT,
    ntask INT DEFAULT 0,
    start_date DATE,
    end_date DATE,
    owner_id INT,
    FOREIGN KEY(owner_id) REFERENCES users(user_id) ON DELETE SET NULL
);
```

### Project Members Table

```sql
CREATE TABLE project_members (
    project_id INT,
    user_id INT,
    joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

### Tasks Table

```sql
CREATE TABLE tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    projects_id INT NOT NULL,
    assigned_to INT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status ENUM('todo', 'in_progress', 'done') DEFAULT 'todo',
    priority ENUM('low', 'medium', 'high', 'urgent') DEFAULT 'medium',
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(projects_id) REFERENCES projects(project_id) ON DELETE CASCADE,
    FOREIGN KEY(assigned_to) REFERENCES users(user_id) ON DELETE SET NULL
);
```

---

## Troubleshooting

### MySQL/MariaDB Not Found

**Windows:**

```cmd
# Add MySQL to PATH temporarily
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 8.0\bin

# Or permanently: System Properties -> Environment Variables -> Path -> Add MySQL bin path
```

**Linux:**

```bash
# Install MariaDB
sudo apt install mariadb-server  # Ubuntu/Debian
sudo systemctl start mariadb
```

### Login Fails with "Unknown label 'username'"

**Solution:** The username column is missing. Run:

```bash
mysql -u root -p project_management < database/src/init_database.sql
```

### Cannot Connect to Database

**Windows:**

1. Make sure MySQL service is running:
   ```cmd
   net start MySQL80
   ```
2. Check MySQL is accessible:
   ```cmd
   mysql -u root -p
   ```

**Linux:**

1. Make sure MariaDB/MySQL is running:
   ```bash
   sudo systemctl status mariadb
   sudo systemctl start mariadb
   ```

### Compile Errors

Make sure you have Java JDK installed:

```bash
java -version
javac -version
```

If not installed, download from: https://www.oracle.com/java/technologies/downloads/

### "Access Denied" Error

**Windows (Run as Administrator):**

```cmd
# Run Command Prompt as Administrator
mysql -u root -p < database/src/init_database.sql
```

**Linux (Use sudo):**

```bash
sudo mariadb < database/src/init_database.sql
```

---

## File Structure

```
Project-management/
├── Main.java                    # Main application entry point
├── README.md                    # This file
├── SETUP_GUIDE.md              # Quick setup guide
├── LICENSE                      # MIT License
├── run.sh                       # Run script (auto-starts MariaDB)
├── database/
│   ├── mariadb-java-client-3.1.4.jar  # JDBC driver
│   └── src/
│       └── init_database.sql   # Complete database setup
└── logic/
    ├── IUser.java              # User interface
    ├── User.java               # Base user class
    ├── Member.java             # Member role
    ├── Owner.java              # Owner role
    ├── Project.java            # Project entity
    ├── Task.java               # Task entity
    ├── ProjectManager.java     # Project management
    ├── UserRegistry.java       # User registry
    ├── TaskFilter.java         # Task filtering
    └── DatabaseManager.java    # Database operations
```

---

## Recent Updates

### Version 2.0 - Latest

- Edit Task (Title, Priority, Deadline, Description)
- Edit Project (Title, Description)
- Mark Task Complete/Incomplete
- Edit User Profile (with password verification)
- Task Filtering with Project Names
- Deadline Validation (No past dates)
- Fixed Task Assignment Display
- Fixed Join/Leave Project
- Fixed Add Member to Project
- Fixed View Project Members
- Organized code into cli/ folder for better structure

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Quick Start

### Windows

```cmd
REM 1. Setup database
mysql -u root -p < database\src\init_database.sql

REM 2. Compile
javac -d . logic\*.java Main.java

REM 3. Run
java -cp ".;database\mariadb-java-client-3.1.4.jar" Main

REM 4. Login
Email: sarah.johnson@company.com
Password: Owner123@
```

### Linux

```bash
# 1. Setup database
sudo mariadb < database/src/init_database.sql

# 2. Compile
javac -d . logic/*.java Main.java

# 3. Run (or use ./run.sh to auto-start MariaDB)
java -cp .:database/mariadb-java-client-3.1.4.jar Main

# 4. Login
Email: sarah.johnson@company.com
Password: Owner123@
```

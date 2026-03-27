# Setup Guide - Project Management System

## Quick Setup (5 Minutes)

### Step 1: Install Prerequisites

**Windows:**
1. Install Java JDK: https://www.oracle.com/java/technologies/downloads/
2. Install MySQL: https://dev.mysql.com/downloads/installer/
3. Verify installation:
   ```cmd
   java -version
   mysql --version
   ```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install default-jdk mariadb-server
sudo systemctl start mariadb
```

### Step 2: Setup Database

**Windows:**
```cmd
mysql -u root -p < database\src\init_database.sql
```

**Linux:**
```bash
sudo mariadb < database/src/init_database.sql
```

Enter your MySQL root password when prompted.

### Step 3: Compile

```bash
javac -d . logic/*.java Main.java
```

### Step 4: Run

**Auto-connects to database (fallback to offline):**

**Windows:**
```cmd
java -cp ".;database\mariadb-java-client-3.1.4.jar" Main
```

**Linux:**
```bash
java -cp .:database/mariadb-java-client-3.1.4.jar Main
```

**Note:** The application automatically:
- Tries to connect to MySQL/MariaDB database
- Falls back to offline mode if database is unavailable
- Offline mode: data is NOT saved
- Database mode: all data is persisted

### Step 5: Login

At the login screen:
```
Email: sarah.johnson@company.com
Password: Owner123@
```

---

## Menu Structure

**Main Menu (5 options):**
- **1. Projects** - View, create, edit, delete projects
- **2. Tasks** - View, create, edit, delete tasks
- **3. Members** - Add members, view members
- **4. Profile** - Edit your profile
- **5. Logout**

**Sub-menus:**
- **Projects Menu** (6 options) - All project operations
- **Tasks Menu** (6 options) - All task operations  
- **Members Menu** (2 options) - Member management

Press `0` in any sub-menu to go back to main menu.

---

## Common Issues & Solutions

### "mysql is not recognized"

**Windows:** Add MySQL to PATH
```cmd
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 8.0\bin
```

**Linux:** Install MariaDB
```bash
sudo apt install mariadb-server
```

### "Access Denied"

**Windows:** Run Command Prompt as Administrator

**Linux:** Use sudo
```bash
sudo mariadb < database/src/init_database.sql
```

### "javac: command not found"

Install Java JDK (not just JRE):
- Windows: Download from Oracle website
- Linux: `sudo apt install default-jdk`

---

## Test Accounts

| Email                          | Password      | Role   |
|--------------------------------|---------------|--------|
| admin@example.com              | Password123!  | Admin  |
| john@example.com               | Password1!    | Member |
| sarah.johnson@company.com      | Owner123@     | Owner  |
| michael.chen@company.com       | Owner123@     | Owner  |

---

## What Gets Installed

The setup script creates:
- ✅ `project_management` database
- ✅ 5 tables (users, projects, project_members, tasks, task_comments)
- ✅ 10 test users
- ✅ 5 sample projects
- ✅ 10 sample tasks

---

## Next Steps

After setup:
1. Run the application
2. Login with a test account
3. Go to **Projects** → Create a project
4. Go to **Tasks** → Add tasks to your project
5. Go to **Members** → Invite members to join

Enjoy! 🎉

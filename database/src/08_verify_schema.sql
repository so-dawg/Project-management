-- Updated schema: Clean users table (no role/projects columns)
USE project_management;

-- Verify the current schema
DESCRIBE users;
DESCRIBE projects;
DESCRIBE tasks;
DESCRIBE task_comments;

-- Show current data
SELECT '=== USERS ===' AS '';
SELECT * FROM users;

SELECT '=== PROJECTS ===' AS '';
SELECT p.project_id, p.pname, p.owner_id,
       CONCAT(u.first_name, ' ', u.last_name) AS owner_name
FROM projects p
JOIN users u ON p.owner_id = u.user_id;

-- Show who owns how many projects
SELECT '=== PROJECT OWNERSHIP SUMMARY ===' AS '';
SELECT
    u.user_id,
    CONCAT(u.first_name, ' ', u.last_name) AS user_name,
    COUNT(p.project_id) AS projects_owned
FROM users u
LEFT JOIN projects p ON u.user_id = p.owner_id
GROUP BY u.user_id, user_name
ORDER BY projects_owned DESC;

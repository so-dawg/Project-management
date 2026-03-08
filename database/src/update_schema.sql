-- Update schema: Remove role column, simplify to project ownership
USE project_management;

-- Remove role column from users table
ALTER TABLE users DROP COLUMN role;

-- Remove projects column (not needed)
ALTER TABLE users DROP COLUMN projects;

-- Verify the changes
DESCRIBE users;
DESCRIBE projects;

-- Show current data
SELECT '=== USERS (after update) ===' AS '';
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

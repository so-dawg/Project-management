-- DML Examples: UPDATE and DELETE statements
USE project_management;

-- ==================== UPDATE EXAMPLES ====================

-- Update 1: Change task status
UPDATE tasks SET status = 'in_progress' WHERE task_id = 6;

-- Update 2: Change project end date
UPDATE projects SET end_date = '2026-05-15' WHERE project_id = 1;

-- Update 3: Mark multiple tasks as done
UPDATE tasks SET status = 'done' WHERE due_date < '2026-02-15' AND status != 'done';

-- Update 4: Change task priority to high
UPDATE tasks SET priority = 'high' WHERE priority = 'medium' AND due_date < CURDATE();

-- Verify updates
SELECT '=== AFTER UPDATES ===' AS '';
SELECT task_id, title, status, priority FROM tasks WHERE task_id IN (6, 7, 8);
SELECT project_id, pname, end_date FROM projects WHERE project_id = 1;


-- ==================== DELETE EXAMPLES ====================

-- Delete 1: Remove a single task comment
DELETE FROM task_comments WHERE comment_id = 1;

-- Delete 2: Remove all comments from a specific user
-- DELETE FROM task_comments WHERE user_id = 1;

-- Delete 3: Remove a task (cascades to task_comments)
-- DELETE FROM tasks WHERE task_id = 7;

-- Delete 4: Remove all completed tasks (cascades to comments)
-- DELETE FROM tasks WHERE status = 'done';

-- Verify deletions
SELECT '=== COMMENTS (after delete) ===' AS '';
SELECT tc.comment_id, t.title, CONCAT(u.first_name, ' ', u.last_name) AS commenter, tc.comment
FROM task_comments tc
JOIN tasks t ON tc.task_id = t.task_id
JOIN users u ON tc.user_id = u.user_id;


-- ==================== DATA DICTIONARY ====================
-- Show table structures for report documentation

SELECT '=== DATA DICTIONARY ===' AS '';

SELECT '--- USERS TABLE ---' AS '';
DESCRIBE users;

SELECT '--- PROJECTS TABLE ---' AS '';
DESCRIBE projects;

SELECT '--- TASKS TABLE ---' AS '';
DESCRIBE tasks;

SELECT '--- TASK_COMMENTS TABLE ---' AS '';
DESCRIBE task_comments;


-- ==================== FINAL VERIFICATION ====================
SELECT '=== FINAL DATA SUMMARY ===' AS '';

SELECT 'Users:' AS '', COUNT(*) AS count FROM users;
SELECT 'Projects:' AS '', COUNT(*) AS count FROM projects;
SELECT 'Tasks:' AS '', COUNT(*) AS count FROM tasks;
SELECT 'Comments:' AS '', COUNT(*) AS count FROM task_comments;

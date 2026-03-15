-- Test all the views, procedures, and functions
-- Run this to check if everything works

USE project_management;

-- ------------------------------------------
-- Test Views
-- ------------------------------------------
SELECT '=== TESTING VIEWS ===' AS test_section;

SELECT '--- v_active_tasks ---' AS view_name;
SELECT * FROM v_active_tasks LIMIT 5;

SELECT '--- v_project_dashboard ---' AS view_name;
SELECT * FROM v_project_dashboard LIMIT 5;

SELECT '--- v_user_workload ---' AS view_name;
SELECT * FROM v_user_workload LIMIT 5;

SELECT '--- v_overdue_tasks ---' AS view_name;
SELECT * FROM v_overdue_tasks LIMIT 5;

-- ------------------------------------------
-- Test Procedures
-- ------------------------------------------
SELECT '=== TESTING PROCEDURES ===' AS test_section;

SELECT '--- sp_get_project_summary ---' AS procedure_name;
CALL sp_get_project_summary(1);

SELECT '--- sp_get_tasks_by_status ---' AS procedure_name;
CALL sp_get_tasks_by_status('in_progress');

SELECT '--- sp_get_user_tasks ---' AS procedure_name;
CALL sp_get_user_tasks(3);

-- ------------------------------------------
-- Test Functions
-- ------------------------------------------
SELECT '=== TESTING FUNCTIONS ===' AS test_section;

SELECT '--- fn_get_task_count ---' AS function_name;
SELECT 
    project_id,
    pname,
    fn_get_task_count(project_id) AS task_count
FROM projects
LIMIT 5;

SELECT '--- fn_completion_percentage ---' AS function_name;
SELECT 
    project_id,
    pname,
    fn_completion_percentage(project_id) AS percentage
FROM projects
LIMIT 5;

SELECT '--- fn_is_overdue ---' AS function_name;
SELECT 
    task_id,
    title,
    fn_is_overdue(task_id) AS overdue_status
FROM tasks
LIMIT 10;

-- ------------------------------------------
-- Show data counts
-- ------------------------------------------
SELECT '=== DATA SUMMARY ===' AS report_section;

SELECT 'Users' AS table_name, COUNT(*) AS count FROM users
UNION ALL
SELECT 'Projects', COUNT(*) FROM projects
UNION ALL
SELECT 'Tasks', COUNT(*) FROM tasks;

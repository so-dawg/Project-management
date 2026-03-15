-- Views - Saved queries we can reuse
-- Like creating a virtual table

USE project_management;

-- ------------------------------------------
-- View 1: Active tasks (not done yet)
-- ------------------------------------------
CREATE OR REPLACE VIEW v_active_tasks AS
SELECT 
    t.task_id,
    t.title,
    t.status,
    t.priority,
    t.due_date,
    p.pname AS project_name,
    CONCAT(u.first_name, ' ', u.last_name) AS assigned_to
FROM tasks t
JOIN projects p ON t.projects_id = p.project_id
JOIN users u ON t.assigned_to = u.user_id
WHERE t.status IN ('todo', 'in_progress')
ORDER BY t.due_date;

-- How to use: SELECT * FROM v_active_tasks;

-- ------------------------------------------
-- View 2: Project summary with task counts
-- ------------------------------------------
CREATE OR REPLACE VIEW v_project_dashboard AS
SELECT 
    p.project_id,
    p.pname,
    CONCAT(ow.first_name, ' ', ow.last_name) AS owner_name,
    p.start_date,
    p.end_date,
    COUNT(t.task_id) AS total_tasks,
    SUM(CASE WHEN t.status = 'done' THEN 1 ELSE 0 END) AS completed,
    SUM(CASE WHEN t.status = 'in_progress' THEN 1 ELSE 0 END) AS in_progress,
    SUM(CASE WHEN t.status = 'todo' THEN 1 ELSE 0 END) AS todo
FROM projects p
JOIN users ow ON p.owner_id = ow.user_id
LEFT JOIN tasks t ON p.project_id = t.projects_id
GROUP BY p.project_id, p.pname, ow.first_name, ow.last_name, p.start_date, p.end_date;

-- How to use: SELECT * FROM v_project_dashboard;

-- ------------------------------------------
-- View 3: How many tasks each user has
-- ------------------------------------------
CREATE OR REPLACE VIEW v_user_workload AS
SELECT 
    u.user_id,
    CONCAT(u.first_name, ' ', u.last_name) AS user_name,
    COUNT(t.task_id) AS total_tasks,
    SUM(CASE WHEN t.status = 'done' THEN 1 ELSE 0 END) AS completed,
    SUM(CASE WHEN t.status = 'todo' THEN 1 ELSE 0 END) AS pending
FROM users u
LEFT JOIN tasks t ON u.user_id = t.assigned_to
GROUP BY u.user_id, u.first_name, u.last_name
ORDER BY total_tasks DESC;

-- How to use: SELECT * FROM v_user_workload;

-- ------------------------------------------
-- View 4: Tasks that are past due date
-- ------------------------------------------
CREATE OR REPLACE VIEW v_overdue_tasks AS
SELECT 
    t.task_id,
    t.title,
    t.due_date,
    DATEDIFF(CURDATE(), t.due_date) AS days_overdue,
    p.pname AS project_name,
    CONCAT(u.first_name, ' ', u.last_name) AS assigned_to
FROM tasks t
JOIN projects p ON t.projects_id = p.project_id
JOIN users u ON t.assigned_to = u.user_id
WHERE t.due_date < CURDATE() 
  AND t.status != 'done'
ORDER BY days_overdue DESC;

-- How to use: SELECT * FROM v_overdue_tasks;

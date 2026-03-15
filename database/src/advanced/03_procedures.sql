-- Stored Procedures - Saved SQL code we can call with parameters
-- Like functions in programming

USE project_management;

-- ------------------------------------------
-- Procedure 1: Get all info about a project
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_get_project_summary(
    IN project_id_param INT
)
BEGIN
    SELECT 
        p.project_id,
        p.pname,
        CONCAT(ow.first_name, ' ', ow.last_name) AS owner_name,
        p.start_date,
        p.end_date,
        p.ntask,
        COUNT(t.task_id) AS actual_tasks,
        SUM(CASE WHEN t.status = 'done' THEN 1 ELSE 0 END) AS completed,
        SUM(CASE WHEN t.status = 'in_progress' THEN 1 ELSE 0 END) AS in_progress,
        SUM(CASE WHEN t.status = 'todo' THEN 1 ELSE 0 END) AS pending
    FROM projects p
    JOIN users ow ON p.owner_id = ow.user_id
    LEFT JOIN tasks t ON p.project_id = t.projects_id
    WHERE p.project_id = project_id_param
    GROUP BY p.project_id, p.pname, ow.first_name, ow.last_name, p.start_date, p.end_date, p.ntask;
END //

DELIMITER ;

-- How to use: CALL sp_get_project_summary(1);

-- ------------------------------------------
-- Procedure 2: Get tasks by their status
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_get_tasks_by_status(
    IN status_param VARCHAR(20)
)
BEGIN
    SELECT 
        t.task_id,
        t.title,
        t.priority,
        t.due_date,
        p.pname AS project_name,
        CONCAT(u.first_name, ' ', u.last_name) AS assigned_to
    FROM tasks t
    JOIN projects p ON t.projects_id = p.project_id
    JOIN users u ON t.assigned_to = u.user_id
    WHERE t.status = status_param
    ORDER BY t.due_date;
END //

DELIMITER ;

-- How to use: CALL sp_get_tasks_by_status('in_progress');
-- How to use: CALL sp_get_tasks_by_status('done');

-- ------------------------------------------
-- Procedure 3: Update task status
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_update_task_status(
    IN task_id_param INT,
    IN new_status VARCHAR(20),
    OUT message VARCHAR(255)
)
BEGIN
    DECLARE current_status VARCHAR(20);
    
    -- Get current status first
    SELECT status INTO current_status 
    FROM tasks 
    WHERE task_id = task_id_param;
    
    -- Check if status is valid
    IF new_status NOT IN ('todo', 'in_progress', 'done', 'cancelled') THEN
        SET message = 'Error: Invalid status';
    ELSEIF current_status IS NULL THEN
        SET message = 'Error: Task not found';
    ELSE
        -- Update the status
        UPDATE tasks 
        SET status = new_status 
        WHERE task_id = task_id_param;
        SET message = CONCAT('Success: Task updated from ', current_status, ' to ', new_status);
    END IF;
END //

DELIMITER ;

-- How to use: CALL sp_update_task_status(1, 'done', @msg);
-- Then: SELECT @msg;

-- ------------------------------------------
-- Procedure 4: Add a new task
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_add_task(
    IN project_id_param INT,
    IN assigned_to_param INT,
    IN title_param VARCHAR(255),
    IN priority_param VARCHAR(20),
    IN due_date_param DATE,
    OUT new_task_id INT,
    OUT message VARCHAR(255)
)
BEGIN
    DECLARE project_exists INT DEFAULT 0;
    DECLARE user_exists INT DEFAULT 0;
    
    -- Check if project exists
    SELECT COUNT(*) INTO project_exists 
    FROM projects 
    WHERE project_id = project_id_param;
    
    -- Check if user exists
    SELECT COUNT(*) INTO user_exists 
    FROM users 
    WHERE user_id = assigned_to_param;
    
    IF project_exists = 0 THEN
        SET message = 'Error: Project not found';
        SET new_task_id = NULL;
    ELSEIF user_exists = 0 THEN
        SET message = 'Error: User not found';
        SET new_task_id = NULL;
    ELSEIF priority_param NOT IN ('low', 'medium', 'high') THEN
        SET message = 'Error: Invalid priority';
        SET new_task_id = NULL;
    ELSE
        -- Insert new task
        INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date)
        VALUES (project_id_param, assigned_to_param, title_param, 'todo', priority_param, due_date_param);
        
        SET new_task_id = LAST_INSERT_ID();
        SET message = CONCAT('Success: Task created with ID ', new_task_id);
    END IF;
END //

DELIMITER ;

-- How to use: CALL sp_add_task(1, 2, 'New task', 'high', '2026-04-30', @id, @msg);
-- Then: SELECT @id, @msg;

-- ------------------------------------------
-- Procedure 5: Get all tasks for a user
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_get_user_tasks(
    IN user_id_param INT
)
BEGIN
    SELECT 
        t.task_id,
        t.title,
        t.status,
        t.priority,
        t.due_date,
        p.pname AS project_name
    FROM tasks t
    JOIN projects p ON t.projects_id = p.project_id
    WHERE t.assigned_to = user_id_param
    ORDER BY t.due_date;
END //

DELIMITER ;

-- How to use: CALL sp_get_user_tasks(3);

-- ------------------------------------------
-- Procedure 6: Delete a task
-- ------------------------------------------
DELIMITER //

CREATE PROCEDURE sp_delete_task(
    IN task_id_param INT,
    OUT message VARCHAR(255)
)
BEGIN
    DECLARE task_exists INT DEFAULT 0;
    
    -- Check if task exists
    SELECT COUNT(*) INTO task_exists 
    FROM tasks 
    WHERE task_id = task_id_param;
    
    IF task_exists = 0 THEN
        SET message = 'Error: Task not found';
    ELSE
        -- Delete comments first
        DELETE FROM task_comments WHERE task_id = task_id_param;
        -- Delete the task
        DELETE FROM tasks WHERE task_id = task_id_param;
        SET message = CONCAT('Success: Task ', task_id_param, ' deleted');
    END IF;
END //

DELIMITER ;

-- How to use: CALL sp_delete_task(5, @msg);
-- Then: SELECT @msg;

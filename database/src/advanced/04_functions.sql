-- Stored Functions - Like procedures but return a single value
-- Can be used inside SELECT statements

USE project_management;

-- ------------------------------------------
-- Function 1: Count tasks in a project
-- ------------------------------------------
DELIMITER //

CREATE FUNCTION fn_get_task_count(
    project_id_param INT
)
RETURNS INT
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE task_count INT;
    
    SELECT COUNT(*) INTO task_count
    FROM tasks
    WHERE projects_id = project_id_param;
    
    RETURN task_count;
END //

DELIMITER ;

-- How to use: 
-- SELECT project_id, pname, fn_get_task_count(project_id) AS tasks
-- FROM projects;

-- ------------------------------------------
-- Function 2: Calculate completion percentage
-- ------------------------------------------
DELIMITER //

CREATE FUNCTION fn_completion_percentage(
    project_id_param INT
)
RETURNS DECIMAL(5,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE total_tasks INT;
    DECLARE completed_tasks INT;
    DECLARE percentage DECIMAL(5,2);
    
    -- Get total tasks
    SELECT COUNT(*) INTO total_tasks
    FROM tasks
    WHERE projects_id = project_id_param;
    
    -- Get completed tasks
    SELECT COUNT(*) INTO completed_tasks
    FROM tasks
    WHERE projects_id = project_id_param AND status = 'done';
    
    -- Calculate percentage
    IF total_tasks = 0 THEN
        SET percentage = 0.00;
    ELSE
        SET percentage = ROUND((completed_tasks / total_tasks) * 100, 2);
    END IF;
    
    RETURN percentage;
END //

DELIMITER ;

-- How to use:
-- SELECT project_id, pname, fn_completion_percentage(project_id) AS complete_pct
-- FROM projects;

-- ------------------------------------------
-- Function 3: Days until task is due
-- ------------------------------------------
DELIMITER //

CREATE FUNCTION fn_days_until_due(
    task_id_param INT
)
RETURNS INT
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE due_dt DATE;
    DECLARE days_left INT;
    
    SELECT due_date INTO due_dt 
    FROM tasks 
    WHERE task_id = task_id_param;
    
    IF due_dt IS NULL THEN
        SET days_left = NULL;
    ELSE
        SET days_left = DATEDIFF(due_dt, CURDATE());
    END IF;
    
    RETURN days_left;
END //

DELIMITER ;

-- How to use:
-- SELECT task_id, title, fn_days_until_due(task_id) AS days_left
-- FROM tasks;

-- ------------------------------------------
-- Function 4: Get user full name
-- ------------------------------------------
DELIMITER //

CREATE FUNCTION fn_get_full_name(
    user_id_param INT
)
RETURNS VARCHAR(100)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE full_name VARCHAR(100);
    
    SELECT CONCAT(first_name, ' ', last_name) INTO full_name
    FROM users
    WHERE user_id = user_id_param;
    
    RETURN full_name;
END //

DELIMITER ;

-- How to use:
-- SELECT task_id, title, fn_get_full_name(assigned_to) AS assignee
-- FROM tasks;

-- ------------------------------------------
-- Function 5: Check if task is overdue
-- ------------------------------------------
DELIMITER //

CREATE FUNCTION fn_is_overdue(
    task_id_param INT
)
RETURNS VARCHAR(20)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE task_status VARCHAR(20);
    DECLARE task_due_date DATE;
    
    SELECT status, due_date INTO task_status, task_due_date
    FROM tasks
    WHERE task_id = task_id_param;
    
    IF task_status = 'done' THEN
        RETURN 'COMPLETED';
    ELSEIF task_due_date < CURDATE() THEN
        RETURN 'OVERDUE';
    ELSE
        RETURN 'ON_TIME';
    END IF;
END //

DELIMITER ;

-- How to use:
-- SELECT task_id, title, fn_is_overdue(task_id) AS status
-- FROM tasks;

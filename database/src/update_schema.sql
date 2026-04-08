-- Add missing columns to existing database
-- Run this to update your database schema
-- Usage: mysql -u root -p project_management < database/src/update_schema.sql

USE project_management;

-- Add missing columns to existing tables if they don't exist
DELIMITER //

-- Add description column to projects table
CREATE PROCEDURE add_description()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM information_schema.columns
        WHERE table_schema = 'project_management'
          AND table_name = 'projects'
          AND column_name = 'description'
    ) THEN
        ALTER TABLE projects ADD COLUMN description TEXT AFTER pname;
    END IF;
END //

-- Add created_at column to projects table
CREATE PROCEDURE add_created_at()
BEGIN
    IF NOT EXISTS (
        SELECT * FROM information_schema.columns
        WHERE table_schema = 'project_management'
          AND table_name = 'projects'
          AND column_name = 'created_at'
    ) THEN
        ALTER TABLE projects ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER owner_id;
    END IF;
END //

DELIMITER ;

CALL add_description();
CALL add_created_at();

DROP PROCEDURE IF EXISTS add_description;
DROP PROCEDURE IF EXISTS add_created_at;

-- Fix priority ENUM to include 'urgent'
ALTER TABLE tasks MODIFY COLUMN priority ENUM('low', 'medium', 'high', 'urgent') DEFAULT 'medium';

-- Verify the changes
DESCRIBE projects;
DESCRIBE tasks;

SELECT 'Schema updated successfully!' AS '';
SELECT 'Tables ready for Project Management System' AS '';

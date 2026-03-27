-- Add missing columns to existing database
-- Run this to update your database schema
-- Usage: mysql -u root -p project_management < database/src/update_schema.sql

USE project_management;

-- Add description column to projects table if it doesn't exist
ALTER TABLE projects ADD COLUMN IF NOT EXISTS description TEXT AFTER pname;

-- Add created_at column to projects table if it doesn't exist
ALTER TABLE projects ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER owner_id;

-- Fix priority ENUM to include 'urgent'
ALTER TABLE tasks MODIFY COLUMN priority ENUM('low', 'medium', 'high', 'urgent') DEFAULT 'medium';

-- Verify the changes
DESCRIBE projects;
DESCRIBE tasks;

SELECT 'Schema updated successfully!' AS '';
SELECT 'Tables ready for Project Management System' AS '';

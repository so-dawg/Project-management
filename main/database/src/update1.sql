
CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  projects INT,
  email VARCHAR(250),
  password VARCHAR(250),
  role ENUM('owner', 'member') DEFAULT 'member'
);

CREATE TABLE projects (
  project_id INT AUTO_INCREMENT PRIMARY KEY,
  pname VARCHAR(250),
  ntask INT,
  start_date DATE,
  end_date DATE,
  owner_id INT,

  FOREIGN KEY(owner_id) 
    REFERENCES users(user_id) 
    ON DELETE SET NULL
);

CREATE TABLE tasks (
  task_id INT AUTO_INCREMENT PRIMARY KEY,
  projects_id INT NOT NULL,
  assigned_to INT,
  title VARCHAR(200) NOT NULL,
  status ENUM('todo', 'in_progress', 'done') DEFAULT 'todo',
  priority ENUM('low', 'medium', 'high') DEFAULT 'medium',
  due_date DATE,

  FOREIGN KEY(projects_id) 
    REFERENCES projects(project_id)
    ON DELETE CASCADE,

  FOREIGN KEY(assigned_to)
    REFERENCES users(user_id)
    ON DELETE SET NULL
);

CREATE TABLE task_comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT NOT NULL,
    user_id INT NOT NULL,
    comment TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (task_id)
        REFERENCES tasks(task_id)
        ON DELETE CASCADE,

    FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

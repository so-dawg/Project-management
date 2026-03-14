USE project_management;

UPDATE tasks SET status = 'in_progress' WHERE task_id = 6;
UPDATE tasks SET status = 'done' WHERE task_id = 8;
UPDATE tasks SET status = 'in_progress' WHERE task_id = 12;
UPDATE tasks SET status = 'done' WHERE task_id = 18;
UPDATE tasks SET status = 'done' WHERE task_id = 20;

UPDATE projects SET end_date = '2026-05-01' WHERE project_id = 1;
UPDATE projects SET ntask = 13 WHERE project_id = 3;
UPDATE projects SET owner_id = 8 WHERE project_id = 5;

UPDATE users SET password = 'j1114444' WHERE user_id = 3;
UPDATE users SET password = '975385834957' WHERE user_id = 9;
UPDATE users SET email = 'Cutekitty@cadt.edu.kh' WHERE user_id = 6;
UPDATE users SET email = 'bigSwordOtter@ohio.com' WHERE user_id = 10;
UPDATE users SET email = 'ballheadperso@shadow_realm.com' WHERE user_id = 5;

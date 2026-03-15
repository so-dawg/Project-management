-- Extra fake data for project_management database
USE project_management;

-- ==================== MORE USERS ====================
INSERT INTO users (first_name, last_name, email, password) VALUES
('Chris', 'Garcia', 'chris.garcia@company.com', 'Member123@'),
('Michelle', 'White', 'michelle.white@company.com', 'Member123@'),
('Kevin', 'Harris', 'kevin.harris@company.com', 'Member123@'),
('Rachel', 'Clark', 'rachel.clark@company.com', 'Member123@'),
('Daniel', 'Lewis', 'daniel.lewis@company.com', 'Member123@'),
('Sophia', 'Walker', 'sophia.walker@company.com', 'Member123@'),
('Matthew', 'Hall', 'matthew.hall@company.com', 'Member123@'),
('Olivia', 'Young', 'olivia.young@company.com', 'Member123@'),
('Andrew', 'King', 'andrew.king@company.com', 'Member123@'),
('Emma', 'Wright', 'emma.wright@company.com', 'Member123@'),
('Joshua', 'Lopez', 'joshua.lopez@company.com', 'Member123@'),
('Isabella', 'Hill', 'isabella.hill@company.com', 'Member123@'),
('Ryan', 'Scott', 'ryan.scott@company.com', 'Member123@'),
('Mia', 'Green', 'mia.green@company.com', 'Member123@'),
('Nathan', 'Adams', 'nathan.adams@company.com', 'Member123@'),
('Charlotte', 'Baker', 'charlotte.baker@company.com', 'Member123@'),
('Ethan', 'Nelson', 'ethan.nelson@company.com', 'Member123@'),
('Amelia', 'Carter', 'amelia.carter@company.com', 'Member123@'),
('Alexander', 'Mitchell', 'alexander.mitchell@company.com', 'Member123@'),
('Harper', 'Perez', 'harper.perez@company.com', 'Member123@'),
('William', 'Roberts', 'william.roberts@company.com', 'Member123@'),
('Evelyn', 'Turner', 'evelyn.turner@company.com', 'Member123@'),
('Benjamin', 'Phillips', 'benjamin.phillips@company.com', 'Member123@'),
('Abigail', 'Campbell', 'abigail.campbell@company.com', 'Member123@'),
('Lucas', 'Parker', 'lucas.parker@company.com', 'Member123@'),
('Elizabeth', 'Evans', 'elizabeth.evans@company.com', 'Member123@'),
('Henry', 'Edwards', 'henry.edwards@company.com', 'Member123@'),
('Sofia', 'Collins', 'sofia.collins@company.com', 'Member123@'),
('Jack', 'Stewart', 'jack.stewart@company.com', 'Member123@'),
('Avery', 'Sanchez', 'avery.sanchez@company.com', 'Member123@');

-- ==================== MORE PROJECTS ====================
-- Projects owned by Sarah (user_id = 1)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('Customer Support Portal', 0, '2026-03-01', '2026-05-31', 1),
('Marketing Website Refresh', 0, '2026-04-01', '2026-06-30', 1),
('Security Compliance Audit', 0, '2026-04-15', '2026-07-15', 1),
('Big Data Analytics Platform', 0, '2026-05-01', '2026-09-30', 1),
('Employee Training System', 0, '2026-03-01', '2026-06-15', 1),
('Inventory Management System', 0, '2026-04-01', '2026-07-31', 1),
('Social Media Integration', 0, '2026-05-01', '2026-07-31', 1),
('Payment Gateway Upgrade', 0, '2026-06-01', '2026-08-31', 1),
('HR Management System', 0, '2026-07-01', '2026-10-31', 1),
('Fleet Tracking System', 0, '2026-08-01', '2026-11-30', 1);

-- Projects owned by Michael (user_id = 2)
INSERT INTO projects (pname, ntask, start_date, end_date, owner_id) VALUES
('CI/CD Pipeline Setup', 0, '2026-03-15', '2026-05-15', 2),
('Microservices Architecture', 0, '2026-04-01', '2026-08-31', 2),
('Cloud Infrastructure Migration', 0, '2026-05-01', '2026-09-30', 2),
('API Gateway Implementation', 0, '2026-06-01', '2026-09-15', 2),
('Monitoring and Logging System', 0, '2026-04-15', '2026-07-15', 2),
('Disaster Recovery Plan', 0, '2026-07-01', '2026-10-31', 2),
('Blockchain Integration POC', 0, '2026-08-01', '2026-12-31', 2),
('AI Chatbot Development', 0, '2026-05-15', '2026-09-15', 2),
('IoT Sensor Network', 0, '2026-06-01', '2026-10-15', 2),
('Quantum Computing Research', 0, '2026-09-01', '2027-03-31', 2);

-- ==================== TASKS ====================
-- Tasks for Project 6 (Customer Support Portal)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(6, 11, 'Design ticket submission form', 'todo', 'high', '2026-03-20'),
(6, 12, 'Create knowledge base UI', 'todo', 'medium', '2026-03-25'),
(6, 13, 'Implement live chat feature', 'todo', 'high', '2026-04-10'),
(6, 14, 'Build FAQ section', 'todo', 'low', '2026-04-15'),
(6, 11, 'Setup email notifications', 'todo', 'medium', '2026-04-20'),
(6, 12, 'Admin dashboard for support team', 'todo', 'high', '2026-05-01'),
(6, 13, 'Customer feedback system', 'todo', 'medium', '2026-05-10'),
(6, 14, 'Multi-language support', 'todo', 'low', '2026-05-20');

-- Tasks for Project 7 (Marketing Website)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(7, 15, 'Redesign landing page', 'todo', 'high', '2026-04-15'),
(7, 16, 'Create about us page', 'todo', 'medium', '2026-04-20'),
(7, 17, 'Write marketing copy', 'todo', 'medium', '2026-04-25'),
(7, 18, 'Design contact form', 'todo', 'low', '2026-05-01'),
(7, 15, 'SEO optimization', 'todo', 'high', '2026-05-15'),
(7, 16, 'Blog section implementation', 'todo', 'medium', '2026-05-20'),
(7, 17, 'Social media integration', 'todo', 'low', '2026-06-01'),
(7, 18, 'Google Analytics setup', 'todo', 'medium', '2026-06-10'),
(7, 15, 'A/B testing framework', 'todo', 'high', '2026-06-15');

-- Tasks for Project 8 (CI/CD Pipeline)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(8, 19, 'Setup Jenkins server', 'todo', 'high', '2026-03-25'),
(8, 20, 'Configure Docker containers', 'todo', 'high', '2026-04-01'),
(8, 21, 'Create automated test pipeline', 'todo', 'high', '2026-04-10'),
(8, 22, 'Setup staging environment', 'todo', 'medium', '2026-04-20'),
(8, 19, 'Configure deployment scripts', 'todo', 'high', '2026-04-30'),
(8, 20, 'Setup monitoring and alerts', 'todo', 'medium', '2026-05-10'),
(8, 21, 'Kubernetes cluster setup', 'todo', 'high', '2026-05-05');

-- Tasks for Project 9 (Security Audit)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(9, 23, 'Vulnerability scanning', 'todo', 'high', '2026-05-01'),
(9, 24, 'Penetration testing', 'todo', 'high', '2026-05-15'),
(9, 25, 'Code security review', 'todo', 'high', '2026-05-20'),
(9, 26, 'Update security policies', 'todo', 'medium', '2026-06-01'),
(9, 23, 'GDPR compliance check', 'todo', 'high', '2026-06-15'),
(9, 24, 'Security training for team', 'todo', 'medium', '2026-07-01'),
(9, 25, 'SSL certificate renewal', 'todo', 'medium', '2026-06-20'),
(9, 26, 'Access control audit', 'todo', 'high', '2026-06-10');

-- Tasks for Project 10 (Data Analytics Platform)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(10, 27, 'Design data warehouse schema', 'todo', 'high', '2026-05-15'),
(10, 28, 'Setup ETL pipelines', 'todo', 'high', '2026-06-01'),
(10, 29, 'Implement data visualization', 'todo', 'medium', '2026-06-15'),
(10, 30, 'Build reporting module', 'todo', 'medium', '2026-07-01'),
(10, 27, 'Machine learning integration', 'todo', 'low', '2026-08-01'),
(10, 28, 'Real-time analytics dashboard', 'todo', 'high', '2026-08-15'),
(10, 29, 'Data backup and recovery', 'todo', 'high', '2026-09-01'),
(10, 30, 'Performance optimization', 'todo', 'medium', '2026-09-15'),
(10, 27, 'Data quality checks', 'todo', 'high', '2026-07-15');

-- Tasks for Project 11 (Employee Training System)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(11, 31, 'Design course catalog UI', 'todo', 'high', '2026-03-15'),
(11, 32, 'Build video player component', 'todo', 'high', '2026-03-25'),
(11, 33, 'Create quiz system', 'todo', 'medium', '2026-04-05'),
(11, 34, 'Progress tracking dashboard', 'todo', 'medium', '2026-04-15'),
(11, 31, 'Certificate generation', 'todo', 'low', '2026-05-01'),
(11, 32, 'Discussion forums', 'todo', 'medium', '2026-05-10'),
(11, 33, 'Mobile app integration', 'todo', 'high', '2026-05-20'),
(11, 34, 'Admin content management', 'todo', 'high', '2026-04-25'),
(11, 31, 'SCORM compliance', 'todo', 'medium', '2026-06-01');

-- Tasks for Project 12 (Inventory Management)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(12, 35, 'Database schema design', 'todo', 'high', '2026-04-10'),
(12, 36, 'Barcode scanning feature', 'todo', 'high', '2026-04-20'),
(12, 37, 'Stock level alerts', 'todo', 'high', '2026-05-01'),
(12, 38, 'Supplier management module', 'todo', 'medium', '2026-05-15'),
(12, 35, 'Purchase order system', 'todo', 'high', '2026-05-25'),
(12, 36, 'Inventory reports', 'todo', 'medium', '2026-06-05'),
(12, 37, 'Multi-warehouse support', 'todo', 'high', '2026-06-20'),
(12, 38, 'API for third-party integration', 'todo', 'medium', '2026-07-01'),
(12, 35, 'Mobile app for warehouse', 'todo', 'high', '2026-07-15');

-- Tasks for Project 13 (Social Media Integration)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(13, 39, 'Facebook API integration', 'todo', 'high', '2026-05-10'),
(13, 40, 'Twitter/X API integration', 'todo', 'high', '2026-05-15'),
(13, 41, 'LinkedIn sharing feature', 'todo', 'medium', '2026-05-25'),
(13, 42, 'Instagram feed display', 'todo', 'medium', '2026-06-05'),
(13, 39, 'Social login functionality', 'todo', 'high', '2026-06-15'),
(13, 40, 'Auto-posting scheduler', 'todo', 'low', '2026-06-25'),
(13, 41, 'Analytics dashboard', 'todo', 'medium', '2026-07-05'),
(13, 42, 'Comment moderation tool', 'todo', 'low', '2026-07-15');

-- Tasks for Project 14 (Payment Gateway Upgrade)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(14, 43, 'Stripe integration', 'todo', 'high', '2026-06-10'),
(14, 44, 'PayPal integration', 'todo', 'high', '2026-06-15'),
(14, 45, 'Apple Pay support', 'todo', 'medium', '2026-06-25'),
(14, 43, 'Google Pay support', 'todo', 'medium', '2026-07-05'),
(14, 44, 'Subscription billing', 'todo', 'high', '2026-07-15'),
(14, 45, 'Refund processing system', 'todo', 'high', '2026-07-25'),
(14, 43, 'Fraud detection', 'todo', 'high', '2026-08-05'),
(14, 44, 'PCI compliance audit', 'todo', 'high', '2026-08-15'),
(14, 45, 'Payment analytics', 'todo', 'medium', '2026-08-25');

-- Tasks for Project 15 (Microservices Architecture)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(15, 46, 'Service discovery setup', 'todo', 'high', '2026-04-15'),
(15, 47, 'API gateway configuration', 'todo', 'high', '2026-04-25'),
(15, 48, 'User service migration', 'todo', 'high', '2026-05-10'),
(15, 49, 'Order service migration', 'todo', 'high', '2026-05-25'),
(15, 46, 'Product service migration', 'todo', 'medium', '2026-06-10'),
(15, 47, 'Event-driven architecture', 'todo', 'high', '2026-06-25'),
(15, 48, 'Distributed tracing', 'todo', 'medium', '2026-07-10'),
(15, 49, 'Circuit breaker pattern', 'todo', 'high', '2026-07-25'),
(15, 46, 'Service mesh implementation', 'todo', 'medium', '2026-08-10');

-- Tasks for Project 16 (Cloud Infrastructure Migration)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(16, 11, 'AWS account setup', 'todo', 'high', '2026-05-10'),
(16, 12, 'VPC configuration', 'todo', 'high', '2026-05-20'),
(16, 13, 'EC2 instance migration', 'todo', 'high', '2026-06-05'),
(16, 14, 'RDS database setup', 'todo', 'high', '2026-06-20'),
(16, 11, 'S3 bucket configuration', 'todo', 'medium', '2026-07-05'),
(16, 12, 'CloudFront CDN setup', 'todo', 'medium', '2026-07-20'),
(16, 13, 'Lambda functions migration', 'todo', 'high', '2026-08-05'),
(16, 14, 'Load balancer configuration', 'todo', 'high', '2026-08-20'),
(16, 11, 'Auto-scaling groups', 'todo', 'high', '2026-09-05'),
(16, 12, 'Cost optimization review', 'todo', 'medium', '2026-09-20');

-- Tasks for Project 17 (API Gateway Implementation)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(17, 15, 'Kong gateway setup', 'todo', 'high', '2026-06-10'),
(17, 16, 'Rate limiting configuration', 'todo', 'high', '2026-06-20'),
(17, 17, 'Authentication middleware', 'todo', 'high', '2026-07-05'),
(17, 18, 'Request/response transformation', 'todo', 'medium', '2026-07-20'),
(17, 15, 'API versioning strategy', 'todo', 'medium', '2026-08-05'),
(17, 16, 'Developer portal setup', 'todo', 'low', '2026-08-20'),
(17, 17, 'API analytics dashboard', 'todo', 'medium', '2026-09-05');

-- Tasks for Project 18 (Monitoring and Logging System)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(18, 19, 'Prometheus setup', 'todo', 'high', '2026-04-25'),
(18, 20, 'Grafana dashboards', 'todo', 'high', '2026-05-10'),
(18, 21, 'ELK stack configuration', 'todo', 'high', '2026-05-25'),
(18, 19, 'Alert rules configuration', 'todo', 'high', '2026-06-10'),
(18, 20, 'Log aggregation setup', 'todo', 'medium', '2026-06-25'),
(18, 21, 'Distributed tracing with Jaeger', 'todo', 'medium', '2026-07-10');

-- Tasks for Project 19 (Disaster Recovery Plan)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(19, 22, 'Risk assessment', 'todo', 'high', '2026-07-10'),
(19, 23, 'Backup strategy design', 'todo', 'high', '2026-07-25'),
(19, 24, 'Recovery procedures documentation', 'todo', 'high', '2026-08-10'),
(19, 25, 'Failover testing', 'todo', 'high', '2026-08-25'),
(19, 22, 'DR site setup', 'todo', 'high', '2026-09-10'),
(19, 23, 'Regular drill scheduling', 'todo', 'medium', '2026-09-25'),
(19, 24, 'Team training', 'todo', 'medium', '2026-10-10'),
(19, 25, 'Documentation updates', 'todo', 'low', '2026-10-25');

-- Tasks for Project 20 (Blockchain Integration POC)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(20, 26, 'Blockchain platform research', 'todo', 'high', '2026-08-10'),
(20, 27, 'Smart contract development', 'todo', 'high', '2026-08-25'),
(20, 28, 'Web3 integration', 'todo', 'medium', '2026-09-10'),
(20, 29, 'Wallet integration', 'todo', 'medium', '2026-09-25'),
(20, 26, 'NFT marketplace POC', 'todo', 'low', '2026-10-10'),
(20, 27, 'Token economics design', 'todo', 'medium', '2026-10-25'),
(20, 28, 'Security audit', 'todo', 'high', '2026-11-10'),
(20, 29, 'Demo presentation', 'todo', 'medium', '2026-12-10');

-- Tasks for Project 21 (AI Chatbot Development)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(21, 30, 'NLP model selection', 'todo', 'high', '2026-05-25'),
(21, 31, 'Training data collection', 'todo', 'high', '2026-06-10'),
(21, 32, 'Model training pipeline', 'todo', 'high', '2026-06-25'),
(21, 33, 'Chat interface design', 'todo', 'medium', '2026-07-10'),
(21, 30, 'Backend API development', 'todo', 'high', '2026-07-25'),
(21, 31, 'Knowledge base integration', 'todo', 'medium', '2026-08-10'),
(21, 32, 'Sentiment analysis', 'todo', 'low', '2026-08-25'),
(21, 33, 'Multi-language support', 'todo', 'medium', '2026-09-10');

-- Tasks for Project 22 (IoT Sensor Network)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(22, 34, 'Sensor hardware selection', 'todo', 'high', '2026-06-15'),
(22, 35, 'MQTT broker setup', 'todo', 'high', '2026-06-25'),
(22, 36, 'Data ingestion pipeline', 'todo', 'high', '2026-07-10'),
(22, 34, 'Edge computing setup', 'todo', 'medium', '2026-07-25'),
(22, 35, 'Sensor dashboard', 'todo', 'medium', '2026-08-10'),
(22, 36, 'Predictive maintenance ML', 'todo', 'high', '2026-08-25'),
(22, 34, 'Alert system', 'todo', 'high', '2026-09-10'),
(22, 35, 'Historical data analysis', 'todo', 'low', '2026-09-25');

-- Tasks for Project 23 (Quantum Computing Research)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(23, 37, 'Quantum algorithm research', 'todo', 'high', '2026-09-15'),
(23, 38, 'Qiskit framework setup', 'todo', 'medium', '2026-09-30'),
(23, 39, 'Quantum simulation tests', 'todo', 'high', '2026-10-15'),
(23, 40, 'Hybrid classical-quantum pipeline', 'todo', 'medium', '2026-11-01'),
(23, 37, 'Performance benchmarking', 'todo', 'low', '2026-11-15'),
(23, 38, 'Research paper preparation', 'todo', 'medium', '2026-12-01'),
(23, 39, 'IBM Quantum access setup', 'todo', 'high', '2026-10-01'),
(23, 40, 'Error correction implementation', 'todo', 'high', '2026-12-15');

-- Tasks for Project 24 (HR Management System)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(24, 11, 'Employee database design', 'todo', 'high', '2026-07-15'),
(24, 12, 'Payroll integration', 'todo', 'high', '2026-07-30'),
(24, 13, 'Leave management system', 'todo', 'medium', '2026-08-15'),
(24, 14, 'Performance review module', 'todo', 'medium', '2026-08-30'),
(24, 11, 'Recruitment tracking', 'todo', 'high', '2026-09-15'),
(24, 12, 'Benefits administration', 'todo', 'medium', '2026-09-30'),
(24, 13, 'Employee self-service portal', 'todo', 'high', '2026-10-15');

-- Tasks for Project 25 (Fleet Tracking System)
INSERT INTO tasks (projects_id, assigned_to, title, status, priority, due_date) VALUES
(25, 15, 'GPS integration', 'todo', 'high', '2026-08-15'),
(25, 16, 'Real-time tracking dashboard', 'todo', 'high', '2026-08-30'),
(25, 17, 'Route optimization algorithm', 'todo', 'high', '2026-09-15'),
(25, 18, 'Fuel consumption tracking', 'todo', 'medium', '2026-09-30'),
(25, 15, 'Driver behavior monitoring', 'todo', 'medium', '2026-10-15'),
(25, 16, 'Maintenance scheduling', 'todo', 'low', '2026-10-30'),
(25, 17, 'Geofencing alerts', 'todo', 'high', '2026-11-15'),
(25, 18, 'Mobile app for drivers', 'todo', 'high', '2026-11-30');

-- ==================== TASK COMMENTS ====================
INSERT INTO task_comments (task_id, user_id, comment) VALUES
(182, 1, 'Make sure the form is user-friendly and accessible.'),
(182, 11, 'Will use React Hook Forms for validation.'),
(184, 1, 'Consider using WebSocket for real-time communication.'),
(184, 13, 'Planning to integrate Socket.io with Node.js backend.'),
(187, 2, 'The admin dashboard needs role-based access control.'),
(187, 12, 'Will implement RBAC with JWT tokens.'),
(190, 1, 'Keep the brand colors consistent with our guidelines.'),
(190, 15, 'Already have some mockups ready. Will share tomorrow.'),
(195, 2, 'SEO is critical for our marketing success.'),
(195, 15, 'Using Next.js for SSR to improve SEO.'),
(199, 2, 'Use the latest LTS version of Jenkins.'),
(199, 19, 'Will also configure Blue Ocean for better UI.'),
(203, 2, 'Kubernetes is essential for our microservices strategy.'),
(203, 21, 'Setting up EKS cluster with Terraform.'),
(207, 1, 'This is critical for our compliance certification.'),
(207, 23, 'Using OWASP ZAP and Nessus for comprehensive scanning.'),
(211, 2, 'Consider using Snowflake or Redshift for scalability.'),
(211, 27, 'Leaning towards Snowflake. Will present options next week.'),
(215, 1, 'The video player needs to support 4K content.'),
(215, 32, 'Using Video.js with HLS streaming support.'),
(221, 1, 'Barcode scanning needs to work offline.'),
(221, 35, 'Using ZXing library with local caching.'),
(227, 2, 'Facebook integration is our top priority.'),
(227, 30, 'Already have the Graph API integration working.'),
(233, 1, 'Stripe should be our primary payment processor.'),
(233, 34, 'Stripe has excellent documentation and support.'),
(238, 2, 'Service discovery is the foundation of microservices.'),
(238, 37, 'Using Consul for service discovery and health checks.'),
(243, 1, 'AWS is the right choice for our cloud strategy.'),
(243, 11, 'Already have the enterprise account set up.'),
(251, 2, 'Prometheus + Grafana is the gold standard.'),
(251, 19, 'Already have basic metrics collection working.'),
(258, 2, 'Ethereum is the most mature platform for our POC.'),
(258, 26, 'Also considering Polygon for lower gas fees.'),
(264, 2, 'GPT-4 API might be the best starting point.'),
(264, 30, 'Also evaluating open-source alternatives.'),
(270, 2, 'MQTT is the standard for IoT communication.'),
(270, 34, 'Setting up Mosquitto broker with clustering.'),
(276, 1, 'Quantum computing is the future of our research.'),
(276, 37, 'Starting with IBM Quantum Experience platform.'),
(283, 1, 'HR system needs to integrate with existing payroll.'),
(283, 11, 'Will use REST API for payroll integration.'),
(289, 2, 'GPS tracking needs to be accurate within 5 meters.'),
(289, 15, 'Using high-precision GPS modules with GLONASS.');

-- ==================== UPDATE PROJECT TASK COUNTS ====================
UPDATE projects SET ntask = (SELECT COUNT(*) FROM tasks WHERE tasks.projects_id = projects.project_id) WHERE project_id BETWEEN 6 AND 25;

-- ==================== VERIFY DATA ====================
SELECT '=== TOTAL USERS ===' AS '';
SELECT COUNT(*) AS total_users FROM users;

SELECT '=== TOTAL PROJECTS ===' AS '';
SELECT COUNT(*) AS total_projects FROM projects;

SELECT '=== TOTAL TASKS ===' AS '';
SELECT COUNT(*) AS total_tasks FROM tasks;

SELECT '=== TOTAL COMMENTS ===' AS '';
SELECT COUNT(*) AS total_comments FROM task_comments;

SELECT '=== TASKS BY STATUS ===' AS '';
SELECT status, COUNT(*) AS count FROM tasks GROUP BY status ORDER BY count DESC;

SELECT '=== TASKS BY PRIORITY ===' AS '';
SELECT priority, COUNT(*) AS count FROM tasks GROUP BY priority ORDER BY count DESC;

SELECT '=== PROJECTS WITH TASK COUNTS ===' AS '';
SELECT project_id, pname, ntask, 
       CONCAT(u.first_name, ' ', u.last_name) AS owner_name
FROM projects p
JOIN users u ON p.owner_id = u.user_id
ORDER BY project_id;

SELECT '=== TOP TASK ASSIGNEES ===' AS '';
SELECT CONCAT(u.first_name, ' ', u.last_name) AS user_name, COUNT(t.task_id) AS task_count
FROM users u
LEFT JOIN tasks t ON u.user_id = t.assigned_to
GROUP BY u.user_id, user_name
ORDER BY task_count DESC
LIMIT 15;

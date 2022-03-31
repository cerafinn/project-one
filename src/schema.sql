-- project one schema:
DROP TABLE IF EXISTS users, user_roles, reimbursement, reimbursement_type, reimbursement_status;


--create role table and insert roles
CREATE TABLE user_roles (
	id SERIAL PRIMARY KEY,
	role VARCHAR(20) NOT NULL
);

INSERT INTO user_roles (role) VALUES
('employee'),
('finance manager');

--create user table and insert users, with user_role fk
CREATE TABLE users (
	id SERIAL PRIMARY KEY,
	username VARCHAR(200) NOT NULL UNIQUE,
	password VARCHAR(200) NOT NULL,
	user_first_name VARCHAR(200) NOT NULL,
	user_last_name VARCHAR(200) NOT NULL,
	user_email VARCHAR(200) NOT NULL,
	user_role_id INTEGER NOT NULL,
	CONSTRAINT fk_user_userrole FOREIGN KEY (user_role_id) REFERENCES user_roles(id)
);

INSERT INTO users (username, password, user_first_name, user_last_name, user_email, user_role_id) VALUES
('jdoe', 'pass123', 'John', 'Doe', 'jdoe@test.com', '1'),
('mmonroe', 'pass12345', 'Mike', 'Monroe', 'mmunroe@test.com', '1'),
('taiden', 'password123', 'Tom', 'Aiden', 'taiden@test.com', '1'),
('lourdsarah', 'password12345', 'Sarah', 'Lourd', 'lourd@test.com', '2'),
('afruit', 'pass123', 'Amber', 'Frem', 'afrem@test.com', '2');

SELECT users.id AS id, username, password, user_first_name AS first_name, user_last_name AS last_name, user_email AS email, user_roles.role FROM users LEFT JOIN user_roles ON user_roles.id = user_role_id;

--create reimbursement table, along with remit type and status
CREATE TABLE reimbursement_type (
	id SERIAL PRIMARY KEY,
	type VARCHAR(20) NOT NULL
);

INSERT INTO reimbursement_type (type) VALUES
('lodging'),
('travel'),
('food'),
('other');

CREATE TABLE reimbursement_status (
	id SERIAL PRIMARY KEY,
	status VARCHAR(20) NOT NULL
);

INSERT INTO reimbursement_status (status) VALUES
('pending'),
('approved'),
('denied');

CREATE TABLE reimbursement (
	id SERIAL PRIMARY KEY,
	reimb_amount INTEGER NOT NULL,
	reimb_submitted TIMESTAMP,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR(250) NOT NULL,
	reimb_receipt bytea,
	reimb_author INTEGER NOT NULL,
	CONSTRAINT fk_reimbursement_reimbauthor FOREIGN KEY (reimb_author) REFERENCES users(id),
	reimb_resolver INTEGER,
	CONSTRAINT fk_reimbursement_reimbresolver FOREIGN KEY (reimb_resolver) REFERENCES users(id),
	reimb_type_id INTEGER NOT NULL,
	CONSTRAINT fk_reimbursement_reimbtype FOREIGN KEY (reimb_type_id) REFERENCES reimbursement_type(id),
	reimb_status_id INTEGER NOT NULL,
	CONSTRAINT fk_reimbursement_reimbstatus FOREIGN KEY (reimb_status_id) REFERENCES reimbursement_status(id)
);

INSERT INTO reimbursement (reimb_amount, reimb_description, reimb_submitted, reimb_resolved, reimb_author, reimb_resolver, reimb_type_id, reimb_status_id) VALUES
('400', '3-day hotel stay', '2016-06-22 04:34:22.000', NULL, 1, NULL, 1, 1),
('100', 'evening restaurant', '2016-06-22 04:34:22.000', NULL, 1, NULL, 3, 1),
('1000', 'flight back to hq', '2016-06-22 04:34:22.000', '2016-06-22 04:34:22.000', 3, 4, 2, 2),
('400', '3-day hotel stay', '2016-06-22 04:34:22.000', NULL, 2, NULL, 3, 1),
('400', '3-day hotel stay', '2016-06-22 04:34:22.000', '2016-06-22 04:34:22.000', 1, 5, 4, 3),
('400', '3-day hotel stay', '2016-06-22 04:34:22.000', '2016-06-22 04:34:22.000', 1, 4, 4, 2);


SELECT * FROM user_roles;
SELECT * FROM users;
SELECT * FROM reimbursement_status;
SELECT * FROM reimbursement_type;
SELECT * FROM reimbursement;

SELECT reimbursement.id AS id, reimb_amount AS amount, reimb_description AS description, reimbursement_type.type AS type, reimbursement_status.status AS status,
CONCAT(reimb_author.user_first_name, ' ', reimb_author.user_last_name) AS employee,  CONCAT(reimb_resolver.user_first_name, ' ', reimb_resolver.user_last_name) AS manager,
reimb_submitted AS submitted, reimb_resolved AS resolved
FROM reimbursement LEFT JOIN reimbursement_type ON reimb_type_id = reimbursement_type.id LEFT JOIN reimbursement_status ON reimb_status_id = reimbursement_status.id
LEFT JOIN users reimb_author ON reimb_author.id = reimb_author LEFT JOIN users reimb_resolver ON reimb_resolver.id = reimb_resolver;
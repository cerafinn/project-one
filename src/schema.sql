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
	reimb_submitted TIMESTAMP NOT NULL ,
	reimb_resolved TIMESTAMP NOT NULL,
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


SELECT * FROM user_roles;
SELECT * FROM reimbursement_status;
SELECT * FROM reimbursement_type;
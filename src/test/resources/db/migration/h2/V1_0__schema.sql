CREATE TABLE IF NOT EXISTS activity
(
    completed boolean NOT NULL,
    date date NOT NULL,
    time_spent_in_minutes bigint NOT NULL,
    category_id uuid,
    id uuid NOT NULL,
    description varchar(255),
    user_id uuid
);

CREATE TABLE IF NOT EXISTS activity_category
(
    priority integer NOT NULL,
    id uuid NOT NULL,
    name varchar(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id uuid NOT NULL,
    user_name varchar(255),
    password varchar(255)
);

CREATE TABLE IF NOT EXISTS role
(
    id uuid NOT NULL,
    name character varying(255)
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);

INSERT INTO activity_category (id, name, priority) VALUES('125b4db2-94e0-4a2b-8de3-d8aac0d172df','Rozrywka', 5);

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES('9d364f1e-bc50-4537-bff9-adaa38072faf','Seriale', 120, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES('c27279a8-2c25-4bb2-8d76-b878d1f74025','Seriale', 120, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');

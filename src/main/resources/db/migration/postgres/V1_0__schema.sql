DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    password      VARCHAR(255) NOT NULL UNIQUE,
    user_name      VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name      VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role
(
    user_id UUID,
    role_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES role(id),
    UNIQUE (user_id, role_id)
);

DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    id        UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name      VARCHAR(50) NOT NULL UNIQUE,
    priority  INTEGER NOT NULL,
    user_id   UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS activity;

CREATE TABLE activity
(
    id                      UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    description              VARCHAR(50) NOT NULL UNIQUE,
    time_spent_in_minutes   BIGINT NOT NULL,
    category_id             UUID,
    date                    DATE NOT NULL,
    completed               boolean NOT NULL,
    user_id                  UUID,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO role (id, name)
VALUES (gen_random_uuid(), 'ADMIN');

INSERT INTO role (id, name)
VALUES (gen_random_uuid(), 'USER');


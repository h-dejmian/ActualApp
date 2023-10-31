DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id        UUID PRIMARY KEY,
    password      VARCHAR(255) NOT NULL UNIQUE,
    user_name      VARCHAR(255) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id        UUID PRIMARY KEY,
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
    id        UUID  PRIMARY KEY,
    name      VARCHAR(50) NOT NULL UNIQUE,
    priority  INTEGER NOT NULL,
    user_id   UUID,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS activity;

CREATE TABLE activity
(
    id                      UUID PRIMARY KEY,
    description              VARCHAR(50) NOT NULL,
    time_spent_in_minutes   BIGINT NOT NULL,
    category_id             UUID,
    date                    DATE NOT NULL,
    completed               boolean NOT NULL,
    user_id                  UUID,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO role (id, name)
VALUES ('372e3c55-85d3-4fc3-ba76-c7aef66b45b0', 'ADMIN');

INSERT INTO role (id, name)
VALUES (' 21f9ed3b-63c2-42c0-9190-2461c679dfb9 ', 'USER');

INSERT INTO users (id, user_name, password)
VALUES ('2f85b8fe-2888-4afb-b022-3d34ee604192', 'testUser', 'testPassword');

INSERT INTO category (id, name, priority, user_id) VALUES('d37b09cb-d8c1-4873-bdae-382902834855','Rozrywka', 5, '2f85b8fe-2888-4afb-b022-3d34ee604192');

INSERT INTO activity(id, description, time_spent_in_minutes, category_id, date, completed,  user_id )
VALUES('9d364f1e-bc50-4537-bff9-adaa38072faf','Seriale', 120, 'd37b09cb-d8c1-4873-bdae-382902834855', '2023-09-04', true, '2f85b8fe-2888-4afb-b022-3d34ee604192');
INSERT INTO activity(id, description, time_spent_in_minutes, category_id, date, completed, user_id )
VALUES('c27279a8-2c25-4bb2-8d76-b878d1f74025','Seriale', 120, 'd37b09cb-d8c1-4873-bdae-382902834855', '2023-09-04', true, '2f85b8fe-2888-4afb-b022-3d34ee604192');

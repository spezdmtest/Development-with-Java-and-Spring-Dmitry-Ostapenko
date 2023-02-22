INSERT INTO users (id, email, password)
VALUES (1, 'my@email.com', '$2a$10$/kgIB/bDXLYhJz.1iDDRw.t4j/ipmfzrDgzX3qfJhhRYbw.9ykLYC');

INSERT INTO roles (id ,name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USER');
ALTER SEQUENCE user_seq RESTART WITH 2;

INSERT INTO users_roles (user_id, role_id)
VALUES  (1,1);
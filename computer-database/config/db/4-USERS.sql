use `computer-database-db`;

DROP TABLE if EXISTS user_roles;
DROP TABLE if EXISTS users;

CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(255) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));
  
CREATE TABLE user_roles (
  user_role_id int NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
  
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$41gNgeb//NGo1pi5Zyst6OOzF27ONJz9M5uog8WM0IGsrjNyh7wS2', 1);
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_ADMIN');
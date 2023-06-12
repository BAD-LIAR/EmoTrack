CREATE TABLE IF NOT EXISTS users (
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS event (
    id bigint NOT NULL AUTO_INCREMENT,
    title varchar(255) DEFAULT NULL,
    description varchar(2000) DEFAULT NULL,
    emotional_condition bigint DEFAULT NULL,
    day_emotional_condition bigint DEFAULT NULL,
    start_date datetime DEFAULT NULL,
    end_date datetime DEFAULT NULL,
    user_id bigint default null,

    PRIMARY KEY (id),

    CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users (id)

);





INSERT INTO users (first_name, last_name)
VALUES ('Test1', 'Test1'), ('Test2', 'Test2');

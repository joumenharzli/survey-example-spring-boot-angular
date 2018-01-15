DROP TABLE IF EXISTS subjects;
CREATE TABLE subjects (
  id    INT8         NOT NULL AUTO_INCREMENT,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_subjects PRIMARY KEY (id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  id         INT8         NOT NULL AUTO_INCREMENT,
  label      VARCHAR(100) NOT NULL,
  subject_id INT8         NOT NULL,
  CONSTRAINT pk_questions PRIMARY KEY (id),
  CONSTRAINT fk_subjects_questions FOREIGN KEY (subject_id) REFERENCES subjects (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id   INT8         NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_responses;
CREATE TABLE user_responses (
  content     VARCHAR(100) NOT NULL,
  question_id INT8         NOT NULL,
  user_id     INT8         NOT NULL,
  CONSTRAINT pk_user_responses PRIMARY KEY (question_id, user_id),
  CONSTRAINT fk_questions_user_responses FOREIGN KEY (question_id) REFERENCES questions (id),
  CONSTRAINT fk_users_user_responses FOREIGN KEY (user_id) REFERENCES users (id)
);


DROP TABLE IF EXISTS subjects;
CREATE TABLE subjects (
  id    INT          NOT NULL AUTO_INCREMENT,
  label VARCHAR(100) NOT NULL,
  CONSTRAINT pk_subjects PRIMARY KEY (id)
);

DROP TABLE IF EXISTS questions;
CREATE TABLE questions (
  id         INT          NOT NULL AUTO_INCREMENT,
  label      VARCHAR(100) NOT NULL,
  subject_id INT          NOT NULL,
  CONSTRAINT pk_questions PRIMARY KEY (id),
  CONSTRAINT fk_questions FOREIGN KEY (subject_id) REFERENCES subjects (id)
);


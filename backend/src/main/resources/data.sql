INSERT INTO subjects (id, label) VALUES (1, 'Personal information');
INSERT INTO subjects (id, label) VALUES (2, 'Appreciations');

INSERT INTO questions (id, label, subject_id) VALUES (1, 'What''s your name ?', 1);
INSERT INTO questions (id, label, subject_id) VALUES (2, 'How old are you ?', 1);
INSERT INTO questions (id, label, subject_id) VALUES (3, 'Do you like this example ?', 2);
INSERT INTO questions (id, label, subject_id) VALUES (4, 'Any suggestions ?', 2);

INSERT INTO users (id, name) VALUES (1, 'demo');

INSERT INTO user_responses (content, question_id, user_id) VALUES ('Joe', 1, 1);
INSERT INTO user_responses (content, question_id, user_id) VALUES ('25', 2, 1);

INSERT INTO activity_category (id, name, priority) VALUES(gen_random_uuid(),'Rozrywka', 5);
INSERT INTO activity_category (id, name, priority) VALUES(gen_random_uuid(),'Programowanie', 1);
INSERT INTO activity_category (id, name, priority) VALUES(gen_random_uuid(),'Muzyka', 1);
INSERT INTO activity_category (id, name, priority) VALUES(gen_random_uuid(),'Sport', 2);
INSERT INTO activity_category (id, name, priority) VALUES(gen_random_uuid(),'Gotowanie', 3);

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Seriale', 120, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Spacer', 60, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Słuchanie muzyki', 60, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Seriale', 120, '2023-09-04', true, 'babdef25-ee00-4f8b-a454-dbe1b77140b5');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 120, '2023-09-04', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Teoria', 60, '2023-09-04', false, '26d6fd75-21af-4e31-bcb8-999b9db60e00');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Codecool', 450, '2023-09-02', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Codecool', 450, '2023-09-03', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 90, '2023-09-05', true, '7b99e3db-5f42-48c3-b122-cbe8d3c3b45e');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Drugie śniadanie', 20, '2023-09-05', true, '7b99e3db-5f42-48c3-b122-cbe8d3c3b45e');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na gitarze', 90, '2023-09-05', true, '22cadc57-fac8-410b-bb7f-ad8d2523b34a');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na ukulele', 30, '2023-09-05', true, '22cadc57-fac8-410b-bb7f-ad8d2523b34a');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na ukulele', 30, '2023-09-05', true, '22cadc57-fac8-410b-bb7f-ad8d2523b34a');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 120, '2023-09-05', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Bieganie', 60, '2023-09-06', true, '85b06158-ff34-4954-93e7-82b4fc17fe95');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Ćwiczenia', 60, '2023-09-06', true, '85b06158-ff34-4954-93e7-82b4fc17fe95');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Teoria', 60, '2023-09-06', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 60, '2023-09-06', false, '26d6fd75-21af-4e31-bcb8-999b9db60e00');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 60, '2023-09-06', true, '7b99e3db-5f42-48c3-b122-cbe8d3c3b45e');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na gitarze', 90, '2023-09-07', true, '22cadc57-fac8-410b-bb7f-ad8d2523b34a');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 60, '2023-09-07', true, '7b99e3db-5f42-48c3-b122-cbe8d3c3b45e');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Drugie Śniadanie', 30, '2023-09-07', true, '7b99e3db-5f42-48c3-b122-cbe8d3c3b45e');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Projekt', 90, '2023-09-07', true, '26d6fd75-21af-4e31-bcb8-999b9db60e00');

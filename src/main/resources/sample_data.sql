INSERT INTO category (id, name, priority) VALUES(gen_random_uuid(),'Rozrywka', 5);
INSERT INTO category (id, name, priority) VALUES(gen_random_uuid(),'Programowanie', 1);
INSERT INTO category (id, name, priority) VALUES(gen_random_uuid(),'Muzyka', 1);
INSERT INTO category (id, name, priority) VALUES(gen_random_uuid(),'Sport', 2);
INSERT INTO category (id, name, priority) VALUES(gen_random_uuid(),'Gotowanie', 3);

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Seriale', 120, '2023-09-04', true, 'cbf471e6-237f-4762-9811-6869e92233c7');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Spacer', 60, '2023-09-04', true, 'cbf471e6-237f-4762-9811-6869e92233c7');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Słuchanie muzyki', 60, '2023-09-04', true, '057bccad-305e-4d5b-b32b-cd313ecfa74c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Seriale', 120, '2023-09-04', true, 'cbf471e6-237f-4762-9811-6869e92233c7');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 120, '2023-09-04', true, '49d46674-a464-45d0-8685-97294f2b2d50');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Teoria', 60, '2023-09-04', false, '49d46674-a464-45d0-8685-97294f2b2d50');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Codecool', 450, '2023-09-02', true, '49d46674-a464-45d0-8685-97294f2b2d50');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Codecool', 450, '2023-09-03', true, '49d46674-a464-45d0-8685-97294f2b2d50');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 90, '2023-09-05', true, 'a0254f2e-cdae-4ade-a80f-a19e2f5a4da5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Drugie śniadanie', 20, '2023-09-05', true, 'a0254f2e-cdae-4ade-a80f-a19e2f5a4da5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na gitarze', 90, '2023-09-05', true, '057bccad-305e-4d5b-b32b-cd313ecfa74c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na ukulele', 30, '2023-09-05', true, '057bccad-305e-4d5b-b32b-cd313ecfa74c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na ukulele', 30, '2023-09-05', true, '057bccad-305e-4d5b-b32b-cd313ecfa74c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 120, '2023-09-05', true, '49d46674-a464-45d0-8685-97294f2b2d50');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Bieganie', 60, '2023-09-06', true, 'ccda62e0-5159-4c8b-895d-158955b25d1c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Ćwiczenia', 60, '2023-09-06', true, '85b06158-ff34-4954-93e7-82b4fc17fe95');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Teoria', 60, '2023-09-06', true, '49d46674-a464-45d0-8685-97294f2b2d50');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Praktyka', 60, '2023-09-06', false, '49d46674-a464-45d0-8685-97294f2b2d50');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 60, '2023-09-06', true, 'a0254f2e-cdae-4ade-a80f-a19e2f5a4da5');

INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Gra na gitarze', 90, '2023-09-07', true, '057bccad-305e-4d5b-b32b-cd313ecfa74c');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Obiad', 60, '2023-09-07', true, 'a0254f2e-cdae-4ade-a80f-a19e2f5a4da5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Drugie Śniadanie', 30, '2023-09-07', true, 'a0254f2e-cdae-4ade-a80f-a19e2f5a4da5');
INSERT INTO activity(id, description, time_spent_in_minutes, date, completed, category_id ) VALUES(gen_random_uuid(),'Projekt', 90, '2023-09-07', true, '49d46674-a464-45d0-8685-97294f2b2d50');

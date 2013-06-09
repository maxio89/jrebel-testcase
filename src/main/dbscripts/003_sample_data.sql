INSERT INTO country (id, name) VALUES (1, 'United Kingdom');
INSERT INTO country (id, name) VALUES (2, 'United States of America');

INSERT INTO country_region (id, name, country_id) VALUES (1, 'Aberdeen', 1);
INSERT INTO country_region (id, name, country_id) VALUES (2, 'Aberdeenshire', 1);
INSERT INTO country_region (id, name, country_id) VALUES (3, 'Illinois', 2);
INSERT INTO country_region (id, name, country_id) VALUES (4, 'New York', 2);
INSERT INTO country_region (id, name, country_id) VALUES (5, 'Colorado', 2);

INSERT INTO users (id, email, firstname, lastname, password_digest, registration_date, active, role, country_region_id) VALUES (nextval('users_id_sequence'), 'piotr.kozlowski@itcrowd.pl', 'Piotr', 'K.', '47bce5c74f589f4867dbd57e9ca9f808', current_timestamp, TRUE, 'ADMIN', 1);

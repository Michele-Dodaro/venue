DROP TABLE IF EXISTS promotion_items CASCADE;
DROP TABLE IF EXISTS promotion CASCADE;
TRUNCATE TABLE menu_items RESTART IDENTITY CASCADE;
DROP TABLE IF EXISTS menu_items CASCADE;
DROP TABLE IF EXISTS menu_categories CASCADE;
DROP TABLE IF EXISTS reservation_items CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;
DROP TABLE IF EXISTS event_layout CASCADE;
DROP TABLE IF EXISTS event CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS ticket CASCADE;


CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE event_layout (
    id SERIAL PRIMARY KEY,
    conformation VARCHAR(50),
    row INTEGER NOT NULL,
    number INTEGER NOT NULL,
    price1 NUMERIC(10, 2) NOT NULL,
    price2 NUMERIC(10, 2),
    price3 NUMERIC(10, 2)
);

CREATE TABLE event (
    id SERIAL PRIMARY KEY,
    image TEXT NOT NULL,
    name VARCHAR(100) NOT NULL,
    genre VARCHAR(100),
    description TEXT,
    date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL,
    layout_id INTEGER REFERENCES event_layout(id) ON DELETE SET NULL
);


CREATE TABLE reservations (
    id SERIAL PRIMARY KEY,
    event_id INTEGER NOT NULL REFERENCES event(id) ON DELETE CASCADE,
    customer_name VARCHAR(255) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    customer_phone VARCHAR(50) NOT NULL,
    number_of_participants INTEGER NOT NULL DEFAULT 1,
    total_amount NUMERIC(10, 2) NOT NULL,
    payment_status VARCHAR(50) NOT NULL,
    reservation_status VARCHAR(50) NOT NULL,
    creation_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE reservation_items (
    id SERIAL PRIMARY KEY,
    reservation_id INTEGER NOT NULL REFERENCES reservations(id) ON DELETE CASCADE,
    event_layout_id INTEGER NOT NULL REFERENCES event_layout(id) ON DELETE CASCADE
);

CREATE TABLE menu_categories (
    id SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    plate VARCHAR(255) NOT NULL,
    description TEXT,
    original_price NUMERIC(10, 2) NOT NULL,
    menu_categories_id INTEGER REFERENCES menu_categories(id) ON DELETE SET NULL
);

CREATE TABLE promotion (
    id SERIAL PRIMARY KEY,
    promotion_table NUMERIC(10,2),
    promotion_price NUMERIC(10, 2),
    created_At TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    expires_in TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP + INTERVAL '1 day')
);

CREATE TABLE promotion_items (
    id SERIAL PRIMARY KEY,
    promotion_id INTEGER REFERENCES promotion(id) ON DELETE CASCADE,
    menu_items_id INTEGER REFERENCES menu_items(id) ON DELETE CASCADE,
    event_layout_id INT REFERENCES event_layout(id) ON DELETE CASCADE
);
CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    row_field VARCHAR(10) NOT NULL,
    column_field INT NOT NULL,
    layout_id INTEGER REFERENCES event_layout(id) ON DELETE SET NULL,
    avaliable BOOLEAN NOT NULL
);
INSERT INTO users (email, password) VALUES ('michele@prova.com','$2a$10$.qgWYq/4aIufTyk69iUdBOtGIN1p6j/pplEAkXV2.405quxNCusBa');


-- MODIFICA: added event layouts with Italian names
INSERT INTO event_layout (conformation, row, number, price1, price2, price3) VALUES
('Parterre', 6, 7, 15.00, 12.00, 10.00),
('Balconata VIP', 5, 5, 40.00, 35.00, 30.00),
('Tavolo Standard', 10, 10, 20.00, 15.00, 10.00);

-- MODIFICA: created upcoming events with real image URLs and Italian descriptions
INSERT INTO event (image, name, genre, description, date, active, layout_id) VALUES
('https://images.unsplash.com/photo-1470229722913-7c0e2dbbafd3?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 'Tributo Iron Maiden', 'Heavy Metal', 'La serata tributo definitiva agli Iron Maiden.', '2026-08-15 22:00:00', TRUE, 1),
('https://images.unsplash.com/photo-1546708770-589dab7b22c7?q=80&w=1112&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 'Festival Sangue e Budella', 'Death Metal', 'Gruppi death metal locali che ti sciolgono la faccia.', '2026-08-22 21:00:00', TRUE, 1),
('https://images.unsplash.com/photo-1516450360452-9312f5e86fc7', 'Sessione Acustica Doom', 'Doom Metal', 'Lento, pesante e acustico.', '2026-09-05 20:30:00', TRUE, 3);

-- MODIFICA: registered some sample reservations
INSERT INTO reservations (event_id, customer_name, customer_email, customer_phone, number_of_participants, total_amount, payment_status, reservation_status) VALUES
(1, 'Bruce Dickinson', 'bruce@example.com', '555-666-01', 4, 60.00, 'PAGATO', 'CONFERMATO'),
(2, 'Chuck Schuldiner', 'chuck@example.com', '555-666-02', 2, 30.00, 'IN ATTESA', 'IN ATTESA');

-- MODIFICA: linked reservations to layouts
INSERT INTO reservation_items (reservation_id, event_layout_id) VALUES
(1, 1),
(2, 1);

-- MODIFICA: added menu categories in Italian
INSERT INTO menu_categories (type) VALUES
('Birra alla Spina'),
('Hamburger'),
('Cocktail e Cicchetti');

-- MODIFICA: added themed menu items with Italian descriptions
INSERT INTO menu_items (plate, description, original_price, menu_categories_id) VALUES
('La Birra del Soldato', 'Ale inglese amara premium alla spina.', 6.50, 1),
('Hamburger del Burattinaio', 'Doppio manzo, pancetta, cheddar, jalapenos.', 14.00, 2),
('Sabato di Sangue', 'Vodka, succo di pomodoro, salsa estremamente piccante.', 8.00, 3),
('Pale Ale Pantera', 'IPA forte per svegliarti.', 7.00, 1);

-- MODIFICA: created special promotions
INSERT INTO promotion (promotion_table, promotion_price, expires_in) VALUES
(1.00, 7.00, '2026-12-31 23:59:59'),
(2.00, 8.00, '2026-10-31 23:59:59');

-- MODIFICA: linked promotions to menu items and layouts
INSERT INTO promotion_items (promotion_id, menu_items_id, event_layout_id) VALUES
(1, 2, 3),
(2, 1, 1);

-- MODIFICA: populated ticket availability
INSERT INTO ticket (row_field, column_field, layout_id, avaliable) VALUES
('A', 1, 2, TRUE),
('A', 2, 2, FALSE),
('B', 1, 3, TRUE),
('B', 2, 3, TRUE);
DROP TABLE IF EXISTS promotion_items CASCADE;
DROP TABLE IF EXISTS promotion CASCADE;
DROP TABLE IF EXISTS menu_items CASCADE;
DROP TABLE IF EXISTS menu_categories CASCADE;
DROP TABLE IF EXISTS reservation_items CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;
DROP TABLE IF EXISTS event_layout CASCADE;
DROP TABLE IF EXISTS event CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);


CREATE TABLE event (
    id SERIAL PRIMARY KEY,
    genre VARCHAR(100),
    description TEXT,
    date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE event_layout (
    id SERIAL PRIMARY KEY,
    conformation VARCHAR(50),
    row VARCHAR(50),
    number INTEGER NOT NULL,
    price1 NUMERIC(10, 2) NOT NULL,
    price2 NUMERIC(10, 2),
    price3 NUMERIC(10, 2),
    event_id INT NOT NULL REFERENCES event(id) ON DELETE CASCADE
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
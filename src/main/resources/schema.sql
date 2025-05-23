---------- Drop tables ----------
DROP TABLE IF EXISTS manufacturer CASCADE;
DROP TABLE IF EXISTS motorcycle_model CASCADE;
DROP TABLE IF EXISTS motorcycle_stock CASCADE;
DROP TABLE IF EXISTS application_user CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS permission CASCADE;
DROP TABLE IF EXISTS role_permissions CASCADE;
DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS customer_order CASCADE;


---------- Create tables ----------
CREATE TABLE manufacturer
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    partner_since DATE NOT NULL
);

CREATE TABLE motorcycle_model
(
    id SERIAL PRIMARY KEY,
    manufacturer_id INTEGER REFERENCES manufacturer(id) ON DELETE CASCADE,
    model_name VARCHAR(50) NOT NULL,
    model_year INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    displacement INTEGER NOT NULL,
    horse_power INTEGER NOT NULL,
    top_speed INTEGER NOT NULL,
    gearbox INTEGER NOT NULL,
    fuel_capacity FLOAT NOT NULL,
    fuel_consumption_per_100kms FLOAT NOT NULL,
    motorcycle_model_type INTEGER NOT NULL
);

CREATE TABLE motorcycle_stock
(
    id SERIAL PRIMARY KEY,
    motorcycle_model_id INTEGER REFERENCES motorcycle_model(id),
    mileage INTEGER NOT NULL,
    purchasing_price DECIMAL(10,2) NOT NULL,
    profit_margin DECIMAL(5,2) NOT NULL,
    profit_on_unit DECIMAL(10,2) NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL,
    in_stock INTEGER NOT NULL,
    color VARCHAR(40) NOT NULL
);

CREATE TABLE customer
(
    id                   SERIAL PRIMARY KEY,
    first_name           VARCHAR(50)  NOT NULL,
    last_name            VARCHAR(50)  NOT NULL,
    email                VARCHAR(100) NOT NULL UNIQUE,
    phone_number         VARCHAR(20)  NOT NULL,
    address              VARCHAR(200) NOT NULL,
    city                 VARCHAR(50)  NOT NULL,
    postal_code          VARCHAR(10)  NOT NULL,
    country              VARCHAR(50)  NOT NULL,
    date_of_registration DATE         NOT NULL
);

CREATE TABLE customer_order
(
    id                      SERIAL PRIMARY KEY,
    order_date              DATE           NOT NULL,
    order_status            VARCHAR(20)    NOT NULL,
    original_price          DECIMAL(10, 2) NOT NULL,
    discount                DECIMAL(5, 2)  NOT NULL,
    total_amount            DECIMAL(10, 2) NOT NULL,
    estimated_delivery_date DATE NOT NULL,
    motorcycle_stock_id     INTEGER REFERENCES motorcycle_stock (id),
    customer_id             INTEGER REFERENCES customer (id)
);

---------- Security related tables ----------
CREATE TABLE permission
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE role
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE role_permissions (
    role_id INTEGER NOT NULL REFERENCES role(id),
    permission_id INTEGER NOT NULL REFERENCES permission(id)
);

CREATE TABLE application_user
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_registration DATE NOT NULL,
    position_at_company VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL,
    role_id INTEGER NOT NULL REFERENCES role(id)
);

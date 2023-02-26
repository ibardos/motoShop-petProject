---------- Drop tables ----------
DROP TABLE IF EXISTS manufacturer CASCADE;
DROP TABLE IF EXISTS motorcycle_model CASCADE;
DROP TABLE IF EXISTS motorcycle_stock CASCADE;


---------- Create tables ----------
CREATE TABLE manufacturer
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    partner_since DATE NOT NULL
);

CREATE TABLE motorcycle_model
(
    id SERIAL PRIMARY KEY,
    manufacturer_id INTEGER REFERENCES manufacturer(id) ON DELETE CASCADE,
    model_name VARCHAR(40) NOT NULL,
    model_year INTEGER NOT NULL,
    weight INTEGER NOT NULL,
    displacement INTEGER NOT NULL,
    horse_power INTEGER NOT NULL,
    top_speed INTEGER NOT NULL,
    gearbox INTEGER NOT NULL,
    fuel_capacity FLOAT NOT NULL,
    fuel_consumption_per_100kms FLOAT NOT NULL,
    motorcycle_model_type VARCHAR(20) NOT NULL
);

CREATE TABLE motorcycle_stock
(
    id SERIAL PRIMARY KEY,
    motorcycle_model_id INTEGER REFERENCES motorcycle_model(id),
    mileage INTEGER NOT NULL,
    purchasing_price DECIMAL NOT NULL,
    profit_margin FLOAT NOT NULL,
    profit_on_unit DECIMAL NOT NULL,
    selling_price DECIMAL NOT NULL,
    in_stock INTEGER NOT NULL,
    color VARCHAR(40) NOT NULL
);
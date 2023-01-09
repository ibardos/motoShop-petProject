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
    manufacturer_id INTEGER REFERENCES manufacturer(id),
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


---------- Insert data ----------
INSERT INTO manufacturer (name, country, partner_since)
VALUES ('BMW', 'Germany', '2002-06-01'),
       ('Honda', 'Italy', '2008-01-01'),
       ('Husqvarna', 'Sweden', '2016-01-01'),
       ('KTM', 'Austria', '2002-06-01'),
       ('MV Agusta', 'Italy', '2023-01-01'),
       ('Suzuki', 'Japan', '2002-06-01'),
       ('Yamaha', 'Japan', '2004-03-01')
;

INSERT INTO motorcycle_model (manufacturer_id, model_name, model_year, weight, displacement, horse_power, top_speed, gearbox, fuel_capacity, fuel_consumption_per_100kms, motorcycle_model_type)
VALUES ((SELECT id FROM manufacturer WHERE name = 'BMW'), 'R 1250 GS Adventure', '2023', '268', '1254', '136', '193', '6', '29.9', '4.75', 'TOURING_ENDURO'),
       ((SELECT id FROM manufacturer WHERE name = 'BMW'), 'S 1000 RR', '2022', '197', '999', '205', '302', '6', '16.5', '8.33', 'SUPERSPORT'),
       ((SELECT id FROM manufacturer WHERE name = 'Honda'), 'CRF 1000L Africa Twin DCT', '2023', '226', '1084', '101', '214', '6', '18.1', '4.90', 'TOURING_ENDURO'),
       ((SELECT id FROM manufacturer WHERE name = 'Husqvarna'), '701 SM', '2022', '156', '693', '74', '201', '6', '13.0', '4.05', 'SUPERMOTO'),
       ((SELECT id FROM manufacturer WHERE name = 'KTM'), '1290 Super Adventure S', '2023', '245', '1301', '160', '228', '6', '23.0', '5.70', 'TOURING_SPORT'),
       ((SELECT id FROM manufacturer WHERE name = 'Suzuki'), 'DR-Z 400 SM', '2005', '126', '439', '62', '148', '5', '10.0', '4.20', 'SUPERMOTO'),
       ((SELECT id FROM manufacturer WHERE name = 'Suzuki'), 'V-Strom 1050DE Adventure', '2023', '252', '1037', '100', '189', '6', '20.0', '3.99', 'TOURING_ENDURO'),
       ((SELECT id FROM manufacturer WHERE name = 'Yamaha'), 'YZF-R1 M', '2023', '204', '998', '200', '301', '6', '17.0', '7.13', 'SUPERSPORT')
;

INSERT INTO motorcycle_stock (motorcycle_model_id, mileage, purchasing_price, profit_margin, profit_on_unit, selling_price, in_stock, color)
VALUES ((SELECT id FROM motorcycle_model WHERE model_name = 'R 1250 GS Adventure'), '0', '16955', '0.2', '3400', '20355', '3', 'Gravity blue metallic'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'S 1000 RR'), '200', '20600', '0.2', '4200', '24800', '1', 'M-Motorsport'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'CRF 1000L Africa Twin DCT'), '0', '15800', '0.15', '2400', '18200', '2', 'Grand Prix Red'),
       ((SELECT id FROM motorcycle_model WHERE model_name = '701 SM'), '0', '10870', '0.15', '1700', '12570', '2', 'White'),
       ((SELECT id FROM motorcycle_model WHERE model_name = '1290 Super Adventure S'), '0', '17350', '0.2', '3500', '20850', '1', 'Orange'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'DR-Z 400 SM'), '38000', '4080', '0.1', '500', '4580', '1', 'Black'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'V-Strom 1050DE Adventure'), '0', '15300', '0.15', '2300', '17600', '3', 'Champion Yellow'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'V-Strom 1050DE Adventure'), '0', '15300', '0.15', '2300', '17600', '3', 'Metallic Matte Sword Silver'),
       ((SELECT id FROM motorcycle_model WHERE model_name = 'YZF-R1 M'), '0', '23480', '0.15', '3600', '27080', '1', 'Carbon-fiber/blue')
;
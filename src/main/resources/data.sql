---------- Insert data ----------
INSERT INTO manufacturer (name, country, partner_since)
VALUES ('BMW', 'Germany', '2002-06-01'),
       ('Honda', 'Japan', '2008-01-01'),
       ('Husqvarna', 'Sweden', '2016-01-01'),
       ('KTM', 'Austria', '2002-06-01'),
       ('MV Agusta', 'Italy', '2023-01-01'),
       ('Suzuki', 'Japan', '2002-06-01'),
       ('Yamaha', 'Japan', '2004-03-01')
;

INSERT INTO motorcycle_model (manufacturer_id, model_name, model_year, weight, displacement, horse_power, top_speed, gearbox, fuel_capacity, fuel_consumption_per_100kms, motorcycle_model_type)
VALUES ((SELECT id FROM manufacturer WHERE name = 'BMW'), 'R 1250 GS Adventure', '2023', '268', '1254', '136', '193', '6', '29.9', '4.75', '4'),
       ((SELECT id FROM manufacturer WHERE name = 'BMW'), 'S 1000 RR', '2022', '197', '999', '205', '302', '6', '16.5', '8.33', '0'),
       ((SELECT id FROM manufacturer WHERE name = 'Honda'), 'CRF 1000L Africa Twin DCT', '2023', '226', '1084', '101', '214', '6', '18.1', '4.90', '4'),
       ((SELECT id FROM manufacturer WHERE name = 'Husqvarna'), '701 SM', '2022', '156', '693', '74', '201', '6', '13.0', '4.05', '1'),
       ((SELECT id FROM manufacturer WHERE name = 'KTM'), '1290 Super Adventure S', '2023', '245', '1301', '160', '228', '6', '23.0', '5.70', '5'),
       ((SELECT id FROM manufacturer WHERE name = 'MV Agusta'), 'Turismo Veloce 800 Lusso', '2023', '192', '798', '110', '230', '6', '21.5', '5.50', '5'),
       ((SELECT id FROM manufacturer WHERE name = 'Suzuki'), 'DR-Z 400 SM', '2005', '126', '439', '62', '148', '5', '10.0', '4.20', '1'),
       ((SELECT id FROM manufacturer WHERE name = 'Suzuki'), 'V-Strom 1050DE Adventure', '2023', '252', '1037', '100', '189', '6', '20.0', '3.99', '4'),
       ((SELECT id FROM manufacturer WHERE name = 'Yamaha'), 'YZF-R1 M', '2023', '204', '998', '200', '301', '6', '17.0', '7.13', '0')
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
       ((SELECT id FROM motorcycle_model WHERE model_name = 'YZF-R1 M'), '0', '23480', '0.15', '3600', '27080', '0', 'Carbon-fiber/blue')
;

INSERT INTO customer (first_name, last_name, email, phone_number, address, city, postal_code, country, date_of_registration)
VALUES ('Marcus', 'Fenix', 'marcus.fenix@cog.gov', '+1-555-0123', '25 Coalition Street', 'Ephyra', 'EP1 2COG', 'Sera','2023-01-15'),
       ('Dominic', 'Santiago', 'dom.santiago@cog.gov', '+1-555-0124', '8 Jacinto Avenue', 'New Ephyra', 'NE2 3COG','Sera', '2023-03-22'),
       ('Augustus', 'Cole', 'cole.train@cog.gov', '+1-555-0125', '42 Thrashball Road', 'Hanover', 'HN4 1COG', 'Sera','2023-06-30')
;


INSERT INTO customer_order (order_date, order_status, original_price, discount, total_amount, estimated_delivery_date, motorcycle_stock_id, customer_id)
VALUES ('2025-01-30', '1', '17600', '0.00', '17600.00', '2025-03-03', '8', '1'),
       ('2025-02-10', '1', '27080', '0.10', '24372.00', '2025-03-10', '9', '1'),
       ('2025-05-15', '0', '20355', '0.10', '18319.50', '2025-07-25', '1', '3')
;

---------- Security related inserts ----------
INSERT INTO permission (name)
VALUES ('Read'),
       ('Create'),
       ('Update'),
       ('Delete')
;

INSERT INTO role (name)
VALUES ('User'),
       ('Sales'),
       ('Admin')
;

INSERT INTO role_permissions (role_id, permission_id)
VALUES ((SELECT id FROM role WHERE name = 'User'), (SELECT id FROM permission WHERE name = 'Read')),
       ((SELECT id FROM role WHERE name = 'Sales'), (SELECT id FROM permission WHERE name = 'Read')),
       ((SELECT id FROM role WHERE name = 'Sales'), (SELECT id FROM permission WHERE name = 'Create')),
       ((SELECT id FROM role WHERE name = 'Sales'), (SELECT id FROM permission WHERE name = 'Update')),
       ((SELECT id FROM role WHERE name = 'Admin'), (SELECT id FROM permission WHERE name = 'Read')),
       ((SELECT id FROM role WHERE name = 'Admin'), (SELECT id FROM permission WHERE name = 'Create')),
       ((SELECT id FROM role WHERE name = 'Admin'), (SELECT id FROM permission WHERE name = 'Update')),
       ((SELECT id FROM role WHERE name = 'Admin'), (SELECT id FROM permission WHERE name = 'Delete'))
;

INSERT INTO application_user (username, password, email, first_name, last_name, date_of_registration, position_at_company, enabled, role_id)
VALUES ('user', '$2a$10$zagoF51UwzBUzY4ccaCikO5mGVTaw9I/XyA65k9BT6Rb8pe.cG9ve', 'user@motoShop.com', 'Tom', 'User', '2023-12-21', 'Assistant', 'TRUE', (SELECT id FROM role WHERE name = 'User')),
       ('sales', '$2a$10$OIqT3/qM3eU8xZf4nOwXxuxo4Frzu8mI1CpNSpV2L644NsUVtONkO', 'sales@motoShop.com', 'Jack', 'Sales', '2023-12-21', 'Sales manager', 'TRUE', (SELECT id FROM role WHERE name = 'Sales')),
       ('admin', '$2a$10$DI.Jifz9lsaUibQVyLEoCecgpxNS4FnP2B2smkQz1Dj/4wcLVONIO', 'admin@motoShop.com', 'Jill', 'Admin', '2023-12-21', 'Store manager', 'TRUE', (SELECT id FROM role WHERE name = 'Admin'))
;

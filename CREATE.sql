CREATE TYPE user_status AS ENUM ('active','blocked');
CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY ,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,	
	phone_number VARCHAR(15) NOT NULL,
	registration_date TIMESTAMP DEFAULT NOW(),
	status user_status,
	balance DECIMAL(15,2) DEFAULT 0.0
	
);


CREATE TABLE credentials (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id) UNIQUE,
	username VARCHAR(100) NOT NULL,
	password VARCHAR(128) NOT NULL,
	email VARCHAR(100) NOT NULL
	
);


CREATE TABLE driver_licenses (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id) UNIQUE,
	number VARCHAR(100) NOT NULL,
	data_of_issue TIMESTAMP NOT NULL,
	data_of_expity TIMESTAMP NOT NULL,
	have_b BOOL NOT NULL,
	img TEXT NOT NULL
);

CREATE TABLE currencies (
	id BIGSERIAL PRIMARY KEY,
	currency_code VARCHAR(5) DEFAULT 'BYN',
	exchange_rate DECIMAL(15,2) NOT NULL
);


CREATE TYPE payment_type AS ENUM ('penalty','replenishment');
CREATE TYPE payment_status AS ENUM ('approved','rejected');

CREATE TABLE payments (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id),
	currency_id BIGINT REFERENCES currencies(id),
	amount DECIMAL(6,2) NOT NULL,
	payment_date TIMESTAMP DEFAULT NOW(),
	payment_type payment_type NOT NULL,
	status payment_status NOT NULL
);



CREATE TYPE user_role AS ENUM ('admin','user');

CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	role user_role NOT NULL
);


CREATE TABLE user_roles (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id),
	role_id BIGINT REFERENCES roles(id)
);



CREATE TYPE car_status AS ENUM('available', 'rented', 'unavailable');

CREATE TABLE cars (
	id BIGSERIAL PRIMARY KEY,
	make VARCHAR(30) NOT NULL,
	model VARCHAR(30) NOT NULL,
	year VARCHAR(5) NOT NULL,
	status car_status NOT NULL
);


CREATE TYPE car_type AS ENUM('economy','standard','comfort','minivan','electric','premium','cabriolet','sport','SUV');

CREATE TABLE types (
	id BIGSERIAL PRIMARY KEY,
	type car_type,
	price_per_minute DECIMAL(5,2) NOT NULL
);

CREATE TABLE car_types (
	id BIGSERIAL PRIMARY KEY,
	car_id BIGINT REFERENCES cars(id),
	type_id BIGINT REFERENCES types(id)
);


CREATE TYPE reservation_status AS ENUM ('active', 'completed', 'cancelled');

CREATE TABLE reservations (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users(id),
	car_id BIGINT REFERENCES cars(id),
	start_time TIMESTAMP DEFAULT NOW(),
	end_time TIMESTAMP NOT NULL,
	status reservation_status NOT NULL,
	total_cost DECIMAL(15,2) NOT NULL
);


CREATE TABLE penalties (
	id BIGSERIAL PRIMARY KEY,
	reservation_id BIGINT REFERENCES reservations(id),
	car_id BIGINT REFERENCES cars(id),
	amount DECIMAL(15,2) NOT NULL,
	reason TEXT,
	date_issued TIMESTAMP NOT NULL
);


CREATE TABLE trips (
		id BIGSERIAL PRIMARY KEY,
		reservation_id BIGINT REFERENCES reservations(id),
		car_id BIGINT REFERENCES cars(id),
		distance_km DECIMAL(5,2) DEFAULT 0.0
	);
	

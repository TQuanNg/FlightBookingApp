-- Users table with role support
CREATE TABLE IF NOT EXISTS Users (
                                     user_id SERIAL PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Flights table
CREATE TABLE IF NOT EXISTS Flights (
                                       flight_id SERIAL PRIMARY KEY,
                                       flight_number VARCHAR(10) NOT NULL,
    departure_city VARCHAR(50) NOT NULL,
    arrival_city VARCHAR(50) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    available_seats INT NOT NULL
    );

-- Bookings table for handling both one-way and round-trip flights
CREATE TABLE IF NOT EXISTS Bookings (
                                        booking_id SERIAL PRIMARY KEY,
                                        user_id SERIAL NOT NULL,
                                        outbound_flight_id SERIAL NOT NULL,
                                        return_flight_id SERIAL,
                                        number_of_travelers INT NOT NULL,
                                        total_price DECIMAL(10, 2) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    booking_status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED',
    is_round_trip BOOLEAN NOT NULL DEFAULT FALSE,
    boarding_group VARCHAR(3),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (outbound_flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE,
    FOREIGN KEY (return_flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE
    );

-- Ticket table (kept for backward compatibility)
CREATE TABLE IF NOT EXISTS Ticket (
                                      ticket_id SERIAL PRIMARY KEY,
                                      user_id SERIAL NOT NULL,
                                      flight_id SERIAL NOT NULL,
                                      ticket_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      number_of_travelers INT NOT NULL,
                                      boarding_group VARCHAR(3) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE
    );

-- Purchase History table
CREATE TABLE IF NOT EXISTS PurchaseHistory (
                                               purchase_id SERIAL PRIMARY KEY,
                                               user_id SERIAL NOT NULL,
                                               ticket_id SERIAL NOT NULL,
                                               purchase_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                               FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (ticket_id) REFERENCES Ticket(ticket_id) ON DELETE CASCADE
    );

-- Cart Items table with round-trip support
CREATE TABLE IF NOT EXISTS cart_item (
                                         cart_item_id SERIAL PRIMARY KEY,
                                         user_id SERIAL NOT NULL,
                                         flight_id SERIAL NOT NULL,
                                         return_flight_id SERIAL,
                                         number_of_travelers INT NOT NULL,
                                         total_price DECIMAL(10, 2) NOT NULL,
    is_round_trip BOOLEAN NOT NULL DEFAULT FALSE,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE,
    FOREIGN KEY (return_flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE
    );

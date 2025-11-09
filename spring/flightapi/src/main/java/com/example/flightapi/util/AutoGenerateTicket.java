package com.example.flightapi.util;

import com.example.flightapi.model.Entity.Flight;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AutoGenerateTicket {
    private final Random random = new Random();
    private final Set<String> generatedFlightNumbers = new HashSet<>();

    public List<Flight> generateFlights(String departureCity, String arrivalCity, int count) {
        List<Flight> flights = new ArrayList<>();

        for(int i = 0; i < count; ++i) {
            Flight flight = new Flight();
            flight.setFlightNumber(this.generateUniqueFlightNumber());
            flight.setDepartureCity(departureCity);
            flight.setArrivalCity(arrivalCity);
            LocalDateTime departureTime = this.generateRandomFutureTime();
            LocalDateTime arrivalTime = departureTime.plusHours((long)(1 + this.random.nextInt(10)));
            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
            flight.setPrice(this.generateRandomPrice());
            flight.setAvailableSeats(50 + this.random.nextInt(151));
            flights.add(flight);
        }

        return flights;
    }

    public List<Flight> generateReturnFlights(String departureCity, String arrivalCity, int count, LocalDateTime earliestReturnTime) {
        List<Flight> returnFlights = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            Flight flight = new Flight();
            flight.setFlightNumber(this.generateUniqueFlightNumber());
            flight.setDepartureCity(arrivalCity); // Swap cities for return
            flight.setArrivalCity(departureCity);

            // Return flights should be after the outbound flight arrival
            LocalDateTime departureTime = earliestReturnTime.plusHours((long)(2 + this.random.nextInt(72))); // 2-74 hours after earliest return
            LocalDateTime arrivalTime = departureTime.plusHours((long)(1 + this.random.nextInt(10)));

            flight.setDepartureTime(departureTime);
            flight.setArrivalTime(arrivalTime);
            flight.setPrice(this.generateRandomPrice());
            flight.setAvailableSeats(50 + this.random.nextInt(151));
            returnFlights.add(flight);
        }

        return returnFlights;
    }

    private String generateUniqueFlightNumber() {
        String flightNumber;
        int attempts = 0;
        do {
            flightNumber = "FL" + (1000 + this.random.nextInt(9000)) + System.currentTimeMillis() % 1000;
            attempts++;
            if (attempts > 100) {
                // Fallback to timestamp-based generation if too many collisions
                flightNumber = "FL" + System.currentTimeMillis() + this.random.nextInt(100);
                break;
            }
        } while (generatedFlightNumbers.contains(flightNumber));

        generatedFlightNumbers.add(flightNumber);
        return flightNumber;
    }

    private LocalDateTime generateRandomFutureTime() {
        return LocalDateTime.now().plusDays((long)this.random.nextInt(30)).plusHours((long)this.random.nextInt(24));
    }

    private BigDecimal generateRandomPrice() {
        double price = (double)100.0F + (double)4900.0F * this.random.nextDouble();
        return BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
    }
}

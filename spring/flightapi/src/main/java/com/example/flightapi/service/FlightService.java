package com.example.flightapi.service;

import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.util.AutoGenerateTicket;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AutoGenerateTicket autoGenerateTicket;

    public FlightService(FlightRepository flightRepository, AutoGenerateTicket autoGenerateTicket) {
        this.flightRepository = flightRepository;
        this.autoGenerateTicket = autoGenerateTicket;
    }

    public List<Flight> searchFlights(String departureCity, String arrivalCity, LocalDateTime startTime, LocalDateTime endTime, Integer numTravelers) {
        List<Flight> flights = this.flightRepository.findByUserInput(departureCity, arrivalCity, startTime, endTime, numTravelers);
        if (flights.isEmpty()) {
            List<Flight> generatedFlights = this.creatingRandomFlights(departureCity, arrivalCity, 5);
            return generatedFlights;
        } else {
            return flights;
        }
    }

    private List<Flight> creatingRandomFlights(String departureCity, String arrivalCity, int numberOfFlight) {
        List<Flight> generatedFlights = this.autoGenerateTicket.generateFlights(departureCity, arrivalCity, numberOfFlight);
        this.flightRepository.saveAll(generatedFlights);
        return generatedFlights;
    }
}


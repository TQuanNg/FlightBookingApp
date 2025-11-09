package com.example.flightapi.service;

import com.example.flightapi.model.DTO.FlightSearchResponseDTO;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.util.AutoGenerateTicket;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FlightService {
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private AutoGenerateTicket autoGenerateTicket;

    public FlightService(FlightRepository flightRepository, AutoGenerateTicket autoGenerateTicket) {
        this.flightRepository = flightRepository;
        this.autoGenerateTicket = autoGenerateTicket;
    }

    public FlightSearchResponseDTO searchFlights(String departureCity, String arrivalCity, LocalDateTime startTime, LocalDateTime endTime, Integer numTravelers, String tripType) {
        // Search for outbound flights
        List<Flight> outboundFlights = this.flightRepository.findByUserInput(departureCity, arrivalCity, startTime, endTime, numTravelers);
        if (outboundFlights.isEmpty()) {
            logger.info("No existing flights found, generating new flights for route: {} to {}", departureCity, arrivalCity);
            outboundFlights = this.creatingRandomFlights(departureCity, arrivalCity, 5);
        }

        FlightSearchResponseDTO response = new FlightSearchResponseDTO();
        response.setTripType(tripType);
        response.setOutboundFlights(outboundFlights);

        // If round trip, search/generate return flights
        if ("ROUND_TRIP".equalsIgnoreCase(tripType)) {
            List<Flight> returnFlights = this.flightRepository.findByUserInput(arrivalCity, departureCity, startTime, endTime, numTravelers);
            if (returnFlights.isEmpty()) {
                // Generate return flights with earliest return time based on outbound flight
                LocalDateTime earliestReturnTime = outboundFlights.isEmpty() ?
                    startTime :
                    outboundFlights.get(0).getArrivalTime();
                logger.info("Generating return flights for route: {} to {}", arrivalCity, departureCity);
                returnFlights = this.creatingRandomReturnFlights(arrivalCity, departureCity, 5, earliestReturnTime);
            }
            response.setReturnFlights(returnFlights);
        } else {
            response.setReturnFlights(null);
        }

        return response;
    }

    @Transactional
    protected List<Flight> creatingRandomFlights(String departureCity, String arrivalCity, int numberOfFlight) {
        try {
            List<Flight> generatedFlights = this.autoGenerateTicket.generateFlights(departureCity, arrivalCity, numberOfFlight);
            List<Flight> savedFlights = this.flightRepository.saveAll(generatedFlights);
            logger.info("Successfully generated and saved {} flights for route: {} to {}", savedFlights.size(), departureCity, arrivalCity);
            return savedFlights;
        } catch (Exception e) {
            logger.error("Error generating flights for route: {} to {}. Error: {}", departureCity, arrivalCity, e.getMessage());
            throw new RuntimeException("Failed to generate flights: " + e.getMessage(), e);
        }
    }

    @Transactional
    protected List<Flight> creatingRandomReturnFlights(String departureCity, String arrivalCity, int numberOfFlight, LocalDateTime earliestReturnTime) {
        try {
            List<Flight> generatedFlights = this.autoGenerateTicket.generateReturnFlights(departureCity, arrivalCity, numberOfFlight, earliestReturnTime);
            List<Flight> savedFlights = this.flightRepository.saveAll(generatedFlights);
            logger.info("Successfully generated and saved {} return flights for route: {} to {}", savedFlights.size(), departureCity, arrivalCity);
            return savedFlights;
        } catch (Exception e) {
            logger.error("Error generating return flights for route: {} to {}. Error: {}", departureCity, arrivalCity, e.getMessage());
            throw new RuntimeException("Failed to generate return flights: " + e.getMessage(), e);
        }
    }
}

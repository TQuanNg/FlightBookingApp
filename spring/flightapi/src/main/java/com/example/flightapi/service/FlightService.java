package com.example.flightapi.service;

import com.example.flightapi.model.DTO.FlightSearchResponseDTO;
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

    public FlightSearchResponseDTO searchFlights(String departureCity, String arrivalCity, LocalDateTime startTime, LocalDateTime endTime, Integer numTravelers, String tripType) {
        // Search for outbound flights
        List<Flight> outboundFlights = this.flightRepository.findByUserInput(departureCity, arrivalCity, startTime, endTime, numTravelers);
        if (outboundFlights.isEmpty()) {
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
                returnFlights = this.creatingRandomReturnFlights(arrivalCity, departureCity, 5, earliestReturnTime);
            }
            response.setReturnFlights(returnFlights);
        } else {
            response.setReturnFlights(null);
        }

        return response;
    }

    // Legacy method for backward compatibility - defaults to ONE_WAY
    public List<Flight> searchFlights(String departureCity, String arrivalCity, LocalDateTime startTime, LocalDateTime endTime, Integer numTravelers) {
        FlightSearchResponseDTO response = searchFlights(departureCity, arrivalCity, startTime, endTime, numTravelers, "ONE_WAY");
        return response.getOutboundFlights();
    }

    private List<Flight> creatingRandomFlights(String departureCity, String arrivalCity, int numberOfFlight) {
        List<Flight> generatedFlights = this.autoGenerateTicket.generateFlights(departureCity, arrivalCity, numberOfFlight);
        this.flightRepository.saveAll(generatedFlights);
        return generatedFlights;
    }

    private List<Flight> creatingRandomReturnFlights(String departureCity, String arrivalCity, int numberOfFlight, LocalDateTime earliestReturnTime) {
        List<Flight> generatedFlights = this.autoGenerateTicket.generateReturnFlights(departureCity, arrivalCity, numberOfFlight, earliestReturnTime);
        this.flightRepository.saveAll(generatedFlights);
        return generatedFlights;
    }
}

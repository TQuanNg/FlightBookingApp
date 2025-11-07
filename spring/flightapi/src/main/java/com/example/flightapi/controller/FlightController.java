package com.example.flightapi.controller;

import com.example.flightapi.model.DTO.FlightSearchResponseDTO;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.service.FlightService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/flights"})
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping({"/search"})
    public ResponseEntity<FlightSearchResponseDTO> searchFlights(@RequestParam String departureCity, @RequestParam String arrivalCity, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam int numTravelers, @RequestParam(defaultValue = "ONE_WAY") String tripType) {
        FlightSearchResponseDTO response = this.flightService.searchFlights(departureCity, arrivalCity, startTime, endTime, numTravelers, tripType);
        return response.getOutboundFlights().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
    }

    // Legacy endpoint for backward compatibility
    @GetMapping({"/search/simple"})
    public ResponseEntity<List<Flight>> searchFlightsSimple(@RequestParam String departureCity, @RequestParam String arrivalCity, @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime, @RequestParam int numTravelers) {
        List<Flight> flights = this.flightService.searchFlights(departureCity, arrivalCity, startTime, endTime, numTravelers);
        return flights.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(flights);
    }
}

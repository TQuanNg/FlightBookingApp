package com.example.flightapi.controller;

import com.example.flightapi.model.DTO.FlightSearchResponseDTO;
import com.example.flightapi.service.FlightService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping({"/flights"})
public class FlightController {
    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping({"/search"})
    public ResponseEntity<FlightSearchResponseDTO> searchFlights(
            @RequestParam String departureCity,
            @RequestParam String arrivalCity,
            @RequestParam LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime,
            @RequestParam int numTravelers,
            @RequestParam(defaultValue = "ONE_WAY") String tripType) {

        try {
            LocalDateTime searchEndTime = endTime != null ? endTime : startTime.plusDays(7);

            FlightSearchResponseDTO response = this.flightService.searchFlights(
                departureCity, arrivalCity, startTime, searchEndTime, numTravelers, tripType);

            return response.getOutboundFlights().isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error searching flights: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

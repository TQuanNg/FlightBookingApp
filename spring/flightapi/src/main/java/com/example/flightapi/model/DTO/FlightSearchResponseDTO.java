package com.example.flightapi.model.DTO;

import com.example.flightapi.model.Entity.Flight;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchResponseDTO {
    private String tripType;
    private List<Flight> outboundFlights;
    private List<Flight> returnFlights; // null for one-way trips
}


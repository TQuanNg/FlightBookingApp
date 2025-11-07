package com.example.flightapi.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CartItemProjection {
    Long getCartItemId();

    Long getFlightId();

    BigDecimal getTotalPrice();

    Integer getNumberOfTravelers();

    Boolean getIsRoundTrip();

    String getFlightNumber();

    String getDepartureCity();

    LocalDateTime getDepartureTime();

    String getArrivalCity();

    LocalDateTime getArrivalTime();

    Long getReturnFlightId();

    String getReturnFlightNumber();

    String getReturnDepartureCity();

    LocalDateTime getReturnDepartureTime();

    String getReturnArrivalCity();

    LocalDateTime getReturnArrivalTime();
}
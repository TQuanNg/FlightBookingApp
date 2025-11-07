//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.flightapi.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Generated;

@Entity
@Table(
        name = "Flights"
)
public class Flight {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long flightId;
    @Column(
            name = "flight_number",
            nullable = false,
            length = 10
    )
    private String flightNumber;
    @Column(
            name = "departure_city",
            nullable = false,
            length = 50
    )
    private String departureCity;
    @Column(
            name = "arrival_city",
            nullable = false,
            length = 50
    )
    private String arrivalCity;
    @Column(
            name = "departure_time",
            nullable = false
    )
    private LocalDateTime departureTime;
    @Column(
            name = "arrival_time",
            nullable = false
    )
    private LocalDateTime arrivalTime;
    @Column(
            name = "price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;
    @Column(
            name = "available_seats",
            nullable = false
    )
    private Integer availableSeats;

    public Long getFlightId() {
        return this.flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return this.departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return this.arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDateTime getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableSeats() {
        return this.availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Generated
    public Flight() {
    }

    @Generated
    public Flight(final Long flightId, final String flightNumber, final String departureCity, final String arrivalCity, final LocalDateTime departureTime, final LocalDateTime arrivalTime, final BigDecimal price, final Integer availableSeats) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }
}


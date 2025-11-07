package com.example.flightapi.model.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartDTO {
    private Long cartItemId;
    private Long flightId;
    private String flightNumber;
    private String departureCity;
    private LocalDateTime departureTime;
    private String arrivalCity;
    private LocalDateTime arrivalTime;
    private BigDecimal totalPrice;
    private Integer numberOfTravelers;
    private Boolean isRoundTrip;
    private Long returnFlightId;
    private String returnFlightNumber;
    private String returnDepartureCity;
    private LocalDateTime returnDepartureTime;
    private String returnArrivalCity;
    private LocalDateTime returnArrivalTime;

    public CartDTO(Long cartItemId, Long flightId, String flightNumber, String departureCity, LocalDateTime departureTime, String arrivalCity, LocalDateTime arrivalTime, BigDecimal totalPrice, Integer numberOfTravelers, Boolean isRoundTrip, Long returnFlightId, String returnFlightNumber, String returnDepartureCity, LocalDateTime returnDepartureTime, String returnArrivalCity, LocalDateTime returnArrivalTime) {
        this.cartItemId = cartItemId;
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.totalPrice = totalPrice;
        this.numberOfTravelers = numberOfTravelers;
        this.isRoundTrip = isRoundTrip;
        this.returnFlightId = returnFlightId;
        this.returnFlightNumber = returnFlightNumber;
        this.returnDepartureCity = returnDepartureCity;
        this.returnDepartureTime = returnDepartureTime;
        this.returnArrivalCity = returnArrivalCity;
        this.returnArrivalTime = returnArrivalTime;
    }

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

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

    public LocalDateTime getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalCity() {
        return this.arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public LocalDateTime getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getNumberOfTravelers() {
        return this.numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public Boolean getIsRoundTrip() {
        return this.isRoundTrip;
    }

    public void setIsRoundTrip(Boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public Long getReturnFlightId() {
        return this.returnFlightId;
    }

    public void setReturnFlightId(Long returnFlightId) {
        this.returnFlightId = returnFlightId;
    }

    public String getReturnFlightNumber() {
        return this.returnFlightNumber;
    }

    public void setReturnFlightNumber(String returnFlightNumber) {
        this.returnFlightNumber = returnFlightNumber;
    }

    public String getReturnDepartureCity() {
        return this.returnDepartureCity;
    }

    public void setReturnDepartureCity(String returnDepartureCity) {
        this.returnDepartureCity = returnDepartureCity;
    }

    public LocalDateTime getReturnDepartureTime() {
        return this.returnDepartureTime;
    }

    public void setReturnDepartureTime(LocalDateTime returnDepartureTime) {
        this.returnDepartureTime = returnDepartureTime;
    }

    public String getReturnArrivalCity() {
        return this.returnArrivalCity;
    }

    public void setReturnArrivalCity(String returnArrivalCity) {
        this.returnArrivalCity = returnArrivalCity;
    }

    public LocalDateTime getReturnArrivalTime() {
        return this.returnArrivalTime;
    }

    public void setReturnArrivalTime(LocalDateTime returnArrivalTime) {
        this.returnArrivalTime = returnArrivalTime;
    }
}


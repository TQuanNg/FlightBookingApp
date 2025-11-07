package com.example.flightapi.model.DTO;

import com.example.flightapi.model.Entity.BookingStatus;

import java.math.BigDecimal;

public class AdminBookingDTO {
    private Long bookingId;
    private Long userId;
    private String username;
    private String userEmail;
    private Long outboundFlightId;
    private String outboundFlightNumber;
    private String departureCity;
    private String arrivalCity;
    private String departureTime;
    private String arrivalTime;
    private Long returnFlightId;
    private String returnFlightNumber;
    private String returnDepartureTime;
    private String returnArrivalTime;
    private Integer numberOfTravelers;
    private BigDecimal totalPrice;
    private String bookingDate;
    private BookingStatus status;
    private Boolean isRoundTrip;
    private String boardingGroup;

    public AdminBookingDTO() {}

    public AdminBookingDTO(Long bookingId, Long userId, String username, String userEmail,
                           Long outboundFlightId, String outboundFlightNumber,
                           String departureCity, String arrivalCity,
                           String departureTime, String arrivalTime,
                           Long returnFlightId, String returnFlightNumber,
                           String returnDepartureTime, String returnArrivalTime,
                           Integer numberOfTravelers, BigDecimal totalPrice,
                           String bookingDate, BookingStatus status, Boolean isRoundTrip,
                           String boardingGroup) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.username = username;
        this.userEmail = userEmail;
        this.outboundFlightId = outboundFlightId;
        this.outboundFlightNumber = outboundFlightNumber;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.returnFlightId = returnFlightId;
        this.returnFlightNumber = returnFlightNumber;
        this.returnDepartureTime = returnDepartureTime;
        this.returnArrivalTime = returnArrivalTime;
        this.numberOfTravelers = numberOfTravelers;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.status = status;
        this.isRoundTrip = isRoundTrip;
        this.boardingGroup = boardingGroup;
    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getOutboundFlightId() {
        return outboundFlightId;
    }

    public void setOutboundFlightId(Long outboundFlightId) {
        this.outboundFlightId = outboundFlightId;
    }

    public String getOutboundFlightNumber() {
        return outboundFlightNumber;
    }

    public void setOutboundFlightNumber(String outboundFlightNumber) {
        this.outboundFlightNumber = outboundFlightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getReturnFlightId() {
        return returnFlightId;
    }

    public void setReturnFlightId(Long returnFlightId) {
        this.returnFlightId = returnFlightId;
    }

    public String getReturnFlightNumber() {
        return returnFlightNumber;
    }

    public void setReturnFlightNumber(String returnFlightNumber) {
        this.returnFlightNumber = returnFlightNumber;
    }

    public String getReturnDepartureTime() {
        return returnDepartureTime;
    }

    public void setReturnDepartureTime(String returnDepartureTime) {
        this.returnDepartureTime = returnDepartureTime;
    }

    public String getReturnArrivalTime() {
        return returnArrivalTime;
    }

    public void setReturnArrivalTime(String returnArrivalTime) {
        this.returnArrivalTime = returnArrivalTime;
    }

    public Integer getNumberOfTravelers() {
        return numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Boolean getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(Boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public String getBoardingGroup() {
        return boardingGroup;
    }

    public void setBoardingGroup(String boardingGroup) {
        this.boardingGroup = boardingGroup;
    }
}


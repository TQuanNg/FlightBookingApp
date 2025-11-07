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

    public AdminBookingDTO() {
    }

    public AdminBookingDTO(Long bookingId, Long userId, String username, String userEmail, Long outboundFlightId, String outboundFlightNumber, String departureCity, String arrivalCity, String departureTime, String arrivalTime, Long returnFlightId, String returnFlightNumber, String returnDepartureTime, String returnArrivalTime, Integer numberOfTravelers, BigDecimal totalPrice, String bookingDate, BookingStatus status, Boolean isRoundTrip, String boardingGroup) {
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

    public Long getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getOutboundFlightId() {
        return this.outboundFlightId;
    }

    public void setOutboundFlightId(Long outboundFlightId) {
        this.outboundFlightId = outboundFlightId;
    }

    public String getOutboundFlightNumber() {
        return this.outboundFlightNumber;
    }

    public void setOutboundFlightNumber(String outboundFlightNumber) {
        this.outboundFlightNumber = outboundFlightNumber;
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

    public String getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public String getReturnDepartureTime() {
        return this.returnDepartureTime;
    }

    public void setReturnDepartureTime(String returnDepartureTime) {
        this.returnDepartureTime = returnDepartureTime;
    }

    public String getReturnArrivalTime() {
        return this.returnArrivalTime;
    }

    public void setReturnArrivalTime(String returnArrivalTime) {
        this.returnArrivalTime = returnArrivalTime;
    }

    public Integer getNumberOfTravelers() {
        return this.numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingDate() {
        return this.bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return this.status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Boolean getIsRoundTrip() {
        return this.isRoundTrip;
    }

    public void setIsRoundTrip(Boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public String getBoardingGroup() {
        return this.boardingGroup;
    }

    public void setBoardingGroup(String boardingGroup) {
        this.boardingGroup = boardingGroup;
    }
}


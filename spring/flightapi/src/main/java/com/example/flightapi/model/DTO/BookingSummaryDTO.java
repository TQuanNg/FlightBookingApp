package com.example.flightapi.model.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Generated;

public class BookingSummaryDTO {
    private Long ticketId;
    private String departurePlace;
    private String departureTime;
    private String arrivalPlace;
    private String arrivalTime;
    private String returnDeparturePlace;
    private String returnDepartureTime;
    private String returnArrivalPlace;
    private String returnArrivalTime;
    private BigDecimal price;
    private LocalDateTime purchaseDate;
    private Integer numberOfTravelers;
    private String boardingGroup;
    private Boolean isRoundTrip;

    public Long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getDeparturePlace() {
        return this.departurePlace;
    }

    public void setDeparturePlace(String departurePlace) {
        this.departurePlace = departurePlace;
    }

    public String getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalPlace() {
        return this.arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getReturnDeparturePlace() {
        return this.returnDeparturePlace;
    }

    public void setReturnDeparturePlace(String returnDeparturePlace) {
        this.returnDeparturePlace = returnDeparturePlace;
    }

    public String getReturnDepartureTime() {
        return this.returnDepartureTime;
    }

    public void setReturnDepartureTime(String returnDepartureTime) {
        this.returnDepartureTime = returnDepartureTime;
    }

    public String getReturnArrivalPlace() {
        return this.returnArrivalPlace;
    }

    public void setReturnArrivalPlace(String returnArrivalPlace) {
        this.returnArrivalPlace = returnArrivalPlace;
    }

    public String getReturnArrivalTime() {
        return this.returnArrivalTime;
    }

    public void setReturnArrivalTime(String returnArrivalTime) {
        this.returnArrivalTime = returnArrivalTime;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Integer getNumberOfTravelers() {
        return this.numberOfTravelers;
    }

    public void setNumberOfTravelers(Integer numberOfTravelers) {
        this.numberOfTravelers = numberOfTravelers;
    }

    public String getBoardingGroup() {
        return this.boardingGroup;
    }

    public void setBoardingGroup(String boardingGroup) {
        this.boardingGroup = boardingGroup;
    }

    public Boolean getRoundTrip() {
        return this.isRoundTrip;
    }

    public void setRoundTrip(Boolean roundTrip) {
        this.isRoundTrip = roundTrip;
    }

    @Generated
    public BookingSummaryDTO() {
    }

    @Generated
    public BookingSummaryDTO(final Long ticketId, final String departurePlace, final String departureTime, final String arrivalPlace, final String arrivalTime, final String returnDeparturePlace, final String returnDepartureTime, final String returnArrivalPlace, final String returnArrivalTime, final BigDecimal price, final LocalDateTime purchaseDate, final Integer numberOfTravelers, final String boardingGroup, final Boolean isRoundTrip) {
        this.ticketId = ticketId;
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
        this.returnDeparturePlace = returnDeparturePlace;
        this.returnDepartureTime = returnDepartureTime;
        this.returnArrivalPlace = returnArrivalPlace;
        this.returnArrivalTime = returnArrivalTime;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.numberOfTravelers = numberOfTravelers;
        this.boardingGroup = boardingGroup;
        this.isRoundTrip = isRoundTrip;
    }
}

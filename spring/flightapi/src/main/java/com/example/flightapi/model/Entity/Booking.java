package com.example.flightapi.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Generated;

@Entity
@Table(
        name = "Bookings"
)
public class Booking {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long bookingId;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            name = "outbound_flight_id",
            nullable = false
    )
    private Flight outboundFlight;
    @ManyToOne
    @JoinColumn(
            name = "return_flight_id"
    )
    private Flight returnFlight;
    @Column(
            name = "number_of_travelers",
            nullable = false
    )
    private Integer numberOfTravelers;
    @Column(
            name = "total_price",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalPrice;
    @Column(
            name = "booking_date",
            nullable = false
    )
    private LocalDateTime bookingDate = LocalDateTime.now();
    @Column(
            name = "booking_status",
            nullable = false,
            length = 20
    )
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @Column(
            name = "is_round_trip",
            nullable = false
    )
    private Boolean isRoundTrip;
    @Column(
            name = "boarding_group",
            length = 3
    )
    private String boardingGroup;

    public Long getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getOutboundFlight() {
        return this.outboundFlight;
    }

    public void setOutboundFlight(Flight outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public Flight getReturnFlight() {
        return this.returnFlight;
    }

    public void setReturnFlight(Flight returnFlight) {
        this.returnFlight = returnFlight;
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

    public LocalDateTime getBookingDate() {
        return this.bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
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

    @Generated
    public Booking() {
        this.status = BookingStatus.CONFIRMED;
        this.isRoundTrip = false;
    }

    @Generated
    public Booking(final Long bookingId, final User user, final Flight outboundFlight, final Flight returnFlight, final Integer numberOfTravelers, final BigDecimal totalPrice, final LocalDateTime bookingDate, final BookingStatus status, final Boolean isRoundTrip, final String boardingGroup) {
        this.status = BookingStatus.CONFIRMED;
        this.isRoundTrip = false;
        this.bookingId = bookingId;
        this.user = user;
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
        this.numberOfTravelers = numberOfTravelers;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.status = status;
        this.isRoundTrip = isRoundTrip;
        this.boardingGroup = boardingGroup;
    }
}

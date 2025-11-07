package com.example.flightapi.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
        name = "Ticket"
)
public class Ticket {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long ticketId;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User userId;
    @ManyToOne
    @JoinColumn(
            name = "flight_id",
            nullable = false
    )
    private Flight flightId;
    @Column(
            name = "ticket_date",
            nullable = false
    )
    private LocalDateTime ticketDate = LocalDateTime.now();
    @Column(
            name = "number_of_travelers",
            nullable = false
    )
    private Integer numberOfTravelers;
    @Column(
            name = "boarding_group",
            nullable = false,
            length = 3
    )
    private String boardingGroup;
    @Column(
            name = "total_price",
            nullable = false
    )
    private BigDecimal totalPrice;

    public Long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Flight getFlightId() {
        return this.flightId;
    }

    public void setFlightId(Flight flightId) {
        this.flightId = flightId;
    }

    public LocalDateTime getTicketDate() {
        return this.ticketDate;
    }

    public void setTicketDate(LocalDateTime ticketDate) {
        this.ticketDate = ticketDate;
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

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Generated
    public Ticket() {
    }

    @Generated
    public Ticket(final Long ticketId, final User userId, final Flight flightId, final LocalDateTime ticketDate, final Integer numberOfTravelers, final String boardingGroup, final BigDecimal totalPrice) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.flightId = flightId;
        this.ticketDate = ticketDate;
        this.numberOfTravelers = numberOfTravelers;
        this.boardingGroup = boardingGroup;
        this.totalPrice = totalPrice;
    }
}

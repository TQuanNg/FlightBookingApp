package com.example.flightapi.model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Generated;

@Entity
@Table(
        name = "PurchaseHistory"
)
public class PurchaseHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long purchaseId;
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            name = "ticket_id",
            nullable = false
    )
    private Ticket ticket;
    @Column(
            name = "purchase_date",
            nullable = false
    )
    private LocalDateTime purchaseDate = LocalDateTime.now();

    public Long getPurchaseId() {
        return this.purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket getTicket() {
        return this.ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public LocalDateTime getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Generated
    public PurchaseHistory() {
    }

    @Generated
    public PurchaseHistory(final Long purchaseId, final User user, final Ticket ticket, final LocalDateTime purchaseDate) {
        this.purchaseId = purchaseId;
        this.user = user;
        this.ticket = ticket;
        this.purchaseDate = purchaseDate;
    }
}


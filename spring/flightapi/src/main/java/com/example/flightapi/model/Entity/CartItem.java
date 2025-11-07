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
        name = "cart_item"
)
public class CartItem {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "cart_item_id"
    )
    private Long cartItemId;
    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId;
    @Column(
            name = "flight_id",
            nullable = false
    )
    private Long flightId;
    @Column(
            name = "return_flight_id"
    )
    private Long returnFlightId;
    @Column(
            name = "number_of_travelers",
            nullable = false
    )
    private Integer numberOfTravelers;
    @Column(
            name = "total_price",
            nullable = false
    )
    private BigDecimal totalPrice;
    @Column(
            name = "is_round_trip",
            nullable = false
    )
    private Boolean isRoundTrip = false;
    @Column(
            name = "added_at",
            nullable = false
    )
    private LocalDateTime addedAt;

    public Long getCartItemId() {
        return this.cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlightId() {
        return this.flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getReturnFlightId() {
        return this.returnFlightId;
    }

    public void setReturnFlightId(Long returnFlightId) {
        this.returnFlightId = returnFlightId;
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

    public Boolean getIsRoundTrip() {
        return this.isRoundTrip;
    }

    public void setIsRoundTrip(Boolean isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public LocalDateTime getAddedAt() {
        return this.addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    @Generated
    public CartItem(final Long cartItemId, final Long userId, final Long flightId, final Long returnFlightId, final Integer numberOfTravelers, final BigDecimal totalPrice, final Boolean isRoundTrip, final LocalDateTime addedAt) {
        this.cartItemId = cartItemId;
        this.userId = userId;
        this.flightId = flightId;
        this.returnFlightId = returnFlightId;
        this.numberOfTravelers = numberOfTravelers;
        this.totalPrice = totalPrice;
        this.isRoundTrip = isRoundTrip;
        this.addedAt = addedAt;
    }

    @Generated
    public CartItem() {
    }
}

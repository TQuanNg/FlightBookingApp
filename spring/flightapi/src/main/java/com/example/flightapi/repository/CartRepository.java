package com.example.flightapi.repository;

import com.example.flightapi.model.Entity.CartItem;
import com.example.flightapi.repository.projection.CartItemProjection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    void deleteByUserIdAndCartItemId(Long userId, Long cartItemId);

    @Query("    SELECT\n        c.cartItemId AS cartItemId,\n        c.flightId AS flightId,\n        f.flightNumber AS flightNumber,\n        f.departureCity AS departureCity,\n        f.departureTime AS departureTime,\n        f.arrivalCity AS arrivalCity,\n        f.arrivalTime AS arrivalTime,\n        c.totalPrice AS totalPrice,\n        c.numberOfTravelers AS numberOfTravelers,\n        c.isRoundTrip AS isRoundTrip,\n        c.returnFlightId AS returnFlightId,\n        rf.flightNumber AS returnFlightNumber,\n        rf.departureCity AS returnDepartureCity,\n        rf.departureTime AS returnDepartureTime,\n        rf.arrivalCity AS returnArrivalCity,\n        rf.arrivalTime AS returnArrivalTime\n    FROM CartItem c\n    JOIN Flight f ON c.flightId = f.flightId\n    LEFT JOIN Flight rf ON c.returnFlightId = rf.flightId\n    WHERE c.userId = :userId\n")
    List<CartItemProjection> findCartItemsByUserId(@Param("userId") Long userId);
}

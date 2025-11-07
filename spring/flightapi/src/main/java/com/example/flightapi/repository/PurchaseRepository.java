package com.example.flightapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.flightapi.model.Entity.PurchaseHistory;


public interface PurchaseRepository extends JpaRepository<PurchaseHistory, Long> {
    @Query(
            value = " SELECT \n            ph.purchase_date AS date,\n            f.departure_time AS depart,\n            f.departure_city AS origin,\n            f.arrival_city AS destination,\n            f.arrival_time AS arrival,\n            f.flight_number AS flight_number,\n            t.total_price AS price\n        FROM \n            purchase_history ph\n        JOIN \n            Ticket t ON ph.ticket_id = t.ticket_id\n        JOIN \n            Flights f ON t.flight_id = f.flight_id\n        WHERE \n            ph.user_id = :userId\n        ORDER BY \n            ph.purchase_date DESC",
            nativeQuery = true
    )
    List<Object[]> findByUserId(@Param("userId") Long userId);
}

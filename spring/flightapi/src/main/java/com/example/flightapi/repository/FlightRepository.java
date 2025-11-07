package com.example.flightapi.repository;

import com.example.flightapi.model.Entity.Flight;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query(
            value = "SELECT * FROM flights WHERE departure_city = :departureCity AND arrival_city = :arrivalCity AND departure_time >= :startTime AND departure_time <= :endTime AND available_seats >= :numTravelers",
            nativeQuery = true
    )
    List<Flight> findByUserInput(@Param("departureCity") String departureCity, @Param("arrivalCity") String arrivalCity, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("numTravelers") Integer numTravelers);

    @Modifying
    @Transactional
    @Query("UPDATE Flight f SET f.availableSeats = f.availableSeats - :numberOfTravelers WHERE f.flightId = :flightId")
    int updateAvailableSeats(@Param("flightId") Long flightId, @Param("numberOfTravelers") int numberOfTravelers);
}

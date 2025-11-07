package com.example.flightapi.repository;


import com.example.flightapi.model.Entity.Booking;
import com.example.flightapi.model.Entity.BookingStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId ORDER BY b.bookingDate DESC")
    List<Booking> findByUserId(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b ORDER BY b.bookingDate DESC")
    List<Booking> findAllBookings();

    @Query("SELECT b FROM Booking b WHERE b.status = :status ORDER BY b.bookingDate DESC")
    List<Booking> findByStatus(@Param("status") BookingStatus status);

    @Modifying
    @Query("UPDATE Booking b SET b.status = :status WHERE b.bookingId = :bookingId")
    int updateBookingStatus(@Param("bookingId") Long bookingId, @Param("status") BookingStatus status);
}

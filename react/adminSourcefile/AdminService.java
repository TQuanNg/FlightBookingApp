package com.example.flightapi.service;

import com.example.flightapi.model.DTO.AdminBookingDTO;
import com.example.flightapi.model.DTO.AdminUserDTO;
import com.example.flightapi.model.Entity.*;
import com.example.flightapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    // User Management
    public List<AdminUserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new AdminUserDTO(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getRole(),
                        user.getCreatedAt().toString()
                )).collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse updateUserRole(Long userId, UserRole newRole) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ApiResponse(false, "User not found");
        }

        User user = userOptional.get();
        user.setRole(newRole);
        userRepository.save(user);

        return new ApiResponse(true, "User role updated successfully");
    }

    @Transactional
    public ApiResponse deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            return new ApiResponse(false, "User not found");
        }

        userRepository.deleteById(userId);
        return new ApiResponse(true, "User deleted successfully");
    }

    // Booking Management
    public List<AdminBookingDTO> getAllBookings() {
        return bookingRepository.findAllBookings().stream()
                .map(this::convertToAdminBookingDTO)
                .collect(Collectors.toList());
    }

    public List<AdminBookingDTO> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status).stream()
                .map(this::convertToAdminBookingDTO)
                .collect(Collectors.toList());
    }

    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Transactional
    public ApiResponse updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return new ApiResponse(false, "Booking not found");
        }

        Booking booking = bookingOptional.get();
        BookingStatus oldStatus = booking.getStatus();
        booking.setStatus(newStatus);
        bookingRepository.save(booking);

        // If cancelling, restore seats
        if (newStatus == BookingStatus.CANCELLED && oldStatus != BookingStatus.CANCELLED) {
            restoreSeats(booking);
        }

        return new ApiResponse(true, "Booking status updated successfully");
    }

    @Transactional
    public ApiResponse cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return new ApiResponse(false, "Booking not found");
        }

        Booking booking = bookingOptional.get();
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            return new ApiResponse(false, "Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        // Restore available seats
        restoreSeats(booking);

        return new ApiResponse(true, "Booking cancelled successfully");
    }

    @Transactional
    public ApiResponse deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            return new ApiResponse(false, "Booking not found");
        }

        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getStatus() != BookingStatus.CANCELLED) {
                restoreSeats(booking);
            }
        }

        bookingRepository.deleteById(bookingId);
        return new ApiResponse(true, "Booking deleted successfully");
    }

    private void restoreSeats(Booking booking) {
        Flight outboundFlight = booking.getOutboundFlight();
        outboundFlight.setAvailableSeats(
                outboundFlight.getAvailableSeats() + booking.getNumberOfTravelers()
        );
        flightRepository.save(outboundFlight);

        if (booking.getIsRoundTrip() && booking.getReturnFlight() != null) {
            Flight returnFlight = booking.getReturnFlight();
            returnFlight.setAvailableSeats(
                    returnFlight.getAvailableSeats() + booking.getNumberOfTravelers()
            );
            flightRepository.save(returnFlight);
        }
    }

    // Flight Management
    @Transactional
    public ApiResponse createFlight(Flight flight) {
        try {
            flight.setFlightId(null);
            flightRepository.save(flight);
            return new ApiResponse(true, "Flight created successfully", flight);
        } catch (Exception e) {
            return new ApiResponse(false, "Failed to create flight: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse updateFlight(Long flightId, Flight updatedFlight) {
        Optional<Flight> existingFlightOpt = flightRepository.findById(flightId);
        if (existingFlightOpt.isEmpty()) {
            return new ApiResponse(false, "Flight not found");
        }

        Flight existingFlight = existingFlightOpt.get();
        existingFlight.setFlightNumber(updatedFlight.getFlightNumber());
        existingFlight.setDepartureCity(updatedFlight.getDepartureCity());
        existingFlight.setArrivalCity(updatedFlight.getArrivalCity());
        existingFlight.setDepartureTime(updatedFlight.getDepartureTime());
        existingFlight.setArrivalTime(updatedFlight.getArrivalTime());
        existingFlight.setPrice(updatedFlight.getPrice());
        existingFlight.setAvailableSeats(updatedFlight.getAvailableSeats());

        flightRepository.save(existingFlight);
        return new ApiResponse(true, "Flight updated successfully", existingFlight);
    }

    @Transactional
    public ApiResponse deleteFlight(Long flightId) {
        if (!flightRepository.existsById(flightId)) {
            return new ApiResponse(false, "Flight not found");
        }

        // Check if flight has bookings
        // For safety, you might want to prevent deletion if there are active bookings

        flightRepository.deleteById(flightId);
        return new ApiResponse(true, "Flight deleted successfully");
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long flightId) {
        return flightRepository.findById(flightId);
    }

    // Helper method
    private AdminBookingDTO convertToAdminBookingDTO(Booking booking) {
        User user = booking.getUser();
        Flight outbound = booking.getOutboundFlight();
        Flight returnFlight = booking.getReturnFlight();

        return new AdminBookingDTO(
                booking.getBookingId(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                outbound.getFlightId(),
                outbound.getFlightNumber(),
                outbound.getDepartureCity(),
                outbound.getArrivalCity(),
                outbound.getDepartureTime().toString(),
                outbound.getArrivalTime().toString(),
                returnFlight != null ? returnFlight.getFlightId() : null,
                returnFlight != null ? returnFlight.getFlightNumber() : null,
                returnFlight != null ? returnFlight.getDepartureTime().toString() : null,
                returnFlight != null ? returnFlight.getArrivalTime().toString() : null,
                booking.getNumberOfTravelers(),
                booking.getTotalPrice(),
                booking.getBookingDate().toString(),
                booking.getStatus(),
                booking.getIsRoundTrip(),
                booking.getBoardingGroup()
        );
    }
}


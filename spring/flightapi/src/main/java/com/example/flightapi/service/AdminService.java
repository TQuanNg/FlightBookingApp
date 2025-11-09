package com.example.flightapi.service;

import com.example.flightapi.model.DTO.AdminBookingDTO;
import com.example.flightapi.model.DTO.AdminUserDTO;
import com.example.flightapi.model.DTO.AdminStatsDTO;
import com.example.flightapi.model.Entity.Booking;
import com.example.flightapi.model.Entity.BookingStatus;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.model.Entity.UserRole;
import com.example.flightapi.repository.BookingRepository;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private FlightRepository flightRepository;

    public List<AdminUserDTO> getAllUsers() {
        return (List)this.userRepository.findAll().stream().map((user) -> new AdminUserDTO(user.getUserId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getRole(), user.getCreatedAt().toString())).collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse updateUserRole(Long userId, UserRole newRole) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ApiResponse(false, "User not found");
        } else {
            User user = (User)userOptional.get();
            user.setRole(newRole);
            this.userRepository.save(user);
            return new ApiResponse(true, "User role updated successfully");
        }
    }

    @Transactional
    public ApiResponse deleteUser(Long userId) {
        if (!this.userRepository.existsById(userId)) {
            return new ApiResponse(false, "User not found");
        } else {
            this.userRepository.deleteById(userId);
            return new ApiResponse(true, "User deleted successfully");
        }
    }

    public List<AdminBookingDTO> getAllBookings() {
        return (List)this.bookingRepository.findAllBookings().stream().map(this::convertToAdminBookingDTO).collect(Collectors.toList());
    }

    public List<AdminBookingDTO> getBookingsByStatus(BookingStatus status) {
        return (List)this.bookingRepository.findByStatus(status).stream().map(this::convertToAdminBookingDTO).collect(Collectors.toList());
    }

    public Optional<Booking> getBookingById(Long bookingId) {
        return this.bookingRepository.findById(bookingId);
    }

    @Transactional
    public ApiResponse updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        Optional<Booking> bookingOptional = this.bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return new ApiResponse(false, "Booking not found");
        } else {
            Booking booking = (Booking)bookingOptional.get();
            BookingStatus oldStatus = booking.getStatus();
            booking.setStatus(newStatus);
            this.bookingRepository.save(booking);
            if (newStatus == BookingStatus.CANCELLED && oldStatus != BookingStatus.CANCELLED) {
                this.restoreSeats(booking);
            }

            return new ApiResponse(true, "Booking status updated successfully");
        }
    }

    @Transactional
    public ApiResponse cancelBooking(Long bookingId) {
        Optional<Booking> bookingOptional = this.bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            return new ApiResponse(false, "Booking not found");
        } else {
            Booking booking = (Booking)bookingOptional.get();
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                return new ApiResponse(false, "Booking is already cancelled");
            } else {
                booking.setStatus(BookingStatus.CANCELLED);
                this.bookingRepository.save(booking);
                this.restoreSeats(booking);
                return new ApiResponse(true, "Booking cancelled successfully");
            }
        }
    }

    @Transactional
    public ApiResponse deleteBooking(Long bookingId) {
        if (!this.bookingRepository.existsById(bookingId)) {
            return new ApiResponse(false, "Booking not found");
        } else {
            Optional<Booking> bookingOptional = this.bookingRepository.findById(bookingId);
            if (bookingOptional.isPresent()) {
                Booking booking = (Booking)bookingOptional.get();
                if (booking.getStatus() != BookingStatus.CANCELLED) {
                    this.restoreSeats(booking);
                }
            }

            this.bookingRepository.deleteById(bookingId);
            return new ApiResponse(true, "Booking deleted successfully");
        }
    }

    private void restoreSeats(Booking booking) {
        Flight outboundFlight = booking.getOutboundFlight();
        outboundFlight.setAvailableSeats(outboundFlight.getAvailableSeats() + booking.getNumberOfTravelers());
        this.flightRepository.save(outboundFlight);
        if (booking.getIsRoundTrip() && booking.getReturnFlight() != null) {
            Flight returnFlight = booking.getReturnFlight();
            returnFlight.setAvailableSeats(returnFlight.getAvailableSeats() + booking.getNumberOfTravelers());
            this.flightRepository.save(returnFlight);
        }

    }

    public ApiResponse createFlight(Flight flight) {
        try {
            flight.setFlightId(null);

            Flight savedFlight = this.flightRepository.save(flight);
            return new ApiResponse(true, "Flight created successfully", savedFlight);
        } catch (Exception e) {
            return new ApiResponse(false, "Failed to create flight: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse updateFlight(Long flightId, Flight updatedFlight) {
        Optional<Flight> existingFlightOpt = this.flightRepository.findById(flightId);
        if (existingFlightOpt.isEmpty()) {
            return new ApiResponse(false, "Flight not found");
        } else {
            Flight existingFlight = (Flight)existingFlightOpt.get();
            existingFlight.setFlightNumber(updatedFlight.getFlightNumber());
            existingFlight.setDepartureCity(updatedFlight.getDepartureCity());
            existingFlight.setArrivalCity(updatedFlight.getArrivalCity());
            existingFlight.setDepartureTime(updatedFlight.getDepartureTime());
            existingFlight.setArrivalTime(updatedFlight.getArrivalTime());
            existingFlight.setPrice(updatedFlight.getPrice());
            existingFlight.setAvailableSeats(updatedFlight.getAvailableSeats());
            this.flightRepository.save(existingFlight);
            return new ApiResponse(true, "Flight updated successfully", existingFlight);
        }
    }

    @Transactional
    public ApiResponse deleteFlight(Long flightId) {
        if (!this.flightRepository.existsById(flightId)) {
            return new ApiResponse(false, "Flight not found");
        } else {
            this.flightRepository.deleteById(flightId);
            return new ApiResponse(true, "Flight deleted successfully");
        }
    }

    public List<Flight> getAllFlights() {
        return this.flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long flightId) {
        return this.flightRepository.findById(flightId);
    }

    public AdminStatsDTO getStats() {
        long totalUsers = this.userRepository.count();
        long totalBookings = this.bookingRepository.count();
        long totalFlights = this.flightRepository.count();
        return new AdminStatsDTO(totalUsers, totalBookings, totalFlights);
    }

    private AdminBookingDTO convertToAdminBookingDTO(Booking booking) {
        User user = booking.getUser();
        Flight outbound = booking.getOutboundFlight();
        Flight returnFlight = booking.getReturnFlight();
        return new AdminBookingDTO(booking.getBookingId(),
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
                booking.getBoardingGroup());
    }
}

package com.example.flightapi.controller;

import com.example.flightapi.model.DTO.AdminBookingDTO;
import com.example.flightapi.model.DTO.AdminUserDTO;
import com.example.flightapi.model.Entity.Booking;
import com.example.flightapi.model.Entity.BookingStatus;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.model.Entity.UserRole;
import com.example.flightapi.service.AdminService;
import com.example.flightapi.service.ApiResponse;
import com.example.flightapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Authorization check helper
    private ResponseEntity<?> checkAdminAuth(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        String extractedToken = token.replace("Bearer ", "");
        if (!JwtUtil.isAdmin(extractedToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admin role required.");
        }
        return null;
    }

    private ResponseEntity<?> checkAdminOrStaffAuth(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
        String extractedToken = token.replace("Bearer ", "");
        if (!JwtUtil.isAdminOrStaff(extractedToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admin or Staff role required.");
        }
        return null;
    }

    // USER MANAGEMENT
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        List<AdminUserDTO> users = adminService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<?> updateUserRole(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId,
            @RequestParam UserRole role) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.updateUserRole(userId, role);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(
            @RequestHeader("Authorization") String token,
            @PathVariable Long userId) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.deleteUser(userId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    // BOOKING MANAGEMENT
    @GetMapping("/bookings")
    public ResponseEntity<?> getAllBookings(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        List<AdminBookingDTO> bookings = adminService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/status/{status}")
    public ResponseEntity<?> getBookingsByStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable BookingStatus status) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        List<AdminBookingDTO> bookings = adminService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<?> getBookingById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        Optional<Booking> booking = adminService.getBookingById(bookingId);
        return booking.isPresent()
                ? ResponseEntity.ok(booking.get())
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/bookings/{bookingId}/status")
    public ResponseEntity<?> updateBookingStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.updateBookingStatus(bookingId, status);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.cancelBooking(bookingId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<?> deleteBooking(
            @RequestHeader("Authorization") String token,
            @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.deleteBooking(bookingId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    // FLIGHT MANAGEMENT
    @GetMapping("/flights")
    public ResponseEntity<?> getAllFlights(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        List<Flight> flights = adminService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/flights/{flightId}")
    public ResponseEntity<?> getFlightById(
            @RequestHeader("Authorization") String token,
            @PathVariable Long flightId) {
        ResponseEntity<?> authCheck = checkAdminOrStaffAuth(token);
        if (authCheck != null) return authCheck;

        Optional<Flight> flight = adminService.getFlightById(flightId);
        return flight.isPresent()
                ? ResponseEntity.ok(flight.get())
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/flights")
    public ResponseEntity<?> createFlight(
            @RequestHeader("Authorization") String token,
            @RequestBody Flight flight) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.createFlight(flight);
        return response.isSuccess()
                ? ResponseEntity.status(HttpStatus.CREATED).body(response)
                : ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/flights/{flightId}")
    public ResponseEntity<?> updateFlight(
            @RequestHeader("Authorization") String token,
            @PathVariable Long flightId,
            @RequestBody Flight flight) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.updateFlight(flightId, flight);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @DeleteMapping("/flights/{flightId}")
    public ResponseEntity<?> deleteFlight(
            @RequestHeader("Authorization") String token,
            @PathVariable Long flightId) {
        ResponseEntity<?> authCheck = checkAdminAuth(token);
        if (authCheck != null) return authCheck;

        ApiResponse response = adminService.deleteFlight(flightId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }
}


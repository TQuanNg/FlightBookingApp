package com.example.flightapi.controller;

import com.example.flightapi.model.DTO.AdminBookingDTO;
import com.example.flightapi.model.DTO.AdminUserDTO;
import com.example.flightapi.model.DTO.AdminStatsDTO;
import com.example.flightapi.model.Entity.Booking;
import com.example.flightapi.model.Entity.BookingStatus;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.model.Entity.UserRole;
import com.example.flightapi.service.AdminService;
import com.example.flightapi.service.ApiResponse;
import com.example.flightapi.util.JwtUtil;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/admin"})
public class AdminController {
    @Autowired
    private AdminService adminService;

    private ResponseEntity<?> checkAdminAuth(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String extractedToken = token.replace("Bearer ", "");
            return !JwtUtil.isAdmin(extractedToken) ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admin role required.") : null;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
    }

    private ResponseEntity<?> checkAdminOrStaffAuth(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String extractedToken = token.replace("Bearer ", "");
            return !JwtUtil.isAdminOrStaff(extractedToken) ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admin or Staff role required.") : null;
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }
    }

    @GetMapping({"/stats"})
    public ResponseEntity<?> getStats(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            AdminStatsDTO stats = this.adminService.getStats();
            return ResponseEntity.ok(stats);
        }
    }

    @GetMapping({"/users"})
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            List<AdminUserDTO> users = this.adminService.getAllUsers();
            return ResponseEntity.ok(users);
        }
    }

    @PutMapping({"/users/{userId}/role"})
    public ResponseEntity<?> updateUserRole(@RequestHeader("Authorization") String token, @PathVariable Long userId, @RequestParam UserRole role) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.updateUserRole(userId, role);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping({"/users/{userId}"})
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable Long userId) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.deleteUser(userId);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping({"/bookings"})
    public ResponseEntity<?> getAllBookings(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            List<AdminBookingDTO> bookings = this.adminService.getAllBookings();
            return ResponseEntity.ok(bookings);
        }
    }

    @GetMapping({"/bookings/status/{status}"})
    public ResponseEntity<?> getBookingsByStatus(@RequestHeader("Authorization") String token, @PathVariable BookingStatus status) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            List<AdminBookingDTO> bookings = this.adminService.getBookingsByStatus(status);
            return ResponseEntity.ok(bookings);
        }
    }

    @GetMapping({"/bookings/{bookingId}"})
    public ResponseEntity<?> getBookingById(@RequestHeader("Authorization") String token, @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            Optional<Booking> booking = this.adminService.getBookingById(bookingId);
            return booking.isPresent() ? ResponseEntity.ok((Booking)booking.get()) : ResponseEntity.notFound().build();
        }
    }

    @PutMapping({"/bookings/{bookingId}/status"})
    public ResponseEntity<?> updateBookingStatus(@RequestHeader("Authorization") String token, @PathVariable Long bookingId, @RequestParam BookingStatus status) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.updateBookingStatus(bookingId, status);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping({"/bookings/{bookingId}/cancel"})
    public ResponseEntity<?> cancelBooking(@RequestHeader("Authorization") String token, @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.cancelBooking(bookingId);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping({"/bookings/{bookingId}"})
    public ResponseEntity<?> deleteBooking(@RequestHeader("Authorization") String token, @PathVariable Long bookingId) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.deleteBooking(bookingId);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping({"/flights"})
    public ResponseEntity<?> getAllFlights(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            List<Flight> flights = this.adminService.getAllFlights();
            return ResponseEntity.ok(flights);
        }
    }

    @GetMapping({"/flights/{flightId}"})
    public ResponseEntity<?> getFlightById(@RequestHeader("Authorization") String token, @PathVariable Long flightId) {
        ResponseEntity<?> authCheck = this.checkAdminOrStaffAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            Optional<Flight> flight = this.adminService.getFlightById(flightId);
            return flight.isPresent() ? ResponseEntity.ok((Flight)flight.get()) : ResponseEntity.notFound().build();
        }
    }

    @PostMapping({"/flights"})
    public ResponseEntity<?> createFlight(@RequestHeader("Authorization") String token, @RequestBody Flight flight) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.createFlight(flight);
            return response.isSuccess() ? ResponseEntity.status(HttpStatus.CREATED).body(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping({"/flights/{flightId}"})
    public ResponseEntity<?> updateFlight(@RequestHeader("Authorization") String token, @PathVariable Long flightId, @RequestBody Flight flight) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.updateFlight(flightId, flight);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping({"/flights/{flightId}"})
    public ResponseEntity<?> deleteFlight(@RequestHeader("Authorization") String token, @PathVariable Long flightId) {
        ResponseEntity<?> authCheck = this.checkAdminAuth(token);
        if (authCheck != null) {
            return authCheck;
        } else {
            ApiResponse response = this.adminService.deleteFlight(flightId);
            return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
        }
    }
}
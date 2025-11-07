package com.example.flightapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.flightapi.model.DTO.BookingSummaryDTO;
import com.example.flightapi.model.DTO.CartDTO;
import com.example.flightapi.model.DTO.PurchaseHistoryDTO;
import com.example.flightapi.service.BookingService;
import com.example.flightapi.service.CartService;
import com.example.flightapi.service.TicketService;
import com.example.flightapi.service.ApiResponse;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private CartService cartService;
    
    @Autowired
    private BookingService bookingService;


    @PostMapping("/cart")
    public ResponseEntity<?> addToCart(
            @RequestParam Long userId,
            @RequestParam Long flightId,
            @RequestParam(required = false) Long returnFlightId,
            @RequestParam Integer numberOfTravelers) {

        cartService.addToCart(userId, flightId, returnFlightId, numberOfTravelers);
        return ResponseEntity.ok("Flight(s) added to cart successfully.");
    }
    
    @GetMapping("/cart")
    public ResponseEntity<List<CartDTO>> getCartItems(@RequestParam Long userId) {
        List<CartDTO> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> clearCart(@RequestParam Long userId, @RequestParam Long cartItemId) {
        cartService.clearCart(userId, cartItemId);
        return ResponseEntity.ok("Cart cleared successfully.");
    }

    @PostMapping("/book")
    public ResponseEntity<BookingSummaryDTO> bookTicket(
            @RequestParam Long userId,
            @RequestParam Long flightId,
            @RequestParam(required = false) Long returnFlightId,
            @RequestParam(required = false) Long cartId,
            @RequestParam Integer numberOfTravelers,
            @RequestParam String boardingGroup) {

        BookingSummaryDTO summary = bookingService.createBooking(userId, flightId, returnFlightId, cartId, numberOfTravelers, boardingGroup);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingSummaryDTO>> getUserBookings(@RequestParam Long userId) {
        List<BookingSummaryDTO> bookings = bookingService.getUserBookings(userId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/bookings/cancel")
    public ResponseEntity<ApiResponse> cancelBooking(
            @RequestParam Long userId,
            @RequestParam Long bookingId) {
        ApiResponse response = bookingService.cancelUserBooking(userId, bookingId);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PurchaseHistoryDTO>> getPurchaseHistory(@RequestParam Long userId) {
        List<PurchaseHistoryDTO> history = ticketService.getPurchaseHistory(userId);
        return ResponseEntity.ok(history);
    }
}

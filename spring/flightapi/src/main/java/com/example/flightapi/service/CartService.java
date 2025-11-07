package com.example.flightapi.service;

import com.example.flightapi.model.DTO.CartDTO;
import com.example.flightapi.model.Entity.CartItem;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.repository.CartRepository;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.repository.projection.CartItemProjection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private FlightRepository flightRepository;

    public void addToCart(Long userId, Long flightId, Long returnFlightId, Integer numberOfTravelers) {
        Flight outboundFlight = (Flight)this.flightRepository.findById(flightId).orElseThrow(() -> new IllegalArgumentException("Flight not found"));
        BigDecimal totalPrice = this.calculateTotal(outboundFlight, numberOfTravelers);
        boolean isRoundTrip = false;
        if (returnFlightId != null) {
            Flight returnFlight = (Flight)this.flightRepository.findById(returnFlightId).orElseThrow(() -> new IllegalArgumentException("Return flight not found"));
            totalPrice = totalPrice.add(this.calculateTotal(returnFlight, numberOfTravelers));
            isRoundTrip = true;
        }

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setFlightId(flightId);
        cartItem.setReturnFlightId(returnFlightId);
        cartItem.setNumberOfTravelers(numberOfTravelers);
        cartItem.setTotalPrice(totalPrice);
        cartItem.setIsRoundTrip(isRoundTrip);
        cartItem.setAddedAt(LocalDateTime.now());
        this.cartRepository.save(cartItem);
    }

    public BigDecimal calculateTotal(Flight flight, Integer numberOfTravelers) {
        return flight.getPrice().multiply(BigDecimal.valueOf((long)numberOfTravelers));
    }

    public List<CartDTO> getCartItems(Long userId) {
        List<CartItemProjection> cartItems = this.cartRepository.findCartItemsByUserId(userId);
        return cartItems.stream().map((item) -> new CartDTO(item.getCartItemId(), item.getFlightId(), item.getFlightNumber(), item.getDepartureCity(), item.getDepartureTime(), item.getArrivalCity(), item.getArrivalTime(), item.getTotalPrice(), item.getNumberOfTravelers(), item.getIsRoundTrip(), item.getReturnFlightId(), item.getReturnFlightNumber(), item.getReturnDepartureCity(), item.getReturnDepartureTime(), item.getReturnArrivalCity(), item.getReturnArrivalTime())).toList();
    }

    @Transactional
    public void clearCart(Long userId, Long cartItemId) {
        if (cartItemId != null) {
            this.cartRepository.deleteByUserIdAndCartItemId(userId, cartItemId);
        } else {
            this.cartRepository.deleteByUserId(userId);
        }

    }
}

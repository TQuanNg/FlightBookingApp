package com.example.flightapi.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.flightapi.model.DTO.BookingSummaryDTO;
import com.example.flightapi.model.DTO.PurchaseHistoryDTO;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.model.Entity.PurchaseHistory;
import com.example.flightapi.model.Entity.Ticket;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.repository.CartRepository;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.repository.PurchaseRepository;
import com.example.flightapi.repository.TicketRepository;
import com.example.flightapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final PurchaseRepository purchaseRepository;
    private final CartRepository cartRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, FlightRepository flightRepository, PurchaseRepository purchaseRepository, CartRepository cartRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.purchaseRepository = purchaseRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartId) {
        this.cartRepository.deleteByUserIdAndCartItemId(userId, cartId);
    }

    @Transactional
    public BookingSummaryDTO bookTicket(Long userId, Long flightId, Long cartId, Integer numberOfTravelers, String boardingGroup) {
        Optional<User> user = this.userRepository.findById(userId);
        Optional<Flight> flightOptional = this.flightRepository.findById(flightId);
        Flight flight = (Flight)flightOptional.orElseThrow(() -> new RuntimeException("Flight not found"));
        BigDecimal totalPrice = this.calculateTotal(flight, numberOfTravelers);
        Ticket ticket = this.createTicket(user, flight, numberOfTravelers, boardingGroup, totalPrice);
        Ticket savedTicket = (Ticket)this.ticketRepository.save(ticket);
        PurchaseHistory purchaseHistory = this.addToPurchaseHistory(user, savedTicket);
        this.purchaseRepository.save(purchaseHistory);
        this.updateAvailableSeats(flightId, numberOfTravelers);
        this.deleteCartItem(userId, cartId);
        return this.prepareBookingSummary(savedTicket, flight, totalPrice, numberOfTravelers, boardingGroup);
    }

    private Ticket createTicket(Optional<User> user, Flight flight, Integer numberOfTravelers, String boardingGroup, BigDecimal totalPrice) {
        Ticket ticket = new Ticket();
        ticket.setUserId((User)user.get());
        ticket.setFlightId(flight);
        ticket.setNumberOfTravelers(numberOfTravelers);
        ticket.setBoardingGroup(boardingGroup);
        ticket.setTotalPrice(totalPrice);
        return ticket;
    }

    private PurchaseHistory addToPurchaseHistory(Optional<User> user, Ticket savedTicket) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setUser((User)user.get());
        purchaseHistory.setTicket(savedTicket);
        return purchaseHistory;
    }

    private void updateAvailableSeats(Long flightId, Integer numberOfTravelers) {
        int updatedSeats = this.flightRepository.updateAvailableSeats(flightId, numberOfTravelers);
        if (updatedSeats == 0) {
            throw new RuntimeException("Failed to update available seats. Flight may be overbooked.");
        }
    }

    private BookingSummaryDTO prepareBookingSummary(Ticket ticket, Flight flight, BigDecimal totalPrice, Integer numberOfTravelers, String boardingGroup) {
        BookingSummaryDTO summary = new BookingSummaryDTO();
        summary.setTicketId(ticket.getTicketId());
        summary.setDeparturePlace(flight.getDepartureCity());
        summary.setDepartureTime(flight.getDepartureTime().toString());
        summary.setArrivalPlace(flight.getArrivalCity());
        summary.setArrivalTime(flight.getArrivalTime().toString());
        summary.setPrice(totalPrice);
        summary.setPurchaseDate(ticket.getTicketDate());
        summary.setNumberOfTravelers(numberOfTravelers);
        summary.setBoardingGroup(boardingGroup);
        return summary;
    }

    public BigDecimal calculateTotal(Flight flight, Integer numberOfTravelers) {
        return flight.getPrice().multiply(BigDecimal.valueOf((long)numberOfTravelers));
    }

    public List<PurchaseHistoryDTO> getPurchaseHistory(Long userId) {
        List<Object[]> rawResults = this.purchaseRepository.findByUserId(userId);
        List<PurchaseHistoryDTO> purchaseHistoryList = rawResults.stream().map((result) -> new PurchaseHistoryDTO(this.convertToLocalDateTime(result[0]), this.convertToLocalDateTime(result[1]), (String)result[2], (String)result[3], this.convertToLocalDateTime(result[4]), (String)result[5], (BigDecimal)result[6])).toList();
        return purchaseHistoryList;
    }

    private LocalDateTime convertToLocalDateTime(Object timestamp) {
        return timestamp instanceof Timestamp ? ((Timestamp)timestamp).toLocalDateTime() : null;
    }
}


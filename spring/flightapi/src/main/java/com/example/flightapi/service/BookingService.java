package com.example.flightapi.service;

import com.example.flightapi.model.DTO.BookingSummaryDTO;
import com.example.flightapi.model.Entity.Booking;
import com.example.flightapi.model.Entity.BookingStatus;
import com.example.flightapi.model.Entity.Flight;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.repository.BookingRepository;
import com.example.flightapi.repository.CartRepository;
import com.example.flightapi.repository.FlightRepository;
import com.example.flightapi.repository.UserRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public BookingSummaryDTO createBooking(Long userId, Long outboundFlightId, Long returnFlightId, Long cartItemId, Integer numberOfTravelers, String boardingGroup) {
        User user = (User)this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Flight outboundFlight = (Flight)this.flightRepository.findById(outboundFlightId).orElseThrow(() -> new RuntimeException("Outbound flight not found"));
        if (outboundFlight.getAvailableSeats() < numberOfTravelers) {
            throw new RuntimeException("Not enough seats available on outbound flight");
        } else {
            Flight returnFlight = null;
            boolean isRoundTrip = false;
            BigDecimal totalPrice = outboundFlight.getPrice().multiply(BigDecimal.valueOf((long)numberOfTravelers));
            if (returnFlightId != null) {
                returnFlight = (Flight)this.flightRepository.findById(returnFlightId).orElseThrow(() -> new RuntimeException("Return flight not found"));
                if (returnFlight.getAvailableSeats() < numberOfTravelers) {
                    throw new RuntimeException("Not enough seats available on return flight");
                }

                totalPrice = totalPrice.add(returnFlight.getPrice().multiply(BigDecimal.valueOf((long)numberOfTravelers)));
                isRoundTrip = true;
            }

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setOutboundFlight(outboundFlight);
            booking.setReturnFlight(returnFlight);
            booking.setNumberOfTravelers(numberOfTravelers);
            booking.setTotalPrice(totalPrice);
            booking.setIsRoundTrip(isRoundTrip);
            booking.setBoardingGroup(boardingGroup);
            booking.setStatus(BookingStatus.CONFIRMED);
            Booking savedBooking = (Booking)this.bookingRepository.save(booking);
            outboundFlight.setAvailableSeats(outboundFlight.getAvailableSeats() - numberOfTravelers);
            this.flightRepository.save(outboundFlight);
            if (returnFlight != null) {
                returnFlight.setAvailableSeats(returnFlight.getAvailableSeats() - numberOfTravelers);
                this.flightRepository.save(returnFlight);
            }

            if (cartItemId != null) {
                this.cartRepository.deleteByUserIdAndCartItemId(userId, cartItemId);
            }

            return this.createBookingSummaryDTO(savedBooking, outboundFlight, returnFlight);
        }
    }

    public List<BookingSummaryDTO> getUserBookings(Long userId) {
        List<Booking> bookings = this.bookingRepository.findByUserId(userId);
        return (List)bookings.stream().map((booking) -> this.createBookingSummaryDTO(booking, booking.getOutboundFlight(), booking.getReturnFlight())).collect(Collectors.toList());
    }

    @Transactional
    public ApiResponse cancelUserBooking(Long userId, Long bookingId) {
        Optional<Booking> bookingOpt = this.bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            return new ApiResponse(false, "Booking not found");
        } else {
            Booking booking = (Booking)bookingOpt.get();
            if (!booking.getUser().getUserId().equals(userId)) {
                return new ApiResponse(false, "Unauthorized to cancel this booking");
            } else if (booking.getStatus() == BookingStatus.CANCELLED) {
                return new ApiResponse(false, "Booking is already cancelled");
            } else {
                booking.setStatus(BookingStatus.CANCELLED);
                this.bookingRepository.save(booking);
                Flight outbound = booking.getOutboundFlight();
                outbound.setAvailableSeats(outbound.getAvailableSeats() + booking.getNumberOfTravelers());
                this.flightRepository.save(outbound);
                if (booking.getIsRoundTrip() && booking.getReturnFlight() != null) {
                    Flight returnFlight = booking.getReturnFlight();
                    returnFlight.setAvailableSeats(returnFlight.getAvailableSeats() + booking.getNumberOfTravelers());
                    this.flightRepository.save(returnFlight);
                }

                return new ApiResponse(true, "Booking cancelled successfully");
            }
        }
    }

    private BookingSummaryDTO createBookingSummaryDTO(Booking booking, Flight outbound, Flight returnFlight) {
        BookingSummaryDTO dto = new BookingSummaryDTO();
        dto.setTicketId(booking.getBookingId());
        dto.setDeparturePlace(outbound.getDepartureCity());
        dto.setDepartureTime(outbound.getDepartureTime().toString());
        dto.setArrivalPlace(outbound.getArrivalCity());
        dto.setArrivalTime(outbound.getArrivalTime().toString());
        dto.setPrice(booking.getTotalPrice());
        dto.setPurchaseDate(booking.getBookingDate());
        dto.setNumberOfTravelers(booking.getNumberOfTravelers());
        dto.setBoardingGroup(booking.getBoardingGroup());
        if (returnFlight != null) {
            dto.setReturnDeparturePlace(returnFlight.getDepartureCity());
            dto.setReturnDepartureTime(returnFlight.getDepartureTime().toString());
            dto.setReturnArrivalPlace(returnFlight.getArrivalCity());
            dto.setReturnArrivalTime(returnFlight.getArrivalTime().toString());
            dto.setRoundTrip(true);
        } else {
            dto.setRoundTrip(false);
        }

        return dto;
    }
}

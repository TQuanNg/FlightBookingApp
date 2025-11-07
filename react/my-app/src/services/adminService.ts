import apiClient from './apiClient';
import { 
  AdminUserDTO, 
  AdminBookingDTO, 
  Flight, 
  ApiResponse, 
  UserRole, 
  BookingStatus 
} from '../types';

export const adminService = {
  // User Management
  async getAllUsers(): Promise<AdminUserDTO[]> {
    const response = await apiClient.get('/api/admin/users');
    return response.data;
  },

  async updateUserRole(userId: number, role: UserRole): Promise<ApiResponse> {
    const response = await apiClient.put(`/api/admin/users/${userId}/role`, null, {
      params: { role }
    });
    return response.data;
  },

  async deleteUser(userId: number): Promise<ApiResponse> {
    const response = await apiClient.delete(`/api/admin/users/${userId}`);
    return response.data;
  },

  // Booking Management
  async getAllBookings(): Promise<AdminBookingDTO[]> {
    const response = await apiClient.get('/api/admin/bookings');
    return response.data;
  },

  async getBookingsByStatus(status: BookingStatus): Promise<AdminBookingDTO[]> {
    const response = await apiClient.get(`/api/admin/bookings/status/${status}`);
    return response.data;
  },

  async getBookingById(bookingId: number): Promise<AdminBookingDTO> {
    const response = await apiClient.get(`/api/admin/bookings/${bookingId}`);
    return response.data;
  },

  async updateBookingStatus(bookingId: number, status: BookingStatus): Promise<ApiResponse> {
    const response = await apiClient.put(`/api/admin/bookings/${bookingId}/status`, null, {
      params: { status }
    });
    return response.data;
  },

  async cancelBooking(bookingId: number): Promise<ApiResponse> {
    const response = await apiClient.post(`/api/admin/bookings/${bookingId}/cancel`);
    return response.data;
  },

  async deleteBooking(bookingId: number): Promise<ApiResponse> {
    const response = await apiClient.delete(`/api/admin/bookings/${bookingId}`);
    return response.data;
  },

  // Flight Management
  async getAllFlights(): Promise<Flight[]> {
    const response = await apiClient.get('/api/admin/flights');
    return response.data;
  },

  async getFlightById(flightId: number): Promise<Flight> {
    const response = await apiClient.get(`/api/admin/flights/${flightId}`);
    return response.data;
  },

  async createFlight(flight: Omit<Flight, 'id' | 'flightId'>): Promise<ApiResponse> {
    // Prepare flight data for backend - ensure proper formatting
    const flightData = {
      flightNumber: flight.flightNumber,
      departureCity: flight.departureCity,
      arrivalCity: flight.arrivalCity,
      departureTime: flight.departureTime,
      arrivalTime: flight.arrivalTime,
      price: Number(flight.price), // Ensure it's a number
      availableSeats: Number(flight.availableSeats)
    };
    const response = await apiClient.post('/api/admin/flights', flightData);
    return response.data;
  },

  async updateFlight(flightId: number, flight: Partial<Flight>): Promise<ApiResponse> {
    // Prepare flight data for backend - ensure proper formatting
    const flightData = {
      flightNumber: flight.flightNumber,
      departureCity: flight.departureCity,
      arrivalCity: flight.arrivalCity,
      departureTime: flight.departureTime,
      arrivalTime: flight.arrivalTime,
      price: flight.price ? Number(flight.price) : undefined,
      availableSeats: flight.availableSeats ? Number(flight.availableSeats) : undefined
    };
    const response = await apiClient.put(`/api/admin/flights/${flightId}`, flightData);
    return response.data;
  },

  async deleteFlight(flightId: number): Promise<ApiResponse> {
    const response = await apiClient.delete(`/api/admin/flights/${flightId}`);
    return response.data;
  }
};

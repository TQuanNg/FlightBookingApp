import apiClient from './apiClient';
import { BookingSummaryDTO, ApiResponse } from '../types';

export const bookingService = {
  async bookTicket(
    userId: number,
    flightId: number,
    numberOfTravelers: number,
    boardingGroup: string,
    returnFlightId?: number,
    cartId?: number
  ): Promise<BookingSummaryDTO> {
    const params: any = {
      userId,
      flightId,
      numberOfTravelers,
      boardingGroup
    };
    
    if (returnFlightId) params.returnFlightId = returnFlightId;
    if (cartId) params.cartId = cartId;

    const response = await apiClient.post('/book', null, { params });
    return response.data;
  },

  async getUserBookings(userId: number): Promise<BookingSummaryDTO[]> {
    const response = await apiClient.get('/bookings', {
      params: { userId }
    });
    return response.data;
  },

  async cancelBooking(userId: number, bookingId: number): Promise<ApiResponse> {
    const response = await apiClient.post('/bookings/cancel', null, {
      params: { userId, bookingId }
    });
    return response.data;
  }
};

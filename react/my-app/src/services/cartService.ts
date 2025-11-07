import apiClient from './apiClient';
import { CartDTO, ApiResponse } from '../types';

export const cartService = {
  async addToCart(
    userId: number,
    flightId: number,
    numberOfTravelers: number,
    returnFlightId?: number
  ): Promise<ApiResponse> {
    const params: any = {
      userId,
      flightId,
      numberOfTravelers
    };
    
    if (returnFlightId) params.returnFlightId = returnFlightId;

    const response = await apiClient.post('/cart', null, { params });
    return response.data;
  },

  async getCartItems(userId: number): Promise<CartDTO[]> {
    const response = await apiClient.get('/cart', {
      params: { userId }
    });
    return response.data;
  },

  async clearCart(userId: number, cartItemId: number): Promise<ApiResponse> {
    const response = await apiClient.delete('/cart', {
      params: { userId, cartItemId }
    });
    return response.data;
  }
};

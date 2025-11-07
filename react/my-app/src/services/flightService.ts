import apiClient from './apiClient';
import { Flight, FlightSearchParams, ApiResponse } from '../types';

export const flightService = {
  async searchFlights(params: FlightSearchParams): Promise<Flight[]> {
    const response = await apiClient.get('/flights/search', { params });
    return response.data;
  },

  async getAllFlights(): Promise<Flight[]> {
    const response = await apiClient.get('/api/admin/flights');
    return response.data;
  },

  async getFlightById(flightId: number): Promise<Flight> {
    const response = await apiClient.get(`/api/admin/flights/${flightId}`);
    return response.data;
  },

  async createFlight(flight: Omit<Flight, 'id'>): Promise<ApiResponse> {
    const response = await apiClient.post('/api/admin/flights', flight);
    return response.data;
  },

  async updateFlight(flightId: number, flight: Partial<Flight>): Promise<ApiResponse> {
    const response = await apiClient.put(`/api/admin/flights/${flightId}`, flight);
    return response.data;
  },

  async deleteFlight(flightId: number): Promise<ApiResponse> {
    const response = await apiClient.delete(`/api/admin/flights/${flightId}`);
    return response.data;
  }
};

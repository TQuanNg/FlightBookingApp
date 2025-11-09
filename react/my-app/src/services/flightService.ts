import apiClient from './apiClient';
import { Flight, FlightSearchParams, FlightSearchResponseDTO, ApiResponse } from '../types';

export const flightService = {
  async searchFlights(params: FlightSearchParams): Promise<FlightSearchResponseDTO> {
    // Clean up params - remove endTime if it's empty (for ONE_WAY trips)
    const cleanParams = {
      ...params,
      endTime: params.endTime || undefined // Send undefined instead of empty string
    };
    
    const response = await apiClient.get<FlightSearchResponseDTO>('/flights/search', { params: cleanParams });
    return response.data;
  },

  // Legacy endpoint for simple flight search (returns flat list)
  async searchFlightsSimple(params: FlightSearchParams): Promise<Flight[]> {
    const response = await apiClient.get<Flight[]>('/flights/search/simple', { params });
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

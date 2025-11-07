import apiClient from './apiClient';
import { PurchaseHistoryDTO } from '../types';

export const ticketService = {
  async getPurchaseHistory(userId: number): Promise<PurchaseHistoryDTO[]> {
    const response = await apiClient.get('/history', {
      params: { userId }
    });
    return response.data;
  }
};

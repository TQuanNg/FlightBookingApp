import apiClient from './apiClient';
import { LoginRequest, LoginResponse, RegisterRequest, ApiResponse, UserDTO } from '../types';

export const authService = {
  async register(data: RegisterRequest): Promise<ApiResponse<UserDTO>> {
    const response = await apiClient.post('/api/users/register', data);
    return response.data;
  },

  async login(username: string, passwordHash: string): Promise<LoginResponse> {
    console.log('AuthService: Attempting login for user:', username);
    
    const response = await apiClient.post('/api/users/login', null, {
      params: { username, passwordHash }
    });
    
    console.log('AuthService: Login response:', response.data);
    
    const loginResponse = response.data;
    
    // Handle nested data structure from backend
    if (loginResponse.success && loginResponse.data) {
      const { token, user } = loginResponse.data;
      console.log('AuthService: Storing token and user from nested data:', { token: token?.substring(0, 20) + '...', user });
      localStorage.setItem('token', token);
      localStorage.setItem('user', JSON.stringify(user));
      
      // Also store at top level for backward compatibility
      loginResponse.token = token;
      loginResponse.user = user;
    } else if (loginResponse.success && loginResponse.token) {
      // Fallback for old response format
      console.log('AuthService: Storing token and user from top level');
      localStorage.setItem('token', loginResponse.token);
      if (loginResponse.user) {
        localStorage.setItem('user', JSON.stringify(loginResponse.user));
      }
    } else {
      console.error('AuthService: Login response does not contain expected data:', loginResponse);
    }
    
    console.log('AuthService: Final login response:', loginResponse);
    return loginResponse;
  },

  async getCurrentUser(): Promise<UserDTO> {
    const response = await apiClient.post('/api/users/me');
    return response.data;
  },

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  },

  getStoredUser(): UserDTO | null {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  getToken(): string | null {
    return localStorage.getItem('token');
  }
};

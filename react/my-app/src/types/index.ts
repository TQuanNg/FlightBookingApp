// User Types
export enum UserRole {
  USER = 'USER',
  ADMIN = 'ADMIN',
  STAFF = 'STAFF'
}

export interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
}

export interface UserDTO {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
}

export interface AdminUserDTO extends UserDTO {
  createdAt: string;
}

// Flight Types
export enum FlightStatus {
  ON_TIME = 'ON_TIME',
  DELAYED = 'DELAYED',
  CANCELLED = 'CANCELLED',
  BOARDING = 'BOARDING'
}

export interface Flight {
  id?: number; // Optional for frontend compatibility
  flightId?: number; // Backend uses flightId
  flightNumber: string;
  departureCity: string;
  arrivalCity: string;
  departureTime: string;
  arrivalTime: string;
  duration?: number; // Optional, calculated field
  availableSeats: number;
  price: number; // Main price field used by backend
  status?: string; // Optional, may not be returned by backend
}

// Booking Types
export enum BookingStatus {
  PENDING = 'PENDING',
  CONFIRMED = 'CONFIRMED',
  CANCELLED = 'CANCELLED',
  COMPLETED = 'COMPLETED'
}

export interface Booking {
  id: number;
  userId: number;
  flightId: number;
  returnFlightId?: number;
  numberOfTravelers: number;
  boardingGroup: string;
  status: BookingStatus;
  bookingDate: string;
  totalPrice: number;
}

export interface BookingSummaryDTO {
  ticketId: number;
  departurePlace: string;
  departureTime: string;
  arrivalPlace: string;
  arrivalTime: string;
  returnDeparturePlace?: string;
  returnDepartureTime?: string;
  returnArrivalPlace?: string;
  returnArrivalTime?: string;
  price: number;
  purchaseDate: string;
  numberOfTravelers: number;
  boardingGroup: string;
  isRoundTrip: boolean;
}

export interface AdminBookingDTO {
  bookingId: number;
  userId: number;
  username: string;
  userEmail: string;
  outboundFlightId: number;
  outboundFlightNumber: string;
  departureCity: string;
  arrivalCity: string;
  departureTime: string;
  arrivalTime: string;
  returnFlightId?: number;
  returnFlightNumber?: string;
  returnDepartureTime?: string;
  returnArrivalTime?: string;
  numberOfTravelers: number;
  totalPrice: number;
  bookingDate: string;
  status: BookingStatus;
  isRoundTrip: boolean;
  boardingGroup: string;
}

export interface AdminUserDTO {
  userId: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: UserRole;
  createdAt: string;
}

// Cart Types
export interface CartItem {
  id: number;
  userId: number;
  flightId: number;
  returnFlightId?: number;
  numberOfTravelers: number;
  addedAt: string;
}

export interface CartDTO {
  cartItemId: number;
  flightId: number;
  flightNumber: string;
  departureCity: string;
  departureTime: string;
  arrivalCity: string;
  arrivalTime: string;
  totalPrice?: number;
  numberOfTravelers: number;
  isRoundTrip: boolean;
  returnFlightId?: number;
  returnFlightNumber?: string;
  returnDepartureCity?: string;
  returnDepartureTime?: string;
  returnArrivalCity?: string;
  returnArrivalTime?: string;
}

// Purchase History
export interface PurchaseHistoryDTO {
  bookingId: number;
  flight: Flight;
  returnFlight?: Flight;
  numberOfTravelers: number;
  boardingGroup: string;
  status: BookingStatus;
  bookingDate: string;
  totalPrice: number;
  purchaseDate: string;
}

// API Response
export interface ApiResponse<T = any> {
  success: boolean;
  message: string;
  data?: T;
}

// Auth Types
export interface LoginRequest {
  username: string;
  passwordHash: string;
}

export interface LoginResponse {
  success: boolean;
  message: string;
  data?: {
    token: string;
    user: UserDTO;
    role: string;
  };
  // Deprecated fields for backward compatibility
  token?: string;
  user?: UserDTO;
}

export interface RegisterRequest {
  username: string;
  email: string;
  passwordHash: string;
  firstName: string;
  lastName: string;
}

// Flight Search Types
export enum TripType {
  ONE_WAY = 'ONE_WAY',
  ROUND_TRIP = 'ROUND_TRIP'
}

export interface FlightSearchParams {
  departureCity: string;
  arrivalCity: string;
  startTime: string;
  endTime: string;
  numTravelers: number;
  tripType?: TripType | string;
}

export interface FlightSearchResponseDTO {
  tripType: string;
  outboundFlights: Flight[];
  returnFlights: Flight[] | null;
}

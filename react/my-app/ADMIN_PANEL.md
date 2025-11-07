# Admin Panel Documentation

## Overview
The admin panel provides comprehensive management capabilities for users, bookings, and flights. Access is restricted to users with ADMIN role.

## Routes
- `/admin` - Admin Dashboard (landing page with navigation cards)
- `/admin/users` - User Management
- `/admin/bookings` - Booking Management
- `/admin/flights` - Flight Management

## Features

### Admin Dashboard (`/admin`)
- Navigation cards to all admin sections
- Quick stats overview (placeholder for future implementation)
- Neo-brutalism design with hover effects

### User Management (`/admin/users`)
**Features:**
- View all registered users in a list
- Display user information: username, full name, email, role, join date
- Edit user roles (ADMIN, STAFF, USER) via modal
- Delete users with confirmation
- Color-coded role badges:
  - ADMIN: Red
  - STAFF: Blue
  - USER: Gray

**API Methods:**
- `getAllUsers()` - GET /api/admin/users
- `updateUserRole(userId, role)` - PUT /api/admin/users/{userId}/role
- `deleteUser(userId)` - DELETE /api/admin/users/{userId}

### Booking Management (`/admin/bookings`)
**Features:**
- View all bookings with detailed information
- Filter bookings by status (All, Pending, Confirmed, Completed, Cancelled)
- Display booking details:
  - Booking ID, user info (username, email)
  - Outbound flight details (flight number, cities, times)
  - Return flight details (for round trips)
  - Number of travelers, boarding group, total price, booking date
- Update booking status via modal
- Cancel bookings (changes status to CANCELLED)
- Delete bookings with confirmation
- Color-coded status badges:
  - CONFIRMED: Green
  - PENDING: Yellow
  - CANCELLED: Red
  - COMPLETED: Blue

**API Methods:**
- `getAllBookings()` - GET /api/admin/bookings
- `getBookingsByStatus(status)` - GET /api/admin/bookings/status/{status}
- `getBookingById(bookingId)` - GET /api/admin/bookings/{id}
- `updateBookingStatus(bookingId, status)` - PUT /api/admin/bookings/{id}/status
- `cancelBooking(bookingId)` - POST /api/admin/bookings/{id}/cancel
- `deleteBooking(bookingId)` - DELETE /api/admin/bookings/{id}

### Flight Management (`/admin/flights`)
**Features:**
- View all flights in a list
- Create new flights via modal form
- Edit existing flights via modal form
- Delete flights with confirmation
- Display flight information:
  - Flight number, departure/arrival cities
  - Departure/arrival times
  - Available seats, base price
  - Flight status (ON_TIME, DELAYED, BOARDING, CANCELLED)
- Color-coded status badges (same as bookings)

**Form Fields:**
- Flight Number (text)
- Departure City (text)
- Arrival City (text)
- Departure Time (datetime-local)
- Arrival Time (datetime-local)
- Available Seats (number)
- Base Price (number, decimal)
- Status (select dropdown)

**API Methods:**
- `getAllFlights()` - GET /api/admin/flights
- `getFlightById(flightId)` - GET /api/admin/flights/{id}
- `createFlight(flight)` - POST /api/admin/flights
- `updateFlight(flightId, flight)` - PUT /api/admin/flights/{id}
- `deleteFlight(flightId)` - DELETE /api/admin/flights/{id}

## Authorization
All admin routes are protected by `ProtectedRoute` component with `requireAdmin` prop. Only users with ADMIN role can access these pages.

## UI Components Used
- **Card**: Container for list items with Neo-brutalism styling
- **Button**: Action buttons with variants (primary, secondary, danger)
- **Modal**: Popup dialogs for forms and confirmations
- **Select**: Dropdown for status/role selection
- **Input**: Text and number inputs for forms
- **Loading**: Loading spinner with message
- **Alert**: Error/success message display

## Design System
All admin pages follow the Neo-brutalism design:
- Bold black borders (border-4, border-2)
- Offset shadows (shadow-brutal)
- Yellow accent color (#FFC700)
- High contrast color schemes
- Bold uppercase headings
- Clear visual hierarchy

## Backend Integration
All admin operations are handled through `adminService.ts`, which uses Axios with JWT authentication. The service automatically includes the auth token from localStorage in all requests.

## Error Handling
- API errors are caught and displayed via Alert component
- User confirmations for destructive actions (delete, cancel)
- Success/error messages via browser alerts
- Loading states during async operations

## Type Safety
All admin DTOs are typed:
- `AdminUserDTO`: User data with creation date
- `AdminBookingDTO`: Booking data with flat structure matching backend
- `Flight`: Flight data with optional basePrice for admin operations
- `BookingStatus`: Enum for booking statuses
- `FlightStatus`: Enum for flight statuses
- `UserRole`: Enum for user roles

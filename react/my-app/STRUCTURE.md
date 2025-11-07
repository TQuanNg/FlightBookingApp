# 📊 Application Structure

## Component Hierarchy

```
App
├── BrowserRouter
│   └── Routes
│       ├── /login → LoginPage
│       ├── /register → RegisterPage
│       └── MainLayout (Protected)
│           ├── Navbar
│           │   ├── Logo
│           │   ├── Cart Button (with counter badge)
│           │   ├── Bookings Button
│           │   ├── Admin Button (if admin/staff)
│           │   ├── User Info
│           │   └── Logout Button
│           ├── Routes
│           │   ├── / → HomePage
│           │   │   ├── FlightSearchForm
│           │   │   ├── FlightCard[] (search results)
│           │   │   ├── Modal (booking confirmation)
│           │   │   └── Alert (errors/success)
│           │   ├── /cart → CartPage
│           │   │   ├── CartItemCard[]
│           │   │   ├── Total Summary Card
│           │   │   └── Modal (checkout)
│           │   ├── /bookings → BookingsPage
│           │   │   └── BookingCard[]
│           │   └── /admin → AdminDashboard
│           │       └── Management Cards
│           └── Footer
```

## Data Flow

```
User Action
    ↓
React Component
    ↓
Dispatch Redux Action
    ↓
Async Thunk (Redux Toolkit)
    ↓
API Service
    ↓
Axios → Backend API
    ↓
Response
    ↓
Update Redux Store
    ↓
Component Re-renders
    ↓
UI Updates
```

## State Management (Redux)

```
Store
├── auth
│   ├── user
│   ├── token
│   ├── isAuthenticated
│   ├── loading
│   └── error
├── flights
│   ├── flights[]
│   ├── selectedFlight
│   ├── searchParams
│   ├── loading
│   └── error
├── cart
│   ├── items[]
│   ├── loading
│   └── error
└── bookings
    ├── bookings[]
    ├── loading
    └── error
```

## API Service Architecture

```
apiClient (Axios Instance)
├── Interceptors
│   ├── Request: Add JWT token
│   └── Response: Handle 401 errors
├── authService
│   ├── register()
│   ├── login()
│   ├── getCurrentUser()
│   └── logout()
├── flightService
│   ├── searchFlights()
│   ├── getAllFlights()
│   ├── createFlight()
│   ├── updateFlight()
│   └── deleteFlight()
├── cartService
│   ├── addToCart()
│   ├── getCartItems()
│   └── clearCart()
├── bookingService
│   ├── bookTicket()
│   ├── getUserBookings()
│   └── cancelBooking()
└── adminService
    ├── User Management
    ├── Flight Management
    └── Booking Management
```

## Page Components

### HomePage
- FlightSearchForm (departure, arrival, dates, travelers)
- Flight Results List
- Flight Selection Modal
- Add to Cart Flow

### CartPage
- Cart Items List
- Item Summary
- Remove Item
- Checkout Modal
- Boarding Group Selection

### BookingsPage
- User Bookings List
- Booking Details
- Cancel Booking
- Status Display

### LoginPage
- Username Input
- Password Input
- Submit Button
- Link to Register

### RegisterPage
- User Information Form
- Password Confirmation
- Submit Button
- Link to Login

### AdminDashboard
- User Management Card
- Flight Management Card
- Booking Management Card
- Quick Stats Display

## Reusable UI Components

### Button
- Variants: primary, secondary, danger, success, accent
- Supports disabled state
- Hover & active animations

### Input
- Label support
- Error message display
- Various types (text, email, password, number, date)

### Card
- Optional title
- Border & shadow styling
- Clickable variant

### Modal
- Overlay backdrop
- Close button
- Configurable width
- Scrollable content

### Table
- Column configuration
- Row click handler
- Empty state
- Responsive

### Loading
- Animated spinner
- Custom message
- Full-screen or inline

### Alert
- Types: error, success, info
- Closeable
- Icon display

### Select
- Label support
- Error message
- Options array

## Routing Structure

```
Public Routes:
├── /login
└── /register

Protected Routes (requires auth):
├── /
├── /cart
└── /bookings

Admin Routes (requires admin/staff role):
└── /admin
    ├── /admin/users (future)
    ├── /admin/flights (future)
    └── /admin/bookings (future)
```

## TypeScript Types

### User Types
- User
- UserDTO
- AdminUserDTO
- UserRole (enum)

### Flight Types
- Flight
- FlightSearchParams

### Booking Types
- Booking
- BookingSummaryDTO
- AdminBookingDTO
- BookingStatus (enum)

### Cart Types
- CartItem
- CartDTO

### Common Types
- ApiResponse<T>
- LoginRequest
- LoginResponse
- RegisterRequest

## Styling System

### TailwindCSS Classes
```css
.btn-brutal - Button with Neo-brutalism style
.card-brutal - Card with borders and shadows
.input-brutal - Input with bold borders
.table-brutal - Table with heavy styling
```

### Custom Colors
```css
bg-primary → black
bg-secondary → white
bg-accent → yellow
bg-danger → red
bg-success → green
bg-info → cyan
```

### Shadow Utilities
```css
shadow-brutal → 4px 4px 0px 0px
shadow-brutal-lg → 8px 8px 0px 0px
shadow-brutal-sm → 2px 2px 0px 0px
```

## Build Output

```
dist/
├── index.html
├── assets/
│   ├── index-[hash].js
│   ├── index-[hash].css
│   └── [other assets]
└── [other files]
```

## Environment Variables

```
VITE_API_BASE_URL - Backend API URL (default: http://localhost:8080)
```

## Browser Support

✅ Chrome 90+
✅ Firefox 88+
✅ Safari 14+
✅ Edge 90+

## Mobile Support

✅ iOS Safari 14+
✅ Chrome Mobile
✅ Samsung Internet
✅ Android WebView

# Flight Booking System - Setup Guide

## Complete Feature List

Based on your backend controllers, this application includes:

### Authentication (UserController)
✅ User Registration
✅ User Login with JWT
✅ Get Current User Profile
✅ Protected Routes

### Flight Search & Booking (FlightController)
✅ Search Flights by:
  - Departure City
  - Arrival City
  - Date Range
  - Number of Travelers
✅ View Flight Details
✅ Filter Available Seats

### Ticket & Cart Management (TicketController)
✅ Add Flights to Cart
✅ Support for Round Trip Bookings
✅ View Cart Items
✅ Remove from Cart
✅ Checkout Process
✅ Booking Confirmation with Boarding Group Selection
✅ View User Bookings
✅ Cancel Bookings
✅ Purchase History

### Admin Features (AdminController)
✅ User Management:
  - View All Users
  - Update User Roles (USER, ADMIN, STAFF)
  - Delete Users

✅ Booking Management:
  - View All Bookings
  - Filter by Status (PENDING, CONFIRMED, CANCELLED, COMPLETED)
  - View Booking Details
  - Update Booking Status
  - Cancel Bookings
  - Delete Bookings

✅ Flight Management:
  - View All Flights
  - Create New Flights
  - Update Flight Details
  - Delete Flights

## Architecture & Best Practices

### ✅ Clean Folder Structure
```
src/
├── components/       # Reusable components
│   ├── ui/          # Base UI components
│   ├── features/    # Feature components
│   └── layout/      # Layout components
├── pages/           # Page components
├── layouts/         # Layout wrappers
├── store/           # Redux store
│   └── slices/      # Redux slices
├── services/        # API services
├── types/           # TypeScript types
└── hooks/           # Custom hooks
```

### ✅ Separation of Concerns
- **Components**: Presentational logic only
- **Services**: API calls and business logic
- **Store**: State management
- **Types**: TypeScript definitions

### ✅ Redux Toolkit
- Slices for each domain (auth, flights, cart, bookings)
- Async thunks for API calls
- Proper error handling
- Loading states

### ✅ Reusable Components
- Button, Input, Card, Modal, Table
- Alert, Loading, Select
- All with Neo-brutalism styling

### ✅ Feature-Based Organization
- FlightSearchForm
- FlightCard
- CartItemCard
- BookingCard

### ✅ Protected Routes
- Authentication required
- Role-based access (Admin/Staff)

### ✅ Neo-Brutalism Design
- Bold borders (2-4px)
- Offset shadows
- High contrast colors
- Accent yellow (#FFC700)
- Responsive design

## Quick Start

1. **Install Dependencies**
```bash
cd my-app
npm install
```

2. **Configure Environment**
Create `.env` file:
```
VITE_API_BASE_URL=http://localhost:8080
```

3. **Start Development Server**
```bash
npm run dev
```

4. **Access Application**
Open http://localhost:5173

## User Flow

### Regular User
1. Register/Login
2. Search for flights
3. Add flights to cart (with optional return flight)
4. Checkout from cart
5. Select boarding group
6. Confirm booking
7. View bookings
8. Cancel bookings if needed

### Admin/Staff
1. Login with admin credentials
2. Access admin dashboard
3. Manage users, flights, and bookings
4. View system statistics

## API Integration

All backend endpoints are integrated:
- `/api/users/*` - Authentication
- `/flights/search` - Flight search
- `/cart`, `/book`, `/bookings` - Ticket management
- `/api/admin/*` - Admin operations

## Responsive Breakpoints

- **Mobile**: < 768px (single column)
- **Tablet**: 768px - 1024px (2 columns)
- **Desktop**: > 1024px (3 columns)

## Component Library

### UI Components
- **Button**: 5 variants (primary, secondary, danger, success, accent)
- **Input**: Form input with label and error
- **Card**: Container with optional title
- **Modal**: Overlay dialog
- **Table**: Data table with sorting
- **Loading**: Loading spinner
- **Alert**: Error/success/info messages
- **Select**: Dropdown with label

### Feature Components
- **FlightSearchForm**: Multi-field search
- **FlightCard**: Flight display with booking
- **CartItemCard**: Cart item with actions
- **BookingCard**: Booking display with status

### Layout Components
- **Navbar**: Navigation with cart counter
- **MainLayout**: Page wrapper with footer
- **ProtectedRoute**: Route guard

## State Management

### Redux Slices
1. **authSlice**: User authentication
2. **flightSlice**: Flight search and selection
3. **cartSlice**: Shopping cart
4. **bookingSlice**: User bookings

Each slice includes:
- State interface
- Async thunks
- Reducers
- Actions

## TypeScript Types

All backend DTOs are typed:
- User, UserDTO, AdminUserDTO
- Flight, FlightSearchParams
- Booking, BookingSummaryDTO, AdminBookingDTO
- Cart, CartDTO
- PurchaseHistoryDTO
- ApiResponse

## Error Handling

- API errors displayed as alerts
- Form validation
- Loading states
- 401 auto-logout
- User-friendly error messages

## Security

- JWT token storage
- Protected routes
- Role-based access
- Auto logout on token expiry
- Secure API calls

## Performance

- Code splitting by route
- Lazy loading
- Optimized Redux selectors
- Memoized components
- Efficient re-renders

## Browser Support

✅ Chrome (latest)
✅ Firefox (latest)
✅ Safari (latest)
✅ Edge (latest)

## Build & Deploy

### Development
```bash
npm run dev
```

### Production Build
```bash
npm run build
```

### Preview Production
```bash
npm run preview
```

### Deploy
Build output is in `dist/` folder. Deploy to:
- Vercel
- Netlify
- AWS S3
- Any static host

## Troubleshooting

### Build Errors
- Run `npm install` again
- Clear node_modules: `rm -rf node_modules && npm install`
- Check Node version: `node -v` (should be 18+)

### API Connection Issues
- Verify backend is running
- Check `.env` file
- Check CORS settings on backend
- Verify API endpoints

### Styling Issues
- Run `npm run dev` to rebuild Tailwind
- Check browser console for errors
- Verify TailwindCSS config

## Next Steps

1. Connect to your backend API
2. Test all features
3. Customize colors/branding
4. Add more admin features
5. Deploy to production

## Support

For issues or questions:
1. Check this documentation
2. Review component examples
3. Check TypeScript types
4. Review Redux state flow

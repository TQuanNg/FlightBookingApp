# ✈️ Flight Booking Application

A modern, full-stack flight booking system built with React, TypeScript, and Spring Boot. This application provides a seamless experience for users to search, book, and manage flight reservations, with a powerful admin panel for flight and booking management.

![React](https://img.shields.io/badge/React-19.1.1-61DAFB?logo=react&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.9-3178C6?logo=typescript&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Backend-6DB33F?logo=springboot&logoColor=white)
![TailwindCSS](https://img.shields.io/badge/TailwindCSS-4.1-38B2AC?logo=tailwindcss&logoColor=white)

## 🌟 Features

### 👤 User Features
- **Flight Search**: Search for one-way or round-trip flights with flexible date options
- **Smart Flight Selection**: Intuitive step-by-step selection process for outbound and return flights
- **Shopping Cart**: Add multiple flights to cart before checkout
- **Booking Management**: View and manage your flight bookings
- **User Profile**: Manage personal information and booking history
- **Authentication**: Secure JWT-based authentication system

### 🔐 Admin Features
- **Dashboard**: Real-time statistics showing total users, bookings, and flights
- **User Management**: Manage user accounts, roles, and permissions
- **Booking Management**: View, update, and cancel bookings with status filtering
- **Flight Management**: Create, edit, and delete flights with validation
- **Statistics**: Track system-wide metrics and performance

### 🎨 Design Features
- **Neobrutalism UI**: Bold, modern design with thick borders and vibrant colors
- **Responsive Design**: Fully responsive across all device sizes
- **Loading States**: Smooth loading indicators and error handling
- **Form Validation**: Client-side and server-side validation

## 🚀 Tech Stack

### Frontend
- **React 19.1** - Latest React with concurrent features
- **TypeScript 5.9** - Type-safe development
- **Redux Toolkit** - State management
- **React Router v7** - Client-side routing
- **Axios** - HTTP client
- **TailwindCSS 4.1** - Utility-first CSS framework
- **Heroicons** - Beautiful icon set
- **date-fns** - Modern date utility library
- **Vite** - Lightning-fast build tool

### Backend
- **Spring Boot** - Java-based backend framework
- **Spring Security** - Authentication and authorization
- **JWT** - Token-based authentication
- **JPA/Hibernate** - ORM for database operations
- **PostgreSQL** - Relational database

## 📋 Prerequisites

- **Node.js** 18+ and npm/yarn
- **Java** 17+ (for backend)
- **PostgreSQL** 12+ (for database)
- **Git**

## 🛠️ Installation & Setup

### Frontend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/TQuanNg/FlightBookingApp.git
   cd FlightBookingApp/react/my-app
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure environment**
   
   Create a `.env` file in the `my-app` directory:
   ```env
   VITE_API_BASE_URL=http://localhost:8080
   ```

4. **Start development server**
   ```bash
   npm run dev
   ```

   The application will be available at `http://localhost:5173`

### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd FlightBookingApp/backend
   ```

2. **Configure database**
   
   Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/flight_booking
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

   Backend will be available at `http://localhost:8080`

## 📁 Project Structure

```
FlightBookingApp/
├── react/
│   └── my-app/
│       ├── src/
│       │   ├── components/        # Reusable UI components
│       │   │   ├── features/      # Feature-specific components
│       │   │   ├── layout/        # Layout components
│       │   │   └── ui/            # Generic UI components
│       │   ├── pages/             # Page components
│       │   │   ├── admin/         # Admin panel pages
│       │   │   └── auth/          # Authentication pages
│       │   ├── services/          # API service layer
│       │   ├── store/             # Redux store and slices
│       │   ├── hooks/             # Custom React hooks
│       │   ├── types/             # TypeScript type definitions
│       │   └── layouts/           # Page layouts
│       ├── public/                # Static assets
│       └── package.json
└── backend/
    └── src/
        ├── controller/            # REST API controllers
        ├── service/               # Business logic
        ├── repository/            # Data access layer
        ├── model/                 # Entity and DTO classes
        └── util/                  # Utility classes
```

## 🎯 Key Functionalities

### Flight Search System
- **One-Way & Round-Trip**: Support for both trip types
- **Date Filtering**: Search flights within specific date ranges
- **Auto-Generation**: Automatically generates flights if none exist for a route
- **Smart Pricing**: Dynamic pricing based on various factors
- **Capacity Management**: Real-time seat availability tracking

### Booking Flow
1. Search for flights (one-way or round-trip)
2. Select outbound flight
3. Select return flight (if round-trip)
4. Review booking details
5. Add to cart
6. Proceed to checkout
7. Receive confirmation

### Admin Operations
- Manage user roles (USER, STAFF, ADMIN)
- Filter bookings by status (PENDING, CONFIRMED, CANCELLED, COMPLETED)
- Create/update flights with validation
- Cancel and delete bookings with seat restoration

## 🔑 API Endpoints

### Public Endpoints
```
GET  /flights/search              # Search flights
POST /api/auth/login             # User login
POST /api/auth/register          # User registration
```

### Protected Endpoints (User)
```
GET    /api/users/{userId}/bookings        # Get user bookings
POST   /api/cart                           # Add to cart
DELETE /api/cart/{cartItemId}              # Remove from cart
POST   /api/bookings/checkout              # Checkout cart
```

### Protected Endpoints (Admin/Staff)
```
GET    /api/admin/stats                    # Get statistics
GET    /api/admin/users                    # Get all users
PUT    /api/admin/users/{userId}/role      # Update user role
GET    /api/admin/bookings                 # Get all bookings
PUT    /api/admin/bookings/{id}/status     # Update booking status
GET    /api/admin/flights                  # Get all flights
POST   /api/admin/flights                  # Create flight
PUT    /api/admin/flights/{id}             # Update flight
DELETE /api/admin/flights/{id}             # Delete flight
```

## 🎨 UI Components

The application uses a custom **Neobrutalism** design system with:

- **Bold Typography**: Large, impactful text
- **Thick Borders**: 4px black borders throughout
- **Vibrant Colors**: Yellow (#FFC700) accents and bold color blocks
- **Sharp Corners**: No border radius for a modern, edgy look
- **High Contrast**: Strong visual hierarchy

## 🧪 Testing

```bash
# Run frontend tests
npm run test

# Run backend tests
./mvnw test
```

## 📦 Build for Production

### Frontend
```bash
npm run build
```

Build output will be in the `dist/` directory.

### Backend
```bash
./mvnw clean package
```

JAR file will be in the `target/` directory.

## 👥 Authors

- **TQuanNg** - *Initial work* - [GitHub](https://github.com/TQuanNg)


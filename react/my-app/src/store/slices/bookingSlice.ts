import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import type { BookingSummaryDTO } from '../../types';
import { bookingService } from '../../services/bookingService';

interface BookingState {
  bookings: BookingSummaryDTO[];
  loading: boolean;
  error: string | null;
}

const initialState: BookingState = {
  bookings: [],
  loading: false,
  error: null,
};

export const fetchUserBookings = createAsyncThunk(
  'bookings/fetchUserBookings',
  async (userId: number, { rejectWithValue }) => {
    try {
      const bookings = await bookingService.getUserBookings(userId);
      return bookings;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch bookings');
    }
  }
);

export const createBooking = createAsyncThunk(
  'bookings/create',
  async (
    { userId, flightId, numberOfTravelers, boardingGroup, returnFlightId, cartId }:
    { userId: number; flightId: number; numberOfTravelers: number; boardingGroup: string; returnFlightId?: number; cartId?: number },
    { rejectWithValue }
  ) => {
    try {
      const booking = await bookingService.bookTicket(
        userId,
        flightId,
        numberOfTravelers,
        boardingGroup,
        returnFlightId,
        cartId
      );
      return booking;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to create booking');
    }
  }
);

export const cancelBooking = createAsyncThunk(
  'bookings/cancel',
  async ({ userId, bookingId }: { userId: number; bookingId: number }, { rejectWithValue }) => {
    try {
      await bookingService.cancelBooking(userId, bookingId);
      const bookings = await bookingService.getUserBookings(userId);
      return bookings;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to cancel booking');
    }
  }
);

const bookingSlice = createSlice({
  name: 'bookings',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch User Bookings
      .addCase(fetchUserBookings.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchUserBookings.fulfilled, (state, action: PayloadAction<BookingSummaryDTO[]>) => {
        state.loading = false;
        state.bookings = action.payload;
      })
      .addCase(fetchUserBookings.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Create Booking
      .addCase(createBooking.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(createBooking.fulfilled, (state, action: PayloadAction<BookingSummaryDTO>) => {
        state.loading = false;
        state.bookings.unshift(action.payload);
      })
      .addCase(createBooking.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Cancel Booking
      .addCase(cancelBooking.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(cancelBooking.fulfilled, (state, action: PayloadAction<BookingSummaryDTO[]>) => {
        state.loading = false;
        state.bookings = action.payload;
      })
      .addCase(cancelBooking.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearError } = bookingSlice.actions;
export default bookingSlice.reducer;

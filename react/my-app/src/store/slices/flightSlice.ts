import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import type { Flight, FlightSearchParams } from '../../types';
import { flightService } from '../../services/flightService';

interface FlightState {
  flights: Flight[];
  selectedFlight: Flight | null;
  loading: boolean;
  error: string | null;
  searchParams: FlightSearchParams | null;
}

const initialState: FlightState = {
  flights: [],
  selectedFlight: null,
  loading: false,
  error: null,
  searchParams: null,
};

export const searchFlights = createAsyncThunk(
  'flights/search',
  async (params: FlightSearchParams, { rejectWithValue }) => {
    try {
      const flights = await flightService.searchFlights(params);
      return { flights, params };
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to search flights');
    }
  }
);

export const getAllFlights = createAsyncThunk(
  'flights/getAll',
  async (_, { rejectWithValue }) => {
    try {
      const flights = await flightService.getAllFlights();
      return flights;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch flights');
    }
  }
);

const flightSlice = createSlice({
  name: 'flights',
  initialState,
  reducers: {
    setSelectedFlight: (state, action: PayloadAction<Flight | null>) => {
      state.selectedFlight = action.payload;
    },
    clearFlights: (state) => {
      state.flights = [];
      state.searchParams = null;
    },
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Search Flights
      .addCase(searchFlights.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(searchFlights.fulfilled, (state, action) => {
        state.loading = false;
        state.flights = action.payload.flights;
        state.searchParams = action.payload.params;
      })
      .addCase(searchFlights.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Get All Flights
      .addCase(getAllFlights.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(getAllFlights.fulfilled, (state, action: PayloadAction<Flight[]>) => {
        state.loading = false;
        state.flights = action.payload;
      })
      .addCase(getAllFlights.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { setSelectedFlight, clearFlights, clearError } = flightSlice.actions;
export default flightSlice.reducer;

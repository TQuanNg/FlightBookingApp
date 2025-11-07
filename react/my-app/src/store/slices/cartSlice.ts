import { createSlice, createAsyncThunk, PayloadAction } from '@reduxjs/toolkit';
import type { CartDTO } from '../../types';
import { cartService } from '../../services/cartService';

interface CartState {
  items: CartDTO[];
  loading: boolean;
  error: string | null;
}

const initialState: CartState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchCartItems = createAsyncThunk(
  'cart/fetchItems',
  async (userId: number, { rejectWithValue }) => {
    try {
      const items = await cartService.getCartItems(userId);
      return items;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to fetch cart items');
    }
  }
);

export const addToCart = createAsyncThunk(
  'cart/addItem',
  async (
    { userId, flightId, numberOfTravelers, returnFlightId }: 
    { userId: number; flightId: number; numberOfTravelers: number; returnFlightId?: number },
    { rejectWithValue }
  ) => {
    try {
      await cartService.addToCart(userId, flightId, numberOfTravelers, returnFlightId);
      const items = await cartService.getCartItems(userId);
      return items;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to add to cart');
    }
  }
);

export const removeFromCart = createAsyncThunk(
  'cart/removeItem',
  async ({ userId, cartItemId }: { userId: number; cartItemId: number }, { rejectWithValue }) => {
    try {
      await cartService.clearCart(userId, cartItemId);
      const items = await cartService.getCartItems(userId);
      return items;
    } catch (error: any) {
      return rejectWithValue(error.response?.data?.message || 'Failed to remove from cart');
    }
  }
);

const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    clearError: (state) => {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Fetch Cart Items
      .addCase(fetchCartItems.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchCartItems.fulfilled, (state, action: PayloadAction<CartDTO[]>) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchCartItems.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Add to Cart
      .addCase(addToCart.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(addToCart.fulfilled, (state, action: PayloadAction<CartDTO[]>) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(addToCart.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      // Remove from Cart
      .addCase(removeFromCart.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(removeFromCart.fulfilled, (state, action: PayloadAction<CartDTO[]>) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(removeFromCart.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { clearError } = cartSlice.actions;
export default cartSlice.reducer;

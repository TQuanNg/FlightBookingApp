import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../hooks/useRedux';
import { fetchCartItems, removeFromCart } from '../store/slices/cartSlice';
import { createBooking } from '../store/slices/bookingSlice';
import { CartItemCard } from '../components/features';
import { Loading, Alert, Modal, Button, Select } from '../components/ui';
import { format } from 'date-fns';
import type { CartDTO } from '../types';

const CartPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { items, loading, error } = useAppSelector(state => state.cart);
  const { user } = useAppSelector(state => state.auth);
  const [selectedItem, setSelectedItem] = useState<CartDTO | null>(null);
  const [boardingGroup, setBoardingGroup] = useState('A');
  const [booking, setBooking] = useState(false);

  useEffect(() => {
    if (user) {
      dispatch(fetchCartItems(user.id));
    }
  }, [dispatch, user]);

  const handleRemove = async (cartId: number) => {
    if (!user) return;
    if (window.confirm('Remove this item from cart?')) {
      await dispatch(removeFromCart({ userId: user.id, cartItemId: cartId }));
    }
  };

  const handleCheckout = (item: CartDTO) => {
    setSelectedItem(item);
  };

  const handleConfirmBooking = async () => {
    if (!user || !selectedItem) return;
    
    setBooking(true);
    try {
      await dispatch(createBooking({
        userId: user.id,
        flightId: selectedItem.flightId,
        numberOfTravelers: selectedItem.numberOfTravelers,
        boardingGroup,
        returnFlightId: selectedItem.returnFlightId,
        cartId: selectedItem.cartItemId
      })).unwrap();

      alert('Booking confirmed!');
      setSelectedItem(null);
      navigate('/bookings');
    } catch (err) {
      alert('Booking failed');
    } finally {
      setBooking(false);
    }
  };

  if (loading) return <Loading message="Loading cart..." />;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-5xl font-bold mb-8 uppercase">Shopping Cart</h1>

      {error && <Alert type="error" message={error} />}

      {items.length === 0 ? (
        <div className="card-brutal p-12 text-center bg-gray-100">
          <p className="text-2xl font-bold mb-4">Your cart is empty</p>
          <Button variant="accent" onClick={() => navigate('/')}>
            Search Flights
          </Button>
        </div>
      ) : (
        <div className="space-y-6">
          {items.map(item => (
            <CartItemCard
              key={item.cartItemId}
              item={item}
              onRemove={handleRemove}
              onCheckout={handleCheckout}
            />
          ))}

          <div className="card-brutal p-6 bg-accent">
            <div className="flex justify-between items-center">
              <p className="text-2xl font-bold">
                Total: ${items.reduce((sum, item) => sum + (item.totalPrice || 0), 0).toFixed(2)}
              </p>
            </div>
          </div>
        </div>
      )}

      <Modal
        isOpen={!!selectedItem}
        onClose={() => setSelectedItem(null)}
        title="Confirm Booking"
        maxWidth="lg"
      >
        {selectedItem && (
          <div className="space-y-6">
            <div>
              <h3 className="text-xl font-bold mb-2">Flight Details</h3>
              <div className="border-4 border-black p-4">
                <p className="font-bold">{selectedItem.flightNumber}</p>
                <p>{selectedItem.departureCity} → {selectedItem.arrivalCity}</p>
                <p className="text-sm">{format(new Date(selectedItem.departureTime), 'MMM dd, HH:mm')} - {format(new Date(selectedItem.arrivalTime), 'MMM dd, HH:mm')}</p>
                {selectedItem.isRoundTrip && selectedItem.returnFlightNumber && (
                  <>
                    <hr className="border-2 border-black my-2" />
                    <p className="font-bold">{selectedItem.returnFlightNumber}</p>
                    <p>{selectedItem.returnDepartureCity} → {selectedItem.returnArrivalCity}</p>
                    <p className="text-sm">{format(new Date(selectedItem.returnDepartureTime!), 'MMM dd, HH:mm')} - {format(new Date(selectedItem.returnArrivalTime!), 'MMM dd, HH:mm')}</p>
                  </>
                )}
              </div>
            </div>

            <Select
              label="Boarding Group"
              value={boardingGroup}
              onChange={(e) => setBoardingGroup(e.target.value)}
              options={[
                { value: 'A', label: 'Group A - Priority' },
                { value: 'B', label: 'Group B - Standard' },
                { value: 'C', label: 'Group C - Economy' }
              ]}
            />

            <div className="border-t-4 border-black pt-4">
              <p className="text-sm font-bold">Travelers: {selectedItem.numberOfTravelers}</p>
              <p className="text-2xl font-bold mt-2">Total: ${selectedItem.totalPrice?.toFixed(2) || '0.00'}</p>
            </div>

            <div className="flex gap-4">
              <Button
                variant="secondary"
                onClick={() => setSelectedItem(null)}
                className="flex-1"
              >
                Cancel
              </Button>
              <Button
                variant="success"
                onClick={handleConfirmBooking}
                disabled={booking}
                className="flex-1"
              >
                {booking ? 'Booking...' : 'Confirm Booking'}
              </Button>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default CartPage;

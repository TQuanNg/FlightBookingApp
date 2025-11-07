import React, { useEffect } from 'react';
import { useAppDispatch, useAppSelector } from '../hooks/useRedux';
import { fetchUserBookings, cancelBooking } from '../store/slices/bookingSlice';
import { BookingCard } from '../components/features';
import { Loading, Alert, Button } from '../components/ui';
import { useNavigate } from 'react-router-dom';

const BookingsPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { bookings, loading, error } = useAppSelector(state => state.bookings);
  const { user } = useAppSelector(state => state.auth);

  useEffect(() => {
    if (user) {
      dispatch(fetchUserBookings(user.id));
    }
  }, [dispatch, user]);

  const handleCancel = async (bookingId: number) => {
    if (!user) return;
    if (window.confirm('Are you sure you want to cancel this booking?')) {
      try {
        await dispatch(cancelBooking({ userId: user.id, bookingId })).unwrap();
        alert('Booking cancelled successfully');
      } catch (err) {
        alert('Failed to cancel booking');
      }
    }
  };

  if (loading) return <Loading message="Loading bookings..." />;

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-5xl font-bold mb-8 uppercase">My Bookings</h1>

      {error && <Alert type="error" message={error} />}

      {bookings.length === 0 ? (
        <div className="card-brutal p-12 text-center bg-gray-100">
          <p className="text-2xl font-bold mb-4">No bookings yet</p>
          <Button variant="accent" onClick={() => navigate('/')}>
            Book a Flight
          </Button>
        </div>
      ) : (
        <div className="space-y-6">
          {bookings.map(booking => (
            <BookingCard
              key={booking.ticketId}
              booking={booking}
              onCancel={handleCancel}
              showActions={true}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default BookingsPage;

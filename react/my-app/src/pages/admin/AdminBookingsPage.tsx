import React, { useEffect, useState } from 'react';
import { adminService } from '../../services/adminService';
import { Card, Button, Loading, Alert, Select, Modal } from '../../components/ui';
import { AdminBookingDTO, BookingStatus } from '../../types';
import { format } from 'date-fns';
import { TrashIcon, PencilIcon } from '@heroicons/react/24/outline';

const AdminBookingsPage: React.FC = () => {
  const [bookings, setBookings] = useState<AdminBookingDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedBooking, setSelectedBooking] = useState<AdminBookingDTO | null>(null);
  const [newStatus, setNewStatus] = useState<BookingStatus>(BookingStatus.PENDING);
  const [showModal, setShowModal] = useState(false);
  const [filterStatus, setFilterStatus] = useState<string>('ALL');

  useEffect(() => {
    fetchBookings();
  }, []);

  const fetchBookings = async () => {
    try {
      setLoading(true);
      const data = await adminService.getAllBookings();
      setBookings(data);
      setError(null);
    } catch (err) {
      setError('Failed to load bookings');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchByStatus = async (status: BookingStatus) => {
    try {
      setLoading(true);
      const data = await adminService.getBookingsByStatus(status);
      setBookings(data);
      setError(null);
    } catch (err) {
      setError('Failed to load bookings');
    } finally {
      setLoading(false);
    }
  };

  const handleFilterChange = (status: string) => {
    setFilterStatus(status);
    if (status === 'ALL') {
      fetchBookings();
    } else {
      fetchByStatus(status as BookingStatus);
    }
  };

  const handleUpdateStatus = async () => {
    if (!selectedBooking) return;
    
    try {
      const response = await adminService.updateBookingStatus(selectedBooking.bookingId, newStatus);
      if (response.success) {
        alert(response.message);
        setShowModal(false);
        if (filterStatus === 'ALL') {
          fetchBookings();
        } else {
          fetchByStatus(filterStatus as BookingStatus);
        }
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to update status');
    }
  };

  const handleCancelBooking = async (bookingId: number) => {
    if (!window.confirm(`Are you sure you want to cancel booking #${bookingId}?`)) return;
    
    try {
      const response = await adminService.cancelBooking(bookingId);
      if (response.success) {
        alert(response.message);
        if (filterStatus === 'ALL') {
          fetchBookings();
        } else {
          fetchByStatus(filterStatus as BookingStatus);
        }
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to cancel booking');
    }
  };

  const handleDeleteBooking = async (bookingId: number) => {
    if (!window.confirm(`Are you sure you want to DELETE booking #${bookingId}? This action cannot be undone!`)) return;
    
    try {
      const response = await adminService.deleteBooking(bookingId);
      if (response.success) {
        alert(response.message);
        if (filterStatus === 'ALL') {
          fetchBookings();
        } else {
          fetchByStatus(filterStatus as BookingStatus);
        }
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to delete booking');
    }
  };

  const openStatusModal = (booking: AdminBookingDTO) => {
    setSelectedBooking(booking);
    setNewStatus(booking.status);
    setShowModal(true);
  };

  const getStatusColor = (status: BookingStatus) => {
    switch (status) {
      case BookingStatus.CONFIRMED: return 'bg-green-600 text-white';
      case BookingStatus.PENDING: return 'bg-yellow-500 text-black';
      case BookingStatus.CANCELLED: return 'bg-red-600 text-white';
      case BookingStatus.COMPLETED: return 'bg-blue-600 text-white';
      default: return 'bg-gray-500 text-white';
    }
  };

  if (loading) return <Loading message="Loading bookings..." />;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div>
          <h1 className="text-5xl font-bold uppercase">Booking Management</h1>
          <p className="text-xl mt-2">Total Bookings: {bookings.length}</p>
        </div>
        <div className="flex gap-2">
          <Select
            label=""
            value={filterStatus}
            onChange={(e) => handleFilterChange(e.target.value)}
            options={[
              { value: 'ALL', label: 'All Bookings' },
              { value: BookingStatus.PENDING, label: 'Pending' },
              { value: BookingStatus.CONFIRMED, label: 'Confirmed' },
              { value: BookingStatus.COMPLETED, label: 'Completed' },
              { value: BookingStatus.CANCELLED, label: 'Cancelled' }
            ]}
          />
          <Button variant="primary" onClick={fetchBookings}>
            Refresh
          </Button>
        </div>
      </div>

      {error && <Alert type="error" message={error} />}

      <div className="space-y-4">
        {bookings.map(booking => (
          <Card key={booking.bookingId}>
            <div className="flex flex-col md:flex-row justify-between gap-4">
              <div className="flex-1">
                <div className="flex items-center gap-3 mb-2">
                  <h3 className="text-2xl font-bold">Booking #{booking.bookingId}</h3>
                  <span className={`px-3 py-1 font-bold text-sm border-2 border-black ${getStatusColor(booking.status)}`}>
                    {booking.status}
                  </span>
                  {booking.isRoundTrip && (
                    <span className="px-3 py-1 font-bold text-sm border-2 border-black bg-accent">
                      ROUND TRIP
                    </span>
                  )}
                </div>

                <div className="mb-4">
                  <p className="text-lg"><span className="font-bold">User:</span> {booking.username} ({booking.userEmail})</p>
                  <p className="text-lg"><span className="font-bold">Flight:</span> {booking.outboundFlightNumber} - {booking.departureCity} → {booking.arrivalCity}</p>
                  <p className="text-sm">{format(new Date(booking.departureTime), 'MMM dd, yyyy HH:mm')} - {format(new Date(booking.arrivalTime), 'MMM dd, yyyy HH:mm')}</p>
                  
                  {booking.isRoundTrip && booking.returnFlightNumber && (
                    <>
                      <hr className="border-2 border-black my-2" />
                      <p className="text-lg"><span className="font-bold">Return:</span> {booking.returnFlightNumber}</p>
                      <p className="text-sm">{format(new Date(booking.returnDepartureTime!), 'MMM dd, yyyy HH:mm')} - {format(new Date(booking.returnArrivalTime!), 'MMM dd, yyyy HH:mm')}</p>
                    </>
                  )}
                </div>

                <div className="grid grid-cols-2 md:grid-cols-4 gap-2 text-sm">
                  <p><span className="font-bold">Travelers:</span> {booking.numberOfTravelers}</p>
                  <p><span className="font-bold">Group:</span> {booking.boardingGroup}</p>
                  <p><span className="font-bold">Price:</span> ${booking.totalPrice}</p>
                  <p><span className="font-bold">Booked:</span> {format(new Date(booking.bookingDate), 'MMM dd')}</p>
                </div>
              </div>

              <div className="flex flex-col gap-2 justify-center">
                <Button
                  variant="primary"
                  onClick={() => openStatusModal(booking)}
                  className="flex items-center gap-2"
                >
                  <PencilIcon className="w-5 h-5" />
                  Update Status
                </Button>
                {booking.status !== BookingStatus.CANCELLED && (
                  <Button
                    variant="secondary"
                    onClick={() => handleCancelBooking(booking.bookingId)}
                  >
                    Cancel
                  </Button>
                )}
                <Button
                  variant="danger"
                  onClick={() => handleDeleteBooking(booking.bookingId)}
                  className="flex items-center gap-2"
                >
                  <TrashIcon className="w-5 h-5" />
                  Delete
                </Button>
              </div>
            </div>
          </Card>
        ))}
      </div>

      <Modal
        isOpen={showModal}
        onClose={() => setShowModal(false)}
        title="Update Booking Status"
      >
        {selectedBooking && (
          <div className="space-y-6">
            <div>
              <p className="text-lg mb-2"><span className="font-bold">Booking ID:</span> #{selectedBooking.bookingId}</p>
              <p className="text-lg mb-2"><span className="font-bold">Current Status:</span> {selectedBooking.status}</p>
            </div>

            <Select
              label="New Status"
              value={newStatus}
              onChange={(e) => setNewStatus(e.target.value as BookingStatus)}
              options={[
                { value: BookingStatus.PENDING, label: 'Pending' },
                { value: BookingStatus.CONFIRMED, label: 'Confirmed' },
                { value: BookingStatus.COMPLETED, label: 'Completed' },
                { value: BookingStatus.CANCELLED, label: 'Cancelled' }
              ]}
            />

            <div className="flex gap-4">
              <Button
                variant="secondary"
                onClick={() => setShowModal(false)}
                className="flex-1"
              >
                Cancel
              </Button>
              <Button
                variant="primary"
                onClick={handleUpdateStatus}
                className="flex-1"
              >
                Update Status
              </Button>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default AdminBookingsPage;

import React from 'react';
import type { BookingSummaryDTO } from '../../types';
import Card from '../ui/Card';
import Button from '../ui/Button';
import { format } from 'date-fns';

interface BookingCardProps {
  booking: BookingSummaryDTO;
  onCancel?: (bookingId: number) => void;
  showActions?: boolean;
}

const BookingCard: React.FC<BookingCardProps> = ({ 
  booking, 
  onCancel,
  showActions = true 
}) => {
  return (
    <Card>
      <div className="flex flex-col md:flex-row justify-between gap-4">
        <div className="flex-1">
          <div className="flex items-center gap-3 mb-4">
            <h3 className="text-2xl font-bold">Ticket #{booking.ticketId}</h3>
            <span className="px-3 py-1 font-bold text-sm border-2 border-black bg-green-600 text-white">
              {booking.isRoundTrip ? 'ROUND TRIP' : 'ONE WAY'}
            </span>
          </div>
          
          <div className="mb-4">
            <h4 className="text-lg font-bold mb-2">Outbound Flight</h4>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="font-bold text-sm">From</p>
                <p>{booking.departurePlace}</p>
                <p className="text-sm">{format(new Date(booking.departureTime), 'MMM dd, yyyy HH:mm')}</p>
              </div>
              <div>
                <p className="font-bold text-sm">To</p>
                <p>{booking.arrivalPlace}</p>
                <p className="text-sm">{format(new Date(booking.arrivalTime), 'MMM dd, yyyy HH:mm')}</p>
              </div>
            </div>
          </div>
          
          {booking.isRoundTrip && booking.returnDeparturePlace && (
            <>
              <hr className="border-2 border-black my-4" />
              <div className="mb-4">
                <h4 className="text-lg font-bold mb-2">Return Flight</h4>
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <p className="font-bold text-sm">From</p>
                    <p>{booking.returnDeparturePlace}</p>
                    <p className="text-sm">{format(new Date(booking.returnDepartureTime!), 'MMM dd, yyyy HH:mm')}</p>
                  </div>
                  <div>
                    <p className="font-bold text-sm">To</p>
                    <p>{booking.returnArrivalPlace}</p>
                    <p className="text-sm">{format(new Date(booking.returnArrivalTime!), 'MMM dd, yyyy HH:mm')}</p>
                  </div>
                </div>
              </div>
            </>
          )}
          
          <div className="mt-4">
            <p><span className="font-bold">Travelers:</span> {booking.numberOfTravelers}</p>
            <p><span className="font-bold">Boarding Group:</span> {booking.boardingGroup}</p>
            <p><span className="font-bold">Purchased:</span> {format(new Date(booking.purchaseDate), 'MMM dd, yyyy HH:mm')}</p>
          </div>
        </div>
        
        <div className="flex flex-col justify-between items-end">
          <div className="text-right">
            <p className="text-3xl font-bold">${booking.price.toFixed(2)}</p>
            <p className="text-sm">Total Price</p>
          </div>
          
          {showActions && onCancel && (
            <Button
              variant="danger"
              onClick={() => onCancel(booking.ticketId)}
            >
              Cancel Booking
            </Button>
          )}
        </div>
      </div>
    </Card>
  );
};

export default BookingCard;

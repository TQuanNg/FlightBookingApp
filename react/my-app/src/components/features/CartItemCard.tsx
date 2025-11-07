import React from 'react';
import type { CartDTO } from '../../types';
import Card from '../ui/Card';
import Button from '../ui/Button';
import { TrashIcon } from '@heroicons/react/24/outline';
import { format } from 'date-fns';

interface CartItemCardProps {
  item: CartDTO;
  onRemove: (cartId: number) => void;
  onCheckout: (item: CartDTO) => void;
}

const CartItemCard: React.FC<CartItemCardProps> = ({ item, onRemove, onCheckout }) => {
  return (
    <Card>
      <div className="flex flex-col md:flex-row justify-between gap-4">
        <div className="flex-1">
          <h3 className="text-xl font-bold mb-2">
            {item.flightNumber}
          </h3>
          
          <div className="grid grid-cols-2 gap-4 mb-4">
            <div>
              <p className="font-bold text-sm">Departure</p>
              <p>{item.departureCity}</p>
              <p className="text-sm">{format(new Date(item.departureTime), 'MMM dd, HH:mm')}</p>
            </div>
            <div>
              <p className="font-bold text-sm">Arrival</p>
              <p>{item.arrivalCity}</p>
              <p className="text-sm">{format(new Date(item.arrivalTime), 'MMM dd, HH:mm')}</p>
            </div>
          </div>
          
          {item.isRoundTrip && item.returnFlightNumber && (
            <>
              <hr className="border-2 border-black my-4" />
              <h4 className="text-lg font-bold mb-2">Return Flight</h4>
              <h3 className="text-xl font-bold mb-2">
                {item.returnFlightNumber}
              </h3>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="font-bold text-sm">Departure</p>
                  <p>{item.returnDepartureCity}</p>
                  <p className="text-sm">{format(new Date(item.returnDepartureTime!), 'MMM dd, HH:mm')}</p>
                </div>
                <div>
                  <p className="font-bold text-sm">Arrival</p>
                  <p>{item.returnArrivalCity}</p>
                  <p className="text-sm">{format(new Date(item.returnArrivalTime!), 'MMM dd, HH:mm')}</p>
                </div>
              </div>
            </>
          )}
          
          <div className="mt-4">
            <p className="font-bold">Travelers: {item.numberOfTravelers}</p>
            <p className="text-sm">{item.isRoundTrip ? 'Round Trip' : 'One Way'}</p>
          </div>
        </div>
        
        <div className="flex flex-col justify-between items-end">
          <div className="text-right">
            <p className="text-3xl font-bold">${item.totalPrice?.toFixed(2) || '0.00'}</p>
            <p className="text-sm">Total Price</p>
          </div>
          
          <div className="flex gap-2">
            <Button
              variant="danger"
              onClick={() => onRemove(item.cartItemId)}
              className="flex items-center gap-2"
            >
              <TrashIcon className="w-5 h-5" />
              Remove
            </Button>
            <Button
              variant="success"
              onClick={() => onCheckout(item)}
            >
              Checkout
            </Button>
          </div>
        </div>
      </div>
    </Card>
  );
};

export default CartItemCard;

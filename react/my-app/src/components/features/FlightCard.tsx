import React from 'react';
import type { Flight } from '../../types';
import Card from '../ui/Card';
import Button from '../ui/Button';
import { ClockIcon, MapPinIcon, CurrencyDollarIcon, UsersIcon } from '@heroicons/react/24/outline';
import { format } from 'date-fns';

interface FlightCardProps {
  flight: Flight;
  onSelect: (flight: Flight) => void;
  buttonText?: string;
}

const FlightCard: React.FC<FlightCardProps> = ({ 
  flight, 
  onSelect,
  buttonText = 'Select Flight' 
}) => {
  return (
    <Card className="hover:bg-gray-50">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center gap-4">
        <div className="flex-1">
          <div className="flex items-center gap-2 mb-2">
            <span className="text-2xl font-bold">{flight.flightNumber}</span>
            <span className="px-3 py-1 bg-black text-white font-bold text-sm border-2 border-black">
              {flight.status}
            </span>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
            <div className="flex items-center gap-2">
              <MapPinIcon className="w-5 h-5" />
              <div>
                <p className="font-bold text-sm">From</p>
                <p className="text-lg">{flight.departureCity}</p>
                <p className="text-sm">{format(new Date(flight.departureTime), 'MMM dd, yyyy HH:mm')}</p>
              </div>
            </div>
            
            <div className="flex items-center gap-2">
              <MapPinIcon className="w-5 h-5" />
              <div>
                <p className="font-bold text-sm">To</p>
                <p className="text-lg">{flight.arrivalCity}</p>
                <p className="text-sm">{format(new Date(flight.arrivalTime), 'MMM dd, yyyy HH:mm')}</p>
              </div>
            </div>
          </div>
          
          <div className="flex items-center gap-6 mt-4">
            <div className="flex items-center gap-2">
              <ClockIcon className="w-5 h-5" />
              <span className="font-bold">{flight.duration} min</span>
            </div>
            <div className="flex items-center gap-2">
              <UsersIcon className="w-5 h-5" />
              <span className="font-bold">{flight.availableSeats} seats</span>
            </div>
          </div>
        </div>
        
        <div className="flex flex-col items-end gap-4">
          <div className="text-right">
            <div className="flex items-center gap-2 justify-end">
              <CurrencyDollarIcon className="w-6 h-6" />
              <span className="text-3xl font-bold">${flight.price}</span>
            </div>
            <p className="text-sm font-medium">per person</p>
          </div>
          
          <Button
            variant="accent"
            onClick={() => onSelect(flight)}
            disabled={flight.availableSeats === 0}
          >
            {flight.availableSeats === 0 ? 'Sold Out' : buttonText}
          </Button>
        </div>
      </div>
    </Card>
  );
};

export default FlightCard;

import React, { useState } from 'react';
import type { FlightSearchParams } from '../../types';
import { TripType } from '../../types';
import Input from '../ui/Input';
import Button from '../ui/Button';
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid';

interface FlightSearchFormProps {
  onSearch: (params: FlightSearchParams) => void;
  loading?: boolean;
}

const FlightSearchForm: React.FC<FlightSearchFormProps> = ({ onSearch, loading }) => {
  const [tripType, setTripType] = useState<'ROUND_TRIP' | 'ONE_WAY'>('ROUND_TRIP');
  const [formData, setFormData] = useState({
    departureCity: '',
    arrivalCity: '',
    startTime: '',
    endTime: '',
    numTravelers: 1,
  });

  // Get today's date in YYYY-MM-DD format
  const today = new Date().toISOString().split('T')[0];

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Convert date to datetime format (set time to start/end of day)
    const startDateTime = formData.startTime ? `${formData.startTime}T00:00:00` : '';
    
    // For ONE_WAY, don't send endTime at all (backend will handle it)
    // For ROUND_TRIP, send the endTime
    const searchParams: FlightSearchParams = {
      departureCity: formData.departureCity,
      arrivalCity: formData.arrivalCity,
      startTime: startDateTime,
      numTravelers: formData.numTravelers,
      tripType: tripType
    };

    // Only add endTime if it exists and trip is ROUND_TRIP
    if (tripType === 'ROUND_TRIP' && formData.endTime) {
      searchParams.endTime = `${formData.endTime}T23:59:59`;
    }
    
    onSearch(searchParams);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    
    // Capitalize first letter of city names
    if (name === 'departureCity' || name === 'arrivalCity') {
      const capitalizedValue = value.charAt(0).toUpperCase() + value.slice(1);
      setFormData(prev => ({
        ...prev,
        [name]: capitalizedValue
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: name === 'numTravelers' ? parseInt(value) : value
      }));
    }
  };

  const handleTripTypeChange = (newTripType: 'ROUND_TRIP' | 'ONE_WAY') => {
    setTripType(newTripType);
    // Clear return date when switching to one-way
    if (newTripType === 'ONE_WAY') {
      setFormData(prev => ({
        ...prev,
        endTime: ''
      }));
    }
  };

  return (
    <form onSubmit={handleSubmit} className="card-brutal p-6" style={{ backgroundColor: '#FFC700' }}>
      <h2 className="text-3xl font-bold mb-6 uppercase">Search Flights</h2>
      
      {/* Trip Type Selection */}
      <div className="mb-6 flex gap-4">
        <label className="flex items-center gap-2 cursor-pointer">
          <input
            type="radio"
            name="tripType"
            value="ROUND_TRIP"
            checked={tripType === 'ROUND_TRIP'}
            onChange={(e) => handleTripTypeChange(e.target.value as 'ROUND_TRIP' | 'ONE_WAY')}
            className="w-5 h-5"
          />
          <span className="font-bold text-lg">Round Trip</span>
        </label>
        <label className="flex items-center gap-2 cursor-pointer">
          <input
            type="radio"
            name="tripType"
            value="ONE_WAY"
            checked={tripType === 'ONE_WAY'}
            onChange={(e) => handleTripTypeChange(e.target.value as 'ROUND_TRIP' | 'ONE_WAY')}
            className="w-5 h-5"
          />
          <span className="font-bold text-lg">One Way</span>
        </label>
      </div>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <Input
          label="From"
          name="departureCity"
          value={formData.departureCity}
          onChange={handleChange}
          placeholder="Departure City"
          required
        />
        
        <Input
          label="To"
          name="arrivalCity"
          value={formData.arrivalCity}
          onChange={handleChange}
          placeholder="Arrival City"
          required
        />
        
        <Input
          label="Departure Date"
          name="startTime"
          type="date"
          value={formData.startTime}
          onChange={handleChange}
          min={today}
          required
        />
        
        {tripType === 'ROUND_TRIP' && (
          <Input
            label="Return Date"
            name="endTime"
            type="date"
            value={formData.endTime}
            onChange={handleChange}
            min={formData.startTime || today}
            required
          />
        )}
        
        <Input
          label="Travelers"
          name="numTravelers"
          type="number"
          min="1"
          max="9"
          value={formData.numTravelers}
          onChange={handleChange}
          required
        />
      </div>
      
      <Button
        type="submit"
        variant="primary"
        className="w-full mt-6 flex items-center justify-center gap-2"
        disabled={loading}
      >
        <MagnifyingGlassIcon className="w-5 h-5" />
        {loading ? 'Searching...' : 'Search Flights'}
      </Button>
    </form>
  );
};

export default FlightSearchForm;

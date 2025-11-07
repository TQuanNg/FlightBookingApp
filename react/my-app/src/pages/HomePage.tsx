import React, { useState } from 'react';
import { useAppDispatch, useAppSelector } from '../hooks/useRedux';
import { searchFlights } from '../store/slices/flightSlice';
import { addToCart } from '../store/slices/cartSlice';
import { FlightSearchForm, FlightCard } from '../components/features';
import { Loading, Alert, Modal, Button } from '../components/ui';
import type { Flight, FlightSearchParams } from '../types';

const HomePage: React.FC = () => {
  const dispatch = useAppDispatch();
  const { flights, loading, error, searchParams } = useAppSelector(state => state.flights);
  const { user, isAuthenticated } = useAppSelector(state => state.auth);
  const [selectedFlight, setSelected] = useState<Flight | null>(null);
  const [returnFlight, setReturnFlight] = useState<Flight | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [travelers, setTravelers] = useState(1);
  const [addingToCart, setAddingToCart] = useState(false);

  const handleSearch = (params: FlightSearchParams) => {
    // Store the number of travelers from the search
    setTravelers(params.numTravelers);
    dispatch(searchFlights(params));
  };

  const handleSelectFlight = (flight: Flight) => {
    console.log('HomePage: Selected flight:', flight);
    if (!selectedFlight) {
      setSelected(flight);
      // Ask if they want a return flight
      const wantReturn = window.confirm('Do you want to add a return flight?');
      if (!wantReturn) {
        setShowModal(true);
      }
    } else {
      setReturnFlight(flight);
      setShowModal(true);
    }
  };

  const handleAddToCart = async () => {
    console.log('HomePage: handleAddToCart called');
    console.log('HomePage: isAuthenticated:', isAuthenticated);
    console.log('HomePage: user:', user);
    
    if (!isAuthenticated || !user) {
      alert('Please login to book flights');
      return;
    }
    
    if (!selectedFlight || travelers < 1) {
      alert('Invalid booking details');
      return;
    }
    
    console.log('HomePage: Adding to cart with:', {
      userId: user.id,
      flightId: selectedFlight.flightId || selectedFlight.id,
      numberOfTravelers: travelers,
      returnFlightId: returnFlight?.flightId || returnFlight?.id,
    });
    
    setAddingToCart(true);
    try {
      await dispatch(addToCart({
        userId: user.id,
        flightId: selectedFlight.flightId || selectedFlight.id!,
        numberOfTravelers: travelers,
        returnFlightId: returnFlight?.flightId || returnFlight?.id,
      })).unwrap();
      
      alert('Flight(s) added to cart successfully!');
      setShowModal(false);
      setSelected(null);
      setReturnFlight(null);
    } catch (err) {
      console.error('Failed to add to cart:', err);
      alert('Failed to add to cart: ' + (err instanceof Error ? err.message : 'Unknown error'));
    } finally {
      setAddingToCart(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-5xl font-bold mb-4 uppercase">Book Your Flight</h1>
        <p className="text-xl font-medium">Find and book flights to your dream destination</p>
      </div>

      <div className="mb-8">
        <FlightSearchForm onSearch={handleSearch} loading={loading} />
      </div>

      {error && <Alert type="error" message={error} />}

      {loading && <Loading message="Searching for flights..." />}

      {!loading && flights.length === 0 && searchParams && (
        <div className="card-brutal p-12 text-center bg-gray-100">
          <p className="text-2xl font-bold">No flights found</p>
          <p className="text-lg mt-2">Try adjusting your search criteria</p>
        </div>
      )}

      {!loading && flights.length > 0 && (
        <div>
          <h2 className="text-3xl font-bold mb-6 uppercase">
            Available Flights ({flights.length})
          </h2>
          <div className="space-y-4">
            {flights.map(flight => (
              <FlightCard
                key={flight.flightId || flight.id}
                flight={flight}
                onSelect={handleSelectFlight}
                buttonText={selectedFlight && !returnFlight ? 'Select as Return' : 'Add to Cart'}
              />
            ))}
          </div>
        </div>
      )}

      <Modal
        isOpen={showModal}
        onClose={() => {
          setShowModal(false);
          setSelected(null);
          setReturnFlight(null);
        }}
        title="Confirm Booking Details"
        maxWidth="lg"
      >
        <div className="space-y-6">
          {selectedFlight && (
            <div>
              <h3 className="text-xl font-bold mb-2">Outbound Flight</h3>
              <div className="border-4 border-black p-4">
                <p className="font-bold">{selectedFlight.flightNumber}</p>
                <p>{selectedFlight.departureCity} → {selectedFlight.arrivalCity}</p>
                <p className="text-lg font-bold mt-2">${selectedFlight.price} per person</p>
              </div>
            </div>
          )}

          {returnFlight && (
            <div>
              <h3 className="text-xl font-bold mb-2">Return Flight</h3>
              <div className="border-4 border-black p-4">
                <p className="font-bold">{returnFlight.flightNumber}</p>
                <p>{returnFlight.departureCity} → {returnFlight.arrivalCity}</p>
                <p className="text-lg font-bold mt-2">${returnFlight.price} per person</p>
              </div>
            </div>
          )}

          <div>
            <label className="block font-bold mb-2">Number of Travelers</label>
            <p className="text-sm text-gray-600 mb-2">Travelers from search: {travelers}</p>
            <input
              type="number"
              min="1"
              max="9"
              value={travelers}
              onChange={(e) => setTravelers(parseInt(e.target.value) || 1)}
              className="input-brutal w-32"
            />
            <p className="text-xs text-gray-500 mt-1">You can adjust the number if needed</p>
          </div>

          <div className="border-t-4 border-black pt-4">
            <p className="text-2xl font-bold">
              Total: ${((selectedFlight?.price || 0) + (returnFlight?.price || 0)) * travelers}
            </p>
          </div>

          <div className="flex gap-4">
            <Button
              variant="secondary"
              onClick={() => {
                setShowModal(false);
                setSelected(null);
                setReturnFlight(null);
              }}
              className="flex-1"
            >
              Cancel
            </Button>
            <Button
              variant="accent"
              onClick={handleAddToCart}
              disabled={addingToCart}
              className="flex-1"
            >
              {addingToCart ? 'Adding...' : 'Add to Cart'}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default HomePage;

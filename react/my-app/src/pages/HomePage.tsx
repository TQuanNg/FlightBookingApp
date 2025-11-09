import React, { useState } from 'react';
import { useAppDispatch, useAppSelector } from '../hooks/useRedux';
import { searchFlights } from '../store/slices/flightSlice';
import { addToCart } from '../store/slices/cartSlice';
import { FlightSearchForm, FlightCard } from '../components/features';
import { Loading, Alert, Modal, Button } from '../components/ui';
import type { Flight, FlightSearchParams } from '../types';

const HomePage: React.FC = () => {
  const dispatch = useAppDispatch();
  const { outboundFlights, returnFlights, tripType, loading, error, searchParams } = useAppSelector(state => state.flights);
  const { user, isAuthenticated } = useAppSelector(state => state.auth);
  const [selectedFlight, setSelected] = useState<Flight | null>(null);
  const [selectedReturnFlight, setSelectedReturnFlight] = useState<Flight | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [travelers, setTravelers] = useState(1);
  const [addingToCart, setAddingToCart] = useState(false);
  const [selectingReturn, setSelectingReturn] = useState(false);

  const handleSearch = (params: FlightSearchParams) => {
    // Store the number of travelers from the search
    setTravelers(params.numTravelers);
    // Reset selections when doing a new search
    setSelected(null);
    setSelectedReturnFlight(null);
    setSelectingReturn(false);
    dispatch(searchFlights(params));
  };

  const handleSelectFlight = (flight: Flight) => {
    console.log('HomePage: Selected flight:', flight);
    
    if (!selectingReturn) {
      // Selecting outbound flight
      setSelected(flight);
      
      // If it's a round trip search, move to return flight selection
      if (tripType === 'ROUND_TRIP' && returnFlights.length > 0) {
        setSelectingReturn(true);
      } else {
        // For one-way trips, show modal immediately
        setShowModal(true);
      }
    } else {
      // Selecting return flight
      setSelectedReturnFlight(flight);
      setShowModal(true);
    }
  };

  const handleBackToOutbound = () => {
    setSelectingReturn(false);
    setSelected(null);
    setSelectedReturnFlight(null);
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
      returnFlightId: selectedReturnFlight?.flightId || selectedReturnFlight?.id,
    });
    
    setAddingToCart(true);
    try {
      await dispatch(addToCart({
        userId: user.id,
        flightId: selectedFlight.flightId || selectedFlight.id!,
        numberOfTravelers: travelers,
        returnFlightId: selectedReturnFlight?.flightId || selectedReturnFlight?.id,
      })).unwrap();
      
      alert('Flight(s) added to cart successfully!');
      setShowModal(false);
      setSelected(null);
      setSelectedReturnFlight(null);
      setSelectingReturn(false);
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

      {!loading && outboundFlights.length === 0 && searchParams && (
        <div className="card-brutal p-12 text-center bg-gray-100">
          <p className="text-2xl font-bold">No flights found</p>
          <p className="text-lg mt-2">Try adjusting your search criteria</p>
        </div>
      )}

      {!loading && outboundFlights.length > 0 && (
        <div>
          {/* Show either Outbound or Return flights, not both */}
          {!selectingReturn ? (
            // Outbound Flights Section
            <div>
              <h2 className="text-3xl font-bold mb-6 uppercase">
                Select Your Outbound Flight ({outboundFlights.length})
              </h2>
              <div className="space-y-4">
                {outboundFlights.map(flight => (
                  <FlightCard
                    key={flight.flightId || flight.id}
                    flight={flight}
                    onSelect={handleSelectFlight}
                    buttonText="Select Flight"
                  />
                ))}
              </div>
              
              {/* Helper message for round trips */}
              {tripType === 'ROUND_TRIP' && returnFlights.length > 0 && (
                <div className="card-brutal p-6 mt-4 bg-blue-100">
                  <p className="text-lg font-bold">💡 Select an outbound flight to continue to return flights</p>
                </div>
              )}
            </div>
          ) : (
            // Return Flights Section (replaces outbound display)
            <div>
              {/* Show selected outbound flight info */}
              {selectedFlight && (
                <div className="card-brutal p-6 mb-6 bg-green-100">
                  <div className="flex justify-between items-start">
                    <div>
                      <h3 className="text-xl font-bold mb-2">✓ Selected Outbound Flight</h3>
                      <p className="text-lg font-bold">{selectedFlight.flightNumber}</p>
                      <p>{selectedFlight.departureCity} → {selectedFlight.arrivalCity}</p>
                      <p className="text-lg font-bold mt-1">${selectedFlight.price} per person</p>
                    </div>
                    <Button variant="secondary" onClick={handleBackToOutbound} className="text-sm">
                      Change Flight
                    </Button>
                  </div>
                </div>
              )}
              
              <h2 className="text-3xl font-bold mb-6 uppercase">
                Select Your Return Flight ({returnFlights.length})
              </h2>
              <div className="space-y-4">
                {returnFlights.map(flight => (
                  <FlightCard
                    key={flight.flightId || flight.id}
                    flight={flight}
                    onSelect={handleSelectFlight}
                    buttonText="Select Return Flight"
                  />
                ))}
              </div>
            </div>
          )}
        </div>
      )}

      <Modal
        isOpen={showModal}
        onClose={() => {
          setShowModal(false);
          setSelected(null);
          setSelectedReturnFlight(null);
          setSelectingReturn(false);
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

          {selectedReturnFlight && (
            <div>
              <h3 className="text-xl font-bold mb-2">Return Flight</h3>
              <div className="border-4 border-black p-4">
                <p className="font-bold">{selectedReturnFlight.flightNumber}</p>
                <p>{selectedReturnFlight.departureCity} → {selectedReturnFlight.arrivalCity}</p>
                <p className="text-lg font-bold mt-2">${selectedReturnFlight.price} per person</p>
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
              Total: ${((selectedFlight?.price || 0) + (selectedReturnFlight?.price || 0)) * travelers}
            </p>
          </div>

          <div className="flex gap-4">
            <Button
              variant="secondary"
              onClick={() => {
                setShowModal(false);
                setSelected(null);
                setSelectedReturnFlight(null);
                setSelectingReturn(false);
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

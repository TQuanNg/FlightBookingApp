import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { adminService } from '../../services/adminService';
import { Card, Button, Loading, Alert, Input, Select, Modal } from '../../components/ui';
import { Flight, FlightStatus } from '../../types';
import { format } from 'date-fns';
import { PlusIcon, PencilIcon, TrashIcon, ArrowLeftIcon } from '@heroicons/react/24/outline';

const AdminFlightsPage: React.FC = () => {
  const navigate = useNavigate();
  const [flights, setFlights] = useState<Flight[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [currentFlight, setCurrentFlight] = useState<Flight | null>(null);
  
  const emptyFlight: Partial<Flight> = {
    flightNumber: '',
    departureCity: '',
    arrivalCity: '',
    departureTime: '',
    arrivalTime: '',
    availableSeats: 0,
    price: 0
  };

  const [formData, setFormData] = useState<Partial<Flight>>(emptyFlight);

  useEffect(() => {
    fetchFlights();
  }, []);

  const fetchFlights = async () => {
    try {
      setLoading(true);
      const data = await adminService.getAllFlights();
      setFlights(data);
      setError(null);
    } catch (err) {
      setError('Failed to load flights');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'availableSeats' || name === 'price' ? Number(value) : value
    }));
  };

  const handleSubmit = async () => {
    if (!formData.flightNumber || !formData.departureCity || !formData.arrivalCity || 
        !formData.departureTime || !formData.arrivalTime || !formData.availableSeats || !formData.price) {
      alert('Please fill in all required fields');
      return;
    }

    // Validate arrival time is after departure time
    if (formData.departureTime && formData.arrivalTime) {
      const departure = new Date(formData.departureTime);
      const arrival = new Date(formData.arrivalTime);
      if (arrival <= departure) {
        alert('Arrival time must be after departure time');
        return;
      }
    }

    try {
      if (editMode && currentFlight) {
        const response = await adminService.updateFlight(currentFlight.flightId || currentFlight.id!, formData);
        if (response.success) {
          alert(response.message);
        } else {
          alert(response.message);
        }
      } else {
        const response = await adminService.createFlight(formData as Flight);
        if (response.success) {
          alert(response.message);
        } else {
          alert(response.message);
        }
      }
      setShowModal(false);
      setFormData(emptyFlight);
      fetchFlights();
    } catch (err) {
      alert(`Failed to ${editMode ? 'update' : 'create'} flight`);
    }
  };

  const handleDelete = async (flightId: number) => {
    if (!window.confirm(`Are you sure you want to delete flight #${flightId}? This action cannot be undone!`)) return;
    
    try {
      const response = await adminService.deleteFlight(flightId);
      if (response.success) {
        alert(response.message);
        fetchFlights();
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to delete flight');
    }
  };

  const openCreateModal = () => {
    setEditMode(false);
    setCurrentFlight(null);
    setFormData(emptyFlight);
    setShowModal(true);
  };

  const openEditModal = (flight: Flight) => {
    setEditMode(true);
    setCurrentFlight(flight);
    setFormData({
      flightNumber: flight.flightNumber,
      departureCity: flight.departureCity,
      arrivalCity: flight.arrivalCity,
      departureTime: flight.departureTime,
      arrivalTime: flight.arrivalTime,
      availableSeats: flight.availableSeats,
      price: flight.price
    });
    setShowModal(true);
  };

  const getStatusColor = (status: string | FlightStatus) => {
    switch (status) {
      case FlightStatus.ON_TIME: return 'bg-green-600 text-white';
      case FlightStatus.DELAYED: return 'bg-yellow-500 text-black';
      case FlightStatus.CANCELLED: return 'bg-red-600 text-white';
      case FlightStatus.BOARDING: return 'bg-blue-600 text-white';
      default: return 'bg-gray-500 text-white';
    }
  };

  if (loading) return <Loading message="Loading flights..." />;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-6">
        <Button 
          variant="secondary" 
          onClick={() => navigate('/admin')}
          className="flex items-center gap-2"
        >
          <ArrowLeftIcon className="w-5 h-5" />
          Back to Dashboard
        </Button>
      </div>

      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div>
          <h1 className="text-5xl font-bold uppercase">Flight Management</h1>
          <p className="text-xl mt-2">Total Flights: {flights.length}</p>
        </div>
        <div className="flex gap-2">
          <Button variant="primary" onClick={openCreateModal} className="flex items-center gap-2">
            <PlusIcon className="w-5 h-5" />
            Add Flight
          </Button>
          <Button variant="secondary" onClick={fetchFlights}>
            Refresh
          </Button>
        </div>
      </div>

      {error && <Alert type="error" message={error} />}

      <div className="space-y-4">
        {flights.map(flight => (
          <Card key={flight.flightId || flight.id}>
            <div className="flex flex-col md:flex-row justify-between gap-4">
              <div className="flex-1">
                <div className="flex items-center gap-3 mb-2">
                  <h3 className="text-2xl font-bold">{flight.flightNumber}</h3>
                  {flight.status && (
                    <span className={`px-3 py-1 font-bold text-sm border-2 border-black ${getStatusColor(flight.status)}`}>
                      {flight.status}
                    </span>
                  )}
                </div>

                <div className="mb-4">
                  <p className="text-xl font-bold">{flight.departureCity} → {flight.arrivalCity}</p>
                  <p className="text-sm">
                    Departure: {format(new Date(flight.departureTime), 'MMM dd, yyyy HH:mm')}
                  </p>
                  <p className="text-sm">
                    Arrival: {format(new Date(flight.arrivalTime), 'MMM dd, yyyy HH:mm')}
                  </p>
                </div>

                <div className="grid grid-cols-2 md:grid-cols-3 gap-2 text-sm">
                  <p><span className="font-bold">Available Seats:</span> {flight.availableSeats}</p>
                  <p><span className="font-bold">Price:</span> ${flight.price}</p>
                  <p><span className="font-bold">ID:</span> {flight.flightId || flight.id}</p>
                </div>
              </div>

              <div className="flex flex-col gap-2 justify-center">
                <Button
                  variant="primary"
                  onClick={() => openEditModal(flight)}
                  className="flex items-center justify-center gap-2"
                >
                  <PencilIcon className="w-5 h-5" />
                  Edit
                </Button>
                <Button
                  variant="danger"
                  onClick={() => handleDelete(flight.flightId || flight.id!)}
                  className="flex items-center justify-center gap-2"
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
        title={editMode ? 'Edit Flight' : 'Create New Flight'}
      >
        <div className="space-y-4">
          <Input
            label="Flight Number"
            name="flightNumber"
            value={formData.flightNumber || ''}
            onChange={handleInputChange}
            placeholder="e.g. FL123"
            required
          />

          <div className="grid grid-cols-2 gap-4">
            <Input
              label="Departure City"
              name="departureCity"
              value={formData.departureCity || ''}
              onChange={handleInputChange}
              placeholder="e.g. New York"
              required
            />
            <Input
              label="Arrival City"
              name="arrivalCity"
              value={formData.arrivalCity || ''}
              onChange={handleInputChange}
              placeholder="e.g. Los Angeles"
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <Input
              label="Departure Time"
              name="departureTime"
              type="datetime-local"
              value={formData.departureTime ? format(new Date(formData.departureTime), "yyyy-MM-dd'T'HH:mm") : ''}
              onChange={handleInputChange}
              required
            />
            <Input
              label="Arrival Time"
              name="arrivalTime"
              type="datetime-local"
              value={formData.arrivalTime ? format(new Date(formData.arrivalTime), "yyyy-MM-dd'T'HH:mm") : ''}
              onChange={handleInputChange}
              min={formData.departureTime ? format(new Date(formData.departureTime), "yyyy-MM-dd'T'HH:mm") : undefined}
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <Input
              label="Available Seats"
              name="availableSeats"
              type="number"
              value={formData.availableSeats || ''}
              onChange={handleInputChange}
              min={0}
              required
            />
            <Input
              label="Price ($)"
              name="price"
              type="number"
              value={formData.price || ''}
              onChange={handleInputChange}
              min={0}
              step={0.01}
              required
            />
          </div>

          <div className="flex gap-4 pt-4">
            <Button
              variant="secondary"
              onClick={() => setShowModal(false)}
              className="flex-1"
            >
              Cancel
            </Button>
            <Button
              variant="primary"
              onClick={handleSubmit}
              className="flex-1"
            >
              {editMode ? 'Update Flight' : 'Create Flight'}
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default AdminFlightsPage;

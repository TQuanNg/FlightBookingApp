import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Card } from '../../components/ui';
import { 
  UsersIcon, 
  TicketIcon, 
  PaperAirplaneIcon 
} from '@heroicons/react/24/outline';

const AdminDashboard: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-5xl font-bold mb-8 uppercase">Admin Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card 
          className="hover:bg-accent transition-colors cursor-pointer"
          onClick={() => navigate('/admin/users')}
        >
          <div className="flex items-center gap-4 mb-4">
            <UsersIcon className="w-12 h-12" />
            <h2 className="text-2xl font-bold">User Management</h2>
          </div>
          <p className="font-medium">Manage users, roles, and permissions</p>
          <Button variant="primary" className="mt-4 w-full">
            Manage Users
          </Button>
        </Card>

        <Card 
          className="hover:bg-accent transition-colors cursor-pointer"
          onClick={() => navigate('/admin/bookings')}
        >
          <div className="flex items-center gap-4 mb-4">
            <TicketIcon className="w-12 h-12" />
            <h2 className="text-2xl font-bold">Booking Management</h2>
          </div>
          <p className="font-medium">View and manage all bookings</p>
          <Button variant="primary" className="mt-4 w-full">
            Manage Bookings
          </Button>
        </Card>

        <Card 
          className="hover:bg-accent transition-colors cursor-pointer"
          onClick={() => navigate('/admin/flights')}
        >
          <div className="flex items-center gap-4 mb-4">
            <PaperAirplaneIcon className="w-12 h-12" />
            <h2 className="text-2xl font-bold">Flight Management</h2>
          </div>
          <p className="font-medium">Add, edit, and delete flights</p>
          <Button variant="primary" className="mt-4 w-full">
            Manage Flights
          </Button>
        </Card>
      </div>

      <div className="mt-12 card-brutal p-8 bg-accent">
        <h2 className="text-3xl font-bold mb-4">Quick Stats</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="border-4 border-black p-6 bg-white">
            <p className="text-sm font-bold">TOTAL BOOKINGS</p>
            <p className="text-4xl font-bold mt-2">--</p>
          </div>
          <div className="border-4 border-black p-6 bg-white">
            <p className="text-sm font-bold">ACTIVE FLIGHTS</p>
            <p className="text-4xl font-bold mt-2">--</p>
          </div>
          <div className="border-4 border-black p-6 bg-white">
            <p className="text-sm font-bold">REGISTERED USERS</p>
            <p className="text-4xl font-bold mt-2">--</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;

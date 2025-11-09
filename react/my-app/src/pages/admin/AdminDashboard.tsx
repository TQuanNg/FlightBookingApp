import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Card, Loading, Alert } from '../../components/ui';
import { 
  UsersIcon, 
  TicketIcon, 
  PaperAirplaneIcon 
} from '@heroicons/react/24/outline';
import { adminService } from '../../services/adminService';
import type { AdminStatsDTO } from '../../types';

const AdminDashboard: React.FC = () => {
  const navigate = useNavigate();
  const [stats, setStats] = useState<AdminStatsDTO | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      setLoading(true);
      const data = await adminService.getStats();
      setStats(data);
      setError(null);
    } catch (err) {
      setError('Failed to load statistics');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-5xl font-bold uppercase">Admin Dashboard</h1>
        <Button variant="secondary" onClick={fetchStats}>
          Refresh Stats
        </Button>
      </div>

      {error && <Alert type="error" message={error} />}

      {loading && <Loading message="Loading statistics..." />}

      {!loading && stats && (
        <div className="mb-8 card-brutal p-8 bg-accent">
          <h2 className="text-3xl font-bold mb-4">Quick Stats</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="border-4 border-black p-6 bg-white">
              <p className="text-sm font-bold">REGISTERED USERS</p>
              <p className="text-4xl font-bold mt-2">{stats.totalUsers}</p>
            </div>
            <div className="border-4 border-black p-6 bg-white">
              <p className="text-sm font-bold">TOTAL BOOKINGS</p>
              <p className="text-4xl font-bold mt-2">{stats.totalBookings}</p>
            </div>
            <div className="border-4 border-black p-6 bg-white">
              <p className="text-sm font-bold">ACTIVE FLIGHTS</p>
              <p className="text-4xl font-bold mt-2">{stats.totalFlights}</p>
            </div>
          </div>
        </div>
      )}

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
    </div>
  );
};

export default AdminDashboard;

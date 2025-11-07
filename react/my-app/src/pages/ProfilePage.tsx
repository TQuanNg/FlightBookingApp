import React from 'react';
import { useAppSelector } from '../hooks/useRedux';
import { Card } from '../components/ui';
import { 
  UserCircleIcon, 
  EnvelopeIcon, 
  IdentificationIcon,
  ShieldCheckIcon 
} from '@heroicons/react/24/outline';

const ProfilePage: React.FC = () => {
  const { user } = useAppSelector(state => state.auth);

  if (!user) {
    return (
      <div className="container mx-auto px-4 py-8">
        <Card>
          <p className="text-xl font-bold">Please login to view your profile</p>
        </Card>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-5xl font-bold mb-8 uppercase">My Profile</h1>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card className="p-8">
          <div className="flex items-center gap-4 mb-6">
            <div className="w-24 h-24 bg-accent border-4 border-black flex items-center justify-center">
              <UserCircleIcon className="w-16 h-16" />
            </div>
            <div>
              <h2 className="text-3xl font-bold">{user.username}</h2>
              <div className="flex items-center gap-2 mt-2">
                <ShieldCheckIcon className="w-5 h-5" />
                <span className="font-bold text-lg">{user.role}</span>
              </div>
            </div>
          </div>

          <div className="space-y-4">
            <div className="border-4 border-black p-4 bg-white">
              <div className="flex items-center gap-3 mb-2">
                <IdentificationIcon className="w-6 h-6" />
                <span className="font-bold text-sm text-gray-600 uppercase">Full Name</span>
              </div>
              <p className="text-xl font-bold">{user.firstName} {user.lastName}</p>
            </div>

            <div className="border-4 border-black p-4 bg-white">
              <div className="flex items-center gap-3 mb-2">
                <EnvelopeIcon className="w-6 h-6" />
                <span className="font-bold text-sm text-gray-600 uppercase">Email</span>
              </div>
              <p className="text-xl font-bold">{user.email}</p>
            </div>

            <div className="border-4 border-black p-4 bg-white">
              <div className="flex items-center gap-3 mb-2">
                <IdentificationIcon className="w-6 h-6" />
                <span className="font-bold text-sm text-gray-600 uppercase">User ID</span>
              </div>
              <p className="text-xl font-bold">#{user.id}</p>
            </div>
          </div>
        </Card>

        <Card className="p-8">
          <h2 className="text-3xl font-bold mb-6 uppercase">Account Information</h2>
          
          <div className="space-y-6">
            <div className="border-l-4 border-black pl-4">
              <p className="text-sm font-bold text-gray-600 uppercase mb-2">Account Status</p>
              <div className="flex items-center gap-2">
                <div className="w-3 h-3 bg-green-600 rounded-full border-2 border-black"></div>
                <span className="text-xl font-bold">Active</span>
              </div>
            </div>

            <div className="border-l-4 border-black pl-4">
              <p className="text-sm font-bold text-gray-600 uppercase mb-2">Role Privileges</p>
              <ul className="space-y-2">
                <li className="flex items-center gap-2">
                  <span className="text-lg">✓</span>
                  <span className="font-bold">Search & Book Flights</span>
                </li>
                <li className="flex items-center gap-2">
                  <span className="text-lg">✓</span>
                  <span className="font-bold">Manage Bookings</span>
                </li>
                <li className="flex items-center gap-2">
                  <span className="text-lg">✓</span>
                  <span className="font-bold">View Purchase History</span>
                </li>
                {(user.role === 'ADMIN' || user.role === 'STAFF') && (
                  <>
                    <li className="flex items-center gap-2">
                      <span className="text-lg">✓</span>
                      <span className="font-bold">Admin Dashboard Access</span>
                    </li>
                    <li className="flex items-center gap-2">
                      <span className="text-lg">✓</span>
                      <span className="font-bold">Manage Users & Flights</span>
                    </li>
                  </>
                )}
              </ul>
            </div>

            <div className="border-4 border-black p-6 bg-accent">
              <p className="font-bold text-lg mb-2">🎉 Welcome to Flight Booking System!</p>
              <p className="font-medium">
                Thank you for choosing our service. We're committed to providing you with the best flight booking experience.
              </p>
            </div>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default ProfilePage;

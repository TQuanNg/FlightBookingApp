import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../components/layout/Navbar';

const MainLayout: React.FC = () => {
  return (
    <div className="min-h-screen bg-white">
      <Navbar />
      <main>
        <Outlet />
      </main>
      <footer className="border-t-4 border-black bg-gray-100 mt-16">
        <div className="container mx-auto px-4 py-8">
          <div className="text-center">
            <p className="text-xl font-bold">✈️ FLIGHT BOOKING SYSTEM</p>
            <p className="font-medium mt-2">© 2025 All rights reserved</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default MainLayout;

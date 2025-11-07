import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../hooks/useRedux';
import { logout } from '../../store/slices/authSlice';
import { Button } from '../ui';
import { 
  ShoppingCartIcon, 
  TicketIcon, 
  UserCircleIcon,
  ArrowRightOnRectangleIcon
} from '@heroicons/react/24/outline';

const Navbar: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { user, isAuthenticated } = useAppSelector(state => state.auth);
  const { items } = useAppSelector(state => state.cart);

  const handleLogout = () => {
    dispatch(logout());
    navigate('/login');
  };

  return (
    <nav className="border-b-4 border-black bg-accent">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-20">
          <Link to="/" className="text-3xl font-bold hover:translate-x-1 transition-transform">
            ✈️ FLIGHT BOOKING
          </Link>

          <div className="flex items-center gap-4">
            {isAuthenticated ? (
              <>
                <Link to="/cart" className="relative">
                  <Button variant="secondary" className="flex items-center gap-2">
                    <ShoppingCartIcon className="w-5 h-5" />
                    Cart
                    {items.length > 0 && (
                      <span className="absolute -top-2 -right-2 bg-red-600 text-white w-6 h-6 rounded-full flex items-center justify-center text-xs font-bold border-2 border-black">
                        {items.length}
                      </span>
                    )}
                  </Button>
                </Link>

                <Link to="/bookings">
                  <Button variant="secondary" className="flex items-center gap-2">
                    <TicketIcon className="w-5 h-5" />
                    Bookings
                  </Button>
                </Link>

                {(user?.role === 'ADMIN' || user?.role === 'STAFF') && (
                  <Link to="/admin">
                    <Button variant="primary" className="flex items-center gap-2">
                      Admin
                    </Button>
                  </Link>
                )}

                <Link to="/profile">
                  <div className="flex items-center gap-3 px-4 py-2 border-4 border-black bg-white shadow-[4px_4px_0px_0px_rgba(0,0,0,1)] hover:shadow-[6px_6px_0px_0px_rgba(0,0,0,1)] hover:translate-x-[-2px] hover:translate-y-[-2px] transition-all cursor-pointer">
                    <UserCircleIcon className="w-6 h-6" />
                    <div className="flex flex-col">
                      <span className="font-bold text-sm">{user?.username}</span>
                      <span className="text-xs text-gray-600">{user?.role}</span>
                    </div>
                  </div>
                </Link>

                <Button
                  variant="danger"
                  onClick={handleLogout}
                  className="flex items-center gap-2"
                >
                  <ArrowRightOnRectangleIcon className="w-5 h-5" />
                  Logout
                </Button>
              </>
            ) : (
              <>
                <Link to="/login">
                  <Button variant="primary">Login</Button>
                </Link>
                <Link to="/register">
                  <Button variant="accent">Register</Button>
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

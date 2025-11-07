import React from 'react';
import { XCircleIcon, CheckCircleIcon, InformationCircleIcon } from '@heroicons/react/24/solid';

interface AlertProps {
  type?: 'error' | 'success' | 'info';
  message: string;
  onClose?: () => void;
}

const Alert: React.FC<AlertProps> = ({ type = 'info', message, onClose }) => {
  const styles = {
    error: 'bg-red-600 text-white',
    success: 'bg-green-600 text-white',
    info: 'bg-blue-600 text-white',
  };

  const icons = {
    error: XCircleIcon,
    success: CheckCircleIcon,
    info: InformationCircleIcon,
  };

  const Icon = icons[type];

  return (
    <div className={`${styles[type]} border-4 border-black shadow-brutal p-4 mb-4 flex items-center justify-between`}>
      <div className="flex items-center gap-3">
        <Icon className="w-6 h-6" />
        <p className="font-bold">{message}</p>
      </div>
      {onClose && (
        <button
          onClick={onClose}
          className="font-bold hover:opacity-80"
        >
          ✕
        </button>
      )}
    </div>
  );
};

export default Alert;

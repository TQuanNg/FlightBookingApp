import React from 'react';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

const Input: React.FC<InputProps> = ({ label, error, className = '', ...props }) => {
  return (
    <div className="mb-4">
      {label && (
        <label className="block font-bold mb-2 text-sm uppercase">
          {label}
        </label>
      )}
      <input
        className={`input-brutal ${error ? 'border-danger' : ''} ${className}`}
        {...props}
      />
      {error && (
        <p className="mt-2 text-sm text-danger font-medium">{error}</p>
      )}
    </div>
  );
};

export default Input;

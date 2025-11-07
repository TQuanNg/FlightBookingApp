import React from 'react';

interface CardProps {
  children: React.ReactNode;
  className?: string;
  title?: string;
  onClick?: () => void;
}

const Card: React.FC<CardProps> = ({ children, className = '', title, onClick }) => {
  return (
    <div 
      className={`card-brutal p-6 ${onClick ? 'cursor-pointer hover:translate-x-1 hover:translate-y-1 hover:shadow-brutal transition-all' : ''} ${className}`}
      onClick={onClick}
    >
      {title && (
        <h3 className="text-2xl font-bold mb-4 uppercase">{title}</h3>
      )}
      {children}
    </div>
  );
};

export default Card;

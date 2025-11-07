import React from 'react';

interface LoadingProps {
  message?: string;
}

const Loading: React.FC<LoadingProps> = ({ message = 'Loading...' }) => {
  return (
    <div className="flex flex-col items-center justify-center min-h-[400px]">
      <div className="w-16 h-16 border-4 border-black border-t-accent animate-spin"></div>
      <p className="mt-4 text-xl font-bold uppercase">{message}</p>
    </div>
  );
};

export default Loading;

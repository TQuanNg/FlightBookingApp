import React, { useEffect, useState } from 'react';
import { adminService } from '../../services/adminService';
import { Card, Button, Loading, Alert, Select, Modal } from '../../components/ui';
import { AdminUserDTO, UserRole } from '../../types';
import { format } from 'date-fns';
import { TrashIcon, PencilIcon } from '@heroicons/react/24/outline';

const AdminUsersPage: React.FC = () => {
  const [users, setUsers] = useState<AdminUserDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedUser, setSelectedUser] = useState<AdminUserDTO | null>(null);
  const [newRole, setNewRole] = useState<UserRole>(UserRole.USER);
  const [showModal, setShowModal] = useState(false);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const data = await adminService.getAllUsers();
      setUsers(data);
      setError(null);
    } catch (err) {
      setError('Failed to load users');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateRole = async () => {
    if (!selectedUser) return;
    
    try {
      const response = await adminService.updateUserRole(selectedUser.userId, newRole);
      if (response.success) {
        alert(response.message);
        setShowModal(false);
        fetchUsers();
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to update role');
    }
  };

  const handleDeleteUser = async (userId: number, username: string) => {
    if (!window.confirm(`Are you sure you want to delete user "${username}"?`)) return;
    
    try {
      const response = await adminService.deleteUser(userId);
      if (response.success) {
        alert(response.message);
        fetchUsers();
      } else {
        alert(response.message);
      }
    } catch (err) {
      alert('Failed to delete user');
    }
  };

  const openRoleModal = (user: AdminUserDTO) => {
    setSelectedUser(user);
    setNewRole(user.role);
    setShowModal(true);
  };

  if (loading) return <Loading message="Loading users..." />;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-5xl font-bold uppercase">User Management</h1>
          <p className="text-xl mt-2">Total Users: {users.length}</p>
        </div>
        <Button variant="primary" onClick={fetchUsers}>
          Refresh
        </Button>
      </div>

      {error && <Alert type="error" message={error} />}

      <div className="space-y-4">
        {users.map(user => (
          <Card key={user.userId}>
            <div className="flex flex-col md:flex-row justify-between gap-4">
              <div className="flex-1">
                <div className="flex items-center gap-3 mb-2">
                  <h3 className="text-2xl font-bold">{user.username}</h3>
                  <span className={`px-3 py-1 font-bold text-sm border-2 border-black ${
                    user.role === UserRole.ADMIN ? 'bg-red-600 text-white' :
                    user.role === UserRole.STAFF ? 'bg-blue-600 text-white' :
                    'bg-gray-200 text-black'
                  }`}>
                    {user.role}
                  </span>
                </div>
                <p className="text-lg"><span className="font-bold">Name:</span> {user.firstName} {user.lastName}</p>
                <p className="text-lg"><span className="font-bold">Email:</span> {user.email}</p>
                <p className="text-sm text-gray-600">
                  <span className="font-bold">User ID:</span> #{user.userId} | 
                  <span className="font-bold"> Joined:</span> {format(new Date(user.createdAt), 'MMM dd, yyyy')}
                </p>
              </div>

              <div className="flex flex-col gap-2 justify-center">
                <Button
                  variant="primary"
                  onClick={() => openRoleModal(user)}
                  className="flex items-center gap-2"
                >
                  <PencilIcon className="w-5 h-5" />
                  Edit Role
                </Button>
                <Button
                  variant="danger"
                  onClick={() => handleDeleteUser(user.userId, user.username)}
                  className="flex items-center gap-2"
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
        title="Update User Role"
      >
        {selectedUser && (
          <div className="space-y-6">
            <div>
              <p className="text-lg mb-2"><span className="font-bold">User:</span> {selectedUser.username}</p>
              <p className="text-lg mb-2"><span className="font-bold">Current Role:</span> {selectedUser.role}</p>
            </div>

            <Select
              label="New Role"
              value={newRole}
              onChange={(e) => setNewRole(e.target.value as UserRole)}
              options={[
                { value: UserRole.USER, label: 'User' },
                { value: UserRole.STAFF, label: 'Staff' },
                { value: UserRole.ADMIN, label: 'Admin' }
              ]}
            />

            <div className="flex gap-4">
              <Button
                variant="secondary"
                onClick={() => setShowModal(false)}
                className="flex-1"
              >
                Cancel
              </Button>
              <Button
                variant="primary"
                onClick={handleUpdateRole}
                className="flex-1"
              >
                Update Role
              </Button>
            </div>
          </div>
        )}
      </Modal>
    </div>
  );
};

export default AdminUsersPage;

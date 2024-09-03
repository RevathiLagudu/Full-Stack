import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UsersList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedUser, setSelectedUser] = useState(null);
  const [updateName, setUpdateName] = useState('');
  const [updateEmail, setUpdateEmail] = useState('');
  const [updatePassword, setUpdatePassword] = useState('');
  const [updatePhoneNumber, setUpdatePhoneNumber] = useState('');
  const [updateAddress, setUpdateAddress] = useState('');

  // Fetch users on component mount
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get('http://localhost:8081/users');
        setUsers(response.data);
      } catch (error) {
        setError('Error fetching users. Please try again later.');
        console.error('Error fetching users:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  // Handle delete user
  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8081/users/delete/${id}`);
      setUsers(users.filter(user => user.id !== id));
    } catch (error) {
      setError('Error deleting user. Please try again later.');
      console.error('Error deleting user:', error);
    }
  };

  // Handle update user
  const handleUpdate = async () => {
    if (selectedUser) {
      try {
        const updatedUser = {
          name: updateName || selectedUser.name,
          email: updateEmail || selectedUser.email,
          password:updatePassword || selectedUser.password,
          phoneNumber:updatePhoneNumber || selectedUser.phoneNumber,
          address:updateAddress || selectedUser.address
        };
        await axios.put(`http://localhost:8081/users/update/${selectedUser.id}`, updatedUser);
        setUsers(users.map(user => 
          user.id === selectedUser.id ? { ...user, ...updatedUser } : user
        ));
        setSelectedUser(null);
        setUpdateName('');
        setUpdateEmail('');
        setUpdatePassword('');
        setUpdatePhoneNumber('');
        setUpdateAddress('');
      } catch (error) {
        setError('Error updating user. Please try again later.');
        console.error('Error updating user:', error);
      }
    }
  };

  return (
    <div>
      <h1>Registered Users</h1>
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p style={{ color: 'red' }}>{error}</p>
      ) : (
        <div>
          <ul>
            {users.map((user) => (
              <li key={user.id}>
                {user.name} - {user.email}
                <button onClick={() => handleDelete(user.id)}>Delete</button>
                <button onClick={() => {
                  setSelectedUser(user);
                  setUpdateName(user.name);
                  setUpdateEmail(user.email);
                }}>Update</button>
              </li>
            ))}
          </ul>

          {selectedUser && (
            <div>
              <h2>Update User</h2>
              <input
                type="text"
                value={updateName}
                onChange={(e) => setUpdateName(e.target.value)}
                placeholder="Name"
              />
              <input
                type="email"
                value={updateEmail}
                onChange={(e) => setUpdateEmail(e.target.value)}
                placeholder="Email"
              />
              <input
                type="password"
                value={updatePassword}
                onChange={(e) => setUpdatePassword(e.target.value)}
                placeholder="Password"
              />
              <input
                type="phoneNumber"
                value={updatePhoneNumber}
                onChange={(e) => setUpdatePhoneNumber(e.target.value)}
                placeholder="PhoneNumber"
              />
              <input
                type="address"
                value={updateAddress}
                onChange={(e) => setUpdateAddress(e.target.value)}
                placeholder="Address"
              />
              <button onClick={handleUpdate}>Save Changes</button>
              <button onClick={() => setSelectedUser(null)}>Cancel</button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default UsersList;

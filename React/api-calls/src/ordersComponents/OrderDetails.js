import React, { useState } from 'react';
import axios from 'axios';

const OrderDetails = () => {
  const [orderId, setOrderId] = useState('');
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Handle input change
  const handleChange = (e) => {
    setOrderId(e.target.value);
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setOrder(null);

    try {
      const response = await axios.get(`http://localhost:9090/orders/orderId/${orderId}`);
      setOrder(response.data);
    } catch (error) {
      setError('Error fetching order. Please try again.');
      console.error('Error fetching order:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1>Order Details</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="orderId">Order ID:</label>
          <input
            type="number"
            id="orderId"
            value={orderId}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Fetching Order...' : 'Get Order'}
        </button>
      </form>

      {error && <p style={{ color: 'red' }}>{error}</p>}
      {order && (
        <div>
          <h2>Order ID: {order.orderId}</h2>
          <p>Order Date: {order.orderDate}</p>
          <p>User ID: {order.userId}</p>
          <p>Book IDs: {order.bookIds.join(', ')}</p>
        </div>
      )}
    </div>
  );
};

export default OrderDetails;

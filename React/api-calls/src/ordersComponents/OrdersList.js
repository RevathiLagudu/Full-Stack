import React, { useState, useEffect } from 'react';
import axios from 'axios';

const OrdersList = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // Fetch orders from the API when the component mounts
  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await axios.get('http://localhost:9090/orders');
        setOrders(response.data); // Assuming response.data is an array of orders
      } catch (error) {
        setError('Error fetching orders. Please try again.');
        console.error('Error fetching orders:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchOrders();
  }, []);

  if (loading) return <p>Loading orders...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;

  return (
    <div>
      <h1>Orders List</h1>
      {orders.length === 0 ? (
        <p>No orders found.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Order ID</th><br/>
              <th>Order Date</th><br/>
              <th>User ID</th><br/>
              <th>Book IDs</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <tr key={order.orderId}>
                <td>{order.orderId}</td><br/>
                <td>{order.orderDate}</td><br/>
                <td>{order.userId}</td><br/>
                <td>{order.bookIds.join(', ')}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default OrdersList;

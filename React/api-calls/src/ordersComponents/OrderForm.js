import React, { useState } from 'react';
import axios from 'axios';

const OrderForm = () => {
  const [orderDate, setOrderDate] = useState('');
  const [userId, setUserId] = useState('');
  const [bookIds, setBookIds] = useState('');
  const [message, setMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle form field changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    
    if (name === 'orderDate') {
      setOrderDate(value);
    } else if (name === 'userId') {
      setUserId(value);
    } else if (name === 'bookIds') {
      setBookIds(value);
    }
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsSubmitting(true);
    setMessage('');

    // Convert bookIds from comma-separated string to array of integers
    const bookIdsArray = bookIds.split(',').map(id => parseInt(id.trim(), 10));

    try {
      const response = await axios.post('http://localhost:9090/orders', {
        orderDate,
        userId: parseInt(userId, 10),
        bookIds: bookIdsArray,
      });

      setMessage('Order saved successfully!');
      console.log('Order saved:', response.data);
    } catch (error) {
      setMessage('Error saving the order. Please try again.');
      console.error('Error saving order:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div>
      <h1>Order Form</h1>
      <form onSubmit={handleSubmit}>

        <div>
          <label htmlFor="orderDate">Order Date:</label>
          <input
            type="date"
            id="orderDate"
            name="orderDate"
            value={orderDate}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label htmlFor="userId">User ID:</label>
          <input
            type="number"
            id="userId"
            name="userId"
            value={userId}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label htmlFor="bookIds">Book IDs (comma-separated):</label>
          <input
            type="text"
            id="bookIds"
            name="bookIds"
            value={bookIds}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Saving...' : 'Save Order'}
        </button>

        {message && <p>{message}</p>}
      </form>
    </div>
  );
};

export default OrderForm;

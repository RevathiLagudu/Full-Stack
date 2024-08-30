import React, { useState } from 'react'

const AddBookForm = () => {
  const [title, setTitle] = useState('');
  const [price, setPrice] = useState('');
  const [pubdate, setPubdate] = useState('');
  const [error, setError] = useState(null);

  const handleSubmit = async(e) => {
    e.preventDefault();
    try{
        const response = await fetch('http://localhost:8080/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({title, price: parseFloat(price), pubdate}),
        });
        if(!response){
            throw new Error('Network response was not ok');
        }
        setTitle('');
        setPrice('');
        setPubdate('');
    } catch(error) {
        setError(error);
    }
  };

  return (
    <div>
        <h1>Add New Book</h1>
        <form onSubmit={handleSubmit}>
            <div>
                <label>
                    Title:
                    <input 
                        type="text" 
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </label>
            </div>
            <div>
                <label>
                    price:
                    <input
                        type="number"
                        value={price}
                        onChange={(e) => setPrice(e.target.value)}
                        required 
                    />
                </label>
            </div>
            <div>
                <label>
                    Publication Date:
                    <input 
                        type="date"
                        value={pubdate}
                        onChange={(e) => setPubdate(e.target.value)}
                        required
                    />
                </label>
            </div>
            <button type="submit">Add Book</button>
        </form>
        {error && <p>Error: {error.message}</p>}
    </div>
  )
}

export default AddBookForm;
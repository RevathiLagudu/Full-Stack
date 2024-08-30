import React, { useEffect, useState } from 'react'
import Book from './Book';

const BookList = () => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBooks = async() => {
        try{
            const response = await fetch('http://localhost:8080/books');
            if(!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            setBooks(data);
        } catch(error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    };
    fetchBooks();
  }, [])

  if(loading){
    return <p>Loading...</p>
  }
  if(error){
    return <p>Error: {error.message}</p>
  }
  return (
    <div>
        <h1>Book List</h1>
        {books.length > 0 ? (
            books.map(book => <Book key={book.id} book={book} />)
        ) : (
            <p>No books available.</p>
        )}
    </div>
  )
}

export default BookList;
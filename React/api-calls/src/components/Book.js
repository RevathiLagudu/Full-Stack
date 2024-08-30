import React from 'react'

const Book = ({ book }) => {
  return (
    <div className="book">
        <h3>{book.title}</h3>
        <p>Price: ${book.price}</p>
        <p>Publication Date: {book.pubDate}</p>
    </div>
  )
}

export default Book;
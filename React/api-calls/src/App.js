import React from 'react';
// import Register from './Register'; // No need for .js extension
import './App.css';
import AddBookForm from './components/AddBookForm'
import BookList from './components/BookList'
function App() {
  return (
    <div className="App">
      {/* <Register /> */}
      <AddBookForm />
      <BookList />
    </div>
  );
}

export default App;

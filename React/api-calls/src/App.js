import React from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Login from './Login';
import Register from './Register';
import BookList from './components/BookList';
import UsersList from './UsersList';
import OrderForm from './ordersComponents/OrderForm';
import OrdersList from './ordersComponents/OrdersList';
import OrderDetails from './ordersComponents/OrderDetails';
function App() {
  return (
    <div className="App">
     <Router>
      <Routes>
        <Route  path='/login' element={<Login/>}/>
        <Route path='/books' element={<BookList/>}/>
        <Route path='/' element={<Register/>}/>
        <Route path='/usersList' element={<UsersList/>}/>
        <Route path='/orderForm' element={<OrderForm/>}/>
        <Route path='/ordersList' element={<OrdersList/>}/>
        <Route path='/orderDetails' element={<OrderDetails/>}/>
      </Routes>
     </Router>
      
    </div>
  );
}

export default App;

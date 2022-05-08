import React, { useState } from 'react';
import './App.css';
import App from './App';
import UpdateCoffee from './app/UpdateCoffee';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";


function AppRouter() {
  return (
    <Router>
      <Routes>
          <Route path="/" element={<App />} />
          <Route path="/edit" element={<UpdateCoffee />} />
      </Routes>
    </Router>
  );
}

export default AppRouter;

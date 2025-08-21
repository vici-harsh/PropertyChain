import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import './css/main.css'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import './vendor/aos/aos.css'; 
import '@srexi/purecounterjs';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
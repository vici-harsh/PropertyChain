import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import './assets/css/main.css'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/vendor/aos/aos.css';
import './assets/vendor/swiper/swiper-bundle.min.css';
import '@srexi/purecounterjs';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
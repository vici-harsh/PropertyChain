import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import './assets/css/main.css'; // Your updated main.css
import 'bootstrap/dist/css/bootstrap.min.css'; // For layout
import './assets/vendor/aos/aos.css'; // Animations
import './assets/vendor/swiper/swiper-bundle.min.css'; // Carousel
import './assets/vendor/bootstrap-icons/bootstrap-icons.css'; // Icons
import './assets/vendor/fontawesome-free/css/all.min.css'; // FontAwesome icons

// Vendor JS (global)
import './assets/vendor/aos/aos.js';
import '@srexi/purecounterjs/dist/purecounter_vanilla.js'; // Counts
import Swiper from 'swiper/bundle';
import 'swiper/css';   
import 'swiper/css/navigation'; 
import 'swiper/css/pagination';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>
);
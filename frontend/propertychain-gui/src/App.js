import React, { useState, useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './components/Home'; // New
import About from './components/About';
import Services from './components/Services';
import PropertyList from './components/PropertyList';
import AddProperty from './components/AddProperty';
import PropertySingle from './components/PropertySingle';
import Agents from './components/Agents';
import Contact from './components/Contact';
import Login from './components/Login';
import ServiceDetails from './components/ServiceDetails'; 
import StarterPage from './components/StarterPage'; 
import AOS from 'aos';
import 'aos/dist/aos.css';
import Swiper from 'swiper/bundle';
import 'swiper/css';   
import 'swiper/css/navigation'; 
import 'swiper/css/pagination';
import PureCounter from '@srexi/purecounterjs';

// Placeholder for others
const Transfer = () => <div>Transfer Page</div>;
const Escrow = () => <div>Escrow Page</div>;
const History = () => <div>History Page</div>;

function App() {
  const [account, setAccount] = useState(null);

  useEffect(() => {
    // Init AOS
    AOS.init({ easing: 'ease-in-out', duration: 1000, delay: 0 });

    // Init PureCounter
    new PureCounter();
    // new window.PureCounter();

    // Init Swiper
    new Swiper('.init-swiper', {
      loop: true,
      autoplay: { delay: 5000 },
      pagination: { el: '.swiper-pagination', clickable: true },
      slidesPerView: 1,
      breakpoints: { 1200: { slidesPerView: 3 } }
    });

    // Smooth scroll for .scrollto links
    const links = document.querySelectorAll('.scrollto');
    for (const link of links) {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const href = link.getAttribute('href');
        const target = document.querySelector(href);
        if (target) {
          window.scrollTo({
            top: target.offsetTop - 70,
            behavior: 'smooth'
          });
        }
      });
    }
  // }, []);

 return () => {
      for (const link of links) {
        link.removeEventListener('click', () => {});
      }
    };
  }, []);

  return (
    <>
      <Header account={account} />
      <Routes>
        <Route path="/login" element={<Login setAccount={setAccount} />} />
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/services" element={<Services />} />
        <Route path="/properties" element={<PropertyList account={account} />} />
        <Route path="/add-property" element={<AddProperty account={account} />} />
        <Route path="/agents" element={<Agents />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/property/:id" element={<PropertySingle account={account} />} />
        <Route path="/transfer/:id" element={<Transfer />} />
        <Route path="/escrow/:id" element={<Escrow />} />
        <Route path="/history/:id" element={<History />} />
        <Route path="/service-details" element={<ServiceDetails />} />
        <Route path="/starter-page" element={<StarterPage />} />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
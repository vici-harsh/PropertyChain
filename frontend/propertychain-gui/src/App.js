import React, { useEffect, useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/css/main.css'; 
import 'swiper/css/bundle'; 
import 'aos/dist/aos.css'; 
import '@srexi/purecounterjs'; 
import Login from './components/Login';
import PropertyList from './components/PropertyList';
import AddProperty from './components/AddProperty';
import TransferOwnership from './components/TransferOwnership';
import CreateEscrow from './components/CreateEscrow';
import PropertyHistory from './components/PropertyHistory';
import Header from './components/Header';
import Footer from './components/Footer';
import Hero from './components/Hero';
import AOSInitializer from './components/AOSInitializer';
import About from './components/About';
import Agents from './components/Agents';
import Contact from './components/Contact';
import Services from './components/Services';
import ServiceDetails from './components/ServiceDetails';
import { initSwiperSliders, initPureCounter, toggleScrolled } from './utils/templateUtils';


function App() {
  const [account, setAccount] = useState('');

  useEffect(() => {
    initPureCounter();
    window.addEventListener('scroll', toggleScrolled);
    window.addEventListener('load', toggleScrolled);
    return () => {
      window.removeEventListener('scroll', toggleScrolled);
      window.removeEventListener('load', toggleScrolled);
    };
  }, []);

  return (
    <>
      <AOSInitializer />
      <Header account={account} />
      <Hero />
      <Routes>
        <Route path="/" element={<Login setAccount={setAccount} />} />
        <Route path="/properties" element={<PropertyList account={account} />} />
        <Route path="/add-property" element={<AddProperty account={account} />} />
        <Route path="/transfer/:id" element={<TransferOwnership account={account} />} />
        <Route path="/escrow/:id" element={<CreateEscrow account={account} />} />
        <Route path="/history/:id" element={<PropertyHistory account={account} />} />
        <Route path="/agents" element={<Agents />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/about" element={<About />} />
        <Route path="/service-details" element={<ServiceDetails />} />
        <Route path="/services" element={<Services />} />
      </Routes>
      <Footer />
    </>
  );
}


export default App;
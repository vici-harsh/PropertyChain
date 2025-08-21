import { useEffect } from 'react';
import './css/main.css';
import React, { useState } from 'react';
import { Route, Routes } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import PropertyList from './components/PropertyList';
import AddProperty from './components/AddProperty';
import TransferOwnership from './components/TransferOwnership';
import CreateEscrow from './components/CreateEscrow';
import PropertyHistory from './components/PropertyHistory';
import Header from './components/Header';
import Footer from './components/Footer';
import Agents from './components/Agents';
import Contact from './components/Contact';
import AOSInitializer from './components/AOSInitializer';
import 'swiper/css';
import Hero from './components/Hero';
import { initSwiperSliders, initPureCounter, toggleScrolled } from './/utils/templateUtils';


function App() {
  const [account, setAccount] = useState('');

  useEffect(() => {
    initSwiperSliders();
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
      </Routes>
      <Footer />
    </>
  );
}


export default App;
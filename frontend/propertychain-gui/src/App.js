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

function App() {
  const [account, setAccount] = useState('');

  return (
    <div>
      <Header account={account} />
      <Routes>
        <Route path="/" element={<Login setAccount={setAccount} />} />
        <Route path="/properties" element={<PropertyList account={account} />} />
        <Route path="/add-property" element={<AddProperty account={account} />} />
        <Route path="/transfer/:id" element={<TransferOwnership account={account} />} />
        <Route path="/escrow/:id" element={<CreateEscrow account={account} />} />
        <Route path="/history/:id" element={<PropertyHistory account={account} />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
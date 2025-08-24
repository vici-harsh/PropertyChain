// // src/App.js
import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import AOS from 'aos';
import 'aos/dist/aos.css';

import Header from './components/Header';
import Footer from './components/Footer';

import Home from './components/Home';
import PropertyList from './components/PropertyList';
import PropertySingle from './components/PropertySingle';
import AddProperty from './components/AddProperty';
import PropertyHistory from './components/PropertyHistory';
import TransferOwnership from './components/TransferOwnership';
import CreateEscrow from './components/CreateEscrow';
import Services from './components/Services';
import StarterPage from './components/StarterPage';
import Login from './components/Login';
import RequireAccount from "./RequireAccount";
import SelectAccount from "./components/SelectAccount";

export default function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/account" element={<SelectAccount />} />

        <Route
          path="/"
          element={<RequireAccount><Home /></RequireAccount>}
        />
        <Route
          path="/properties"
          element={<RequireAccount><PropertyList /></RequireAccount>}
        />

        {/* Support BOTH paths users might have used */}
        <Route
          path="/add-property"
          element={<RequireAccount><AddProperty /></RequireAccount>}
        />
        <Route
          path="/properties/new"
          element={<RequireAccount><AddProperty /></RequireAccount>}
        />

        <Route
          path="/properties/:id"
          element={<RequireAccount><PropertySingle /></RequireAccount>}
        />
        <Route
          path="/property/:id"
          element={<RequireAccount><PropertySingle /></RequireAccount>}
        />

        <Route
          path="/properties/:id/escrow"
          element={<RequireAccount><CreateEscrow /></RequireAccount>}
        />
        <Route
          path="/escrow/:id"
          element={<RequireAccount><CreateEscrow /></RequireAccount>}
        />

        <Route
          path="/properties/:id/transfer"
          element={<RequireAccount><TransferOwnership /></RequireAccount>}
        />
        <Route
          path="/transfer/:id"
          element={<RequireAccount><TransferOwnership /></RequireAccount>}
        />

        <Route
          path="/properties/:id/history"
          element={<RequireAccount><PropertyHistory /></RequireAccount>}
        />
        <Route
          path="/property/:id/history"
          element={<RequireAccount><PropertyHistory /></RequireAccount>}
        />

        <Route path="*" element={<Navigate to="/account" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

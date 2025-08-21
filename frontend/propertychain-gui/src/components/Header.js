import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Header({ account }) {
  return (
    <header id="header" className="fixed-top">
      <div className="container d-flex align-items-center">
        <h1 className="logo me-auto"><Link to="/">EstateAgency</Link></h1>
        <Navbar expand="lg" className="navbar">
          <Nav className="navbar-nav">
            <Nav.Link as={Link} to="/">Home</Nav.Link>
            <Nav.Link as={Link} to="/about">About</Nav.Link>
            <Nav.Link as={Link} to="/services">Services</Nav.Link>
            <Nav.Link as={Link} to="/properties">Properties</Nav.Link>
            <Nav.Link as={Link} to="/agents">Agents</Nav.Link>
            <Nav.Link as={Link} to="/contact">Contact</Nav.Link>
          </Nav>
        </Navbar>
        <p>Account: {account ? `${account.slice(0, 6)}...${account.slice(-4)}` : 'Not Connected'}</p>
      </div>
    </header>
  );
}

export default Header;
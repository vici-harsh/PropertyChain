import React from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Header({ account }) {
  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Navbar.Brand>PropertyChain</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link as={Link} to="/properties">Properties</Nav.Link>
          <Nav.Link as={Link} to="/add-property">Add Property</Nav.Link>
        </Nav>
        <Nav className="ml-auto">
          <Nav.Link>Account: {account ? account.slice(0, 6) + '...' + account.slice(-4) : 'Not Connected'}</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Header;
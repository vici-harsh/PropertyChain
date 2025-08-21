import React, { useEffect, useRef } from 'react';
import { Navbar, Nav } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Header({ account }) {
  const mobileNavToggleBtn = useRef(null);
  const scrollTop = useRef(null);

  useEffect(() => {
    const toggleMobileNav = () => {
      const body = document.querySelector('body');
      if (body && mobileNavToggleBtn.current) {
        body.classList.toggle('mobile-nav-active');
        mobileNavToggleBtn.current.classList.toggle('bi-list');
        mobileNavToggleBtn.current.classList.toggle('bi-x');
      }
    };

    if (mobileNavToggleBtn.current) {
      mobileNavToggleBtn.current.addEventListener('click', toggleMobileNav);
    }

    const toggleScroll = () => {
      if (scrollTop.current) {
        window.scrollY > 100 ? scrollTop.current.classList.add('active') : scrollTop.current.classList.remove('active');
      }
    };
    window.addEventListener('scroll', toggleScroll);
    window.addEventListener('load', toggleScroll);

    return () => {
      if (mobileNavToggleBtn.current) {
        mobileNavToggleBtn.current.removeEventListener('click', toggleMobileNav);
      }
      window.removeEventListener('scroll', toggleScroll);
      window.removeEventListener('load', toggleScroll);
    };
  }, []);

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
        <button ref={mobileNavToggleBtn} className="mobile-nav-toggle bi-list"></button>
        <a ref={scrollTop} href="#" className="scroll-top d-flex align-items-center justify-content-center"><i className="bi bi-arrow-up-short"></i></a>
      </div>
    </header>
  );
}

export default Header;
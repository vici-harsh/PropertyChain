import React, { useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';

function Header({ account }) {
  const mobileNavToggleBtn = useRef(null);
  const scrollTop = useRef(null);

  useEffect(() => {
    const toggleMobileNav = () => {
      if (mobileNavToggleBtn.current) {
        document.body.classList.toggle('mobile-nav-active');
        mobileNavToggleBtn.current.classList.toggle('bi-list');
        mobileNavToggleBtn.current.classList.toggle('bi-x');
      }
    };

    const toggleScrollTop = () => {
      if (scrollTop.current) {
        window.scrollY > 100
          ? scrollTop.current.classList.add('active')
          : scrollTop.current.classList.remove('active');
      }
    };

    const mobileBtn = mobileNavToggleBtn.current;
    if (mobileBtn) {
      mobileBtn.addEventListener('click', toggleMobileNav);
    }

    window.addEventListener('load', toggleScrollTop);
    window.addEventListener('scroll', toggleScrollTop);

    return () => {
      if (mobileBtn) {
        mobileBtn.removeEventListener('click', toggleMobileNav);
      }
      window.removeEventListener('load', toggleScrollTop);
      window.removeEventListener('scroll', toggleScrollTop);
    };
  }, []);

  const handleScrollTop = (e) => {
    e.preventDefault();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  return (
    <header id="header" className="header d-flex align-items-center fixed-top">
      <div className="container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
        <a href="/" className="logo d-flex align-items-center">
          {/* Uncomment below if using an image logo */}
          {/* <img src="/assets/img/logo.png" alt="Logo" /> */}
          <h1 className="sitename">
            Estate<span>Agency</span>
          </h1>
        </a>
        <nav id="navbar" className="navmenu">
          <ul>
            <li><Link className="nav-link" to="/">Home</Link></li>
            <li><Link className="nav-link" to="/services">Services</Link></li>
            <li><Link className="nav-link" to="/properties">Properties</Link></li>
            <li><Link className="nav-link" to="/add-property">Add Property</Link></li>
            <li><Link className="nav-link" to="/transfer/:id">Transfer</Link></li>
            <li><Link className="nav-link" to="/escrow/:id">Escrow</Link></li>
            <li><Link className="nav-link" to="/history/:id">History</Link></li>
          </ul>
          <i
            ref={mobileNavToggleBtn}
            className="mobile-nav-toggle d-xl-none bi bi-list"
          ></i>
        </nav>
        <span className="small text-muted">
          Account: {account ? `${account.slice(0, 6)}...${account.slice(-4)}` : 'Not Connected'}
        </span>
      </div>
      <a
        ref={scrollTop}
        onClick={handleScrollTop}
        className="scroll-top d-flex align-items-center justify-content-center"
        href="#"
      >
        <i className="bi bi-arrow-up-short"></i>
      </a>
    </header>
  );
}

export default Header;
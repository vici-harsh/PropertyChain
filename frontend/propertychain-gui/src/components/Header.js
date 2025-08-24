import React, { useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Header({ account }) {
  const mobileNavToggleBtn = useRef(null);
  const scrollTop = useRef(null);
  const navigate = useNavigate();

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
    mobileBtn?.addEventListener('click', toggleMobileNav);
    window.addEventListener('load', toggleScrollTop);
    window.addEventListener('scroll', toggleScrollTop);
    return () => {
      mobileBtn?.removeEventListener('click', toggleMobileNav);
      window.removeEventListener('load', toggleScrollTop);
      window.removeEventListener('scroll', toggleScrollTop);
    };
  }, []);

  const handleScrollTop = (e) => {
    e.preventDefault();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const goToMyProps = () => navigate('/properties');

  const acct = account || localStorage.getItem('current_eth_account') || '';

  return (
    <header id="header" className="header d-flex align-items-center fixed-top">
      <div className="container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
        <Link to="/" className="logo d-flex align-items-center">
          <h1 className="sitename">Property<span>Chain</span></h1>
        </Link>

        <nav id="navbar" className="navmenu">
          <ul>
            <li><Link className="nav-link" to="/">Home</Link></li>
            <li><Link className="nav-link" to="/services">Services</Link></li>
            <li><Link className="nav-link" to="/properties">Properties</Link></li>
            <li><Link className="nav-link" to="/add-property">Add Property</Link></li>
          </ul>
          <i ref={mobileNavToggleBtn} className="mobile-nav-toggle d-xl-none bi bi-list"></i>
        </nav>

        <button className="btn btn-sm btn-outline-primary me-2" onClick={goToMyProps}>
          My Properties
        </button>
        <span className="small text-muted">
          Account: {acct ? `${acct.slice(0, 6)}...${acct.slice(-4)}` : 'Not Connected'}
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

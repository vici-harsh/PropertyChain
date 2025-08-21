import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Login({ setAccount }) {
  const [walletAddress, setWalletAddress] = useState('');
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault(); // Prevent default form submission
    console.log('Login attempted with wallet address:', walletAddress); // Debug log
    if (walletAddress) {
      setAccount(walletAddress); // Update parent state
      navigate('/properties'); // Redirect to properties page after login
    } else {
      console.log('Please enter a wallet address');
    }
  };

  return (
    <div className="d-flex flex-column min-vh-100">
      {/* Header with Background */}
      <header id="header" className="fixed-top d-flex align-items-center" style={{ backgroundColor: '#1e1e1e', color: '#fff' }}>
        <div className="container d-flex align-items-center justify-content-between">
          <h1 className="logo"><Link to="/" style={{ color: '#fff', textDecoration: 'none' }}>EstateAgency</Link></h1>
          <nav id="navbar" className="navbar">
            <ul>
              <li><Link className="nav-link scrollto" to="/properties" style={{ color: '#fff' }}>Properties</Link></li>
              <li><Link className="nav-link scrollto" to="/about" style={{ color: '#fff' }}>About</Link></li>
              <li><Link className="nav-link scrollto" to="/contact" style={{ color: '#fff' }}>Contact</Link></li>
            </ul>
            <i className="bi bi-list mobile-nav-toggle" style={{ color: '#fff' }}></i>
          </nav>
        </div>
      </header>

      {/* Main Content with Background Image */}
      <section id="login" className="flex-grow-1 d-flex align-items-center" style={{ backgroundImage: `url(${require('../assets/img/floor-plan.jpg')})`, backgroundSize: 'cover', backgroundPosition: 'center', backgroundRepeat: 'no-repeat' }}>
        <div className="container position-relative">
          <div className="row justify-content-center" data-aos="fade-up">
            <div className="col-lg-4 col-md-6">
              <div className="card" style={{ backgroundColor: 'rgba(255, 255, 255, 0.9)', padding: '20px', borderRadius: '10px' }}>
                <div className="card-body">
                  <h5 className="card-title text-center">Login</h5>
                  <form onSubmit={handleLogin} className="row g-3">
                    <div className="col-12">
                      <input
                        type="text"
                        className="form-control"
                        value={walletAddress}
                        onChange={(e) => setWalletAddress(e.target.value)}
                        placeholder="Enter Wallet Address"
                        required
                      />
                    </div>
                    <div className="col-12 text-center">
                      <button type="submit" className="btn btn-primary">Login</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Footer with Background */}
      <footer id="footer" className="py-4" style={{ backgroundColor: '#1e1e1e', color: '#fff' }}>
        <div className="container">
          <div className="text-center">
            <p>&copy; 2025 EstateAgency. All Rights Reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default Login;
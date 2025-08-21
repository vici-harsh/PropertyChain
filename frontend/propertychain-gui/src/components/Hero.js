import React from 'react';
import { Link } from 'react-router-dom';

function Hero() {
  return (
    <section id="hero" className="d-flex align-items-center">
      <div className="container">
        <h1>Welcome to EstateAgency</h1>
        <h2>Odio et unde deleniti. Deserunt numquam exercitationem.</h2>
        <Link to="/properties" className="btn-get-started">Get Started</Link>
      </div>
    </section>
  );
}

export default Hero;
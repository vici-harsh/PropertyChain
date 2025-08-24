import React from 'react';
import { Link } from 'react-router-dom';

function Hero() {
  return (
    <section id="hero" className="hero d-flex align-items-center section-dark">
      <div className="container" style={{ height: '100%', textAlign: 'center' }}>
        <div className="row" style={{ height: '100%' }}>
          <div className="col-12 hero-img">
            <img
              src="/assets/img/hero-carousel/hero-carousel-1.jpg"
              className="img-fluid"
              alt="Property Listing"
              style={{ width: '100%', height: '100%', objectFit: 'cover', position: 'absolute', top: 0, left: 0 }}
            />
            <div className="property-info" style={{ position: 'absolute', bottom: '20px', left: '50%', transform: 'translateX(-50%)', color: '#fff', background: 'rgba(0, 0, 0, 0.6)', padding: '20px', borderRadius: '10px' }}>
              <h2>247 Venda Road Five</h2>
              <p>Doral, Florida</p>
              <div className="sale-rent" style={{ display: 'inline-block', fontSize: '15px', fontWeight: '500', color: '#fff', padding: '4px 20px', border: '2px solid #2eca6a', borderRadius: '50px', marginBottom: '10px' }}>
                SALE | $356,000
              </div>
              <Link
                to="/properties"
                className="btn-get-started scrollto d-inline-flex align-items-center justify-content-center"
                style={{ background: '#2eca6a', color: '#fff', padding: '10px 30px', borderRadius: '50px', textDecoration: 'none' }}
              >
                <span>View Details</span>
                <i className="bi bi-arrow-right"></i>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default Hero;
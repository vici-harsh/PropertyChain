import React from 'react';

function Services({ isHomeVersion = false }) {
  return (
    <section id="services" className="services section-bg">
      <div className="container">
        <div className="section-title">
          <h2>{isHomeVersion ? 'Our Services' : 'Services'}</h2>
          <p>Necessitatibus eius consequatur ex aliquid fuga eum quidem sint consectetur velit</p>
        </div>
        <div className="row">
          <div className="col-lg-4 col-md-6">
            <div className="service-item">
              <i className="bi bi-card-checklist"></i>
              <h3>Nesciunt Mete</h3>
              <p>Provident nihil minus qui consequatur non omnis maiores...</p>
            </div>
          </div>
          {/* Add more service items */}
        </div>
      </div>
    </section>
  );
}

export default Services;
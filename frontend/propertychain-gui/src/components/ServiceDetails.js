import React from 'react';

function ServiceDetails() {
  return (
    <section id="service-details" className="service-details">
      <div className="container">
        <div className="section-title">
          <h2>Service Details</h2>
          <p>Odio et unde deleniti...</p>
        </div>
        <div className="row">
          <div className="col-lg-4">
            <div className="services-list">
              <a href="#">Web Design</a>
              {/* Add list */}
            </div>
            <div className="download-catalog">
              <a href="#">Catalog PDF</a>
              {/* Add */}
            </div>
            <div className="help-box">
              <h4>Have a Question?</h4>
              <p>+1 5589 55488 55</p>
              <p>contact@example.com</p>
            </div>
          </div>
          <div className="col-lg-8">
            <img src="/assets/img/service-details.jpg" alt="" className="img-fluid" />
            <p>Temporibus et in vero dicta...</p>
            {/* Add content, lists */}
          </div>
        </div>
      </div>
    </section>
  );
}

export default ServiceDetails;
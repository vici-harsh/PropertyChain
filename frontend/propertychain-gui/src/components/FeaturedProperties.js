import React from 'react';

function FeaturedProperties() {
  return (
    <section id="featured-properties" className="featured-properties section-bg">
      <div className="container">
        <div className="section-title">
          <h2>Featured Properties</h2>
          <p>Necessitatibus eius consequatur ex aliquid fuga eum quidem sint consectetur velit</p>
        </div>
        <div className="row">
          <div className="col-lg-4 col-md-6">
            <div className="property-item testimonial-item"> {/* Reusing testimonial-item for card style */}
              <img src="/assets/img/properties/property-1.jpg" alt="Property 1" className="img-fluid" />
              <div className="property-content">
                <div className="price stars"> {/* Mimicking stars with price styling */}
                  <span>Rent</span> <strong>$12.000</strong>
                </div>
                <p>
                  A beautifully designed property located in the heart of the city.
                </p>
                <div className="profile mt-auto"> {/* Mimicking profile for location and link */}
                  <h3><a href="#">204 Olive Road Two</a></h3>
                  <div className="location"><i className="fas fa-map-marker-alt"></i> Doral, Florida</div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="property-item testimonial-item">
              <img src="/assets/img/properties/property-2.jpg" alt="Property 2" className="img-fluid" />
              <div className="property-content">
                <div className="price stars">
                  <span>Sale</span> <strong>$250.000</strong>
                </div>
                <p>
                  Spacious modern home with stunning views and premium amenities.
                </p>
                <div className="profile mt-auto">
                  <h3><a href="#">305 Pine Street</a></h3>
                  <div className="location"><i className="fas fa-map-marker-alt"></i> Miami, Florida</div>
                </div>
              </div>
            </div>
          </div>
          <div className="col-lg-4 col-md-6">
            <div className="property-item testimonial-item">
              <img src="/assets/img/properties/property-3.jpg" alt="Property 3" className="img-fluid" />
              <div className="property-content">
                <div className="price stars">
                  <span>Rent</span> <strong>$8.500</strong>
                </div>
                <p>
                  Cozy apartment in a vibrant neighborhood with easy access.
                </p>
                <div className="profile mt-auto">
                  <h3><a href="#">101 Maple Avenue</a></h3>
                  <div className="location"><i className="fas fa-map-marker-alt"></i> Orlando, Florida</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}

export default FeaturedProperties;
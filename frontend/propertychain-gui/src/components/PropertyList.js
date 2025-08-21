import React, { useEffect, useState } from 'react';
import { getAllProperties } from '../services/api';
import { Link } from 'react-router-dom';

function PropertyList({ account }) {
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    getAllProperties().then(res => setProperties(res.data || [])).catch(err => console.error('Error fetching properties:', err));
  }, []);

  // Array of available image paths (adjust based on your assets)
  const propertyImages = [
    '../assets/img/properties/property-1.jpg',
    '../assets/img/properties/property-2.jpg',
    '../assets/img/properties/property-3.jpg',
    '../assets/img/properties/property-4.jpg',
    '../assets/img/properties/property-5.jpg',
    '../assets/img/properties/property-6.jpg',
  ];

  return (
    <section id="properties" className="properties section-bg">
      <div className="container">
        <div className="section-title">
          <h2>Properties</h2>
          <p>Odio et unde deleniti. Deserunt numquam exercitationem.</p>
        </div>
        <div className="row">
          {properties.map((prop, index) => (
            <div key={prop.id} className="col-lg-4 col-md-6">
              <div className="property-item">
                <img
                  src={require(propertyImages[index % propertyImages.length])} // Cycle through images
                  alt={`Property ${prop.id}`}
                  className="img-fluid"
                  onError={(e) => {
                    e.target.src = require('../assets/img/properties/property-1.jpg'); // Fallback image
                    console.log('Image load failed, using fallback:', e.target.src);
                  }}
                />
                <div className="property-content">
                  <div className="price"><span>Rent</span> <strong>$12.000</strong></div>
                  <h3><a href="#">{prop.address}</a></h3>
                  <div className="location"><i className="fas fa-map-marker-alt"></i> {prop.address}</div>
                  <div className="property-type"><i className="fas fa-home"></i> House</div>
                  <Link to={`/transfer/${prop.id}`} className="btn btn-primary">Transfer</Link>
                  <Link to={`/escrow/${prop.id}`} className="btn btn-secondary">Escrow</Link>
                  <Link to={`/history/${prop.id}`} className="btn btn-info">History</Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}

export default PropertyList;
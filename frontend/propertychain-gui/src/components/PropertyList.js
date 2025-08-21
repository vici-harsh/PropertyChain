import React, { useEffect, useState } from 'react';
import { getAllProperties } from '../services/api';
import { Link } from 'react-router-dom';

function PropertyList({ account }) {
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    getAllProperties().then(res => setProperties(res.data));
  }, []);

  return (
    <section id="properties" className="properties">
      <div class="container">
        <div class="row">
          {properties.map(prop => (
            <div class="col-lg-4 col-md-6">
              <div class="property-item">
                <img src={require('../img/properties/property-1.jpg')} alt="Property 1" className="img-fluid" />
                <div class="property-content">
                  <div class="price"><span>Rent</span> <strong>$1200</strong></div>
                  <h3><a href="#">{prop.address}</a></h3>
                  <div class="location"><i class="fas fa-map-marker-alt"></i> {prop.address}</div>
                  <div class="property-type"><i class="fas fa-home"></i> House</div>
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
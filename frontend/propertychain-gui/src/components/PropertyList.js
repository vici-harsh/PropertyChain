import React, { useEffect, useState } from 'react';
import { getAllProperties } from '../services/api';
import { Link } from 'react-router-dom';

function PropertyList({ account }) {
  const [properties, setProperties] = useState([]);

  useEffect(() => {
    getAllProperties().then(res => setProperties(res.data));
  }, []);

  return (
    <div className="container mt-5">
      <h2>Property Listings</h2>
      <div className="row">
        {properties.map(prop => (
          <div key={prop.id} className="col-md-4 mb-3">
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{prop.address}</h5>
                <p className="card-text">{prop.description}</p>
                <p>Owner: {prop.blockchainOwner}</p>
                <Link to={`/transfer/${prop.id}`} className="btn btn-primary">Transfer</Link>
                <Link to={`/escrow/${prop.id}`} className="btn btn-secondary ml-2">Create Escrow</Link>
                <Link to={`/history/${prop.id}`} className="btn btn-info ml-2">History</Link>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default PropertyList;
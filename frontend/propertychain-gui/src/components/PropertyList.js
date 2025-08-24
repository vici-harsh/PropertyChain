import React, { useEffect, useState } from 'react';
import { getAllProperties } from '../services/api';
import { Link } from 'react-router-dom';

function toCurrency(n) {
  if (n == null) return '—';
  try { return new Intl.NumberFormat('en-CA', { style: 'currency', currency: 'CAD' }).format(n); }
  catch { return `${n}`; }
}

function PropertyList() {
  const [properties, setProperties] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    getAllProperties()
      .then((data) => setProperties(data || []))
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="container my-5 py-5 text-center">
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span>
        </div>
        <p className="mt-3">Loading properties...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container my-5 py-5">
        <div className="alert alert-danger text-center">{error}</div>
      </div>
    );
  }

  return (
    <div className="properties-section bg-light py-5">
      <div className="container">
        <div className="text-center mb-5">
          <h2 className="fw-bold text-primary">Our Properties</h2>
          <p className="text-muted">Find your dream property from our curated collection</p>
        </div>

        {properties.length === 0 ? (
          <div className="text-center py-5">
            <h4 className="text-muted">No properties available</h4>
            <p>Check back later for new listings</p>
          </div>
        ) : (
          <div className="row">
            {properties.map((p) => (
              <div key={p.id} className="col-lg-4 col-md-6 mb-4">
                <div className="card property-card h-100 shadow-sm border-0 overflow-hidden">
                  <div className="property-image-container">
                    <img
                      src={(p.images && p.images[0]) || '/assets/img/properties/property-1.jpg'}
                      alt={p.addressLine || 'Property'}
                      className="property-image card-img-top"
                    />
                    <div className="property-type-badge">{p.propertyType || '—'}</div>
                    <div className="property-price-badge">{toCurrency(p.price)}</div>
                  </div>

                  <div className="card-body">
                    <h5 className="card-title fw-bold">
                      {p.addressLine ? `${p.addressLine}` : 'No Address'}
                    </h5>
                    <p className="card-text text-muted">
                      {(p.city && p.state) ? `${p.city}, ${p.state}` : (p.city || p.state || '')}
                    </p>

                    <div className="d-flex justify-content-between border-top pt-3 mt-3">
                      <div className="property-feature">
                        <i className="fas fa-bed text-primary me-1"></i>
                        <span>{p.bedrooms ?? '—'} Beds</span>
                      </div>
                      <div className="property-feature">
                        <i className="fas fa-bath text-primary me-1"></i>
                        <span>{p.bathrooms ?? '—'} Baths</span>
                      </div>
                      <div className="property-feature">
                        <i className="fas fa-ruler-combined text-primary me-1"></i>
                        <span>{p.areaSqft ?? '—'} sq ft</span>
                      </div>
                    </div>
                  </div>

                  <div className="card-footer bg-white border-0 pt-0">
                    {/* Route consistency: use /properties/:id */}
                    <Link to={`/properties/${p.id}`} className="btn btn-primary w-100 rounded-pill">
                      View Details
                    </Link>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      <style>{`
        .property-card { transition: transform .3s ease, box-shadow .3s ease; border-radius: 12px; }
        .property-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,.1) !important; }
        .property-image-container { position: relative; overflow: hidden; height: 250px; }
        .property-image { height: 100%; width: 100%; object-fit: cover; transition: transform .5s ease; }
        .property-card:hover .property-image { transform: scale(1.05); }
        .property-type-badge { position: absolute; top: 15px; left: 15px; background: rgba(0,0,0,.7); color: #fff; padding: 5px 10px; border-radius: 20px; font-size: .8rem; }
        .property-price-badge { position: absolute; top: 15px; right: 15px; background: #0d6efd; color: white; padding: 5px 10px; border-radius: 20px; font-weight: bold; }
        .property-feature { font-size: .9rem; }
      `}</style>
    </div>
  );
}

export default PropertyList;

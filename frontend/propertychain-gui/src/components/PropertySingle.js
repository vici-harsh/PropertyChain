import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getPropertyById, getPropertyHistory } from '../services/api';

function toCurrency(n) {
  if (n == null) return '—';
  try { return new Intl.NumberFormat('en-CA', { style: 'currency', currency: 'CAD' }).format(n); }
  catch { return `${n}`; }
}

export default function PropertySingle() {
  const { id } = useParams();
  const [property, setProperty] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState('');

  useEffect(() => {
    (async function load() {
      setLoading(true);
      setErr('');
      try {
        const [prop, hist] = await Promise.all([
          getPropertyById(id),
          getPropertyHistory(id),
        ]);
        setProperty(prop || null);
        setHistory(hist || []);
      } catch (e) {
        setErr(e.message);
      } finally {
        setLoading(false);
      }
    })();
  }, [id]);

  if (loading) return <div className="container my-5">Loading...</div>;
  if (err) return <div className="container my-5 alert alert-danger">{err}</div>;
  if (!property) return <div className="container my-5">Not found.</div>;

  const image = (property.images && property.images[0]) || '/assets/img/properties/property-2.jpg';

  return (
    <section id="property-single" className="property-single">
      <div className="container">
        <div className="row g-4">
          <div className="col-lg-8">
            <img src={image} alt="Property" className="img-fluid rounded mb-3" />
            <h2 className="mb-2">{property.addressLine || 'Property'}</h2>
            <p className="text-muted mb-4">
              {(property.city && property.state) ? `${property.city}, ${property.state}` : (property.city || property.state || '')}
              {property.postalCode ? ` ${property.postalCode}` : ''}
            </p>
            <p>{property.description || 'No description provided.'}</p>
          </div>

          <div className="col-lg-4">
            <div className="card shadow-sm">
              <div className="card-body">
                <h5 className="card-title">Quick Summary</h5>
                <ul className="list-unstyled">
                  <li><strong>Price:</strong> {toCurrency(property.price)}</li>
                  <li><strong>Type:</strong> {property.type || '—'}</li>
                  <li><strong>Beds:</strong> {property.bedrooms ?? '—'}</li>
                  <li><strong>Baths:</strong> {property.bathrooms ?? '—'}</li>
                  <li><strong>Area:</strong> {property.areaSqft ?? '—'} sq ft</li>
                  <li><strong>Owner:</strong> {property.owner || '—'}</li>
                </ul>
                <div className="d-grid gap-2">
                  {/* Route consistency: use /properties/:id/... */}
                  <Link to={`/properties/${id}/transfer`} className="btn btn-outline-primary">Transfer Ownership</Link>
                  <Link to={`/properties/${id}/escrow`} className="btn btn-primary">Create Escrow</Link>
                </div>
              </div>
            </div>
          </div>
        </div>

        <h3 className="mt-5">Ownership & Escrow History</h3>
        <div className="table-responsive">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Type</th>
                <th>From</th>
                <th>To</th>
                <th>Tx</th>
                <th>Block</th>
                <th>At</th>
              </tr>
            </thead>
            <tbody>
              {history.length === 0 ? (
                <tr><td colSpan="6" className="text-muted">No history yet.</td></tr>
              ) : history.map((h, i) => (
                <tr key={i}>
                  <td>{h.type || '—'}</td>
                  <td>{h.from || '—'}</td>
                  <td>{h.to || '—'}</td>
                  <td><code>{h.tx || '—'}</code></td>
                  <td>{h.block ?? '—'}</td>
                  <td>{h.at || '—'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
}

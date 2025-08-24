import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getPropertyHistory, getPropertyById } from '../services/api';

export default function PropertyHistory() {
  const { id } = useParams();
  const [history, setHistory] = useState([]);
  const [prop, setProp] = useState(null);
  const [err, setErr] = useState('');

  useEffect(() => {
    (async () => {
      try {
        const [h, p] = await Promise.all([
          getPropertyHistory(id),
          getPropertyById(id),
        ]);
        setHistory(h || []);
        setProp(p || null);
      } catch (e) {
        setErr(e.message);
      }
    })();
  }, [id]);

  if (err) return <div className="container my-5 alert alert-danger">{err}</div>;

  return (
    <div className="container mt-5">
      <h2>History for Property {id}</h2>
      <p className="text-muted">{prop?.addressLine}</p>
      <div className="table-responsive">
        <table className="table table-striped">
          <thead><tr><th>Type</th><th>From</th><th>To</th><th>Tx</th><th>Block</th><th>At</th></tr></thead>
          <tbody>
            {history.length === 0 ? (
              <tr><td colSpan="6" className="text-muted">No history available</td></tr>
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
  );
}

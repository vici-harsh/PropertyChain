// src/TransferOwnership.js
import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { transferOwnership } from '../services/api';

export default function TransferOwnership() {
  const { id } = useParams();
  const [newOwner, setNewOwner] = useState('');
  const [res, setRes] = useState(null);
  const [err, setErr] = useState('');
  const [busy, setBusy] = useState(false);

  const onSubmit = async (e) => {
    e.preventDefault();
    setBusy(true); setErr(''); setRes(null);
    try {
      const data = await transferOwnership(id, newOwner);
      setRes(data);
    } catch (e2) {
      setErr(e2.message);
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="container my-5">
      <h2>Transfer Ownership (Property {id})</h2>
      <form className="row g-3" onSubmit={onSubmit}>
        <div className="col-md-6">
          <label className="form-label">New Owner (0x...)</label>
          <input className="form-control" value={newOwner} onChange={e => setNewOwner(e.target.value)} placeholder="0x..." required />
        </div>
        <div className="col-12">
          <button className="btn btn-primary" disabled={busy}>{busy ? 'Submitting...' : 'Submit Transfer'}</button>
        </div>
      </form>

      {err && <div className="alert alert-danger mt-3">{err}</div>}
      {res && (
        <div className="alert alert-success mt-3">
          <div><strong>Status:</strong> {res.status || 'OK'}</div>
          <div><strong>Tx Hash:</strong> <code>{res.txHash || '—'}</code></div>
        </div>
      )}
    </div>
  );
}

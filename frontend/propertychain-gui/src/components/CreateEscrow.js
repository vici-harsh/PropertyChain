import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { createEscrow, getCurrentAccount } from '../services/api';

export default function CreateEscrow() {
  const { id } = useParams();
  const [seller, setSeller] = useState(getCurrentAccount() || '');
  const [arbiter, setArbiter] = useState('');
  const [ethValue, setEthValue] = useState('0.01');      // user-friendly ETH
  const [unlockInSeconds, setUnlockInSeconds] = useState('3600'); // 1 hour default
  const [res, setRes] = useState(null);
  const [err, setErr] = useState('');
  const [busy, setBusy] = useState(false);

  const onSubmit = async (e) => {
    e.preventDefault();
    setBusy(true); setErr(''); setRes(null);
    try {
      const data = await createEscrow(id, { seller, arbiter, ethValue, unlockInSeconds });
      setRes(data);
    } catch (e2) {
      setErr(e2.message);
    } finally {
      setBusy(false);
    }
  };

  return (
    <div className="container my-5">
      <h2>Create Escrow (Property {id})</h2>
      <form className="row g-3" onSubmit={onSubmit}>
        <div className="col-md-6">
          <label className="form-label">Seller (0x...)</label>
          <input className="form-control" value={seller} onChange={e => setSeller(e.target.value)} placeholder="0x..." required />
        </div>
        <div className="col-md-6">
          <label className="form-label">Arbiter (0x...)</label>
          <input className="form-control" value={arbiter} onChange={e => setArbiter(e.target.value)} placeholder="0x..." required />
        </div>
        <div className="col-md-3">
          <label className="form-label">Amount (ETH)</label>
          <input type="number" step="0.0001" className="form-control" value={ethValue} onChange={e => setEthValue(e.target.value)} required />
        </div>
        <div className="col-md-3">
          <label className="form-label">Unlock in (seconds)</label>
          <input type="number" className="form-control" value={unlockInSeconds} onChange={e => setUnlockInSeconds(e.target.value)} min="0" required />
        </div>
        <div className="col-12">
          <button className="btn btn-primary" disabled={busy}>{busy ? 'Submitting...' : 'Create Escrow'}</button>
        </div>
      </form>

      {err && <div className="alert alert-danger mt-3">{err}</div>}
      {res && (
        <div className="alert alert-success mt-3">
          <div><strong>Escrow Address:</strong> <code>{res.escrowAddress || '—'}</code></div>
          <div><strong>Tx Hash:</strong> <code>{res.txHash || '—'}</code></div>
        </div>
      )}
    </div>
  );
}

import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { createEscrow } from '../services/api';

function CreateEscrow({ account }) {
  const { id } = useParams();
  const [seller, setSeller] = useState('');
  const [arbiter, setArbiter] = useState('');
  const [value, setValue] = useState('');
  const [releaseTime, setReleaseTime] = useState('');

  const handleSubmit = () => {
    createEscrow(id, seller, arbiter, value, releaseTime).then(res => alert(res.data));
  };

  return (
    <div className="container mt-5">
      <h2>Create Escrow for Property {id}</h2>
      <input placeholder="Seller Address" value={seller} onChange={(e) => setSeller(e.target.value)} className="form-control mb-2" />
      <input placeholder="Arbiter Address" value={arbiter} onChange={(e) => setArbiter(e.target.value)} className="form-control mb-2" />
      <input placeholder="Value (wei)" value={value} onChange={(e) => setValue(e.target.value)} className="form-control mb-2" />
      <input placeholder="Release Time (timestamp)" value={releaseTime} onChange={(e) => setReleaseTime(e.target.value)} className="form-control mb-2" />
      <button onClick={handleSubmit} className="btn btn-primary">Create</button>
    </div>
  );
}

export default CreateEscrow;
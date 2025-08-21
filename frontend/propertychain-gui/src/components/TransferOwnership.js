import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import { transferOwnership } from '../services/api';

function TransferOwnership({ account }) {
  const { id } = useParams();
  const [newOwner, setNewOwner] = useState('');

  const handleSubmit = () => {
    transferOwnership(id, newOwner).then(res => alert(res.data));
  };

  return (
    <div className="container mt-5">
      <h2>Transfer Property {id}</h2>
      <input placeholder="New Owner Address" value={newOwner} onChange={(e) => setNewOwner(e.target.value)} className="form-control mb-2" />
      <button onClick={handleSubmit} className="btn btn-primary">Transfer</button>
    </div>
  );
}

export default TransferOwnership;
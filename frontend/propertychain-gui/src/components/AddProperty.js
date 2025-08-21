import React, { useState } from 'react';
import { addProperty } from '../services/api';

function AddProperty({ account }) {
  const [address, setAddress] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit = () => {
    addProperty(address, description).then(res => alert(res.data));
  };

  return (
    <div className="container mt-5">
      <h2>Add Property</h2>
      <input placeholder="Address" value={address} onChange={(e) => setAddress(e.target.value)} className="form-control mb-2" />
      <input placeholder="Description" value={description} onChange={(e) => setDescription(e.target.value)} className="form-control mb-2" />
      <button onClick={handleSubmit} className="btn btn-primary">Add</button>
    </div>
  );
}

export default AddProperty;
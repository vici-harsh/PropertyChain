import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { addProperty, getCurrentAccount } from '../services/api';

const initialState = {
  addressLine: '',
  city: '',
  state: '',
  postalCode: '',
  description: '',
  price: '',
  propertyType: 'Condo',
  bedrooms: '',
  bathrooms: '',
  areaSqft: '',
  lat: '',
  lng: '',
};

const types = ['Apartment', 'House', 'Condo', 'Townhouse', 'Villa', 'Studio'];

export default function AddProperty({ account }) {
  const [form, setForm] = useState(initialState);
  const [images, setImages] = useState(['']); // image URLs as strings
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const selectedAccount = account || getCurrentAccount() || '';

  const onChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const onImageChange = (idx, value) => {
    setImages((arr) => {
      const next = arr.slice();
      next[idx] = value;
      return next;
    });
  };

  const addImageField = () => setImages((arr) => [...arr, '']);
  const removeImageField = (idx) =>
    setImages((arr) => arr.filter((_, i) => i !== idx));

  const onSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError('');
    try {
      const payload = {
        ...form,
        price: form.price ? Number(form.price) : 0,
        bedrooms: form.bedrooms ? Number(form.bedrooms) : 0,
        bathrooms: form.bathrooms ? Number(form.bathrooms) : 0,
        areaSqft: form.areaSqft ? Number(form.areaSqft) : 0,
        lat: form.lat ? Number(form.lat) : undefined,
        lng: form.lng ? Number(form.lng) : undefined,
        ownerWallet: selectedAccount || undefined,
        images: images
          .map((u) => (u || '').trim())
          .filter((u) => u.length > 0), // only send non-empty URLs
      };

      const created = await addProperty(payload);
      navigate(`/properties/${created.id}`);
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="container my-5">
      <h2 className="mb-4">Add Property</h2>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={onSubmit} className="row g-3">
        <div className="col-md-8">
          <label className="form-label">Address Line</label>
          <input name="addressLine" className="form-control" value={form.addressLine} onChange={onChange} required />
        </div>
        <div className="col-md-4">
          <label className="form-label">City</label>
          <input name="city" className="form-control" value={form.city} onChange={onChange} required />
        </div>
        <div className="col-md-4">
          <label className="form-label">State/Province</label>
          <input name="state" className="form-control" value={form.state} onChange={onChange} required />
        </div>
        <div className="col-md-4">
          <label className="form-label">Postal Code</label>
          <input name="postalCode" className="form-control" value={form.postalCode} onChange={onChange} required />
        </div>
        <div className="col-md-4">
          <label className="form-label">Type</label>
          <select name="propertyType" className="form-select" value={form.propertyType} onChange={onChange}>
            {types.map(t => <option key={t} value={t}>{t}</option>)}
          </select>
        </div>

        <div className="col-md-3">
          <label className="form-label">Bedrooms</label>
          <input name="bedrooms" type="number" className="form-control" value={form.bedrooms} onChange={onChange} min="0" />
        </div>
        <div className="col-md-3">
          <label className="form-label">Bathrooms</label>
          <input name="bathrooms" type="number" className="form-control" value={form.bathrooms} onChange={onChange} min="0" />
        </div>
        <div className="col-md-3">
          <label className="form-label">Area (sq ft)</label>
          <input name="areaSqft" type="number" className="form-control" value={form.areaSqft} onChange={onChange} min="0" />
        </div>
        <div className="col-md-3">
          <label className="form-label">Price (CAD)</label>
          <input name="price" type="number" className="form-control" value={form.price} onChange={onChange} min="0" />
        </div>

        <div className="col-12">
          <label className="form-label">Description</label>
          <textarea name="description" className="form-control" rows="3" value={form.description} onChange={onChange} />
        </div>

        <div className="col-md-3">
          <label className="form-label">Latitude (optional)</label>
          <input name="lat" type="number" step="0.000001" className="form-control" value={form.lat} onChange={onChange} />
        </div>
        <div className="col-md-3">
          <label className="form-label">Longitude (optional)</label>
          <input name="lng" type="number" step="0.000001" className="form-control" value={form.lng} onChange={onChange} />
        </div>

        <div className="col-12">
          <label className="form-label">Images (URLs)</label>
          {images.map((u, idx) => (
            <div className="input-group mb-2" key={idx}>
              <input
                className="form-control"
                placeholder="https://example.com/photo.jpg"
                value={u}
                onChange={(e) => onImageChange(idx, e.target.value)}
              />
              <button type="button" className="btn btn-outline-danger" onClick={() => removeImageField(idx)} disabled={images.length === 1}>
                Remove
              </button>
            </div>
          ))}
          <button type="button" className="btn btn-outline-secondary btn-sm" onClick={addImageField}>
            + Add another image
          </button>
        </div>

        <div className="col-12">
          <div className="form-text mb-2">
            Owner (your account): {selectedAccount ? `${selectedAccount.slice(0,6)}...${selectedAccount.slice(-4)}` : 'Not selected'}
          </div>
          <button type="submit" className="btn btn-primary" disabled={saving || !selectedAccount}>
            {saving ? 'Saving...' : 'Create Property'}
          </button>
          {!selectedAccount && (
            <div className="form-text text-danger mt-2">
              Please select your Ganache account first (Account / Login page).
            </div>
          )}
        </div>
      </form>
    </div>
  );
}

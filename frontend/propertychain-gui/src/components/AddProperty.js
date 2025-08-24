import React, { useState } from 'react';
import { addProperty } from '../services/api';
import { Card, Button, Form } from 'react-bootstrap';

function AddProperty({ account }) {
  const [address, setAddress] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit = () => {
    addProperty(address, description).then(res => alert(res.data));
  };

  return (
    <div className="container my-5">
      <Card className="shadow-lg border-0 rounded-4">
        <Card.Body>
          <h3 className="mb-4 text-primary fw-bold">Add New Property</h3>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Address</Form.Label>
              <Form.Control 
                type="text" 
                placeholder="Enter property address" 
                value={address} 
                onChange={(e) => setAddress(e.target.value)} 
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control 
                as="textarea" 
                rows={3} 
                placeholder="Property description" 
                value={description} 
                onChange={(e) => setDescription(e.target.value)} 
              />
            </Form.Group>
            <Button 
              variant="primary" 
              className="px-4 py-2 rounded-pill shadow-sm" 
              onClick={handleSubmit}
            >
              Add Property
            </Button>
          </Form>
        </Card.Body>
      </Card>
    </div>
  );
}

export default AddProperty;

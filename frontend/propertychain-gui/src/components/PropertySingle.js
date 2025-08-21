import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getPropertyHistory } from '../services/api';

function PropertySingle({ account }) {
  const { id } = useParams();
  const [history, setHistory] = useState([]);

  useEffect(() => {
    getPropertyHistory(id).then(res => setHistory(res.data));
  }, [id]);

  return (
    <section id="property-single">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h2>Property Details for ID {id}</h2>
            <p>This is an example of property details.</p>
            <ul>
              {history.map((h, i) => <li key={i}>{h}</li>)}
            </ul>
          </div>
        </div>
      </div>
    </section>
  );
}

export default PropertySingle;
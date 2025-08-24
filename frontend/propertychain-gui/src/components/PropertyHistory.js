// PropertyHistory.js - No major changes needed, as it aligns with backend getHistory
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getPropertyHistory } from '../services/api';

function PropertyHistory({ account }) {
  const { id } = useParams();
  const [history, setHistory] = useState([]);

  useEffect(() => {
    getPropertyHistory(id).then(res => setHistory(res.data));
  }, [id]);

  return (
    <div className="container mt-5">
      <h2>History for Property {id}</h2>
      <ul>
        {history.map((item, index) => <li key={index}>{item}</li>)}
      </ul>
    </div>
  );
}

export default PropertyHistory;
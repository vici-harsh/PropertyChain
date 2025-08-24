import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';

// Vendor CSS (keep CSS, not JS versions, to avoid double init)
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/css/main.css';

const container = document.getElementById('root');
const root = createRoot(container);
root.render(<App />);

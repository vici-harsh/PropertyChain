import React, { useState } from 'react';
import Web3 from 'web3';

function Login({ setAccount }) {
  const [privateKey, setPrivateKey] = useState('');

  const handleLogin = () => {
    const web3 = new Web3('http://localhost:7545');
    const account = web3.eth.accounts.privateKeyToAccount(privateKey);
    setAccount(account.address);
    localStorage.setItem('privateKey', privateKey); // Secure in production
  };

  return (
    <div className="container mt-5">
      <h2>Login with Private Key</h2>
      <input type="password" placeholder="Private Key" value={privateKey} onChange={(e) => setPrivateKey(e.target.value)} className="form-control" />
      <button onClick={handleLogin} className="btn btn-primary mt-2">Login</button>
    </div>
  );
}

export default Login;
// src/services/api.js
// Minimal fetch wrapper for your Spring backend.
// Configure base URL in .env: REACT_APP_API_BASE_URL=http://127.0.0.1:8080

const BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://127.0.0.1:8080';

// ---- Current account helpers (Ganache address) ----
export const ACCOUNT_KEY = 'current_eth_account';
export const getCurrentAccount = () => localStorage.getItem(ACCOUNT_KEY) || '';
export const setCurrentAccount = (addr) => localStorage.setItem(ACCOUNT_KEY, addr);
export const clearCurrentAccount = () => localStorage.removeItem(ACCOUNT_KEY);

// Internal HTTP helper; attaches X-Account for debugging on the backend
async function http(path, { method = 'GET', body, headers } = {}) {
  const acct = getCurrentAccount();
  const res = await fetch(`${BASE_URL}${path}`, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(acct ? { 'X-Account': acct } : {}),
      ...(headers || {})
    },
    body: body ? JSON.stringify(body) : undefined,
  });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(`HTTP ${res.status} ${res.statusText}: ${text}`);
  }
  const ct = res.headers.get('content-type') || '';
  return ct.includes('application/json') ? res.json() : res.text();
}

// ------- Properties (all under /api/properties) -------
export function addProperty(payload) {
  // POST /api/properties
  return http('/api/properties', { method: 'POST', body: payload });
}

export function getAllProperties() {
  // GET /api/properties
  return http('/api/properties');
}

export function getPropertyById(id) {
  // GET /api/properties/{id}
  return http(`/api/properties/${id}`);
}

export function getPropertyHistory(id) {
  // GET /api/properties/{id}/history
  return http(`/api/properties/${id}/history`);
}

export function transferOwnership(id, newOwner) {
  // POST /api/properties/{id}/transfer
  return http(`/api/properties/${id}/transfer`, {
    method: 'POST',
    body: { newOwner }
  });
}

// Escrow: UI collects ETH + unlockInSeconds; convert here to wei + absolute epoch
export function createEscrow(id, { seller, arbiter, ethValue, unlockInSeconds }) {
  // POST /api/properties/{id}/escrow
  const wei = ethToWei(ethValue);
  const nowSec = Math.floor(Date.now() / 1000);
  const releaseTime = nowSec + Number(unlockInSeconds || 0);

  return http(`/api/properties/${id}/escrow`, {
    method: 'POST',
    body: {
      seller,
      arbiter,
      valueWei: wei.toString(), // BigInt string is fine for BigInteger
      releaseTime
    }
  });
}

// precise ETH->wei without float loss
function ethToWei(eth) {
  const s = String(eth ?? '0').trim();
  if (!s) return 0n;
  const [intPart, fracPart = ''] = s.split('.');
  const frac = (fracPart + '000000000000000000').slice(0, 18);
  return (BigInt(intPart || '0') * 10n ** 18n) + BigInt(frac || '0');
}

import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/properties';
const AUTH = { headers: { Authorization: 'Basic ' + btoa('admin:secret') } };

export const addProperty = async (address, description) => axios.post(BASE_URL, { address, description }, AUTH);
export const transferOwnership = async (id, newOwner) => axios.post(`${BASE_URL}/${id}/transfer`, { newOwner }, AUTH);
export const createEscrow = async (id, seller, arbiter, value, releaseTime) => axios.post(`${BASE_URL}/${id}/escrow`, { seller, arbiter, value, releaseTime }, AUTH);
export const getAllProperties = async () => axios.get(BASE_URL, AUTH);
export const getPropertyHistory = async (id) => axios.get(`${BASE_URL}/${id}/history`, AUTH);
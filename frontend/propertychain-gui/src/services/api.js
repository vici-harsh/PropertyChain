// Updated api.js - Added getPropertyById if needed, but since backend lacks it, using getAllProperties in components; no change here
import axios from 'axios';
import { ethers } from 'ethers';

const BASE_URL = 'http://localhost:8080/api/properties';
const AUTH = { headers: { Authorization: 'Basic ' + btoa('admin:secret') } };

export const addProperty = async (address, description) => {
  return axios.post(BASE_URL, { address, description }, AUTH);
};

export const transferOwnership = async (id, newOwner) => {
  return axios.post(`${BASE_URL}/${id}/transfer`, { newOwner }, AUTH);
};

export const createEscrow = async (id, seller, arbiter, value, releaseTime) => {
  return axios.post(`${BASE_URL}/${id}/escrow`, {
    seller,
    arbiter,
    value: ethers.parseEther(value.toString()).toString(), // Convert ETH to wei
    releaseTime: (Math.floor(Date.now() / 1000) + parseInt(releaseTime)).toString() // Unix timestamp
  }, AUTH);
};

export const getAllProperties = async () => {
  return axios.get(BASE_URL, AUTH);
};

export const getPropertyHistory = async (id) => {
  return axios.get(`${BASE_URL}/${id}/history`, AUTH);
};
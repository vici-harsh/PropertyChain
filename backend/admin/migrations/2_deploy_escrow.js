const fs = require('fs');
const path = require('path');
const PropertyEscrow = artifacts.require("PropertyEscrow");

module.exports = async function (deployer, network, accounts) {
  const seller = accounts[1];  // Use Ganache account 1 as seller
  const arbiter = accounts[2];  // Use Ganache account 2 as arbiter
  const propertyId = 1;  // From the sample property added in previous migration
  const releaseTime = Math.floor(Date.now() / 1000) + (60 * 60 * 24 * 7);  // Current time + 7 days (in seconds)

  // Deploy PropertyEscrow with params
  await deployer.deploy(PropertyEscrow, seller, arbiter, propertyId, releaseTime, { from: accounts[0], value: web3.utils.toWei('1', 'ether') });  // Send 1 ETH as initial value (adjust as needed)
  const escrow = await PropertyEscrow.deployed();
  console.log(`Deployed PropertyEscrow at: ${escrow.address} with seller=${seller}, arbiter=${arbiter}, propertyId=${propertyId}, releaseTime=${releaseTime}`);

  // Automatically update .env with escrow address
  const envPath = path.join(__dirname, '..', '.env');  // Assumes .env in project root
  let envContent = fs.readFileSync(envPath, 'utf8');
  envContent = envContent.replace(/ethereum.escrow-contract-address=.*/g, `ethereum.escrow-contract-address=${escrow.address}`);
  fs.writeFileSync(envPath, envContent);
  console.log(`Updated .env with ethereum.escrow-contract-address=${escrow.address}`);
};
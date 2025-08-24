const fs = require('fs');
const path = require('path');
const PropertyOwnership = artifacts.require("PropertyOwnership");

module.exports = async function (deployer, network, accounts) {
  await deployer.deploy(PropertyOwnership, { gas: 672197 });
  const ownership = await PropertyOwnership.deployed();
  console.log(`Deployed PropertyOwnership at: ${ownership.address}`);

  const dummyOwner = accounts[0];
  const dummyAddress = web3.utils.asciiToHex("123 Fake Street"); // Convert to bytes32
  const dummyDescription = web3.utils.asciiToHex("Test Property Description"); // Convert to bytes32
  await ownership.addProperty(dummyOwner, dummyAddress, dummyDescription, { from: accounts[0], gas: 672197 });
  console.log(`Added sample property with ID 1`);

  const envPath = path.join(__dirname, '..', '.env');
  let envContent = fs.readFileSync(envPath, 'utf8');
  envContent = envContent.replace(/ethereum.ownership-contract-address=.*/g, `ethereum.ownership-contract-address=${ownership.address}`);
  fs.writeFileSync(envPath, envContent);
  console.log(`Updated .env with ethereum.ownership-contract-address=${ownership.address}`);
};
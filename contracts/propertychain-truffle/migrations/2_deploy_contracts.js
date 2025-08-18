const PropertyOwnership = artifacts.require("PropertyOwnership");
const PropertyEscrow = artifacts.require("PropertyEscrow");

module.exports = async function (deployer, network, accounts) {
  // Deploy PropertyOwnership first
  await deployer.deploy(PropertyOwnership);
  const po = await PropertyOwnership.deployed();

  // Optionally: add an initial property (from contractOwner)
  await po.addProperty(
    accounts[0],
    "123 Main St",
    "Test Property",
    { from: accounts[0] }
  );

  // Deploy PropertyEscrow with 1 ETH
  const releaseTime = Math.floor(Date.now() / 1000) + 3600; // 1 hour from now
  await deployer.deploy(
    PropertyEscrow,
    accounts[1], // seller
    accounts[2], // arbiter
    1,          // propertyId
    releaseTime,
    { value: web3.utils.toWei("1", "ether") }
  );
};

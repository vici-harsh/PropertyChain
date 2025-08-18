const PropertyOwnership = artifacts.require("PropertyOwnership");
const PropertyEscrow = artifacts.require("PropertyEscrow");

module.exports = function (deployer) {
  deployer.deploy(PropertyOwnership);
  deployer.deploy(PropertyEscrow, "0xBc44B4F6b1E57d60639945A752E7225cA17702Ba", "0x2CE754fEad1dbDDFc5b5823fC51Dd380e668910a", 1, Math.floor(Date.now() / 1000) + 3600, { value: web3.utils.toWei("1", "ether") });
};
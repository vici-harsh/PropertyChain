module.exports = {
  networks: {
    development: {
      host: "127.0.0.1",
      port: 7545,
      network_id: "*", // Match any network id
      gas: 30000000,    // Increased gas limit
      gasPrice: 20000000000
    },
    ganache: {
      host: "127.0.0.1",
      port: 7545,
      network_id: "*",
      gas: 30000000,    // Increased gas limit
      gasPrice: 20000000000
    }
  },
  compilers: {
    solc: {
      version: "0.8.19",
      settings: {
        optimizer: {
          enabled: true,
          runs: 200,
          details: {
            yul: true,  // Enable Yul optimizer
            yulDetails: {
              stackAllocation: true
            }
          }
        },
        evmVersion: "london",
        viaIR: true     // Use intermediate representation for better optimization
      }
    }
  },
  mocha: {
    timeout: 100000
  }
};
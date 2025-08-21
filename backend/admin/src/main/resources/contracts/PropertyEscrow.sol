// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract PropertyEscrow {
    address public buyer;
    address public seller;
    address public arbiter; // Optional for manual disputes
    uint256 public propertyId;
    uint256 public amount;
    uint256 public releaseTime; // Timestamp for auto-release
    bool public fundsReleased;

    event FundsDeposited(address buyer, uint256 amount);
    event FundsReleased(address seller, uint256 amount);
    event DisputeResolved(address to, uint256 amount);

    constructor(address _seller, address _arbiter, uint256 _propertyId, uint256 _releaseTime) payable {
        buyer = msg.sender;
        seller = _seller;
        arbiter = _arbiter;
        propertyId = _propertyId;
        amount = msg.value;
        releaseTime = _releaseTime;
        emit FundsDeposited(buyer, amount);
    }

    function releaseFunds() external {
        require(block.timestamp >= releaseTime, "Too early");
        require(!fundsReleased, "Already released");
        require(msg.sender == buyer || msg.sender == seller || msg.sender == arbiter, "Unauthorized");
        payable(seller).transfer(amount);
        fundsReleased = true;
        emit FundsReleased(seller, amount);
    }

    function resolveDispute(address payable _to) external {
        require(msg.sender == arbiter, "Only arbiter");
        require(!fundsReleased, "Already released");
        _to.transfer(amount);
        fundsReleased = true;
        emit DisputeResolved(_to, amount);
    }

    // Automated refund if timeout without release (dispute resolution)
    function refundBuyer() external {
        require(block.timestamp > releaseTime + 7 days, "Dispute window open");
        require(!fundsReleased, "Already released");
        require(msg.sender == buyer, "Only buyer");
        payable(buyer).transfer(amount);
        fundsReleased = true;
        emit DisputeResolved(buyer, amount);
    }
}
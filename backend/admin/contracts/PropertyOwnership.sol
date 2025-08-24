// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract PropertyOwnership {
    address public contractOwner;

    struct PropertyDetails {
        uint256 propertyId;
        address currentOwner;
        string propertyAddress; // Changed from bytes32 to string
        string description;     // Changed from bytes32 to string
        address[] previousOwners;
    }

    mapping(uint256 => PropertyDetails) public properties;
    uint256 public propertyCount;

    event OwnershipTransferred(uint256 propertyId, address from, address to);

    constructor() {
        contractOwner = msg.sender;
    }

    function addProperty(address _currentOwner, string memory _propertyAddress, string memory _description) public returns (bool) {
        require(msg.sender == contractOwner || msg.sender == _currentOwner, "Unauthorized");
        propertyCount++;
        address[] memory prev = new address[](0);
        properties[propertyCount] = PropertyDetails(propertyCount, _currentOwner, _propertyAddress, _description, prev);
        if (properties[propertyCount].propertyId != propertyCount) revert("Property ID mismatch");
        return true;
    }

    function transferOwnership(uint256 _propertyId, address _newOwner) public returns (bool) {
        require(properties[_propertyId].currentOwner == msg.sender, "Not the owner");
        require(_newOwner != address(0) && _newOwner != properties[_propertyId].currentOwner, "Invalid transfer");
        properties[_propertyId].previousOwners.push(properties[_propertyId].currentOwner);
        properties[_propertyId].currentOwner = _newOwner;
        emit OwnershipTransferred(_propertyId, msg.sender, _newOwner);
        return true;
    }

    function getPropertyDetails(uint256 _propertyId) public view returns (string memory, address, address[] memory, string memory) {
        PropertyDetails memory prop = properties[_propertyId];
        return (prop.propertyAddress, prop.currentOwner, prop.previousOwners, prop.description);
    }

    function getCurrentOwner(uint256 _propertyId) public view returns (address) {
        return properties[_propertyId].currentOwner;
    }
}
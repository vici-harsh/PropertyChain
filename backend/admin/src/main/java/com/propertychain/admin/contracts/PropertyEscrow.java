package com.propertychain.admin.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.9.7.
 */
@SuppressWarnings("rawtypes")
public class PropertyEscrow extends Contract {
    public static final String BINARY = "6080604052604051610ff4380380610ff4833981810160405281019061002591906101ee565b335f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508360015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508260025f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160038190555034600481905550806005819055507f543ba50a5eec5e6178218e364b1d0f396157b3c8fa278522c2cb7fd99407d4745f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660045460405161014c929190610270565b60405180910390a150505050610297565b5f5ffd5b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f61018a82610161565b9050919050565b61019a81610180565b81146101a4575f5ffd5b50565b5f815190506101b581610191565b92915050565b5f819050919050565b6101cd816101bb565b81146101d7575f5ffd5b50565b5f815190506101e8816101c4565b92915050565b5f5f5f5f608085870312156102065761020561015d565b5b5f610213878288016101a7565b9450506020610224878288016101a7565b9350506040610235878288016101da565b9250506060610246878288016101da565b91505092959194509250565b61025b81610180565b82525050565b61026a816101bb565b82525050565b5f6040820190506102835f830185610252565b6102906020830184610261565b9392505050565b610d50806102a45f395ff3fe608060405234801561000f575f5ffd5b506004361061009c575f3560e01c8063b91d400111610064578063b91d400114610122578063d846118214610140578063e8a61cc81461015c578063ee7a3b8e14610166578063fe25e00a146101845761009c565b806308551a53146100a057806369d89575146100be5780637150d8ae146100c8578063aa8c217c146100e6578063b564e92614610104575b5f5ffd5b6100a86101a2565b6040516100b591906108aa565b60405180910390f35b6100c66101c7565b005b6100d0610476565b6040516100dd91906108aa565b60405180910390f35b6100ee61049a565b6040516100fb91906108db565b60405180910390f35b61010c6104a0565b604051610119919061090e565b60405180910390f35b61012a6104b2565b60405161013791906108db565b60405180910390f35b61015a60048036038101906101559190610966565b6104b8565b005b610164610634565b005b61016e610840565b60405161017b91906108db565b60405180910390f35b61018c610846565b60405161019991906108aa565b60405180910390f35b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60055442101561020c576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610203906109eb565b60405180910390fd5b60065f9054906101000a900460ff161561025b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161025290610a53565b60405180910390fd5b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161480610301575060015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b80610358575060025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b610397576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038e90610abb565b60405180910390fd5b60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc60045490811502906040515f60405180830381858888f193505050501580156103fd573d5f5f3e3d5ffd5b50600160065f6101000a81548160ff0219169083151502179055507f221c08a06b07a64803b3787861a3f276212fcccb51c2e6234077a9b8cb13047a60015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660045460405161046c929190610ad9565b60405180910390a1565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60045481565b60065f9054906101000a900460ff1681565b60055481565b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610547576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161053e90610b4a565b60405180910390fd5b60065f9054906101000a900460ff1615610596576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161058d90610a53565b60405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff166108fc60045490811502906040515f60405180830381858888f193505050501580156105db573d5f5f3e3d5ffd5b50600160065f6101000a81548160ff0219169083151502179055507ff396531fad1d4dc2cd92386cdea800c92a507f8fe1bdb80313eb32a57481b1dc81600454604051610629929190610bc3565b60405180910390a150565b62093a806005546106459190610c17565b4211610686576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161067d90610c94565b60405180910390fd5b60065f9054906101000a900460ff16156106d5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016106cc90610a53565b60405180910390fd5b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610763576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161075a90610cfc565b60405180910390fd5b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc60045490811502906040515f60405180830381858888f193505050501580156107c8573d5f5f3e3d5ffd5b50600160065f6101000a81548160ff0219169083151502179055507ff396531fad1d4dc2cd92386cdea800c92a507f8fe1bdb80313eb32a57481b1dc5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600454604051610836929190610ad9565b60405180910390a1565b60035481565b60025f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f6108948261086b565b9050919050565b6108a48161088a565b82525050565b5f6020820190506108bd5f83018461089b565b92915050565b5f819050919050565b6108d5816108c3565b82525050565b5f6020820190506108ee5f8301846108cc565b92915050565b5f8115159050919050565b610908816108f4565b82525050565b5f6020820190506109215f8301846108ff565b92915050565b5f5ffd5b5f6109358261086b565b9050919050565b6109458161092b565b811461094f575f5ffd5b50565b5f813590506109608161093c565b92915050565b5f6020828403121561097b5761097a610927565b5b5f61098884828501610952565b91505092915050565b5f82825260208201905092915050565b7f546f6f206561726c7900000000000000000000000000000000000000000000005f82015250565b5f6109d5600983610991565b91506109e0826109a1565b602082019050919050565b5f6020820190508181035f830152610a02816109c9565b9050919050565b7f416c72656164792072656c6561736564000000000000000000000000000000005f82015250565b5f610a3d601083610991565b9150610a4882610a09565b602082019050919050565b5f6020820190508181035f830152610a6a81610a31565b9050919050565b7f556e617574686f72697a656400000000000000000000000000000000000000005f82015250565b5f610aa5600c83610991565b9150610ab082610a71565b602082019050919050565b5f6020820190508181035f830152610ad281610a99565b9050919050565b5f604082019050610aec5f83018561089b565b610af960208301846108cc565b9392505050565b7f4f6e6c79206172626974657200000000000000000000000000000000000000005f82015250565b5f610b34600c83610991565b9150610b3f82610b00565b602082019050919050565b5f6020820190508181035f830152610b6181610b28565b9050919050565b5f819050919050565b5f610b8b610b86610b818461086b565b610b68565b61086b565b9050919050565b5f610b9c82610b71565b9050919050565b5f610bad82610b92565b9050919050565b610bbd81610ba3565b82525050565b5f604082019050610bd65f830185610bb4565b610be360208301846108cc565b9392505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f610c21826108c3565b9150610c2c836108c3565b9250828201905080821115610c4457610c43610bea565b5b92915050565b7f446973707574652077696e646f77206f70656e000000000000000000000000005f82015250565b5f610c7e601383610991565b9150610c8982610c4a565b602082019050919050565b5f6020820190508181035f830152610cab81610c72565b9050919050565b7f4f6e6c79206275796572000000000000000000000000000000000000000000005f82015250565b5f610ce6600a83610991565b9150610cf182610cb2565b602082019050919050565b5f6020820190508181035f830152610d1381610cda565b905091905056fea264697066735822122039131ffdc00716467d295604753c277aaee7f6a62fffe209bddb78ccf8dabc9b64736f6c634300081e0033";

    public static final String FUNC_AMOUNT = "amount";

    public static final String FUNC_ARBITER = "arbiter";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_FUNDSRELEASED = "fundsReleased";

    public static final String FUNC_PROPERTYID = "propertyId";

    public static final String FUNC_REFUNDBUYER = "refundBuyer";

    public static final String FUNC_RELEASEFUNDS = "releaseFunds";

    public static final String FUNC_RELEASETIME = "releaseTime";

    public static final String FUNC_RESOLVEDISPUTE = "resolveDispute";

    public static final String FUNC_SELLER = "seller";

    public static final Event DISPUTERESOLVED_EVENT = new Event("DisputeResolved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDSDEPOSITED_EVENT = new Event("FundsDeposited", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FUNDSRELEASED_EVENT = new Event("FundsReleased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected PropertyEscrow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PropertyEscrow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PropertyEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PropertyEscrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<DisputeResolvedEventResponse> getDisputeResolvedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(DISPUTERESOLVED_EVENT, transactionReceipt);
        ArrayList<DisputeResolvedEventResponse> responses = new ArrayList<DisputeResolvedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DisputeResolvedEventResponse typedResponse = new DisputeResolvedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DisputeResolvedEventResponse getDisputeResolvedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(DISPUTERESOLVED_EVENT, log);
        DisputeResolvedEventResponse typedResponse = new DisputeResolvedEventResponse();
        typedResponse.log = log;
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<DisputeResolvedEventResponse> disputeResolvedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getDisputeResolvedEventFromLog(log));
    }

    public Flowable<DisputeResolvedEventResponse> disputeResolvedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DISPUTERESOLVED_EVENT));
        return disputeResolvedEventFlowable(filter);
    }

    public static List<FundsDepositedEventResponse> getFundsDepositedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNDSDEPOSITED_EVENT, transactionReceipt);
        ArrayList<FundsDepositedEventResponse> responses = new ArrayList<FundsDepositedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundsDepositedEventResponse typedResponse = new FundsDepositedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FundsDepositedEventResponse getFundsDepositedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNDSDEPOSITED_EVENT, log);
        FundsDepositedEventResponse typedResponse = new FundsDepositedEventResponse();
        typedResponse.log = log;
        typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<FundsDepositedEventResponse> fundsDepositedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFundsDepositedEventFromLog(log));
    }

    public Flowable<FundsDepositedEventResponse> fundsDepositedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDSDEPOSITED_EVENT));
        return fundsDepositedEventFlowable(filter);
    }

    public static List<FundsReleasedEventResponse> getFundsReleasedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FUNDSRELEASED_EVENT, transactionReceipt);
        ArrayList<FundsReleasedEventResponse> responses = new ArrayList<FundsReleasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FundsReleasedEventResponse typedResponse = new FundsReleasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FundsReleasedEventResponse getFundsReleasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FUNDSRELEASED_EVENT, log);
        FundsReleasedEventResponse typedResponse = new FundsReleasedEventResponse();
        typedResponse.log = log;
        typedResponse.seller = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<FundsReleasedEventResponse> fundsReleasedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFundsReleasedEventFromLog(log));
    }

    public Flowable<FundsReleasedEventResponse> fundsReleasedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDSRELEASED_EVENT));
        return fundsReleasedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> amount() {
        final Function function = new Function(FUNC_AMOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> arbiter() {
        final Function function = new Function(FUNC_ARBITER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> buyer() {
        final Function function = new Function(FUNC_BUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> fundsReleased() {
        final Function function = new Function(FUNC_FUNDSRELEASED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> propertyId() {
        final Function function = new Function(FUNC_PROPERTYID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refundBuyer() {
        final Function function = new Function(
                FUNC_REFUNDBUYER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> releaseFunds() {
        final Function function = new Function(
                FUNC_RELEASEFUNDS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> releaseTime() {
        final Function function = new Function(FUNC_RELEASETIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> resolveDispute(String _to) {
        final Function function = new Function(
                FUNC_RESOLVEDISPUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> seller() {
        final Function function = new Function(FUNC_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static PropertyEscrow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertyEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PropertyEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertyEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PropertyEscrow load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PropertyEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PropertyEscrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PropertyEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PropertyEscrow> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String _seller, String _arbiter, BigInteger _propertyId, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _arbiter), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(PropertyEscrow.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<PropertyEscrow> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String _seller, String _arbiter, BigInteger _propertyId, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _arbiter), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(PropertyEscrow.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<PropertyEscrow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _seller, String _arbiter, BigInteger _propertyId, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _arbiter), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(PropertyEscrow.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<PropertyEscrow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String _seller, String _arbiter, BigInteger _propertyId, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _arbiter), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(PropertyEscrow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    public static class DisputeResolvedEventResponse extends BaseEventResponse {
        public String to;

        public BigInteger amount;
    }

    public static class FundsDepositedEventResponse extends BaseEventResponse {
        public String buyer;

        public BigInteger amount;
    }

    public static class FundsReleasedEventResponse extends BaseEventResponse {
        public String seller;

        public BigInteger amount;
    }
}

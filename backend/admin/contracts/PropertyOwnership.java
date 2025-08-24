package com.propertychain.admin.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
import org.web3j.tuples.generated.Tuple4;
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
public class PropertyOwnership extends Contract {
    public static final String BINARY = "Your contract bytecode here";

    public static final String FUNC_ADDPROPERTY = "addProperty";
    public static final String FUNC_CONTRACTOWNER = "contractOwner";
    public static final String FUNC_GETCURRENTOWNER = "getCurrentOwner";
    public static final String FUNC_GETPROPERTYDETAILS = "getPropertyDetails";
    public static final String FUNC_PROPERTIES = "properties";
    public static final String FUNC_PROPERTYCOUNT = "propertyCount";
    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));

    @Deprecated
    protected PropertyOwnership(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PropertyOwnership(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PropertyOwnership(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PropertyOwnership(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.propertyId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.propertyId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.from = (String) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.to = (String) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    // Updated method to use Utf8String instead of Bytes32
    public RemoteFunctionCall<TransactionReceipt> addProperty(String _currentOwner, String _propertyAddress, String _description) {
        final Function function = new Function(
                FUNC_ADDPROPERTY,
                Arrays.<Type>asList(
                        new Address(160, _currentOwner),
                        new Utf8String(_propertyAddress),
                        new Utf8String(_description)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> contractOwner() {
        final Function function = new Function(FUNC_CONTRACTOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getCurrentOwner(BigInteger _propertyId) {
        final Function function = new Function(FUNC_GETCURRENTOWNER,
                Arrays.<Type>asList(new Uint256(_propertyId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<String, String, List<String>, String>> getPropertyDetails(BigInteger _propertyId) {
        final Function function = new Function(FUNC_GETPROPERTYDETAILS,
                Arrays.<Type>asList(new Uint256(_propertyId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicArray<Address>>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, List<String>, String>>(function,
                new Callable<Tuple4<String, String, List<String>, String>>() {
                    @Override
                    public Tuple4<String, String, List<String>, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, List<String>, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                convertToNative((List<Address>) results.get(2).getValue()),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple4<BigInteger, String, String, String>> properties(BigInteger param0) {
        final Function function = new Function(FUNC_PROPERTIES,
                Arrays.<Type>asList(new Uint256(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, String, String, String>>(function,
                new Callable<Tuple4<BigInteger, String, String, String>>() {
                    @Override
                    public Tuple4<BigInteger, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, String, String, String>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> propertyCount() {
        final Function function = new Function(FUNC_PROPERTYCOUNT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(BigInteger _propertyId, String _newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Uint256(_propertyId),
                        new Address(160, _newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PropertyOwnership load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertyOwnership(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PropertyOwnership load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertyOwnership(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PropertyOwnership load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PropertyOwnership(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PropertyOwnership load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PropertyOwnership(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PropertyOwnership> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PropertyOwnership.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PropertyOwnership> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PropertyOwnership.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PropertyOwnership> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PropertyOwnership.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PropertyOwnership> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PropertyOwnership.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public BigInteger propertyId;
        public String from;
        public String to;
    }
}
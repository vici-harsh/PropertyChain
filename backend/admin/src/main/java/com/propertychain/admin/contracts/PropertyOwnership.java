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
    public static final String BINARY = "6080604052348015600e575f5ffd5b50335f5f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506115ec8061005b5f395ff3fe608060405234801561000f575f5ffd5b506004361061007b575f3560e01c80637ace341b116100595780637ace341b14610112578063c361a33514610142578063ce606ee014610160578063f7b108081461017e5761007b565b8063126dbe431461007f5780631ee90a5f146100b257806329507f73146100e2575b5f5ffd5b61009960048036038101906100949190610bd7565b6101b1565b6040516100a99493929190610d68565b60405180910390f35b6100cc60048036038101906100c79190610f16565b610403565b6040516100d99190610fb8565b60405180910390f35b6100fc60048036038101906100f79190610fd1565b61067c565b6040516101099190610fb8565b60405180910390f35b61012c60048036038101906101279190610bd7565b610934565b604051610139919061100f565b60405180910390f35b61014a610970565b6040516101579190611037565b60405180910390f35b610168610976565b604051610175919061100f565b60405180910390f35b61019860048036038101906101939190610bd7565b61099a565b6040516101a89493929190611050565b60405180910390f35b60605f6060805f60015f8781526020019081526020015f206040518060a00160405290815f8201548152602001600182015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600282018054610242906110ce565b80601f016020809104026020016040519081016040528092919081815260200182805461026e906110ce565b80156102b95780601f10610290576101008083540402835291602001916102b9565b820191905f5260205f20905b81548152906001019060200180831161029c57829003601f168201915b505050505081526020016003820180546102d2906110ce565b80601f01602080910402602001604051908101604052809291908181526020018280546102fe906110ce565b80156103495780601f1061032057610100808354040283529160200191610349565b820191905f5260205f20905b81548152906001019060200180831161032c57829003601f168201915b50505050508152602001600482018054806020026020016040519081016040528092919081815260200182805480156103d457602002820191905f5260205f20905b815f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001906001019080831161038b575b505050505081525050905080604001518160200151826080015183606001519450945094509450509193509193565b5f5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16148061048957508373ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16145b6104c8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104bf90611148565b60405180910390fd5b60025f8154809291906104da90611193565b91905055505f5f67ffffffffffffffff8111156104fa576104f9610df2565b5b6040519080825280602002602001820160405280156105285781602001602082028036833780820191505090505b5090506040518060a0016040528060025481526020018673ffffffffffffffffffffffffffffffffffffffff1681526020018581526020018481526020018281525060015f60025481526020019081526020015f205f820151815f01556020820151816001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020190816105e0919061137a565b5060608201518160030190816105f6919061137a565b506080820151816004019080519060200190610613929190610af1565b5090505060025460015f60025481526020019081526020015f205f015414610670576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161066790611493565b60405180910390fd5b60019150509392505050565b5f3373ffffffffffffffffffffffffffffffffffffffff1660015f8581526020019081526020015f206001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461071e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610715906114fb565b60405180910390fd5b5f73ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff16141580156107b9575060015f8481526020019081526020015f206001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614155b6107f8576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107ef90611563565b60405180910390fd5b60015f8481526020019081526020015f2060040160015f8581526020019081526020015f206001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060018154018082558091505060019003905f5260205f20015f9091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508160015f8581526020019081526020015f206001015f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f16b85f49bf01212961345d3016c9a531894accf62eb7680f2045d79185cc0ec083338460405161092293929190611581565b60405180910390a16001905092915050565b5f60015f8381526020019081526020015f206001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b60025481565b5f5f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6001602052805f5260405f205f91509050805f015490806001015f9054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060020180546109e4906110ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610a10906110ce565b8015610a5b5780601f10610a3257610100808354040283529160200191610a5b565b820191905f5260205f20905b815481529060010190602001808311610a3e57829003601f168201915b505050505090806003018054610a70906110ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610a9c906110ce565b8015610ae75780601f10610abe57610100808354040283529160200191610ae7565b820191905f5260205f20905b815481529060010190602001808311610aca57829003601f168201915b5050505050905084565b828054828255905f5260205f20908101928215610b67579160200282015b82811115610b66578251825f6101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555091602001919060010190610b0f565b5b509050610b749190610b78565b5090565b5b80821115610b8f575f815f905550600101610b79565b5090565b5f604051905090565b5f5ffd5b5f5ffd5b5f819050919050565b610bb681610ba4565b8114610bc0575f5ffd5b50565b5f81359050610bd181610bad565b92915050565b5f60208284031215610bec57610beb610b9c565b5b5f610bf984828501610bc3565b91505092915050565b5f81519050919050565b5f82825260208201905092915050565b8281835e5f83830152505050565b5f601f19601f8301169050919050565b5f610c4482610c02565b610c4e8185610c0c565b9350610c5e818560208601610c1c565b610c6781610c2a565b840191505092915050565b5f73ffffffffffffffffffffffffffffffffffffffff82169050919050565b5f610c9b82610c72565b9050919050565b610cab81610c91565b82525050565b5f81519050919050565b5f82825260208201905092915050565b5f819050602082019050919050565b610ce381610c91565b82525050565b5f610cf48383610cda565b60208301905092915050565b5f602082019050919050565b5f610d1682610cb1565b610d208185610cbb565b9350610d2b83610ccb565b805f5b83811015610d5b578151610d428882610ce9565b9750610d4d83610d00565b925050600181019050610d2e565b5085935050505092915050565b5f6080820190508181035f830152610d808187610c3a565b9050610d8f6020830186610ca2565b8181036040830152610da18185610d0c565b90508181036060830152610db58184610c3a565b905095945050505050565b610dc981610c91565b8114610dd3575f5ffd5b50565b5f81359050610de481610dc0565b92915050565b5f5ffd5b5f5ffd5b7f4e487b71000000000000000000000000000000000000000000000000000000005f52604160045260245ffd5b610e2882610c2a565b810181811067ffffffffffffffff82111715610e4757610e46610df2565b5b80604052505050565b5f610e59610b93565b9050610e658282610e1f565b919050565b5f67ffffffffffffffff821115610e8457610e83610df2565b5b610e8d82610c2a565b9050602081019050919050565b828183375f83830152505050565b5f610eba610eb584610e6a565b610e50565b905082815260208101848484011115610ed657610ed5610dee565b5b610ee1848285610e9a565b509392505050565b5f82601f830112610efd57610efc610dea565b5b8135610f0d848260208601610ea8565b91505092915050565b5f5f5f60608486031215610f2d57610f2c610b9c565b5b5f610f3a86828701610dd6565b935050602084013567ffffffffffffffff811115610f5b57610f5a610ba0565b5b610f6786828701610ee9565b925050604084013567ffffffffffffffff811115610f8857610f87610ba0565b5b610f9486828701610ee9565b9150509250925092565b5f8115159050919050565b610fb281610f9e565b82525050565b5f602082019050610fcb5f830184610fa9565b92915050565b5f5f60408385031215610fe757610fe6610b9c565b5b5f610ff485828601610bc3565b925050602061100585828601610dd6565b9150509250929050565b5f6020820190506110225f830184610ca2565b92915050565b61103181610ba4565b82525050565b5f60208201905061104a5f830184611028565b92915050565b5f6080820190506110635f830187611028565b6110706020830186610ca2565b81810360408301526110828185610c3a565b905081810360608301526110968184610c3a565b905095945050505050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52602260045260245ffd5b5f60028204905060018216806110e557607f821691505b6020821081036110f8576110f76110a1565b5b50919050565b7f556e617574686f72697a656400000000000000000000000000000000000000005f82015250565b5f611132600c83610c0c565b915061113d826110fe565b602082019050919050565b5f6020820190508181035f83015261115f81611126565b9050919050565b7f4e487b71000000000000000000000000000000000000000000000000000000005f52601160045260245ffd5b5f61119d82610ba4565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036111cf576111ce611166565b5b600182019050919050565b5f819050815f5260205f209050919050565b5f6020601f8301049050919050565b5f82821b905092915050565b5f600883026112367fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826111fb565b61124086836111fb565b95508019841693508086168417925050509392505050565b5f819050919050565b5f61127b61127661127184610ba4565b611258565b610ba4565b9050919050565b5f819050919050565b61129483611261565b6112a86112a082611282565b848454611207565b825550505050565b5f5f905090565b6112bf6112b0565b6112ca81848461128b565b505050565b5b818110156112ed576112e25f826112b7565b6001810190506112d0565b5050565b601f82111561133257611303816111da565b61130c846111ec565b8101602085101561131b578190505b61132f611327856111ec565b8301826112cf565b50505b505050565b5f82821c905092915050565b5f6113525f1984600802611337565b1980831691505092915050565b5f61136a8383611343565b9150826002028217905092915050565b61138382610c02565b67ffffffffffffffff81111561139c5761139b610df2565b5b6113a682546110ce565b6113b18282856112f1565b5f60209050601f8311600181146113e2575f84156113d0578287015190505b6113da858261135f565b865550611441565b601f1984166113f0866111da565b5f5b82811015611417578489015182556001820191506020850194506020810190506113f2565b868310156114345784890151611430601f891682611343565b8355505b6001600288020188555050505b505050505050565b7f50726f7065727479204944206d69736d617463680000000000000000000000005f82015250565b5f61147d601483610c0c565b915061148882611449565b602082019050919050565b5f6020820190508181035f8301526114aa81611471565b9050919050565b7f4e6f7420746865206f776e6572000000000000000000000000000000000000005f82015250565b5f6114e5600d83610c0c565b91506114f0826114b1565b602082019050919050565b5f6020820190508181035f830152611512816114d9565b9050919050565b7f496e76616c6964207472616e73666572000000000000000000000000000000005f82015250565b5f61154d601083610c0c565b915061155882611519565b602082019050919050565b5f6020820190508181035f83015261157a81611541565b9050919050565b5f6060820190506115945f830186611028565b6115a16020830185610ca2565b6115ae6040830184610ca2565b94935050505056fea2646970667358221220b29f3867769d289e0cfd418fc9abff0b835771db13fe48dc164d50fb54e3a5fe64736f6c634300081e0033";

    public static final String FUNC_ADDPROPERTY = "addProperty";

    public static final String FUNC_CONTRACTOWNER = "contractOwner";

    public static final String FUNC_GETCURRENTOWNER = "getCurrentOwner";

    public static final String FUNC_GETPROPERTYDETAILS = "getPropertyDetails";

    public static final String FUNC_PROPERTIES = "properties";

    public static final String FUNC_PROPERTYCOUNT = "propertyCount";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

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

    public RemoteFunctionCall<TransactionReceipt> addProperty(String _currentOwner, String _propertyAddress, String _description) {
        final Function function = new Function(
                FUNC_ADDPROPERTY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _currentOwner), 
                new org.web3j.abi.datatypes.Utf8String(_propertyAddress), 
                new org.web3j.abi.datatypes.Utf8String(_description)), 
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_propertyId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple4<String, String, List<String>, String>> getPropertyDetails(BigInteger _propertyId) {
        final Function function = new Function(FUNC_GETPROPERTYDETAILS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_propertyId)), 
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
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
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_propertyId), 
                new org.web3j.abi.datatypes.Address(160, _newOwner)), 
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

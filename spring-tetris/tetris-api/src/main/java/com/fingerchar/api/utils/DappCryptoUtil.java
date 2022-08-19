package com.fingerchar.api.utils;

import com.fingerchar.api.dto.ProductOrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.DefaultFunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DappCryptoUtil {

	public static final Logger logger = LoggerFactory.getLogger(DappCryptoUtil.class);

	public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

	public static ProductOrderDto sign(ProductOrderDto order, String privateKey) {
		String signStr = prepareMessage(order);
        signStr = Hash.sha3(signStr);
		logger.info("第一次sha3=>" + signStr);
		signStr = Numeric.cleanHexPrefix(signStr);
		StringBuffer buffer1 = new StringBuffer(signStr);
		StringBuffer buffer2 = new StringBuffer(PERSONAL_MESSAGE_PREFIX);
		buffer2.append(buffer1.length()).append(buffer1);
		signStr = Numeric.toHexString(buffer2.toString().getBytes());
        logger.info("加头部=>" + signStr);
        signStr = Numeric.cleanHexPrefix(signStr);
        signStr = Hash.sha3(signStr);
        logger.info("第二次sha3=>" + signStr);
		ECKeyPair e = ECKeyPair.create(Numeric.hexStringToByteArray(privateKey));
		SignatureData data = Sign.signMessage(Numeric.hexStringToByteArray(signStr), e, false);
		logger.info("v=>" + new BigInteger(data.getV()).toString());
		logger.info("s=>" + Numeric.toHexString(data.getS()));
		logger.info("r=>" + Numeric.toHexString(data.getR()));
		order.setV(new BigInteger(data.getV()).toString());
		order.setS(Numeric.toHexString(data.getS()));
		order.setR(Numeric.toHexString(data.getR()));
		return order;
	}
	
	@SuppressWarnings("rawtypes")
	private static String prepareMessage(ProductOrderDto order) {
		List<Type> typeList = new ArrayList<>();
        Uint256 uint = new Uint256(BigInteger.valueOf(4l));
        typeList.add(uint);
		//contract
        Type<String> addr = new Address(order.getContract());
        typeList.add(addr);
        DefaultFunctionEncoder encoder = new DefaultFunctionEncoder();
        String preStr = encoder.encodeParameters(typeList);
        preStr = Hash.sha3(preStr);
        typeList.clear();
        Bytes32 b32 = new Bytes32(Numeric.hexStringToByteArray(preStr));
        typeList.add(b32);
        //owner
        addr = new Address(order.getOwner());
        typeList.add(addr);
		//salt(uint)
		uint = new Uint256(BigInteger.valueOf(order.getOrderNo()));
        typeList.add(uint);
		//token(address)
        addr = new Address(order.getToken());
        typeList.add(addr);
		//amount
        uint = new Uint256(new BigInteger(order.getAmounts()));
        typeList.add(uint);
        //expired time
        uint = new Uint256(BigInteger.valueOf(order.getExpiredTime()));
        typeList.add(uint);
        //to(address)
        addr = new Address(order.getTo());
        typeList.add(addr);
        return encoder.encodeParameters(typeList);
	}
}

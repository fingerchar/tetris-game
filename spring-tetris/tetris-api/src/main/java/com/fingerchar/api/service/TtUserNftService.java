package com.fingerchar.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.dto.NftItemsDto;
import com.fingerchar.api.utils.RsaEncryUtils;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.HttpUtils;
import com.fingerchar.core.util.ResponseUtil;
import com.fingerchar.db.domain.TtContractNft;
import com.fingerchar.db.domain.TtNftItems;
import com.fingerchar.db.domain.TtUserBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TtUserNftService {
	
	private static final Logger logger = LoggerFactory.getLogger(TtUserNftService.class);

	@Autowired
	IBaseService baseService;

	@Autowired
	TtContractNftManager contractNftManager;

	@Autowired
	TtNftItemsManager nftItemsManager ;
	/**
	 * @param address
	 * @param tokenId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object use(String address, String tokenId) {
		String VALID_PAY_TOKEN = SystemConfCache.cache.get(SysConfConstant.VALID_PAY_TOKEN);
		QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();

		wrapper.eq(TtUserBalance.ADDRESS, address).eq(TtUserBalance.TOKEN, VALID_PAY_TOKEN);
		TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
		if(null == balance) {
			return ResponseUtil.fail(-1, "余额不足");
		}

		try {
			String NFT_ADDRESS = SystemConfCache.cache.get(SysConfConstant.NFT_ADDRESS);
			NftItemsDto nftItemsDto = this.get(address, NFT_ADDRESS, tokenId);
			if(null == nftItemsDto) {
				return ResponseUtil.fail(-1, "have no this nft");
			}
			if(StringUtils.isEmpty(nftItemsDto.getMetaContent())) {
				return ResponseUtil.fail(-1, "this nft metaContent is empty");
			}
			JSONObject metaContent = JSONObject.parseObject(nftItemsDto.getMetaContent());

			BigDecimal useAmount = getNftFee(metaContent);
			BigDecimal hasAmount = new BigDecimal(balance.getAmount());
			if(hasAmount.compareTo(useAmount) < 0) {
				return ResponseUtil.fail(-1, "余额不足");
			}

			BigDecimal lastAmount = hasAmount.subtract(useAmount);
			balance.setAmount(lastAmount.toString());
			this.baseService.update(balance);
			return ResponseUtil.ok();
		} catch (Exception e) {
			logger.error("判断是否nft异常!");
		}
		return ResponseUtil.fail(-1, "use nft error");
	}

	private BigDecimal getNftFee(JSONObject metaContent){
		String value = getAttrValue(metaContent, "Block");
		String nftFee = SystemConfCache.cache.get(SysConfConstant.NFT_USE_FEE);
		JSONObject objFee = JSONObject.parseObject(nftFee);
		for(String key: objFee.keySet()){
			if(value.equals(key)){
				return new BigDecimal(objFee.get(key).toString());
			}
		}
		return new BigDecimal(0);
	}

	private String getAttrValue(JSONObject metaContent, String key){
		JSONArray attributes = metaContent.getJSONArray("attributes");
		if(null == attributes){
			return null;
		}
		JSONObject attribute = null;
		int len = attributes.size();
		for(int i=0; i<len; i++) {
			attribute = attributes.getJSONObject(i);
			if(key.equals(attribute.getString("key"))){
				return attribute.getString("value");
			}
		}

		return null;
	}

	/**
	 * @param owner
	 * @return
	 * @throws Exception 
	 */
	public Object list(String owner, IPage<TtNftItems> pageInfo) throws Exception {
		IPage<TtNftItems> itemsList = this.nftItemsManager.getByItemOwner(owner, pageInfo);
		List<NftItemsDto> list = new ArrayList<>() ;
		TtContractNft ttContractNft = null;
		NftItemsDto nftItemsDto = null;
		for (TtNftItems ttNftItems : itemsList.getRecords()) {
			ttContractNft = this.contractNftManager.getByAddressAndTokenId(ttNftItems.getAddress(),ttNftItems.getTokenId());
			nftItemsDto = new NftItemsDto() ;
			nftItemsDto.setDescription(ttContractNft.getDescription());
			nftItemsDto.setMetaContent(ttContractNft.getMetadataContent());
			nftItemsDto.setMetaUrl(ttContractNft.getMetadataUrl());
			nftItemsDto.setQuantity(ttNftItems.getQuantity());
			nftItemsDto.setToken(ttNftItems.getAddress());
			nftItemsDto.setTokenId(ttNftItems.getTokenId());
			nftItemsDto.setOwner(ttNftItems.getItemOwner());
			nftItemsDto.setName(ttContractNft.getName());
			list.add(nftItemsDto);
		}
		IPage<NftItemsDto> data = new Page<>(itemsList.getPages(), itemsList.getSize(), itemsList.getTotal());
		data.setRecords(list);
		return ResponseUtil.okList(data);
//		Map<String, Object> map = new HashMap<>();
//		map.put("appId", SystemConfCache.cache.get(SysConfConstant.APP_ID));
//		map.put("productId", SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID));
//		map.put("owner", owner);
//		map.put("token", SystemConfCache.cache.get(SysConfConstant.NFT_ADDRESS));
//		map.put("tokenId", null);
//		map.put("page", pageInfo.getCurrent());
//		map.put("limit", pageInfo.getSize());
//		map.put("timestamp", System.currentTimeMillis());
//		String sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
//		map.put("sign", sign);
//		String rst = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.NFT_QUERY_URL), map);
//		JSONObject obj = JSONObject.parseObject(rst);
//		if(obj.getInteger("errno") == 0) {
//			obj = obj.getJSONObject("data");
//			Long page = obj.getLong("page");
//			Long limit = obj.getLong("limit");
//			Long total = obj.getLong("total");
//			Long timestamp = obj.getLong("timestamp");
//			if(null == timestamp || timestamp + SysConfConstant.RSA_REQUEST_EXPIRE_MILLIS < System.currentTimeMillis()) {
//				return ResponseUtil.fail(-1, "签名异常！");
//			}
//			map.put("page", page);
//			map.put("limit", limit);
//			map.put("total", total);
//			map.put("md5", obj.getString("md5"));
//			map.put("timestamp", timestamp);
//			if(RsaEncryUtils.verify(RsaEncryUtils.getMessage(map).getBytes(StandardCharsets.UTF_8), SystemConfCache.cache.get(SysConfConstant.RSA_PUBLIC_KEY), obj.getString("sign"))) {
//				String itemsList = obj.getString("itemsList");
//				if(StringUtils.isEmpty(itemsList)) {
//					return ResponseUtil.okList(pageInfo);
//				}
//				List<NftItemsDto> list = JSONArray.parseArray(obj.getString("itemsList"), NftItemsDto.class);
//				IPage<NftItemsDto> data = new Page<>(page, limit, total);
//				data.setRecords(list);
//				return ResponseUtil.okList(data);
//			} else {
//				return ResponseUtil.fail(-1, "签名异常！");
//			}
//		} else {
//			logger.error("获取nft失败=>" + obj.getString("errmsg"));
//			return ResponseUtil.fail(-1, obj.getString("errmsg"));
//		}
	}

	private NftItemsDto get(String owner, String token, String tokenId) throws Exception {
		NftItemsDto nftItemsDto = new NftItemsDto();
		TtNftItems ttNftItems = this.nftItemsManager.get(token, tokenId, owner);
		if (null == ttNftItems){
			return null;
		}
		TtContractNft ttContractNft = this.contractNftManager.getByAddressAndTokenId(ttNftItems.getAddress(),ttNftItems.getTokenId());
		nftItemsDto.setDescription(ttContractNft.getDescription());
		nftItemsDto.setMetaContent(ttContractNft.getMetadataContent());
		nftItemsDto.setMetaUrl(ttContractNft.getMetadataUrl());
		nftItemsDto.setQuantity(ttNftItems.getQuantity());
		nftItemsDto.setToken(ttNftItems.getAddress());
		nftItemsDto.setTokenId(ttNftItems.getTokenId());
		nftItemsDto.setOwner(ttNftItems.getItemOwner());
		nftItemsDto.setName(ttContractNft.getName());
		return nftItemsDto;
//		Map<String, Object> map = new HashMap<>();
//		map.put("appId", SystemConfCache.cache.get(SysConfConstant.APP_ID));
//		map.put("productId", SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID));
//		map.put("owner", owner);
//		map.put("token", token);
//		map.put("tokenId", tokenId);
//		map.put("timestamp", System.currentTimeMillis());
//		String sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
//		map.put("sign", sign);
//		String rst = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.NFT_CHECK_URL), map);
//		JSONObject obj = JSONObject.parseObject(rst);
//		if(obj.getInteger("errno") == 0) {
//			obj = obj.getJSONObject("data");
//			NftItemsDto nftItemsDto = JSONArray.parseObject(obj.getString("item"), NftItemsDto.class);
//			return nftItemsDto;
//		} else {
//			return null;
//		}
	}
}

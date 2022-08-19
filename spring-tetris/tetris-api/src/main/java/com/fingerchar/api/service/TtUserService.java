package com.fingerchar.api.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.dto.ProductOrderDto;
import com.fingerchar.api.dto.UserDto;
import com.fingerchar.api.utils.DappCryptoUtil;
import com.fingerchar.api.utils.JwtHelper;
import com.fingerchar.api.utils.RsaEncryUtils;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.HttpUtils;
import com.fingerchar.core.util.ResponseUtil;
import com.fingerchar.db.domain.TtPaytokenTx;
import com.fingerchar.db.domain.TtUser;
import com.fingerchar.db.domain.TtUserBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TtUserService {


    private static final Logger logger = LoggerFactory.getLogger(TtUserService.class);


    @Autowired
    IBaseService baseService;

	/**
	 * @param token
	 * @param userAddress
	 * @param timestamp
	 * @param sign
	 * @return
	 */
	public Object getBalance(String token, String userAddress, Long timestamp, String sign) {
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("userAddress", userAddress);
		map.put("timestamp", timestamp);
		if(null == timestamp || timestamp + SysConfConstant.RSA_REQUEST_EXPIRE_MILLIS < System.currentTimeMillis()) {
			return ResponseUtil.fail(-1, "签名验证异常");
		}
		try {
			if(RsaEncryUtils.verify(RsaEncryUtils.getMessage(map).getBytes(StandardCharsets.UTF_8), SystemConfCache.cache.get(SysConfConstant.RSA_PUBLIC_KEY), sign)) {
				QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();
				wrapper.eq(TtUserBalance.ADDRESS, userAddress).eq(TtUserBalance.TOKEN, token);
				TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
				String amount = "0";
				String lockAmount = "0";
				if(null != balance) {
					amount = balance.getAmount();
					lockAmount = balance.getLockAmount();
				}
				map.clear();
				map.put("amount", amount);
				map.put("lockAmount", lockAmount);
				map.put("timestamp", System.currentTimeMillis());
				sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
				map.put("sign", sign);
				return ResponseUtil.ok(map);
			} else {
				return ResponseUtil.fail(-1, "签名不正确");
			}
		} catch (Exception e) {
			return ResponseUtil.fail(-1, "签名验证异常");
		}
	}

	@Transactional(rollbackFor=Exception.class)
	public Object withdraw(ProductOrderDto order, String sign) throws Exception {
		if(null == order.getTimestamp() || order.getTimestamp() + SysConfConstant.RSA_REQUEST_EXPIRE_MILLIS < System.currentTimeMillis()) {
			return ResponseUtil.fail(-1, "签名不正确");
		}
		Map<String, Object> map = order.toMap();
		if(RsaEncryUtils.verify(RsaEncryUtils.getMessage(map).getBytes(StandardCharsets.UTF_8), SystemConfCache.cache.get(SysConfConstant.RSA_PUBLIC_KEY), sign)) {
			QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();
			wrapper.eq(TtUserBalance.ADDRESS, order.getTo())
				.eq(TtUserBalance.TOKEN, order.getToken());
			TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
			if(null == balance) {
				return ResponseUtil.fail(-1, "提现金额不足");
			}
			BigDecimal lastAmount = new BigDecimal(balance.getAmount()).subtract(new BigDecimal(order.getRealAmounts()));
			if(lastAmount.compareTo(BigDecimal.ZERO)  < 0) {
				return ResponseUtil.fail(-1, "提现金额不足");
			}
			balance.setAmount(lastAmount.toString());
			balance.setLockAmount(new BigDecimal(balance.getLockAmount()).add(new BigDecimal(order.getRealAmounts())).toString());
			this.baseService.update(balance);
			TtPaytokenTx tx = new TtPaytokenTx();
			tx.setAmounts(order.getRealAmounts());
			tx.setFrom(order.getFrom());
			tx.setTo(order.getTo());
			tx.setOrderNo(order.getOrderNo());
			tx.setStatus(0);
			tx.setToken(order.getToken());
			tx.setType(order.getType());
			tx.setExpiredTime(order.getExpiredTime());
			this.baseService.save(tx);
			order = DappCryptoUtil.sign(order, SystemConfCache.cache.get(SysConfConstant.WITHDRAW_KEY));
			map.put("r", order.getR());
			map.put("s", order.getS());
			map.put("v", order.getV());
			map.put("timestamp", System.currentTimeMillis());
			sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
			map.put("sign", sign);
			return ResponseUtil.ok(map);
		} else {
			return ResponseUtil.fail(-1, "签名不正确");
		}
	}

	/**
	 * @param token
	 * @param userAddress
	 * @return
	 */
	public TtUserBalance balance(String token, String userAddress) {
		QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();
		wrapper.eq(TtUserBalance.ADDRESS, userAddress).eq(TtUserBalance.TOKEN, token);
		TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
		return balance;
	}


	@Transactional(rollbackFor = Exception.class)
	public Object login(String code){
		if(StringUtils.isEmpty(code)){
			return ResponseUtil.fail(-1, "code is empty");
		}
		String token = null;
		try{
			token = this.getOpenToken(code);
			if(StringUtils.isEmpty(token)) {
				return ResponseUtil.fail(-1, "token is null");
			}
		}catch(Exception e){
			logger.error("获取token失败", e);
			return ResponseUtil.fail(-1, "授权登录失败！");
		}

		try {
			TtUser user = this.getUserInfo(token);
			if(null == user){
				return ResponseUtil.fail(-1, "授权登录失败！");
			}
			Map<String, Object> map = new HashMap<>();
			map.put("token", UserTokenManager.generateToken(user.getAddress()));
			map.put("user", new UserDto(user));
			return ResponseUtil.ok(map);
		} catch (IOException e) {
			logger.error("获取用户信息异常", e);
			return ResponseUtil.fail(-1, "授权登录失败！");
		}
	}


	/**
	 * @param token
	 * @param code
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object login(String token, String code) {
		if(StringUtils.isEmpty(token)) {
			if(StringUtils.isEmpty(code)) {				
				return this.fail();
			} else {
				try {
					token = this.getOpenToken(code);
				} catch (Exception e) {
					logger.error("获取token异常", e);
					return ResponseUtil.fail(-1, "授权登录失败！");
				}
				if(!StringUtils.isEmpty(token)) {
					try {
						TtUser user = this.getUserInfo(token);
						if(null != user) {
							Map<String, Object> map = new HashMap<>();
							map.put("token", UserTokenManager.generateToken(user.getAddress()));
							map.put("user", new UserDto(user));
							return ResponseUtil.ok(map);
						} else {
							return ResponseUtil.fail(-1, "授权登录失败！");
						}
					} catch (IOException e) {
						logger.error("获取用户信息异常", e);
						return ResponseUtil.fail(-1, "授权登录失败！");
					}
				} else {
					logger.error("获取用户信息异常");
					return ResponseUtil.fail(-1, "授权登录失败！");
				}
			}
		}
		String userAddress = JwtHelper.verifyTokenAndGetUserAddress(token);
		if(StringUtils.isEmpty(userAddress)) {
			return this.fail();
		}
		QueryWrapper<TtUser> wrapper = new QueryWrapper<>();
		wrapper.eq(TtUser.ADDRESS, userAddress);
		TtUser user = this.baseService.getByCondition(TtUser.class, wrapper);
		if(null == user) {
			return this.fail();
		} else {
			if(null != user && null != user.getDeleted()) {
				return ResponseUtil.fail(-1, "账号已被禁用");
			}			
			try {
				user = this.getUserInfo(user.getOpenToken());
				if(null != user) {
					Map<Object, Object> result = new HashMap<Object, Object>();
					result.put("token", UserTokenManager.generateToken(user.getAddress()));
					result.put("user", new UserDto(user));
					return ResponseUtil.ok(result);
				} else {
					return this.fail();
				}
			} catch (IOException e) {
				return this.fail();
			}
		}
	}


	/**
	 * @param userAddress
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object reload(String userAddress) {
		QueryWrapper<TtUser> wrapper = new QueryWrapper<>();
		wrapper.eq(TtUser.ADDRESS, userAddress);
		TtUser user = this.baseService.getByCondition(TtUser.class, wrapper);
		if(null == user ) {
			return ResponseUtil.fail(-1, "user not exist");
		}
		if(user.getDeleted()){
			return ResponseUtil.fail(-1, "账号已被禁用");
		}
		try {
			user = this.getUserInfo(user.getOpenToken());
			if(null != user) {
				Map<Object, Object> result = new HashMap<Object, Object>();
				result.put("token", UserTokenManager.generateToken(user.getAddress()));
				result.put("user", new UserDto(user));
				return ResponseUtil.ok(result);
			} else {
				return ResponseUtil.fail(-1, "token is expire");
			}
		} catch (IOException e) {
			return ResponseUtil.fail(-1, "getUserInfo exception");
		}
	}

	private Object fail() {
		Map<String, String> map = new HashMap<>();
		map.put("client_id", SystemConfCache.cache.get(SysConfConstant.APP_ID));
		map.put("product_id", SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID));
		map.put("redirect_uri", SystemConfCache.cache.get(SysConfConstant.REDIRECT_URI));
		map.put("response_type", SystemConfCache.cache.get(SysConfConstant.RESPONSE_TYPE));
		return ResponseUtil.ok(map);
	}

	@Transactional(rollbackFor = Exception.class)
	private TtUser getUserInfo(String token) throws IOException {
		if (null == token){
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("FingerNft-Oauth2-Token", token);
		String result = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.USER_INFO_URL), map);
		JSONObject obj = JSONObject.parseObject(result);
		if(0 == obj.getInteger("errno")) {
			obj = obj.getJSONObject("data");
			String address = obj.getString("address");
			QueryWrapper<TtUser> wrapper = new QueryWrapper<>();
			wrapper.eq(TtUser.ADDRESS, address);
			TtUser user = this.baseService.getByCondition(TtUser.class, wrapper);
			if(null == user) {
				user = new TtUser();
			}
			user.setAddress(address);
			user.setEmail(obj.getString("email"));
			user.setNickname(obj.getString("nickname"));
			user.setOpenToken(token);
			user.setBrief(obj.getString("brief"));
			user.setAvatar(obj.getString("avatar"));
			this.baseService.saveOrUpdate(user);
			return user;
		} else {
			return null;
		}
		
	}
	
	private String getOpenToken(String code) throws IOException {
		Map<String, Object> map = new HashMap<>();
		map.put("client_id", SystemConfCache.cache.get(SysConfConstant.APP_ID));
		map.put("product_id", SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID));
		map.put("grant_type", "authorization_code");
		map.put("code", code);
		map.put("redirect_uri", SystemConfCache.cache.get(SysConfConstant.REDIRECT_URI));
		map.put("client_secret", SystemConfCache.cache.get(SysConfConstant.CLIENT_SECRET));
		String result = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.TOKEN_URL), map);
		JSONObject obj = JSONObject.parseObject(result);
		if(0 == obj.getInteger("errno")) {
			obj = obj.getJSONObject("data");
			return obj.getString("token");
		}
		return null;
	}

}

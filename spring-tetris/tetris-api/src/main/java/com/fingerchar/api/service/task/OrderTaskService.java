package com.fingerchar.api.service.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.service.TtContractNftManager;
import com.fingerchar.api.service.TtSystemConfigService;
import com.fingerchar.api.utils.RsaEncryUtils;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.HttpUtils;
import com.fingerchar.db.domain.TtPaytokenTx;
import com.fingerchar.db.domain.TtUserBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderTaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderTaskService.class);

	@Autowired
	IBaseService baseService;
	
	@Autowired
	TtSystemConfigService configService;

	@Autowired
	TtContractNftManager contractNftManager ;
	
	/**
	 * @throws Exception 
	 * 
	 */
	@Transactional(rollbackFor=Exception.class)
	public void processOrder() throws Exception {
		Map<String, Object> map = new HashMap<>();
		String startBlockNumberStr = this.configService.get(SysConfConstant.START_BLOCK_NUMBER);
		Long startBlockNumber = 0L;
		if(!StringUtils.isEmpty(startBlockNumberStr)) {
			startBlockNumber = Long.valueOf(startBlockNumberStr);
		}
		String limitBlockNumberStr = this.configService.get(SysConfConstant.LIMIT_BLOCK_NUMBER);
		Long limitBlockNumber = 100l;
		if(!StringUtils.isEmpty(limitBlockNumberStr)) {
			limitBlockNumber = Long.valueOf(limitBlockNumberStr);
		}
		map.put("startBlockNumber", startBlockNumber);
		map.put("limitBlockNumber", limitBlockNumber);
		map.put("appId", SystemConfCache.cache.get(SysConfConstant.APP_ID));
		map.put("productId", SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID));
		map.put("timestamp", System.currentTimeMillis());
		String sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
		map.put("sign", sign);
		String rst = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.ORDER_SYNC_URL), map);
		JSONObject obj = JSONObject.parseObject(rst);
		if(obj.getInteger("errno") == 0) {
			obj = obj.getJSONObject("data");
			map.clear();
			map.put("appId", obj.getLong("appId"));
			map.put("productId", obj.getLong("productId"));
			map.put("md5", obj.getString("md5"));
			Long _lastBlockNumber = obj.getLong("startBlockNumber");
			Long timestamp = obj.getLong("timestamp");
			map.put("startBlockNumber", _lastBlockNumber);
			map.put("limitBlockNumber", obj.getLong("limitBlockNumber"));
			map.put("timestamp", timestamp);
			if(null == timestamp || timestamp + SysConfConstant.RSA_REQUEST_EXPIRE_MILLIS < System.currentTimeMillis()) {
				return;
			}
			if(RsaEncryUtils.verify(RsaEncryUtils.getMessage(map).getBytes(StandardCharsets.UTF_8), SystemConfCache.cache.get(SysConfConstant.RSA_PUBLIC_KEY), obj.getString("sign"))) {
				String orderList = obj.getString("orderList");
				if(StringUtils.isEmpty(orderList)) {
//					this.saveLastBlockNumber(_lastBlockNumber);
					return;
				}
				List<TtPaytokenTx> list = JSONArray.parseArray(obj.getString("orderList"), TtPaytokenTx.class);
				this.processOrder(list);
//				this.contractNftManager.processTransfer(obj.getString("transferList"));
//				this.saveLastTime(lastTime);

			} else {
				throw new RuntimeException("签名验证异常！");				
			}
		} else {
			logger.error("同步订单失败=>" + obj.getString("errmsg"));
		}
	}

	/**
	 * @param list
	 */
	@Transactional(rollbackFor=Exception.class)
	private void processOrder(List<TtPaytokenTx> list) {
		if(list.isEmpty()) {
			return;
		}
		Set<Long> orderNos = list.stream().map(TtPaytokenTx::getOrderNo).collect(Collectors.toSet());
		QueryWrapper<TtPaytokenTx> wrapper = new QueryWrapper<>();
		wrapper.in(TtPaytokenTx.ORDER_NO, orderNos);
		List<TtPaytokenTx> existList = this.baseService.findByCondition(TtPaytokenTx.class, wrapper);
		List<TtPaytokenTx> completedList = existList.stream().filter(order->order.getStatus() == 1).collect(Collectors.toList());
		if(null != completedList && !completedList.isEmpty()) {			
			Set<Long> completedOrderNos = completedList.stream().map(TtPaytokenTx::getOrderNo).collect(Collectors.toSet());
			list = list.stream().filter(order->!completedOrderNos.contains(order.getOrderNo())).collect(Collectors.toList());
		}
		if(list.isEmpty()) {
			return;
		}
		List<TtPaytokenTx> unCompletedList = existList.stream().filter(order->order.getStatus() != 1).collect(Collectors.toList());

		Set<String> userAddrs = list.stream().map(order->order.getType() == 1?order.getFrom():order.getTo()).collect(Collectors.toSet());
		QueryWrapper<TtUserBalance> balanceWrapper = new QueryWrapper<>();
		balanceWrapper.in(TtUserBalance.ADDRESS, userAddrs);
		List<TtUserBalance> balanceList = this.baseService.findByCondition(TtUserBalance.class, balanceWrapper);
		Map<String, TtUserBalance> balanceMap = new HashMap<>();
		balanceList.stream().forEach(balance-> {
			balanceMap.put(balance.getAddress().toLowerCase() + "-" + balance.getToken().toLowerCase(), balance);
		});
		Map<String, List<TtPaytokenTx>> map = new HashMap<>();
		list.stream().forEach(order-> {
			String addr = order.getType() == 1?order.getFrom():order.getTo();
			addr = addr.toLowerCase() + "-" + order.getToken().toLowerCase();
			if(null == map.get(addr)) {
				map.put(addr, new ArrayList<TtPaytokenTx>());
			}
			map.get(addr).add(order);
		});
		Iterator<String> it = map.keySet().iterator();
		TtUserBalance userBalance = null;
		String addr = null;
		String[] temp = null;
		while(it.hasNext()) {
			addr = it.next();
			userBalance = balanceMap.get(addr);
			if(null == userBalance) {
				temp = addr.split("-");
				userBalance = new TtUserBalance();
				userBalance.setAddress(temp[0]);
				userBalance.setToken(temp[1]);
				userBalance.setAmount("0");
				userBalance.setLockAmount("0");
			}
			this.processOrder(map.get(addr), userBalance);
		}
		this.saveOrUpdateOrder(list, unCompletedList);
		
	}

	/**
	 * @param orderList
	 * @param userBalance
	 */
	@Transactional(rollbackFor=Exception.class)
	private void processOrder(List<TtPaytokenTx> orderList, TtUserBalance userBalance) {
		if(null == orderList || orderList.isEmpty()) {
			return;
		}
		BigDecimal amount = new BigDecimal(userBalance.getAmount());
		BigDecimal lockAmount = new BigDecimal(userBalance.getLockAmount());
		for(TtPaytokenTx order : orderList) {
			if(order.getType() == 1) {
				amount = amount.add(new BigDecimal(order.getAmounts()));
			} else if(order.getType() == 2) {
				lockAmount = lockAmount.subtract(new BigDecimal(order.getAmounts()));
			} else {
				logger.warn("未知的订单类型");
			}
		}
		if(lockAmount.compareTo(BigDecimal.ZERO) < 0) {
			throw new RuntimeException("提现订单异常=>用户:" + userBalance.getAddress() + ",币种:" + userBalance.getToken());
		}
		userBalance.setAmount(amount.toString());
		userBalance.setLockAmount(lockAmount.toString());
		this.baseService.saveOrUpdate(userBalance);
	}
	
	/**
	 */
	@Transactional(rollbackFor=Exception.class)
	public void processExpiredOrder() {
		Long lastTime = System.currentTimeMillis() / 1000 - 5 * 60;
		QueryWrapper<TtPaytokenTx> wrapper = new QueryWrapper<>();
		wrapper.eq(TtPaytokenTx.STATUS, 0).le(TtPaytokenTx.EXPIRED_TIME, lastTime);
		List<TtPaytokenTx> list = this.baseService.findByCondition(TtPaytokenTx.class, wrapper);
		if(list.isEmpty()) {
			return;
		}
		Set<String> userAddrs = list.stream().map(order->order.getTo()).collect(Collectors.toSet());
		QueryWrapper<TtUserBalance> balanceWrapper = new QueryWrapper<>();
		balanceWrapper.in(TtUserBalance.ADDRESS, userAddrs);
		List<TtUserBalance> balanceList = this.baseService.findByCondition(TtUserBalance.class, balanceWrapper);
		Map<String, TtUserBalance> balanceMap = new HashMap<>();
		balanceList.stream().forEach(balance-> {
			balanceMap.put(balance.getAddress().toLowerCase() + "-" + balance.getToken().toLowerCase(), balance);
		});
		Map<String, BigDecimal> unLockMap = new HashMap<>();
		String key = null;
		for(TtPaytokenTx order : list) {
			if(order.getType() == 2) {
				key = order.getTo().toLowerCase() + "-" + order.getToken().toLowerCase();
				if(null == unLockMap.get(key)) {
					unLockMap.put(key, BigDecimal.ZERO);
				}
				unLockMap.put(key, unLockMap.get(key).add(new BigDecimal(order.getAmounts())));
			}
		}
		
		Iterator<String> it = unLockMap.keySet().iterator();
		BigDecimal unlockValue = null;
		TtUserBalance balance = null;
		while(it.hasNext()) {
			key = it.next();
			unlockValue = unLockMap.get(key);
			balance = balanceMap.get(key);
			balance.setAmount(new BigDecimal(balance.getAmount()).add(unlockValue).toString());
			balance.setLockAmount(new BigDecimal(balance.getLockAmount()).subtract(unlockValue).toString());
			this.baseService.update(balance);
		}
		
		UpdateWrapper<TtPaytokenTx> uwrapper = new UpdateWrapper<>();
		uwrapper.eq(TtPaytokenTx.STATUS, 0).le(TtPaytokenTx.EXPIRED_TIME, lastTime);
		uwrapper.set(TtPaytokenTx.STATUS, 2);
		this.baseService.updateByCondition(TtPaytokenTx.class, uwrapper);
		
	}

	/**
	 * @param lastTime
	 */
	@Transactional(rollbackFor=Exception.class)
	private void saveLastTime(Long lastTime) {
		this.configService.set("LastTime", String.valueOf(lastTime));
	}

	private void saveLastBlockNumber(Long lastBlockNumber) {
		this.configService.set(SysConfConstant.START_BLOCK_NUMBER, String.valueOf(lastBlockNumber));
	}
	
	/**
	 * @param list
	 * @param unCompletedList
	 */
	@Transactional(rollbackFor=Exception.class)
	private void saveOrUpdateOrder(List<TtPaytokenTx> list, List<TtPaytokenTx> unCompletedList) {
		Set<Long> updateNos = unCompletedList.stream().map(TtPaytokenTx::getOrderNo).collect(Collectors.toSet());
		List<TtPaytokenTx> saveList = list.stream().filter(order->!updateNos.contains(order.getOrderNo())).collect(Collectors.toList());
		if(!saveList.isEmpty()) {			
			this.baseService.saveBatch(saveList);
		}
		unCompletedList.stream().forEach(unCompleted-> {
			TtPaytokenTx tx = list.stream().filter(x->x.getOrderNo().equals(unCompleted.getOrderNo())).findFirst().orElse(null);
			if(null != tx) {
				unCompleted.setStatus(1);
				unCompleted.setTxHash(tx.getTxHash());
				unCompleted.setConfirmTime(tx.getConfirmTime());
			}
			this.baseService.update(unCompleted);
		});
	}
}

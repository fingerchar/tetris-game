package com.fingerchar.api.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.dto.ScoreRateDto;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.ResponseUtil;
import com.fingerchar.db.domain.TtPaytoken;
import com.fingerchar.db.domain.TtUserBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TtGameService {

	@Autowired
	IBaseService baseService;

	@Autowired
	TtPayTokenService payTokenService;
	/**
	 * @param owner
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object start(String owner) {
		String VALID_PAY_TOKEN = SystemConfCache.cache.get(SysConfConstant.VALID_PAY_TOKEN);
		QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();
		wrapper.eq(TtUserBalance.ADDRESS, owner).eq(TtUserBalance.TOKEN, VALID_PAY_TOKEN);
		TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
		if(null == balance) {
			return ResponseUtil.fail(-1, "余额不足");
		}
		BigDecimal hasAmount = new BigDecimal(balance.getAmount());
		BigDecimal useAmount = new BigDecimal(SystemConfCache.cache.get(SysConfConstant.PLAY_FEE));
		if(hasAmount.compareTo(useAmount) < 0) {
			return ResponseUtil.fail(-1, "余额不足");
		}
		BigDecimal lastAmount = hasAmount.subtract(useAmount);
		balance.setAmount(lastAmount.toString());
		this.baseService.update(balance);
		return ResponseUtil.ok();
	}

	/**
	 * @param owner
	 * @param score
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object finish(String owner, Long score) {
		String VALID_PAY_TOKEN = SystemConfCache.cache.get(SysConfConstant.VALID_PAY_TOKEN);
		List<ScoreRateDto> list = JSONArray.parseArray(SystemConfCache.cache.get(SysConfConstant.SCORE_RATE), ScoreRateDto.class);
		//验证排序是否正确
		list = list.stream().sorted(new Comparator<ScoreRateDto>() {
			@Override
			public int compare(ScoreRateDto o1, ScoreRateDto o2) {
				return o1.getMaxScore() > o2.getMaxScore()? -1: 1;
			}
		}).collect(Collectors.toList());
		ScoreRateDto rate = null;
		for(ScoreRateDto dto : list) {
			if(score > dto.getMaxScore()) {
				rate = dto;
				break;
			}
		}
		if(null == rate) {
			rate = list.get(list.size() - 1);
		}
		QueryWrapper<TtUserBalance> wrapper = new QueryWrapper<>();
		wrapper.eq(TtUserBalance.ADDRESS, owner).eq(TtUserBalance.TOKEN, VALID_PAY_TOKEN);
		TtUserBalance balance = this.baseService.getByCondition(TtUserBalance.class, wrapper);
		BigDecimal hasAmount = null;
		if(null == balance) {
			balance = new TtUserBalance();
			hasAmount = BigDecimal.ZERO;
		} else {
			hasAmount = new BigDecimal(balance.getAmount());
		}
		BigDecimal getAmount = new BigDecimal(rate.getAmount()).multiply(new BigDecimal(score));
		BigDecimal lastAmount = hasAmount.add(getAmount);
		balance.setAmount(lastAmount.toString());
		this.baseService.update(balance);

		return ResponseUtil.ok(getAmount);
	}

	@Transactional(rollbackFor = Exception.class)
	public Object detail() {
		String APP_ID = SystemConfCache.cache.get(SysConfConstant.APP_ID);
		String PRODUCT_ID = SystemConfCache.cache.get(SysConfConstant.PRODUCT_ID);
		String RESPONSE_TYPE = SystemConfCache.cache.get(SysConfConstant.RESPONSE_TYPE);
		String VALID_PAY_TOKEN = SystemConfCache.cache.get(SysConfConstant.VALID_PAY_TOKEN);
		String REDIRECT_URI = SystemConfCache.cache.get(SysConfConstant.REDIRECT_URI);
		Map<String, Object> map = new HashMap<>();

		QueryWrapper<TtPaytoken> wrapper = new QueryWrapper<>();
		wrapper.eq(TtPaytoken.ADDRESS, VALID_PAY_TOKEN);
		TtPaytoken ttPaytoken = payTokenService.getPayToken(TtPaytoken.class,wrapper);
		List<ScoreRateDto> scoreRate = JSONArray.parseArray(SystemConfCache.cache.get(SysConfConstant.SCORE_RATE), ScoreRateDto.class);
		JSONObject nftUseFee = JSONObject.parseObject(SystemConfCache.cache.get(SysConfConstant.NFT_USE_FEE));
		map.put("appId", APP_ID);
		map.put("productId", PRODUCT_ID);
		map.put("responseType", RESPONSE_TYPE);
		map.put("NftUseFee" , nftUseFee);
		map.put("PlayFee" ,SystemConfCache.cache.get(SysConfConstant.PLAY_FEE));
		map.put("payToken" ,ttPaytoken);
		map.put("scoreRate", scoreRate);
		map.put("redirectUri", REDIRECT_URI);
		return ResponseUtil.ok(map);
	}
}

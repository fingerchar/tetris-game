package com.fingerchar.api.service.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fingerchar.api.cache.SystemConfCache;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.api.service.TtContractNftManager;
import com.fingerchar.api.service.TtSystemConfigService;
import com.fingerchar.api.utils.RsaEncryUtils;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.HttpUtils;
import com.fingerchar.db.domain.TtPaytokenTx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NftTaskService {
    private static final Logger logger = LoggerFactory.getLogger(OrderTaskService.class);

    @Autowired
    IBaseService baseService;

    @Autowired
    TtSystemConfigService configService;

    @Autowired
    TtContractNftManager contractNftManager ;

    @Transactional(rollbackFor = Exception.class)
    public void processNft() throws Exception{
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
        map.put("address", SystemConfCache.cache.get(SysConfConstant.NFT_ADDRESS));
        String sign = RsaEncryUtils.sign(map, SystemConfCache.cache.get(SysConfConstant.RSA_PRIVATE_KEY), StandardCharsets.UTF_8);
        map.put("sign", sign);
        String rst = HttpUtils.postForm(SystemConfCache.cache.get(SysConfConstant.NFT_QUERY_URL), map);
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
            map.put("address", obj.getString("address"));
            map.put("timestamp", timestamp);
            if(null == timestamp || timestamp + SysConfConstant.RSA_REQUEST_EXPIRE_MILLIS < System.currentTimeMillis()) {
                return;
            }
            if(RsaEncryUtils.verify(RsaEncryUtils.getMessage(map).getBytes(StandardCharsets.UTF_8), SystemConfCache.cache.get(SysConfConstant.RSA_PUBLIC_KEY), obj.getString("sign"))) {
                this.saveLastBlockNumber(_lastBlockNumber);
				this.contractNftManager.processTransfer(obj.getString("transferLogList"),obj.getString("contractNfts"));

            } else {
                throw new RuntimeException("签名验证异常！");
            }
        } else {
            logger.error("同步订单失败=>" + obj.getString("errmsg"));
        }
    }
    private void saveLastBlockNumber(Long lastBlockNumber) {
        this.configService.set(SysConfConstant.START_BLOCK_NUMBER, String.valueOf(lastBlockNumber));
    }
}

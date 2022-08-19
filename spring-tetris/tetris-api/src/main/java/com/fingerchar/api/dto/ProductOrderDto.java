package com.fingerchar.api.dto;

import java.util.HashMap;
import java.util.Map;

import com.fingerchar.db.domain.TtPaytokenTx;

public class ProductOrderDto {


    private String contract;

    private Long appId;

    private Long productId;

    private Long orderNo;

    private String token;

    private String from;

    private String to;

    private String amounts;
    
    private String realAmounts;

    private Long expiredTime;

    private Long confrimTime;

    private Integer type;

    private Integer status;

    private String txHash;

    private String v;

    private String r;

    private String s;

    private String owner;

    private Long timestamp;

    public ProductOrderDto() {}
    
    public TtPaytokenTx toOrder() {
        TtPaytokenTx tx = new TtPaytokenTx();
        tx.setAmounts(this.amounts);
        tx.setConfirmTime(this.confrimTime);
        tx.setFrom(this.from);
        tx.setTo(this.to);
        tx.setStatus(this.status);
        tx.setTxHash(this.txHash);
        return tx;
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("contract", this.contract);
        map.put("orderNo", this.orderNo);
        map.put("token", this.token);
        map.put("expiredTime", this.expiredTime);
        map.put("amounts", this.amounts);
        map.put("realAmounts", this.realAmounts);
        map.put("owner", this.owner);
        map.put("status", this.status);
        map.put("to", this.to);
        map.put("from", this.from);
        map.put("type", this.type);
        map.put("r", this.r);
        map.put("s", this.s);
        map.put("v", this.v);
        map.put("timestamp", this.timestamp);
        return map;
    }


    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public String getRealAmounts() {
        return realAmounts;
    }

    public void setRealAmounts(String realAmounts) {
        this.realAmounts = realAmounts;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Long getConfrimTime() {
        return confrimTime;
    }

    public void setConfrimTime(Long confrimTime) {
        this.confrimTime = confrimTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

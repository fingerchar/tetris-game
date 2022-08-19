package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtPaytokenTx
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_paytoken_tx")
public class TtPaytokenTx extends BaseEntity {


    @TableField("`order_no`")
    private Long orderNo;

    @TableField("`from`")
    private String from;

    @TableField("`to`")
    private String to;

    @TableField("`amounts`")
    private String amounts;

    @TableField("`tx_hash`")
    private String txHash;

    @TableField("`type`")
    private Integer type;

    @TableField("`status`")
    private Integer status;

    @TableField("`token`")
    private String token;

    @TableField("`confirm_time`")
    private Long confirmTime;

    @TableField("`expired_time`")
    private Long expiredTime;


    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Long confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public static final String ORDER_NO = "`order_no`";

    public static final String FROM = "`from`";

    public static final String TO = "`to`";

    public static final String AMOUNTS = "`amounts`";

    public static final String TX_HASH = "`tx_hash`";

    public static final String TYPE = "`type`";

    public static final String STATUS = "`status`";

    public static final String TOKEN = "`token`";

    public static final String CONFIRM_TIME = "`confirm_time`";

    public static final String EXPIRED_TIME = "`expired_time`";

    @Override
    public String toString() {
        return "TtPaytokenTx{" +
        "orderNo=" + orderNo +
        ", from=" + from +
        ", to=" + to +
        ", amounts=" + amounts +
        ", txHash=" + txHash +
        ", type=" + type +
        ", status=" + status +
        ", token=" + token +
        ", confirmTime=" + confirmTime +
        ", expiredTime=" + expiredTime +
        "}";
    }
}

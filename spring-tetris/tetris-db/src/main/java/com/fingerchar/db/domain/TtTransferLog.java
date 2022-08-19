package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtTransferLog
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_transfer_log")
public class TtTransferLog extends BaseEntity {


    /**
     * 操作NFT的地址
     */
    @TableField("`address`")
    private String address;

    /**
     * 操作NFT的tokenId
     */
    @TableField("`token_id`")
    private String tokenId;

    /**
     * 数量
     */
    @TableField("`amount`")
    private Long amount;

    /**
     * 订单发起人地址
     */
    @TableField("`from`")
    private String from;

    /**
     * 交易对象地址
     */
    @TableField("`to`")
    private String to;

    /**
     * 交易hash
     */
    @TableField("`tx_hash`")
    private String txHash;

    /**
     * 区块高度
     */
    @TableField("`block_number`")
    private Long blockNumber;

    /**
     * 区块的时间戳
     */
    @TableField("`block_timestamp`")
    private Long blockTimestamp;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Long getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Long getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
    }

    public static final String ADDRESS = "`address`";

    public static final String TOKEN_ID = "`token_id`";

    public static final String AMOUNT = "`amount`";

    public static final String FROM = "`from`";

    public static final String TO = "`to`";

    public static final String TX_HASH = "`tx_hash`";

    public static final String BLOCK_NUMBER = "`block_number`";

    public static final String BLOCK_TIMESTAMP = "`block_timestamp`";

    @Override
    public String toString() {
        return "TtTransferLog{" +
        "address=" + address +
        ", tokenId=" + tokenId +
        ", amount=" + amount +
        ", from=" + from +
        ", to=" + to +
        ", txHash=" + txHash +
        ", blockNumber=" + blockNumber +
        ", blockTimestamp=" + blockTimestamp +
        "}";
    }
}

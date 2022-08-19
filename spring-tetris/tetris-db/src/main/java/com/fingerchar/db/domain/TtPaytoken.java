package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtPaytoken
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_paytoken")
public class TtPaytoken extends BaseEntity {


    @TableField("`address`")
    private String address;

    @TableField("`name`")
    private String name;

    @TableField("`token_id`")
    private Integer tokenId;

    @TableField("`decimals`")
    private Integer decimals;

    @TableField("`symbol`")
    private String symbol;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public static final String ADDRESS = "`address`";

    public static final String NAME = "`name`";

    public static final String TOKEN_ID = "`token_id`";

    public static final String DECIMALS = "`decimals`";

    public static final String SYMBOL = "`symbol`";

    @Override
    public String toString() {
        return "TtPaytoken{" +
        "address=" + address +
        ", name=" + name +
        ", tokenId=" + tokenId +
        ", decimals=" + decimals +
        ", symbol=" + symbol +
        "}";
    }
}

package com.fingerchar.db.domain;

import java.math.BigDecimal;
import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtNftItems
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_nft_items")
public class TtNftItems extends BaseEntity {


    /**
     * nft  id
     */
    @TableField("`nft_id`")
    private Long nftId;

    @TableField("`address`")
    private String address;

    @TableField("`token_id`")
    private String tokenId;

    /**
     * 价格
     */
    @TableField("`price`")
    private BigDecimal price;

    /**
     * usdt价格
     */
    @TableField("`usdt_price`")
    private BigDecimal usdtPrice;

    /**
     * 支付币种地址
     */
    @TableField("`paytoken_address`")
    private String paytokenAddress;

    /**
     * 支付币种名称
     */
    @TableField("`paytoken_name`")
    private String paytokenName;

    /**
     * 支付币种精确度
     */
    @TableField("`paytoken_decimals`")
    private Integer paytokenDecimals;

    /**
     * 支付币种符号
     */
    @TableField("`paytoken_symbol`")
    private String paytokenSymbol;

    /**
     * 售卖数量
     */
    @TableField("`sell_quantity`")
    private Long sellQuantity;

    /**
     * 数量
     */
    @TableField("`quantity`")
    private Long quantity;

    /**
     * 签名
     */
    @TableField("`signature`")
    private String signature;

    /**
     * token拥有者
     */
    @TableField("`item_owner`")
    private String itemOwner;

    @TableField("`category_id`")
    private Long categoryId;

    /**
     * 是否在售
     */
    @TableField("`onsell`")
    private Boolean onsell;

    /**
     * 1=>sale 2=> auction
     */
    @TableField("`onsell_type`")
    private Integer onsellType;

    /**
     * 发布售卖的时间
     */
    @TableField("`onsell_time`")
    private Long onsellTime;

    @TableField("`is_sync`")
    private Boolean isSync;


    public Long getNftId() {
        return nftId;
    }

    public void setNftId(Long nftId) {
        this.nftId = nftId;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getUsdtPrice() {
        return usdtPrice;
    }

    public void setUsdtPrice(BigDecimal usdtPrice) {
        this.usdtPrice = usdtPrice;
    }

    public String getPaytokenAddress() {
        return paytokenAddress;
    }

    public void setPaytokenAddress(String paytokenAddress) {
        this.paytokenAddress = paytokenAddress;
    }

    public String getPaytokenName() {
        return paytokenName;
    }

    public void setPaytokenName(String paytokenName) {
        this.paytokenName = paytokenName;
    }

    public Integer getPaytokenDecimals() {
        return paytokenDecimals;
    }

    public void setPaytokenDecimals(Integer paytokenDecimals) {
        this.paytokenDecimals = paytokenDecimals;
    }

    public String getPaytokenSymbol() {
        return paytokenSymbol;
    }

    public void setPaytokenSymbol(String paytokenSymbol) {
        this.paytokenSymbol = paytokenSymbol;
    }

    public Long getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Long sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getItemOwner() {
        return itemOwner;
    }

    public void setItemOwner(String itemOwner) {
        this.itemOwner = itemOwner;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getOnsell() {
        return onsell;
    }

    public void setOnsell(Boolean onsell) {
        this.onsell = onsell;
    }

    public Integer getOnsellType() {
        return onsellType;
    }

    public void setOnsellType(Integer onsellType) {
        this.onsellType = onsellType;
    }

    public Long getOnsellTime() {
        return onsellTime;
    }

    public void setOnsellTime(Long onsellTime) {
        this.onsellTime = onsellTime;
    }

    public Boolean getIsSync() {
        return isSync;
    }

    public void setIsSync(Boolean isSync) {
        this.isSync = isSync;
    }

    public static final String NFT_ID = "`nft_id`";

    public static final String ADDRESS = "`address`";

    public static final String TOKEN_ID = "`token_id`";

    public static final String PRICE = "`price`";

    public static final String USDT_PRICE = "`usdt_price`";

    public static final String PAYTOKEN_ADDRESS = "`paytoken_address`";

    public static final String PAYTOKEN_NAME = "`paytoken_name`";

    public static final String PAYTOKEN_DECIMALS = "`paytoken_decimals`";

    public static final String PAYTOKEN_SYMBOL = "`paytoken_symbol`";

    public static final String SELL_QUANTITY = "`sell_quantity`";

    public static final String QUANTITY = "`quantity`";

    public static final String SIGNATURE = "`signature`";

    public static final String ITEM_OWNER = "`item_owner`";

    public static final String CATEGORY_ID = "`category_id`";

    public static final String ONSELL = "`onsell`";

    public static final String ONSELL_TYPE = "`onsell_type`";

    public static final String ONSELL_TIME = "`onsell_time`";

    public static final String IS_SYNC = "`is_sync`";

    @Override
    public String toString() {
        return "TtNftItems{" +
        "nftId=" + nftId +
        ", address=" + address +
        ", tokenId=" + tokenId +
        ", price=" + price +
        ", usdtPrice=" + usdtPrice +
        ", paytokenAddress=" + paytokenAddress +
        ", paytokenName=" + paytokenName +
        ", paytokenDecimals=" + paytokenDecimals +
        ", paytokenSymbol=" + paytokenSymbol +
        ", sellQuantity=" + sellQuantity +
        ", quantity=" + quantity +
        ", signature=" + signature +
        ", itemOwner=" + itemOwner +
        ", categoryId=" + categoryId +
        ", onsell=" + onsell +
        ", onsellType=" + onsellType +
        ", onsellTime=" + onsellTime +
        ", isSync=" + isSync +
        "}";
    }
}

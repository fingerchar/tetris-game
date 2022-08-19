package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtUserBalance
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_user_balance")
public class TtUserBalance extends BaseEntity {


    @TableField("`address`")
    private String address;

    @TableField("`token`")
    private String token;

    @TableField("`amount`")
    private String amount;

    @TableField("`lock_amount`")
    private String lockAmount;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(String lockAmount) {
        this.lockAmount = lockAmount;
    }

    public static final String ADDRESS = "`address`";

    public static final String TOKEN = "`token`";

    public static final String AMOUNT = "`amount`";

    public static final String LOCK_AMOUNT = "`lock_amount`";

    @Override
    public String toString() {
        return "TtUserBalance{" +
        "address=" + address +
        ", token=" + token +
        ", amount=" + amount +
        ", lockAmount=" + lockAmount +
        "}";
    }
}

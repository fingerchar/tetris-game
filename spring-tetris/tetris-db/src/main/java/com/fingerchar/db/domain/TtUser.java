package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtUser
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_user")
public class TtUser extends BaseEntity {


    @TableField("`avatar`")
    private String avatar;

    @TableField("`nickname`")
    private String nickname;

    @TableField("`email`")
    private String email;

    @TableField("`address`")
    private String address;

    @TableField("`open_token`")
    private String openToken;

    @TableField("`brief`")
    private String brief;

    @TableField("`max_score`")
    private String maxScore;


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenToken() {
        return openToken;
    }

    public void setOpenToken(String openToken) {
        this.openToken = openToken;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public static final String AVATAR = "`avatar`";

    public static final String NICKNAME = "`nickname`";

    public static final String EMAIL = "`email`";

    public static final String ADDRESS = "`address`";

    public static final String OPEN_TOKEN = "`open_token`";

    public static final String BRIEF = "`brief`";

    public static final String MAX_SCORE = "`max_score`";

    @Override
    public String toString() {
        return "TtUser{" +
        "avatar=" + avatar +
        ", nickname=" + nickname +
        ", email=" + email +
        ", address=" + address +
        ", openToken=" + openToken +
        ", brief=" + brief +
        ", maxScore=" + maxScore +
        "}";
    }
}

package com.fingerchar.db.domain;

import com.fingerchar.db.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @Description TtSystem
 * @Author 
 * @Date 2022-03-29
 * @Version 2.1
 */
@TableName("tt_system")
public class TtSystem extends BaseEntity {


    @TableField("`key_name`")
    private String keyName;

    @TableField("`key_value`")
    private String keyValue;

    @TableField("`show`")
    private Boolean show;


    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public static final String KEY_NAME = "`key_name`";

    public static final String KEY_VALUE = "`key_value`";

    public static final String SHOW = "`show`";

    @Override
    public String toString() {
        return "TtSystem{" +
        "keyName=" + keyName +
        ", keyValue=" + keyValue +
        ", show=" + show +
        "}";
    }
}

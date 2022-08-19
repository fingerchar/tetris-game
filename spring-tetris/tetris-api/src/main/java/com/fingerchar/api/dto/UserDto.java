package com.fingerchar.api.dto;

import com.fingerchar.db.domain.TtUser;

public class UserDto {
	
    private String avatar;

    private String nickname;

    private String email;

    private String address;

    private String brief;

    public UserDto() {

    }

    public  UserDto(TtUser user) {
        if(null != user) {
            this.avatar = user.getAvatar();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.address = user.getAddress();
            this.brief = user.getBrief();
        }
    }

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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}

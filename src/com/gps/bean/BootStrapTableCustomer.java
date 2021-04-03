package com.gps.bean;

import java.util.Objects;

public class BootStrapTableCustomer {
    private int id;
    private String username;
    private String userPhone;
    private String cardId;
    private String password;

    private String createTime;
    private String loginTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BootStrapTableCustomer that = (BootStrapTableCustomer) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(userPhone, that.userPhone) && Objects.equals(cardId, that.cardId) && Objects.equals(password, that.password) && Objects.equals(createTime, that.createTime) && Objects.equals(loginTime, that.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, cardId, password, createTime, loginTime);
    }

    @Override
    public String toString() {
        return "BootStrapTableCustomer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardId='" + cardId + '\'' +
                ", password='" + password + '\'' +
                ", createTime='" + createTime + '\'' +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public BootStrapTableCustomer(int id, String username, String userPhone, String cardId, String password, String createTime, String loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.cardId = cardId;
        this.password = password;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public BootStrapTableCustomer() {
    }
}

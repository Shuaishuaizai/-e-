package com.gps.bean;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Courier {
    private int id;
    private String username;
    private String userPhone;
    private String cardId;
    private String password;
    private String packages;
    private Timestamp createTime;
    private String loginTime;

    public Courier() {
    }

    public Courier(String username, String userPhone, String cardId, String password, String packages) {
        this.username = username;
        this.userPhone = userPhone;
        this.cardId = cardId;
        this.password = password;
        this.packages = packages;

    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardId='" + cardId + '\'' +
                ", password='" + password + '\'' +
                ", packages='" + packages + '\'' +
                ", createTime=" + createTime +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id == courier.id && Objects.equals(username, courier.username) && Objects.equals(userPhone, courier.userPhone) && Objects.equals(cardId, courier.cardId) && Objects.equals(password, courier.password) && Objects.equals(packages, courier.packages) && Objects.equals(createTime, courier.createTime) && Objects.equals(loginTime, courier.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, cardId, password, packages, createTime, loginTime);
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

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Courier(int id, String username, String userPhone, String cardId, String password, String packages, Timestamp createTime, String loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.cardId = cardId;
        this.password = password;
        this.packages = packages;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }
}

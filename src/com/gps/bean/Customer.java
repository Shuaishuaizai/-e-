package com.gps.bean;

import java.sql.Timestamp;
import java.util.Objects;

public class Customer {
    private int id;
    private String username;
    private String userPhone;
    private String cardId;
    private String password;
    private Timestamp createTime;
    private Timestamp loginTime;
    private boolean customer;

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(username, customer.username) && Objects.equals(userPhone, customer.userPhone) && Objects.equals(cardId, customer.cardId) && Objects.equals(password, customer.password) && Objects.equals(createTime, customer.createTime) && Objects.equals(loginTime, customer.loginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, userPhone, cardId, password, createTime, loginTime);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardId='" + cardId + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Customer(int id, String username, String userPhone, String cardId, String password, Timestamp createTime, Timestamp loginTime) {
        this.id = id;
        this.username = username;
        this.userPhone = userPhone;
        this.cardId = cardId;
        this.password = password;
        this.createTime = createTime;
        this.loginTime = loginTime;
    }

    public Customer(String username, String userPhone, String cardId, String password) {
        this.username = username;
        this.userPhone = userPhone;
        this.cardId = cardId;
        this.password = password;
    }

    public Customer() {
    }
}

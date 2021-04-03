package com.gps.bean;
public class BootStrapTableCourier {
    private int id;
    private String username;
    private String userPhone;
    private String cardId;
    private String password;
    private String packages;
    private String createTime;
    private String loginTime;


    @Override
    public String toString() {
        return "BootStrapTableCourier{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", cardId='" + cardId + '\'' +
                ", password='" + password + '\'' +
                ", packages='" + packages + '\'' +
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

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
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

    public BootStrapTableCourier(int id, String username, String userPhone, String cardId, String password, String packages, String createTime, String loginTime) {
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

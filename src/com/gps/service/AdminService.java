package com.gps.service;

import com.gps.dao.BaseAdminDao;
import com.gps.dao.imp.AdminDaoMysql;

import java.util.Date;

public class AdminService {
    private  static BaseAdminDao dao=new AdminDaoMysql();

    /**
     * 更新登陆时间与IP
     * @param username
     * @param date
     * @param ip
     */
    public  static  void  updateLoginTimeAndIp(String username, Date date,String ip){
            dao.updateLoginTime(username,date,ip);
    }

    /**
     * 登陆
     * @param username
     * @param password
     * @return   true 成功   flase 失败
     */
    public static boolean login(String username,String password){
        return dao.login(username,password);

    }
}

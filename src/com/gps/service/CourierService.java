package com.gps.service;

import com.gps.bean.Courier;
import com.gps.bean.Customer;
import com.gps.dao.BaseCourierDao;
import com.gps.dao.imp.CourierDaoMysql;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CourierService {
    private  static BaseCourierDao dao=new CourierDaoMysql();
    /**
     * 用于查询数据库中的全部快递人员数
     *
     * @return
     */

    public static int console() {
        return dao.console();
    }

    /**
     * 更新登陆时间与IP
     * @param username
     * @param date
     *
     */
    public  static  void  updateLoginTimeAndIp(String username, Date date){
       dao.updateLoginTime(username,date);
    }


    /**
     * 用于查询数据库中所有的快递人员
     * @return
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber){
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据手机号，查询快递人员
     * @param phone
     * @return
     */
    public static Courier findByPhone(String phone){

        return dao.findByPhone(phone);
    }

    /**
     * 快递人员的录入
     * @param c 录入的人员
     * @return
     */
    public  static  boolean insert(Courier c,Date date){
        return  dao.insert(c,date);

    }

    /**
     * 快递人员的更新修改
     * @param phone   修改的快递人员的电话号码
     * @param newCourier 修改的新的快递人员
     * @return 结果   true 成功    flase失败
     */
    public static  boolean update(String phone,Courier newCourier){

        return dao.update(phone,newCourier);
    }

    /**
     * 根据手机号码，删除快递人员
     * @param phone
     * @return
     */
    public static boolean delete(String phone){
        return dao.delete(phone);
    }
}

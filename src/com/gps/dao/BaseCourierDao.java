package com.gps.dao;

import com.gps.bean.Courier;
import com.gps.bean.Customer;
import com.gps.bean.Express;
import com.gps.exception.DuplicateCodeException;

import java.util.Date;
import java.util.List;


public interface BaseCourierDao {
    /**
     * 用于查询数据库中的全部快递人数
     *
     * @return [{size:总数,day：新增}.{size:总数,day：新增}]
     */
    int console();
    /**
     * 根据用户名，更新登陆时间和ip
     * @param username
     */
    void updateLoginTime(String username,Date date);


    /**
     * 用于查询数据库中所有的快递人员
     *
     * @return
     */
    List<Courier> findAll(boolean limit,int offset,int pageNumber);


    /**
     * 根据手机号，查询快递人员
     *
     * @param phone
     * @return
     */
    Courier findByPhone(String phone);


    /**
     * 快递人员的录入
     *
     * @param c 录入的人员
     * @return
     */
    boolean insert(Courier c,Date date);

    /**
     * 快递人员的更新修改
     *
     * @param phone      修改的快递人员的电话号码
     * @param newCourier 修改的新的快递人员
     * @return 结果   true 成功    flase失败
     */
    boolean update(String phone, Courier newCourier);

    /**
     * 根据手机号码，删除快递人员
     *
     * @param phone
     * @return
     */
    boolean delete(String phone);


}

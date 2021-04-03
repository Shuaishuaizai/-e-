package com.gps.dao;

import com.gps.bean.Customer;

import java.util.Date;
import java.util.List;


public interface BaseCustomerDao {
    /**
     * 用于查询数据库中的全部用户
     *
     * @return 总数
     */
    int console();
    /**
     * 根据用户名，更新登陆时间和ip
     * @param username
     */
    void updateLoginTime(String username,Date date);


    /**
     * 用于查询数据库中所有的用户
     *
     * @return
     */
    List<Customer> findAll(boolean limit, int offset, int pageNumber);


    /**
     * 根据手机号，查询用户
     *
     * @param phone
     * @return
     */
    Customer findByPhone(String phone);


    /**
     * 用户的录入
     *
     * @param c 录入的人员
     * @return
     */
    boolean insert(Customer c,Date date);

    /**
     * 用户的更新修改
     *
     * @param phone      修改的用户的电话号码
     * @param newCustomer 修改的新的用户
     * @return 结果   true 成功    flase失败
     */
    boolean update(String phone,Customer newCustomer);

    /**
     * 根据手机号码，删除用户
     *
     * @param phone
     * @return
     */
    boolean delete(String phone);


}

package com.gps.dao;

import com.gps.bean.Express;
import com.gps.exception.DuplicateCodeException;

import java.util.List;
import java.util.Map;

public interface BaseExpressDao {
    /**
     * 用于查询数据库中的全部快递（总数+新增）
     *          待取件的快递（总数+新增）
     * @return [{size:总数,day：新增}.{size:总数,day：新增}]
     */
    List<Map<String,Integer>> console();

    /**
     * 用于查询所有的快递
     * @param limit 是否分页的标记，true表示分页，false表示不分页，查询所有的快递
     * @param offset SQL语句的起始索引
     * @param pageNumber 每一页
     * @return 快递的集合
     */
    List<Express> findAll(boolean limit,int offset,int pageNumber);

    /**
     *
     * 根据快递单号 ，查询快递信息
     * @param number  单号
     * @return 查询的快递信息，快递单号 不存在时返回null
     */
    Express findByMumber(String number);

    /**
     *
     * 根据取件码 ，查询快递信息
     * @param code  取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    Express findByCode(String code);
    /**
     *
     * 根据用户的手机号码 ，查询所有快递信息
     * @param userPhone 取件码
     * @return 查询快递信息
     */
    List<Express> findByUserPhone(String userPhone);
    /**
     *
     * 根据用户的手机号码 ，查询所有快递信息
     * @param userPhone 取件码
     * @return 查询快递信息
     */
    List<Express> findByUserPhoneAndStatus(String userPhone,int status);


    /**
     *
     * 根据录入人的手机号码 ，查询所有快递信息
     * @param sysPhone  取件码
     * @return 查询快递信息
     */
    List<Express> findBySysPhone(String sysPhone);

    /**
     * 快递的录入
     * @param e 录入的快递
     * @return 录入的结果  true 成功    flase失败
     */
      boolean insert(Express e) throws DuplicateCodeException;
    /**
     * 快递的更新修改
     * @param id 修改的快递ID
     * @param newExpress 修改的新的快递对象（number，company，username,userPhone）
     * @return 结果   true 成功    flase失败
     */
    boolean update(int id,Express newExpress);

    /**
     * 根据id ，删除单个快递信息
     * @param id  要删除的快递ID
     * @return   结果  true 成功    flase失败
     */
    boolean delete(int id);


    /**
     * 更改快递的状态为1，表示取件完成
     * @param code 取件码
     * @return 结果   true 成功    flase失败
     */
    boolean updateStatus(String code);

}

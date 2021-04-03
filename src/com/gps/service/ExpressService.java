package com.gps.service;

import com.gps.bean.Express;

import com.gps.dao.BaseExpressDao;
import com.gps.dao.imp.ExpressDaoMysql;
import com.gps.exception.DuplicateCodeException;
import com.gps.util.RandomUtil;
import com.gps.util.SMSUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    private static BaseExpressDao dao = new ExpressDaoMysql();

    /**
     * 用于查询数据库中的全部快递（总数+新增）
     * 待取件的快递（总数+新增）
     *
     * @return [{size:总数,day：新增}.{size:总数,day：新增}]
     */

    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有的快递
     *
     * @param limit      是否分页的标记，true表示分页，false表示不分页，查询所有的快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页
     * @return 快递的集合
     */

    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据快递单号 ，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，快递单号 不存在时返回null
     */

    public static Express findByMumber(String number) {
        return dao.findByMumber(number);
    }



    /**
     * 根据取件码 ，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */

    public static Express findByCode(String code) {
        return dao.findByMumber(code);
    }

    /**
     * 根据用户的手机号码 ，查询所有快递信息
     *
     * @param UserPhone 取件码
     * @return 查询快递信息
     */

    public static List<Express> findByUserPhone(String UserPhone) {
        return dao.findByUserPhone(UserPhone);
    }

    /**
     * 根据录入人的手机号码 ，查询所有快递信息
     *
     * @param SysPhone 取件码
     * @return 查询快递信息
     */

    public static List<Express> findBySysPhone(String SysPhone) {
        return dao.findBySysPhone(SysPhone);
    }
    /**
     * 根据录入人的手机号码和状态码 ，查询所有快递信息
     *
     * @param SysPhone 取件码  status 状态码
     * @return 查询快递信息
     */

    public static List<Express> findBySysPhoneAndStatus(String SysPhone ,int status) {
        return dao.findByUserPhoneAndStatus(SysPhone,status);
    }

    /**
     * 快递的录入
     *
     * @param e 录入的快递
     * @return 录入的结果  true 成功    flase失败
     */
    public static boolean insert(Express e) {
        //1.生成了取件码
        e.setCode(RandomUtil.getCode() + "");
        try {
            boolean flag = dao.insert(e);
//            if(flag){
//                //录入成功  发送短信
//              //  SMSUtil.send(e.getUserPhone(),e.getCode());
//            }
            return flag;
        } catch (DuplicateCodeException duplicateCodeException) {
            return insert(e);

        }

    }

    /**
     * 快递的更新修改
     *
     * @param id         修改的快递ID
     * @param newExpress 修改的新的快递对象（number，company，username,userPhone）
     * @return 结果   true 成功    flase失败
     */

    public static boolean update(int id, Express newExpress) {
        if (newExpress.getUserPhone() != null) {
            dao.delete(id);
            return insert(newExpress);
        } else {
            boolean update = dao.update(id, newExpress);
            Express e = dao.findByMumber(newExpress.getNumber());
            if (newExpress.getStatus() == 1) {
                updateStatus(e.getCode());
            }
            return update;
        }


    }

    /**
     * 根据id ，删除单个快递信息
     *
     * @param id 要删除的快递ID
     * @return 结果  true 成功    flase失败
     */

    public static boolean delete(int id) {
        return dao.delete(id);
    }

    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 取件码
     * @return 结果   true 成功    flase失败
     */

    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }
}

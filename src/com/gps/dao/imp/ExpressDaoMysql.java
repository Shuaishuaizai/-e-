package com.gps.dao.imp;

import com.gps.bean.Express;
import com.gps.dao.BaseExpressDao;
import com.gps.exception.DuplicateCodeException;
import com.gps.util.DruidUtil;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {
    //用于查询数据库中的全部快递（总数+新增）
    public static final String SQL_CONSOLE = "SELECT " +
            "COUNT(ID) data1_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) OR NULL) data1_day," +
            "COUNT(STATUS=0 OR NULL) data2_size," +
            "COUNT(TO_DAYS(INTIME)=TO_DAYS(NOW()) AND STATUS=0 OR NULL) data2_day" +
            " FROM EXPRESS";
    //用于查询数据库中的所有的快递信息
    public static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    //用于分页查询数据库中的所有的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    //通过快递单号查询快递信息
    public static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER=?";
    //通过取件码查询快递信息
    public static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE=?";
    //通过录入人的手机号码查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM EXPRESS WHERE SYSPHONE=?";
    //通过录入人的手机号码查询快递信息
    public static final String SQL_FIND_BY_SYSPHONE_STATUS = "SELECT * FROM EXPRESS WHERE SYSPHONE=? AND STATUS=?";

    //通过用户的手机号码查询快递信息
    public static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM EXPRESS WHERE USERPHONE=?";
    //快递的录入
    public static final String SQL_INSERT = "INSERT INTO EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    //快递的修改
    public static final String SQL_UPDATE = "UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=? ,STATUS=? WHERE ID=?";
    //快递的删除
    public static final String SQL_DELETE = "delete from express WHERE id=?";
    //快递的状态码改变（取件）
    public static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1 WHERE CODE=?";

    /**
     * 用于查询数据库中的全部快递（总数+新增）
     * 待取件的快递（总数+新增）
     *
     * @return [{size:总数,day：新增}.{size:总数,day：新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String, Integer>> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.    填充参数（可选）
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()) {
                int data1_size = result.getInt("data1_size");
                int data1_day = result.getInt("data1_day");
                int data2_size = result.getInt("data2_size");
                int data2_day = result.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return data;
    }

    /**
     * 用于查询所有的快递
     *
     * @param limit      是否分页的标记，true表示分页，false表示不分页，查询所有的快递
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            if (limit) {
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数（可选）
                state.setInt(1, offset);
                state.setInt(2, pageNumber);
            } else {
                state = conn.prepareStatement(SQL_FIND_ALL);
            }

            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while (result.next()) {
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                int status = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return data;
    }

    /**
     * 根据快递单号 ，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，快递单号 不存在时返回null
     */
    @Override
    public Express findByMumber(String number) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.    填充参数（可选）
            state.setString(1, number);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                int status = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return null;
    }

    /**
     * 根据取件码 ，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            //3.    填充参数（可选）
            state.setString(1, code);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String number = result.getString("number");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                int status = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                return e;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }
        return null;
    }

    /**
     * 根据用户的手机号码 ，查询所有快递信息
     *
     * @param userPhone 取件码
     * @return 查询快递信息
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.    填充参数（可选）
            state.setString(1, userPhone);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while (result.next()) {
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                int status = result.getInt("status");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return data;
    }

    /**
     * 根据用户的手机号码 ，查询所有快递信息
     *
     * @param userPhone 取件码
     * @param status
     * @return 查询快递信息
     */
    @Override
    public List<Express> findByUserPhoneAndStatus(String userPhone, int status) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE_STATUS);
            //3.    填充参数（可选）
            state.setString(1, userPhone);
            state.setInt(2, status);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while (result.next()) {
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                String sysPhone = result.getString("sysPhone");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return data;
    }

    /**
     * 根据录入人的手机号码 ，查询所有快递信息
     *
     * @param sysPhone 取件码
     * @return 查询快递信息
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.    填充参数（可选）
            state.setString(1, sysPhone);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            while (result.next()) {
                int id = result.getInt("id");
                String number = result.getString("number");
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String company = result.getString("company");
                String code = result.getString("code");
                Timestamp inTime = result.getTimestamp("intime");
                Timestamp outTime = result.getTimestamp("outtime");
                int status = result.getInt("status");
                Express e = new Express(id, number, username, userPhone, company, code, inTime, outTime, status, sysPhone);
                data.add(e);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return null;
    }

    /**
     * 快递的录入
     * INSERT INTO EXPRESS (NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)
     *
     * @param e 录入的快递
     * @return 录入的结果  true 成功    flase失败
     */
    @Override
    public boolean insert(Express e) throws  DuplicateCodeException{
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数
            state.setString(1, e.getNumber());
            state.setString(2, e.getUsername());
            state.setString(3, e.getUserPhone());
            state.setString(4, e.getCompany());
            state.setString(5, e.getCode());
            state.setString(6, e.getSysPhone());
            //4.    执行SQL语句，并获取执行结果
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
            if(e1.getMessage().endsWith("for key 'code'")){
                //是因为取件码重复，而出现了异常
                DuplicateCodeException e2=new DuplicateCodeException(e1.getMessage());
                throw e2;
            }else {
             e1.printStackTrace();
            }
        } finally {
            //5.    资源释放
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 快递的更新修改
     *
     * @param id         修改的快递ID
     * @param newExpress 修改的新的快递对象（number，company，username,userPhone）
     * @return 结果   true 成功    flase失败
     */
    @Override
    public boolean update(int id, Express newExpress) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state=null;
        try {
             state = conn.prepareStatement(SQL_UPDATE);
             state.setString(1,newExpress.getNumber());
             state.setString(2,newExpress.getUsername());
             state.setString(3,newExpress.getCompany());
             state.setInt(4,newExpress.getStatus());
             state.setInt(5,id);
             return  state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id ，删除单个快递信息
     *
     * @param id 要删除的快递ID
     * @return 结果  true 成功    flase失败
     */
    @Override
    public boolean delete(int id) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state=null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setInt(1,id);
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }

        return false;
    }


    /**
     * 更改快递的状态为1，表示取件完成
     *
     * @param code 取件的快递单号
     * @return 结果   true 成功    flase失败
     */
    @Override
    public boolean updateStatus(String code) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state=null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            state.setString(1,code);
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }

        return false;
    }
}

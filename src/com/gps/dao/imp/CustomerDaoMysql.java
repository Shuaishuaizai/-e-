package com.gps.dao.imp;

import com.gps.bean.Courier;
import com.gps.bean.Customer;
import com.gps.dao.BaseCourierDao;
import com.gps.dao.BaseCustomerDao;
import com.gps.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoMysql implements BaseCustomerDao {
    public static final String SQL_CONSOLE="SELECT COUNT(ID) count FROM CUSTOMER";
    //更新登录的时间
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE CUSTOMER SET LOGINTIME=? WHERE USERNAME=?";
    //用于查询数据库中的所有的用户信息
    public static final String SQL_FIND_ALL = "SELECT * FROM CUSTOMER";
    //用于分页查询数据库中的所有的用户信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM CUSTOMER LIMIT ?,?";
    //通过快递电话号码查询用户信息
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM CUSTOMER WHERE USERPHONE=?";
    //用户人员的录入
    public static final String SQL_INSERT = "INSERT INTO CUSTOMER (USERNAME,USERPHONE,CARDID,PASSWORD,CREATETIME) VALUES(?,?,?,?,NOW())";
    //用户人员的修改
    public static final String SQL_UPDATE = "UPDATE CUSTOMER SET USERNAME=?,CARDID=?,PASSWORD=? WHERE USERPHONE=?";
    //用户人员的删除
    public static final String SQL_DELETE = "delete from CUSTOMER WHERE USERPHONE=?";

    /**
     * 用于查询数据库中的全部快递用户
     * @return
     */
    @Override
    public int console() {
        int count=0;
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
                count=result.getInt("count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //6.    资源的释放
            DruidUtil.close(conn, state, result);
        }

        return count;
    }


    /**
     * 根据用户名，更新登陆时间和ip
     *
     * @param username
     * @param date
     *
     */
    @Override
    public void updateLoginTime(String username, java.util.Date date) {
        //1.    获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.    填充参数
            state.setDate(1, new Date(date.getTime()));
            state.setString(2, username);
            //4.    执行
            state.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5.    释放资源
            DruidUtil.close(conn, state, null);
        }
    }



    /**
     * 用于查询数据库中所有的用户
     *
     * @param limit
     * @param offset
     * @param pageNumber
     * @return
     */
    @Override
    public List<Customer> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Customer> data = new ArrayList<>();
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
                String username = result.getString("username");
                String userPhone = result.getString("userphone");
                String cardId = result.getString("cardid");
                String password = result.getString("password");
                Timestamp creaTime = result.getTimestamp("createtime");
                Timestamp loginTime = result.getTimestamp("logintime");
                Customer c = new Customer(id, username, userPhone, cardId, password, creaTime, loginTime);
                data.add(c);
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
     * 根据手机号，查询快递人员
     *
     * @param phone
     * @return
     */
    @Override
    public Customer findByPhone(String phone) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet result = null;
        //2.    预编译SQL语句
        try {
            state = conn.prepareStatement(SQL_FIND_BY_PHONE);
            //3.    填充参数（可选）
            state.setString(1, phone);
            //4.    执行SQL语句
            result = state.executeQuery();
            //5.    获取执行的结果
            if (result.next()) {
                int id = result.getInt("id");
                String username = result.getString("username");
                String cardId = result.getString("cardid");
                String password = result.getString("password");
                Timestamp createTime = result.getTimestamp("createtime");
                Timestamp loginTime = result.getTimestamp("logintime");
                Customer c = new Customer(id, username, phone, cardId, password, createTime, loginTime);
                return c;

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
     * 快递人员的录入
     *
     * @param c 录入的人员
     * @return
     */
    @Override
    public boolean insert(Customer c,java.util.Date date) {
        //获取当前时间
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数
            state.setString(1, c.getUsername());
            state.setString(2, c.getUserPhone());
            state.setString(3, c.getCardId());
            state.setString(4, c.getPassword());
//            state.setDate(6, new java.sql.Date(date.getTime()));
//            state.setTimestamp(5,);
            //4.    执行SQL语句，并获取执行结果
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            //5.    资源释放
            DruidUtil.close(conn, state, null);
        }
        return false;
    }

    /**
     * 快递人员的更新修改
     *
     * @param phone      修改的快递人员的电话号码
     * @param newCustomer 修改的新的快递人员
     * @return 结果   true 成功    flase失败
     */
    @Override
    public boolean update(String phone, Customer newCustomer) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state=null;
        try {
            //UPDATE Customer SET USERNAME=?,CARDID=?,PASSWORD=? WHERE USERPHONE=?";
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newCustomer.getUsername());
            state.setString(2,newCustomer.getCardId());
            state.setString(3,newCustomer.getPassword());
            state.setString(4,phone);
            return  state.executeUpdate()>0?true:false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据手机号码，删除用户
     *
     * @param phone
     * @return
     */
    @Override
    public boolean delete(String phone) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_DELETE);
            state.setString(1, phone);
            return state.executeUpdate() > 0 ? true : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DruidUtil.close(conn, state, null);
        }

        return false;
    }
}

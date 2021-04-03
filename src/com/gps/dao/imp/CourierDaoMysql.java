package com.gps.dao.imp;

import com.gps.bean.Courier;
import com.gps.bean.Customer;
import com.gps.dao.BaseCourierDao;
import com.gps.util.DruidUtil;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class CourierDaoMysql implements BaseCourierDao {
    public static final String SQL_CONSOLE="SELECT COUNT(ID) count FROM COURIER";
    //更新登录的时间
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE COURIER SET LOGINTIME=? WHERE USERNAME=?";
    //用于查询数据库中的所有的快递人员信息
    public static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    //用于分页查询数据库中的所有的快递信息
    public static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    //通过快递电话号码查询快递人员信息
    public static final String SQL_FIND_BY_PHONE = "SELECT * FROM COURIER WHERE USERPHONE=?";
    //快递人员的录入
    public static final String SQL_INSERT = "INSERT INTO COURIER (USERNAME,USERPHONE,CARDID,PASSWORD,PACKAGES,CREATETIME,LOGINTIME) VALUES(?,?,?,?,?,NOW(),?)";
    //快递人员的修改
    public static final String SQL_UPDATE = "UPDATE COURIER SET USERNAME=?,CARDID=?,PASSWORD=? WHERE USERPHONE=?";
    //快递人员的删除
    public static final String SQL_DELETE = "delete from COURIER WHERE USERPHONE=?";

    /**
     * 用于查询数据库中的全部快递人员数
     *
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
            state.setDate(1, new java.sql.Date(date.getTime()));
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
     * 用于查询数据库中所有的快递人员
     *
     * @param limit
     * @param offset
     * @param pageNumber
     * @return
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
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
                String packages = result.getString("packages");
                Timestamp creaTime = result.getTimestamp("createtime");
                String loginTime = result.getString("logintime");
                Courier c = new Courier(id, username, userPhone, cardId, password, packages, creaTime, loginTime);
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
    public Courier findByPhone(String phone) {
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
                String packages = result.getString("packages");
                Timestamp createTime = result.getTimestamp("createtime");
                String loginTime = result.getString("logintime");
                Courier c = new Courier(id, username, phone, cardId, password, packages, createTime, loginTime);
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
    public boolean insert(Courier c,java.util.Date date) {
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
            state.setString(5, c.getPackages());
//            state.setDate(6, new java.sql.Date(date.getTime()));
            state.setString(6, c.getLoginTime());
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
     * @param newCourier 修改的新的快递人员
     * @return 结果   true 成功    flase失败
     */
    @Override
    public boolean update(String phone, Courier newCourier) {
        //1.    连接的获取
        Connection conn = DruidUtil.getConnection();
        //2.    预编译SQL语句
        PreparedStatement state=null;
        try {
            //UPDATE COURIER SET USERNAME=?,CARDID=?,PASSWORD=? WHERE USERPHONE=?";
            state = conn.prepareStatement(SQL_UPDATE);
            state.setString(1,newCourier.getUsername());
            state.setString(2,newCourier.getCardId());
            state.setString(3,newCourier.getPassword());
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
     * 根据手机号码，删除快递人员
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

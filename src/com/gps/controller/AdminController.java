package com.gps.controller;
import com.gps.bean.Message;
import com.gps.mvc.ResponseBody;
import com.gps.service.AdminService;
import com.gps.util.JSONUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
public class AdminController {
    @ResponseBody("/admin/login.do")
    public  String login(HttpServletRequest request, HttpServletResponse response){
        //1. 接参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2. 调用service传参数，并获取结果
        boolean result = AdminService.login(username, password);
        //3. 根据结果，返回不同的数据
        Message msg=null;
        if(result){
            msg=new Message(0,"登录成功");//{status:0,result:"登录成功"}
            //登录时间和IP的更新
            Date date = new Date();
            String ip=request.getRemoteAddr();//IP
            AdminService.updateLoginTimeAndIp(username,date,ip);
            request.getSession().setAttribute("adminUsername",username);
        }else {
            msg=new Message(-1,"登录失败");//{status:-1,result:"登录失败"}
        }
        //4. 将数据返回给JSON
        String json = JSONUtil.toJSON(msg);
        //5. 将JSON 返回给ajax
        return json;
    }
}

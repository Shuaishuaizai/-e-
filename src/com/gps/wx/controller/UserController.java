package com.gps.wx.controller;

import com.gps.bean.Courier;
import com.gps.bean.Customer;
import com.gps.bean.Message;
import com.gps.mvc.ResponseBody;
import com.gps.service.CourierService;
import com.gps.util.JSONUtil;
import com.gps.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {
    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        //String code = RandomUtil.getCode()+"";
        //boolean flag = SMSUtil.loginSMS(userPhone, code);
        String code = "123456";
        boolean flag = true;
        Message msg = new Message();
        if(flag){
            //短信发送成功
            msg.setStatus(0);
            msg.setResult("验证码已发送,请查收!");
        }else{
            //短信发送失败
            msg.setStatus(1);
            msg.setResult("验证码下发失败,请检查手机号或稍后再试");
        }
        UserUtil.setLoginSms(request.getSession(),userPhone,code);

        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        String userCode = request.getParameter("code");
        String sysCode = UserUtil.getLoginSms(request.getSession(), userPhone);
        Message msg = new Message();
        if (sysCode == null) {
            //这个手机号未获取短信
            msg.setStatus(-1);
            msg.setResult("手机号未获取短信");
        } else if (sysCode.equals(userCode)) {
            //手机号码和短信一致，登录成功
            //判断身份 是否为快递员和用户
            Customer customer = new Customer();
            Courier courier = CourierService.findByPhone(userPhone);
            if (userPhone.equals(courier.getUserPhone())) {
                //证明是管理员
                msg.setStatus(1);
                customer.setCustomer(false);
            } else {
                //证明是用户
                msg.setStatus(0);
                customer.setCustomer(true);
            }

            customer.setUserPhone(userPhone);
            UserUtil.setWxcustomer(request.getSession(),customer);
        }else{
            //这里是验证码不一致 , 登陆失败
            msg.setStatus(-2);
            msg.setResult("验证码不一致,请检查");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/userInfo.do")
    public String userInfo(HttpServletRequest request, HttpServletResponse response){
        Customer customer = UserUtil.getWxCustomer(request.getSession());
        boolean isUser = customer.isCustomer();
        Message msg=new Message();
        if (isUser){
            msg.setStatus(0);
        }else {
            msg.setStatus(1);
        }

        msg.setResult(customer.getUserPhone());
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //1.    销毁session
        request.getSession().invalidate();
        //2.    给客户端回复消息
        Message msg = new Message(0);
        return JSONUtil.toJSON(msg);
    }
}

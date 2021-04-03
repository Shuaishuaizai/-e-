package com.gps.util;

import com.gps.bean.Customer;

import javax.servlet.http.HttpSession;

public class UserUtil {
    public  static  String getUserName(HttpSession session){
        return  (String) session.getAttribute("adminUsername");
    }
    public  static  String getUserPhone(HttpSession session){
        return "188888888";
    }
   public  static  void setLoginSms(HttpSession session,String userPhone,String code){
        session.setAttribute(userPhone,code);
   }
   public static String getLoginSms(HttpSession session,String userPhone){
        return (String) session.getAttribute(userPhone);
   }
   public  static  void setWxcustomer(HttpSession session, Customer customer){
        session.setAttribute("wxUser",customer);

   }
    public  static  Customer getWxCustomer(HttpSession session){
      return (Customer) session.getAttribute("wxUser");

    }

}


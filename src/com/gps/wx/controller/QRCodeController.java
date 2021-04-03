package com.gps.wx.controller;

import com.gps.bean.Customer;
import com.gps.bean.Message;
import com.gps.mvc.ResponseBody;
import com.gps.mvc.ResponseView;
import com.gps.util.JSONUtil;
import com.gps.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class QRCodeController {
    @ResponseView("/wx/createQRCode.do")
    public String createQrcode(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        //express|user
        String type = request.getParameter("type");
        String userPhone = null;
        String qRCodeContent = null;
        if ("express".equals(type)) {
            //快递二维码 展示用户单个的快递
            //code
            qRCodeContent = "express_" + code;
        } else {
            //用户二维码:被扫后，快递员端展示用户所有的快递
            //userPhone
            Customer wxCustomer = UserUtil.getWxCustomer(request.getSession());
            userPhone = wxCustomer.getUserPhone();
            qRCodeContent = "userPhone_" + userPhone;

        }

        HttpSession session = request.getSession();
        session.setAttribute("qrcode", qRCodeContent);
        return "/personQRcode.html";
    }

    @ResponseBody("/wx/qrcode.do")
    public String getQRCode(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String qrcode = (String) session.getAttribute("qrcode");
        Message msg=new Message();
        if (qrcode==null){
            msg.setStatus(-1);
            msg.setResult("取件码获取出错，请用户重新操作");
        }else {

        }
        msg.setStatus(0);
        msg.setResult(qrcode);
        return JSONUtil.toJSON(msg);
    }
}

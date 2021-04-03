package com.gps.wx.controller;

import com.gps.bean.BootStrapTableExpress;
import com.gps.bean.Customer;
import com.gps.bean.Express;
import com.gps.bean.Message;
import com.gps.mvc.ResponseBody;
import com.gps.service.ExpressService;
import com.gps.util.DateFormaUtil;
import com.gps.util.JSONUtil;
import com.gps.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExpressController {
    @ResponseBody("/wx/findExpressByUserPhone.do")
    public String findByUserPhone(HttpServletRequest request, HttpServletResponse response) {
        Customer wxCourier = UserUtil.getWxCustomer(request.getSession());
        String userPhone = wxCourier.getUserPhone();
        List<Express> list = ExpressService.findByUserPhone(userPhone);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormaUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormaUtil.format(e.getOutTime());
            String code = e.getCode() == null ? "已取件" : e.getCode();
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
        } else {
            msg.setStatus(0);
            Stream<BootStrapTableExpress> status0Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("待取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted(((o1, o2) -> {
                long o1Time = DateFormaUtil.toTime(o1.getInTime());
                long o2Time = DateFormaUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            }));
            Stream<BootStrapTableExpress> status1Express = list2.stream().filter(express -> {
                if (express.getStatus().equals("已取件")) {
                    return true;
                } else {
                    return false;
                }
            }).sorted(((o1, o2) -> {
                long o1Time = DateFormaUtil.toTime(o1.getInTime());
                long o2Time = DateFormaUtil.toTime(o2.getInTime());
                return (int) (o1Time - o2Time);
            }));
            Object[] s0 = status0Express.toArray();
            Object[] s1 = status1Express.toArray();
            Map data = new HashMap();
            data.put("status0", s0);
            data.put("status1", s1);
            msg.setData(data);
        }
        String json = JSONUtil.toJSON(msg.getData());
        return json;
    }

    @ResponseBody("/wx/userExpressList.do")
    public String expressList(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        List<Express> list = ExpressService.findBySysPhoneAndStatus(userPhone, 0);
        List<BootStrapTableExpress> list2 = new ArrayList<>();
        for (Express e : list) {
            String inTime = DateFormaUtil.format(e.getInTime());
            String outTime = e.getOutTime() == null ? "未出库" : DateFormaUtil.format(e.getOutTime());
            String code = e.getCode() == null ? "已取件" : e.getCode();
            String status = e.getStatus() == 0 ? "待取件" : "已取件";
            BootStrapTableExpress e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername(), e.getUserPhone(), e.getCompany(), code, inTime, outTime, status, e.getSysPhone());
            list2.add(e2);
        }
        Message msg = new Message();
        if (list.size() == 0) {
            msg.setStatus(-1);
            msg.setResult("未查询到快递");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(list2);

        }
        return JSONUtil.toJSON(msg);

    }
    @ResponseBody("/wx/updateStatus.do")
    public String updateExpressStatus(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        boolean flag = ExpressService.updateStatus(code);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("取件成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("取件码不存在,请用户更新二维码");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/wx/findExpressByCode.do")
    public String findExpressByCode(HttpServletRequest request,HttpServletResponse response){
        String code = request.getParameter("code");
        Express e = ExpressService.findByCode(code);
        BootStrapTableExpress e2 =null;
        if(e!=null){
            e2 = new BootStrapTableExpress(e.getId(), e.getNumber(), e.getUsername()
                    , e.getUserPhone(), e.getCompany(), e.getCode(),
                    DateFormaUtil.format(e.getInTime()), e.getOutTime()==null?"未出库":DateFormaUtil.format(e.getOutTime()),e.getStatus()==0?"待取件":"已取件", e.getSysPhone());
        }
        Message msg = new Message();
        if(e == null){
            msg.setStatus(-1);
            msg.setResult("取件码不存在");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(e2);
        }
        return JSONUtil.toJSON(msg);
    }
}

package com.gps.controller;

import com.gps.bean.*;
import com.gps.mvc.ResponseBody;
import com.gps.service.CourierService;
import com.gps.util.DateFormaUtil;
import com.gps.util.JSONUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class CourierController {
    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取查询数据起始的偏移值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2. 获得当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3. 进行分页查询
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
//        //登录时间和IP的更新
//        Date date = new Date();
//        CourierService.updateLoginTimeAndIp(username,date);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        for (Courier c : list) {
            String createTime = DateFormaUtil.format(c.getCreateTime());
            String loginTime = c.getLoginTime()==null?"无": c.getLoginTime();
            BootStrapTableCourier c2 = new BootStrapTableCourier(c.getId(),c.getUsername(),c.getUserPhone(),c.getCardId(),c.getPassword(),c.getPackages(),createTime,loginTime);
            list2.add(c2);
        }
        //4. 将集合封装为 bootstrap-table识别的格式
        Integer total = CourierService.console();
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;

    }
    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String userPhone = request.getParameter("userPhone");
        String cardId = request.getParameter("cardId");
        String password = request.getParameter("password");
        //获取当前的时间
        Date date = new Date();
        //TODO:还没有编写柜子端，未存储任何录入人信息
        Courier c=new Courier(username,userPhone,cardId,password,"0");
        boolean flag =CourierService.insert(c,date);
        Message message = new Message();
        if (flag) {
            //录入成功.
            message.setStatus(0);
            message.setResult("快递人员录入成功！");
        } else {
            //录入失败
            message.setStatus(-1);
            message.setResult("快递人员录入失败！");
        }
        String json = JSONUtil.toJSON(message);
        return json;

    }
    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request, HttpServletResponse response) {
        String userPhone = request.getParameter("userPhone");
        Courier c = CourierService.findByPhone(userPhone);
        Message msg = new Message();
        if (c== null) {
            msg.setStatus(-1);
            msg.setResult("手机号码不存在");
        } else {
            msg.setStatus(0);
            msg.setResult("查询成功！");
            msg.setData(c);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String cardId = request.getParameter("cardId");
        String password = request.getParameter("password");
        Courier newCourier=new Courier();
        newCourier.setUsername(userName);
        newCourier.setCardId(cardId);
        newCourier.setUserPhone(userPhone);
        newCourier.setPassword(password);
        boolean flag = CourierService.update(userPhone, newCourier);
        Message msg=new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功！");
        }else {
            msg.setStatus(-1);
            msg.setResult("修改失败！");
        }
        String json = JSONUtil.toJSON(msg);
        return json;

    }
    @ResponseBody("/courier/delete.do")
    public  String delete(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        boolean flag = CourierService.delete(userPhone);
        System.out.println(userPhone);
        Message msg=new Message();
        if (flag){
            msg.setStatus(0);
            msg.setResult("删除成功！");
        }else {
            msg.setStatus(-1);
            msg.setResult("删除失败！");
        }
        String json=JSONUtil.toJSON(msg);
        return json;
    }
}

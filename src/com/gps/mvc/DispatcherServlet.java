package com.gps.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(urlPatterns = "*.do")
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        InputStream is = DispatcherServlet.class.getClassLoader().getResourceAsStream("application.properties");
       HandlerMapping.load(is);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取用户请求的uri /xx.do
        String uri = req.getRequestURI();
        HandlerMapping.MVCMapping mapping=HandlerMapping.get(uri);
        if(mapping==null){
            resp.sendError(404,"自定义的MVC：映射地址不存在"+uri);
        }
        Object obj=mapping.getObj();
        Method  method=mapping.getMethod();
        Object result=null;
        try {
         result = method.invoke(obj, req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        switch (mapping.getType()){
            case TEXT:
                resp.getWriter().write((String) result);
                break;
            case VIEW:
                resp.sendRedirect((String) result);
                break;
        }
    }
}

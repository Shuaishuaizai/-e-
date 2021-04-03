package com.gps.filter;

import com.gps.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/admin/index.html","/admin/views/*","/express/*"})

public class AccessControlFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        String userName = UserUtil.getUserName(request.getSession());
        if(userName!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            HttpServletResponse response= (HttpServletResponse) servletResponse;
            response.sendError(404,"对不起，你的权限不足！");
        }


    }

    @Override
    public void destroy() {

    }
}

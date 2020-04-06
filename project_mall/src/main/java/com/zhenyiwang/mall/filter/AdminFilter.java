package com.zhenyiwang.mall.filter;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.admin.ResponseResult;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (!method.equalsIgnoreCase("OPTIONS")) {
            if (auth(requestURI)) {
                //需要权限
                String email = (String) request.getSession().getAttribute("authEmail");
                if (email == null) {
                    ResponseResult result = new ResponseResult();
                    result.setCode(10000);
                    result.setMessage("当前页面需要登录才能访问");
                    response.getWriter().println(new Gson().toJson(result));
                    return;
                }
            }
        }


        chain.doFilter(req, resp);
    }

    private boolean auth(String requestURI) {
        if ("/api/admin/admin/login".equalsIgnoreCase(requestURI)) {
            //登陆页面不需要权限
            return false;
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

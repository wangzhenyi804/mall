package com.zhenyiwang.mall.filter;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.admin.ResponseResult;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/api/user/*")
public class MallFilter implements Filter {
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

        /*if (!"OPTIONS".equalsIgnoreCase(method)) {
            if (auth(requestURI)) {
                //需要权限
                String authEmail = (String) request.getSession().getAttribute("authEmail");
                if (StringUtils.isEmpty(authEmail)) {
                    ResponseResult result = new ResponseResult();
                    result.setCode(10000);
                    result.setMessage("当前页面需要登陆后才能访问");
                    response.getWriter().println(new Gson().toJson(result));
                    return;
                }
            }
        }*/

        chain.doFilter(req, resp);
    }

    private boolean auth(String requestURI) {
        //不需要权限的listURI
        List<String> noAuthURI = new ArrayList<>();
        noAuthURI.add("/api/user/user/login");
        noAuthURI.add("/api/user/user/signup");
        noAuthURI.add("/api/user/mall/getType");
        noAuthURI.add("/api/user/mall/getGoodsByType");
        noAuthURI.add("/api/user/mall/getGoodsInfo");
        noAuthURI.add("/api/user/mall/getGoodsMsg");
        noAuthURI.add("/api/user/mall/getGoodsComment");
        noAuthURI.add("/api/user/mall/searchGoods");

        for (String check : noAuthURI) {
            if (check.equals(requestURI)) {
                //不需要权限
                return false;
            }
        }
        return true;
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

package com.zhenyiwang.mall.controller;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.ResponseResult;
import com.zhenyiwang.mall.bean.Type;
import com.zhenyiwang.mall.service.TypeService;
import com.zhenyiwang.mall.service.impl.TypeServiceImpl;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/type/*")
public class TypeServlet extends HttpServlet {
    TypeService typeService = new TypeServiceImpl();
    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/type/","");
        if("addType".equals(action)){
            addType(request,response);
        }
    }

    private void addType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult result = new ResponseResult();
        Type type = gson.fromJson(requestBody, Type.class);
        typeService.addType(type);
        result.setCode(0);
        result.setMessage("添加成功");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(gson.toJson(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/type/","");
        if("getType".equals(action)){
            getType(request,response);
        }
    }

    private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Type> types = typeService.getType();
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(types);
        response.getWriter().println(gson.toJson(result));
    }
}

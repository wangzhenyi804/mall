package com.zhenyiwang.mall.controller;


import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.ResponseResult;
import com.zhenyiwang.mall.bean.User;
import com.zhenyiwang.mall.service.AdminService;
import com.zhenyiwang.mall.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/admin/user/*")
public class UserServlet extends HttpServlet {

    AdminService adminService = new AdminServiceImpl();
    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 查询所有用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/user/","");
        if ("allUser".equals(action)){
            queryAllUser(request,response);
        } else if("searchUser".equals(action)){
            searchUser(request,response);
        } else if("deleteUser".equals(action)){
            deleteUser(request,response);
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        adminService.deleteUser(id);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setMessage("删除成功");
        response.getWriter().println(gson.toJson(result));
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResponseResult result = new ResponseResult();
        //获取请求体
        String word = request.getParameter("word");
        List<User> users = adminService.searchUser(word);
        //System.out.println(users);
        result.setCode(0);
        result.setData(users);
        response.getWriter().println(gson.toJson(result));
    }

    private void queryAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> users = adminService.queryAllUser();
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(users);
        response.getWriter().println(gson.toJson(result));
    }
}

package com.zhenyiwang.mall.controller;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.bean.ResponseResult;
import com.zhenyiwang.mall.service.AdminService;
import com.zhenyiwang.mall.service.impl.AdminServiceImpl;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    Gson gson = new Gson();
    AdminService adminService = new AdminServiceImpl();
    ResponseResult responseResult = new ResponseResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/admin/", "");
        if ("login".equals(action)) {
            login(request, response);
        } else if ("addAdminss".equals(action)) {
            addAdminss(request, response);
        }
    }

    /**
     * 添加管理员
     * 问题：添加成功后不知道怎么返回一个信息给alert()
     *
     * @param request
     * @param response
     */
    private void addAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //传给service层
        int result = adminService.addAdmin(admin);
        if (result == 0) {
            response.getWriter().println("添加管理员成功");
            return;
        }
        response.getWriter().println("添加失败，请输入正确的管理员信息");
    }


    /**
     * 登录的具体逻辑
     * 1.获取请求体中的json数据并解析成admin对象
     * 2.将admin对象传到数据库查询并返回查询结果
     * 3.200表示查询成功；将admin的信息封装成map，并放入responseResult对象转换成json
     * 4.404表示用户名或密码错误
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求体
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //传给service层
        int result = adminService.login(admin);

        if (result == 200) {
            responseResult.setCode(0);
            Map<String, String> map = new HashMap<>();
            map.put("token", admin.getEmail());
            map.put("name", admin.getNickname());
            responseResult.setData("map: " + map);
        } else if (result == 404) {
            responseResult.setCode(10000);
            response.getWriter().println("用户名或密码错误");
        } else {
            responseResult.setData(10000);
            response.getWriter().println("访问异常，请稍后再试");
        }
        //System.out.println(gson.toJson(responseResult));
        response.getWriter().println(gson.toJson(responseResult));


    }

    /**
     * 查询、删除、修改
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        String action = requestURI.replace("/api/admin/admin/", "");
        System.out.println(action);

        if ("allAdmins".equals(action)) {
            allAdmins(request, response);
        } else if ("deleteAdmins".equals(action)) {
            deleteAdmins(request, response);
        } else if ("updateAdminss".equals(action)) {
            updateAdminss(request, response);
        } else if("getAdminsInfo".equals(action)){
            getAdminsInfo(request,response);
        }
    }

    /**
     * 获取管理员信息
     * @param request
     * @param response
     */
    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        Integer id = Integer.valueOf(request.getQueryString().substring(3));
        admin = adminService.getAdminsInfo(admin,id);

        if (admin == null) {
            responseResult.setData(10000);
            response.getWriter().println("该管理员不存在");
        } else {
            responseResult.setCode(0);
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(admin.getId()));
            map.put("email", admin.getEmail());
            map.put("nickname", admin.getNickname());
            map.put("pwd", admin.getPwd());
            //System.out.println(map);
            responseResult.setData(map);
        }
        //System.out.println(gson.toJson(responseResult));
        response.getWriter().println(gson.toJson(responseResult));
    }

    /**
     * 修改管理员信息
     *
     * @param request
     * @param response
     */
    private void updateAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("update1");
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        Integer id = Integer.valueOf(request.getQueryString().substring(3));
        adminService.updateAdminss(admin, id);
        System.out.println(admin);
    }

    /**
     * 删除管理员
     * //问题：1.删除之后不知道怎么返回一个信息给alert()；2.删除之后没有刷新界面
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        adminService.deleteAdmins(request, response);
        response.setHeader("refresh", "0");
        //问题：1.删除之后console.log()没有设置；2.删除之后没有刷新界面
    }

    /**
     * 查询所有管理员信息
     *
     * @param request
     * @param response
     */
    private void allAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Admin> admins = adminService.queryAllAdmins();
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(0);
        responseResult.setData(admins);
        response.getWriter().println(gson.toJson(responseResult));
    }
}

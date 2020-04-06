package com.zhenyiwang.mall.controller.admin;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.admin.Admin;
import com.zhenyiwang.mall.bean.admin.ChangePwdInfo;
import com.zhenyiwang.mall.bean.admin.ResponseResult;
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

    /**
     * 登录、添加、修改、查询、更改密码
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/admin/", "");
        if ("login".equals(action)) {
            login(request, response);
        } else if ("addAdminss".equals(action)) {
            addAdminss(request, response);
        } else if ("updateAdminss".equals(action)) {
            updateAdminss(request, response);
        } else if ("getSearchAdmins".equals(action)) {
            getSearchAdmins(request, response);
        } else if ("changePwd".equals(action)) {
            changePwd(request, response);
        } else if ("logoutAdmin".equals(action)) {
            logoutAdmin(request, response);
        }
    }

    /**
     * 注销登录，同时结束本次会话
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void logoutAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("authEmail", null);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));

    }

    /**
     * 更改用户密码
     * 0:修改成功
     * 1:旧密码错误
     * 2:请保证确认密码与新密码一致
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult responseResult = new ResponseResult();
        ChangePwdInfo changePwdInfo = gson.fromJson(requestBody, ChangePwdInfo.class);
        int result = adminService.changePwd(changePwdInfo);
        if (result == 0) {
            responseResult.setCode(0);
            responseResult.setMessage("修改成功");
            response.getWriter().println(gson.toJson(responseResult));
            return;
        }
        if (result == 1) {
            responseResult.setMessage("旧密码错误");
            return;
        }
        if (result == 2) {
            responseResult.setMessage("请保证确认密码与新密码一致");
        }
    }

    /**
     * 查询管理员信息
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //System.out.println(admin);
        List<Admin> admins = adminService.getSearchAdmins(admin);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(admins);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 添加管理员
     *
     * @param request
     * @param response
     */
    private void addAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult responseResult = new ResponseResult();
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //传给service层
        int result = adminService.addAdmin(admin);
        if (result == 0) {
            responseResult.setMessage("添加管理员成功");
            return;
        }
        responseResult.setMessage("添加失败，请输入正确的管理员信息");
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
        ResponseResult responseResult = new ResponseResult();
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //传给service层
        admin = adminService.login(admin);
        try {
            if (admin == null) {
                responseResult.setCode(10000);
                responseResult.setMessage("用户名或密码错误");
            } else {
                request.getSession().setAttribute("authEmail", admin.getEmail());
                responseResult.setCode(0);
                Map<String, String> map = new HashMap<>();
                map.put("token", admin.getNickname());
                map.put("name", admin.getNickname());
                responseResult.setData(map);
            }
        } catch (Exception e){
            responseResult.setCode(500);
            responseResult.setMessage("服务器繁忙请稍后再试");
        } finally {
            //System.out.println(gson.toJson(responseResult));
            response.getWriter().println(gson.toJson(responseResult));
        }
    }

    /**
     * 修改管理员信息
     *
     * @param request
     * @param response
     */
    private void updateAdminss(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        //获取请求体
        String requestBody = HttpUtils.requestBody(request);
        Admin admin = gson.fromJson(requestBody, Admin.class);
        //传给service层
        adminService.updateAdminss(admin);
        result.setCode(0);
        result.setMessage("修改成功");
        response.getWriter().println(gson.toJson(result));

    }

    /**
     * 删除、获取
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        String action = requestURI.replace("/api/admin/admin/", "");

        if ("allAdmins".equals(action)) {
            allAdmins(request, response);
        } else if ("deleteAdmins".equals(action)) {
            deleteAdmins(request, response);
        } else if ("getAdminsInfo".equals(action)) {
            getAdminsInfo(request, response);
        }
    }


    /**
     * 获取管理员信息
     *
     * @param request
     * @param response
     */
    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult responseResult = new ResponseResult();
        Integer id = Integer.valueOf(request.getQueryString().substring(3));
        Admin admin = adminService.getAdminsInfo(id);

        responseResult.setCode(0);
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(admin.getId()));
        map.put("email", admin.getEmail());
        map.put("nickname", admin.getNickname());
        map.put("pwd", admin.getPwd());
        responseResult.setData(map);
        response.getWriter().println(gson.toJson(responseResult));
    }


    /**
     * 删除管理员
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult responseResult = new ResponseResult();
        Integer id = Integer.valueOf(request.getQueryString().substring(3));
        adminService.deleteAdmins(id);
        responseResult.setCode(0);
        responseResult.setMessage("删除成功");
        response.getWriter().println(gson.toJson(responseResult));
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

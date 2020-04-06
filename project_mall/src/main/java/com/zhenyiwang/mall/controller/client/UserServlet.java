package com.zhenyiwang.mall.controller.client;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.admin.ResponseResult;
import com.zhenyiwang.mall.bean.admin.User;
import com.zhenyiwang.mall.service.UserService;
import com.zhenyiwang.mall.service.impl.UserServiceImpl;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/user/user/*")
public class UserServlet extends HttpServlet {

    Gson gson = new Gson();
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/user/user/", "");
        if ("login".equals(action)) {
            login(request, response);
        } else if ("signup".equals(action)) {
            signup(request, response);
//    } else if ("updateUserData".equals(action)) {
//        updateUserData(request, response);
//    } else if ("updatePwd".equals(action)) {
//        updatePwd(request, response);
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult result = new ResponseResult();
        User user = gson.fromJson(requestBody, User.class);
        Integer tag = userService.signup(user);
        try {
            if (tag == 0) {
                result.setCode(0);
                Map<String, Object> map = new HashMap<>();
                map.put("code", 0);
                map.put("name", user.getNickname());
                map.put("token", user.getNickname());
                result.setData(map);
            } else {
                result.setCode(10000);
                result.setMessage("注册异常，请稍后再试");
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage("服务器异常");
        } finally {
            response.getWriter().println(gson.toJson(result));
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult result = new ResponseResult();
        User user = gson.fromJson(requestBody, User.class);
        user = userService.login(user);
        try {
            if (user == null) {
                result.setCode(10000);
                result.setMessage("用户名或密码错误");
            } else {
                request.getSession().setAttribute("authEmail", user.getEmail());
                result.setCode(0);
                Map<String, Object> map = new HashMap<>();
                map.put("code", 0);
                map.put("name", user.getNickname());
                map.put("token", user.getNickname());
                result.setData(map);
            }
        } catch (Exception e) {
            result.setCode(500);
            result.setMessage("服务器繁忙请稍后再试");
        } finally {
            //System.out.println(gson.toJson(responseResult));
            response.getWriter().println(gson.toJson(result));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

package com.zhenyiwang.mall.controller;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.Msg;
import com.zhenyiwang.mall.bean.ResponseResult;
import com.zhenyiwang.mall.service.MsgService;
import com.zhenyiwang.mall.service.impl.MsgServiceImpl;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/admin/msg/*")
public class MsgServlet extends HttpServlet {

    Gson gson = new Gson();
    MsgService msgService = new MsgServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/msg/", "");
        if("reply".equals(action)){
            reply(request,response);
        }
    }

    private void reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        Msg msg = gson.fromJson(requestBody, Msg.class);
        //System.out.println(msg);
        msgService.reply(msg);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setMessage("回复成功");
        response.getWriter().println(gson.toJson(result));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/msg/", "");
        if ("noReplyMsg".equals(action)) {
            try {
                getMsg(request, response, 1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if ("repliedMsg".equals(action)) {
            try {
                getMsg(request, response, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void getMsg(HttpServletRequest request, HttpServletResponse response, int tag) throws IOException, SQLException {
        ResponseResult result = new ResponseResult();
        List<Msg> msgs = msgService.getMsg(tag);
        //System.out.println(msgs);
        if (msgs == null) {
            result.setCode(404);
            result.setMessage("暂时未有留言");
            response.getWriter().println(gson.toJson(result));
            return;
        }
        result.setCode(0);
        result.setData(msgs);
        response.getWriter().println(gson.toJson(result).replace("nickname", "name"));
    }
}

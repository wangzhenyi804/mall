package com.zhenyiwang.mall.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.*;
import com.zhenyiwang.mall.service.OrderService;
import com.zhenyiwang.mall.service.impl.OrderServiceImpl;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/admin/order/*")
public class OrderServlet extends HttpServlet {

    OrderService orderService = new OrderServiceImpl();
    Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/order/", "");
        if ("ordersByPage".equals(action)) {
            ordersByPage(request, response);
        } else if ("changeOrder".equals(action)) {
            changeOrder(request, response);
        }
    }

    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String requestBody = HttpUtils.requestBody(request);
        ChangeOrderInfo orderInfo = gson.fromJson(requestBody, ChangeOrderInfo.class);
        System.out.println(orderInfo);

        try {
            orderService.changeOrder(orderInfo);
            result.setCode(0);
            result.setMessage("修改成功");
        } catch (Exception e) {
            result.setCode(10000);
            result.setMessage("当前服务器繁忙");
        } finally {
            response.getWriter().println(gson.toJson(result));
        }

    }

    /**
     * 分页显示订单
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void ordersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String requestBody = HttpUtils.requestBody(request);
        OrderConditions orderConditions = gson.fromJson(requestBody, OrderConditions.class);

        try {
            if (!StringUtils.isEmpty(orderConditions.getMoneyLimit1())) {
                Double.parseDouble(orderConditions.getMoneyLimit1());
            }
            if (!StringUtils.isEmpty(orderConditions.getMoneyLimit2())) {
                Double.parseDouble(orderConditions.getMoneyLimit2());
            }
        } catch (Exception e) {
            result.setCode(10000);
            result.setMessage("价格区间输入错误");
            response.getWriter().println(gson.toJson(result));
        }

        try {
            Map<String, Object> map = orderService.ordersByPage(orderConditions);
            result.setCode(0);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(10000);
            result.setMessage("服务器开小差了，请稍后再试");
        } finally {
            response.getWriter().println(gson.toJson(result));
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/order/", "");
        if ("order".equals(action)) {
            getOrder(request, response);
        } else if ("deleteOrder".equals(action)) {
            deleteOrder(request, response);
        }
    }

    /**
     * 删除订单
     *
     * @param request
     * @param response
     */
    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        orderService.deleteOrder(id);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    private void getOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String id = request.getParameter("id");

        if (!StringUtils.isEmpty(id)) {
            int orderId = Integer.parseInt(id);
            EditOrderInfo order = orderService.getOrder(orderId);
            result.setCode(0);
            result.setData(order);
            response.getWriter().println(gson.toJson(result).replace("goodsNum", "num").replace("stateId", "state").replace("specs", "spec"));
        } else {
            result.setCode(10000);
            result.setMessage("订单号异常");
            response.getWriter().println(gson.toJson(result).replace("goodsNum", "num").replace("stateId", "state").replace("specs", "spec"));
        }
    }
}

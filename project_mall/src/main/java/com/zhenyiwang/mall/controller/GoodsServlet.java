package com.zhenyiwang.mall.controller;

import com.google.gson.*;
import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.ResponseResult;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.service.GoodsService;
import com.zhenyiwang.mall.service.impl.GoodsServiceImpl;
import com.zhenyiwang.mall.utils.FileUploadUtils;
import com.zhenyiwang.mall.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {

    GoodsService goodsService = new GoodsServiceImpl();
    Gson gson = new Gson();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if("imgUpload".equals(action)){
            imgUpload(request, response);
        }else if("addGoods".equals(action)){
            addGoods(request, response);
        }
    }

    /**
     * 添加商品
     * @param request
     * @param response
     */
    private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String requestBody = HttpUtils.requestBody(request);
        Goods goods = gson.fromJson(requestBody, Goods.class);
        System.out.println(goods);

        /*JsonElement jsonElement = new JsonParser().parse(requestBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray specList = jsonObject.getAsJsonArray("specList");
        for (JsonElement element : specList) {
            Spec spec = gson.fromJson(element, Spec.class);
            System.out.println(spec);
        }*/


        goodsService.addGoods(goods);
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));


    }

    /**
     * 上传商品图片，并返回一个图片的链接(域名+端口号的形式)
     * @param request
     * @param response
     */
    private void imgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String domain = (String) request.getServletContext().getAttribute("domain");
        Map<String,Object> map = FileUploadUtils.parseRequest(request);
        String file = (String) map.get("file");
        file = domain + file;
        result.setCode(0);
        result.setData(file);
        response.getWriter().println(gson.toJson(result));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if("getGoodsByType".equals(action)){
            getGoodsByType(request,response);
        }
    }

    /**
     * 获取某个商品种类的所有商品
     * @param request
     * @param response
     */
    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeId = request.getParameter("typeId");
        List<Goods> goodsList = goodsService.queryGoodsByType(typeId);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(goodsList);
        response.getWriter().println(gson.toJson(result));

    }
}

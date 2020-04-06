package com.zhenyiwang.mall.controller.admin;

import com.google.gson.*;
import com.zhenyiwang.mall.bean.admin.*;
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
import java.util.List;
import java.util.Map;

@WebServlet("/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {

    GoodsService goodsService = new GoodsServiceImpl();
    Gson gson = new Gson();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if ("imgUpload".equals(action)) {
            imgUpload(request, response);
        } else if ("addGoods".equals(action)) {
            addGoods(request, response);
        } else if ("addSpec".equals(action)) {
            addSpec(request, response);
        } else if ("deleteSpec".equals(action)) {
            deleteSpec(request, response);
        } else if("updateGoods".equals(action)){
            updateGoods(request,response);
        }
    }

    private void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String requestBody = HttpUtils.requestBody(request);
        Goods goods = gson.fromJson(requestBody, Goods.class);
        goodsService.updateGoods(goods);
        result.setCode(0);
        result.setMessage("修改成功");
        response.getWriter().println(gson.toJson(result));
    }

    private void deleteSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        SpecSign specSign = gson.fromJson(requestBody, SpecSign.class);
        goodsService.deleteSpec(specSign);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 添加规格并写回
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void addSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestBody = HttpUtils.requestBody(request);
        ResponseResult result = new ResponseResult();
        Spec spec = gson.fromJson(requestBody, Spec.class);
        if(spec == null){
            result.setMessage("请输入正确的规格信息");
            response.getWriter().println(gson.toJson(result));
            return;
        }
        goodsService.addSpec(spec);

        result.setCode(0);
        result.setData(spec);
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 添加商品
     *
     * @param request
     * @param response
     */
    private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String requestBody = HttpUtils.requestBody(request);
        Goods goods = gson.fromJson(requestBody, Goods.class);
        //System.out.println(goods);

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
     *
     * @param request
     * @param response
     */
    private void imgUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseResult result = new ResponseResult();
        String domain = (String) request.getServletContext().getAttribute("domain");
        Map<String, Object> map = FileUploadUtils.parseRequest(request);
        String file = (String) map.get("file");
        file = domain + file;
        //System.out.println(file);
        result.setCode(0);
        result.setData(file);
        response.getWriter().println(gson.toJson(result));

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/admin/goods/", "");
        if ("getGoodsByType".equals(action)) {
            getGoodsByType(request, response);
        } else if ("deleteGoods".equals(action)) {
            deleteGoods(request, response);
        } else if ("getGoodsInfo".equals(action)) {
            queryGoodsInfo(request, response);
        }
    }

    private void queryGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        GoodsInfo goodsInfo = goodsService.queryGoodsInfo(id);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(goodsInfo);
        result.setMessage("修改成功");
        response.getWriter().println(gson.toJson(result));
    }

    private void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        goodsService.deleteGoods(id);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setMessage("删除成功");
        response.getWriter().println(gson.toJson(result));
    }

    /**
     * 获取某个商品种类的所有商品
     *
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

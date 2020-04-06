package com.zhenyiwang.mall.controller.client;

import com.google.gson.Gson;
import com.zhenyiwang.mall.bean.admin.*;
import com.zhenyiwang.mall.bean.client.ClientGoodsInfo;
import com.zhenyiwang.mall.bean.client.ClientMsg;
import com.zhenyiwang.mall.service.GoodsService;
import com.zhenyiwang.mall.service.MsgService;
import com.zhenyiwang.mall.service.TypeService;
import com.zhenyiwang.mall.service.UserService;
import com.zhenyiwang.mall.service.impl.GoodsServiceImpl;
import com.zhenyiwang.mall.service.impl.MsgServiceImpl;
import com.zhenyiwang.mall.service.impl.TypeServiceImpl;
import com.zhenyiwang.mall.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet("/api/user/mall/*")
public class MallServlet extends HttpServlet {
    Gson gson = new Gson();
    TypeService typeService = new TypeServiceImpl();
    GoodsService goodsService = new GoodsServiceImpl();
    MsgService msgService = new MsgServiceImpl();
    UserService userService = new UserServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String action = requestURI.replace("/api/user/mall/", "");
        if ("getType".equals(action)) {
            getType(request, response);
        } else if ("getGoodsByType".equals(action)) {
            getGoodsByType(request, response);
        } else if ("searchGoods".equals(action)) {
            searchGoods(request, response);
        } else if ("getGoodsInfo".equals(action)) {
            getGoodsInfo(request, response);
        } else if ("getGoodsMsg".equals(action)) {
            getGoodsMsg(request, response);
        } else if ("getGoodsComment".equals(action)) {
            getGoodsComment(request, response);
        }
    }

    /**
     * 获取商品评论
     *
     * @param request
     * @param response
     */
    private void getGoodsComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String goodsId = request.getParameter("goodsId");
        List<Comment> comments = goodsService.getGoodsComment(goodsId);
        User user = null;

        //for(Iterator<Order> iterator = orders.iterator();iterator.hasNext();){
        //            Order order = iterator.next();
        //            Integer userId = order.getUserId();
        //            User user = userDao.getUserInfoById(userId);
        //            order.setUser(user);
        //        }
        for (Iterator<Comment> iterator = comments.iterator();iterator.hasNext();) {
            Comment comment = iterator.next();
            Integer userId = comment.getUserId();
            user = userService.queryUserById(userId);
            comment.setUser(user);
        }
        double rate = goodsService.getCommentRate(goodsId);
        Map<String, Object> map = new HashMap<>();
        map.put("commentList", comments);
        map.put("rate", rate);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(map);
        response.getWriter().println(gson.toJson(result));

    }

    /**
     * 获取商品留言(提问)
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        List<Msg> msgs = msgService.getMsgByGoodsId(id);
        List<ClientMsg> clientMsgs = null;

        for (int i = 0; i < msgs.size(); i++) {
            //适配前台的数据结构
            clientMsgs.get(i).setId(msgs.get(i).getId());
            clientMsgs.get(i).setContent(msgs.get(i).getContent());
            clientMsgs.get(i).setAsker(msgs.get(i).getUser().getNickname());
            clientMsgs.get(i).setTime(msgs.get(i).getCreatetime());
            clientMsgs.get(i).getReply().setContent(msgs.get(i).getReplyContent());
            clientMsgs.get(i).getReply().setTime(msgs.get(i).getReplyTime());
        }
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(clientMsgs);
        response.getWriter().println(gson.toJson(result));
    }

    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        GoodsInfo goodsInfo = goodsService.queryGoodsInfo(id);

        //适配前台的数据类型
        ClientGoodsInfo clientGoodsInfo_ = new ClientGoodsInfo();
        clientGoodsInfo_.setImg(goodsInfo.getGoods().getImg());
        clientGoodsInfo_.setName(goodsInfo.getGoods().getName());
        clientGoodsInfo_.setDesc(goodsInfo.getGoods().getDesc());
        clientGoodsInfo_.setTypeId(goodsInfo.getGoods().getTypeId());
        clientGoodsInfo_.setSpecs(goodsInfo.getSpecs());
        clientGoodsInfo_.setunitPrice(goodsInfo.getGoods().getPrice());

        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(clientGoodsInfo_);
        response.getWriter().println(gson.toJson(result));
    }

    private void searchGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        List<Goods> goodsList = goodsService.searchGoods(keyword);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(goodsList);
        response.getWriter().println(gson.toJson(result));
    }

    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeId = request.getParameter("typeId");
        List<Goods> goodsList = goodsService.queryGoodsByType(typeId);
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(goodsList);
        response.getWriter().println(gson.toJson(result));
    }

    private void getType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Type> types = typeService.getType();
        ResponseResult result = new ResponseResult();
        result.setCode(0);
        result.setData(types);
        response.getWriter().println(gson.toJson(result));
    }
}

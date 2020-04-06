package com.zhenyiwang.mall.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhenyiwang.mall.bean.admin.*;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.dao.OrderDao;
import com.zhenyiwang.mall.dao.UserDao;
import com.zhenyiwang.mall.dao.impl.GoodsDaoImpl;
import com.zhenyiwang.mall.dao.impl.OrderDaoImpl;
import com.zhenyiwang.mall.dao.impl.UserDaoImpl;
import com.zhenyiwang.mall.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    UserDao userDao = new UserDaoImpl();
    GoodsDao goodsDao = new GoodsDaoImpl();

    /**
     * 查找total和orders
     *
     * @param orderConditions
     * @return
     */
    @Override
    public Map<String, Object> ordersByPage(OrderConditions orderConditions) {
        //准备模糊查询语句sql和查询条件
        String suffix = "";
        List<Object> conditions = new ArrayList<>();
        if (orderConditions.getState() != -1) {
            suffix = suffix + "and stateId=?";
            conditions.add(orderConditions.getState());
        }
        if (!StringUtils.isEmpty(orderConditions.getMoneyLimit1())) {
            suffix = suffix + "and amount <= ?";
            conditions.add(orderConditions.getMoneyLimit1());
        }
        if (!StringUtils.isEmpty(orderConditions.getMoneyLimit2())) {
            suffix = suffix + "and amount >= ?";
            conditions.add(orderConditions.getMoneyLimit2());
        }
        if (!StringUtils.isEmpty(orderConditions.getGoods())) {
            suffix = suffix + "and goods like ?";
            conditions.add("%" + orderConditions.getGoods() + "%");
        }
        if (!StringUtils.isEmpty(orderConditions.getAddress())) {
            suffix = suffix + "and address like ?";
            conditions.add("%" + orderConditions.getAddress() + "%");
        }
        if (!StringUtils.isEmpty(orderConditions.getName())) {
            suffix += " and name like ?";
            conditions.add("%" + orderConditions.getName() + "%");
        }
        if (!StringUtils.isEmpty(orderConditions.getId())) {
            suffix += " and id = ?";
            conditions.add(orderConditions.getId());
        }

        String totalSql = "select count(id) from mall_order where 1=1 ";
        //查询total
        Integer total = orderDao.getTotal(totalSql + suffix, conditions);

        //开始处理分页
        String pageSql = " limit ? offset ?";
        conditions.add(orderConditions.getPagesize());
        conditions.add((orderConditions.getCurrentPage() - 1) * orderConditions.getPagesize());

        //查询orders
        String ordersSql = "select * from mall_order where 1=1 ";
        Map<String, Object> map = new HashMap<>();
        List<Order> orders = orderDao.ordersByPage(ordersSql + suffix + pageSql, conditions);

        //将对应的user对象放入orders中
        for (int i = 0; i < orders.size(); i++) {
            User user = userDao.queryUserById(orders.get(i).getUserId());
            user.setName(orders.get(i).getName());
            orders.get(i).setUser(user);
        }
        map.put("total", total);
        map.put("orders", orders);
        return map;
    }

    /**
     * 根据订单号获取特定的订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public EditOrderInfo getOrder(int orderId) {
        EditOrderInfo editOrderInfo = orderDao.getEditOrderInfo(orderId);
        //通过商品id查询所有规格specs
        Spec spec = goodsDao.querySpecById(editOrderInfo.getGoodsDetailId());
        System.out.println("spec: " + spec);
        List<Spec> specs = goodsDao.queryAllSpecs(spec);
        List<State> states = orderDao.getAllStates();

        Spec curSpec = new Spec();
        curSpec.setId(editOrderInfo.getGoodsDetailId());
        editOrderInfo.setCurSpec(curSpec);

        State curState = new State();
        curState.setId(editOrderInfo.getStateId());
        editOrderInfo.setCurState(curState);

        editOrderInfo.setSpecs(specs);
        editOrderInfo.setStates(states);
        return editOrderInfo;
    }

    /**
     * 更新订单数据
     *
     * @param orderInfo
     */
    @Override
    public void changeOrder(ChangeOrderInfo orderInfo) {
        orderDao.changeOrder(orderInfo);
    }

    @Override
    public void deleteOrder(String id) {
        orderDao.deleteOrder(id);
    }
}

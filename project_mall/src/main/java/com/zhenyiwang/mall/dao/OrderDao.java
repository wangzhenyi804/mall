package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.ChangeOrderInfo;
import com.zhenyiwang.mall.bean.EditOrderInfo;
import com.zhenyiwang.mall.bean.Order;
import com.zhenyiwang.mall.bean.State;

import java.util.List;

public interface OrderDao {
    List<Order> ordersByPage(String sql,List<Object> conditions);

    int getTotal(String sql,List<Object> conditions);

    EditOrderInfo getEditOrderInfo(int orderId);

    List<State> getAllStates();

    void changeOrder(ChangeOrderInfo orderInfo);

    void deleteOrder(String id);
}

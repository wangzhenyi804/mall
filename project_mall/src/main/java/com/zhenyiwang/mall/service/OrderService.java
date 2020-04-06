package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.ChangeOrderInfo;
import com.zhenyiwang.mall.bean.admin.EditOrderInfo;
import com.zhenyiwang.mall.bean.admin.OrderConditions;

import java.util.Map;

public interface OrderService {
    Map<String, Object> ordersByPage(OrderConditions orderConditions);

    EditOrderInfo getOrder(int orderId);

    void changeOrder(ChangeOrderInfo orderInfo);

    void deleteOrder(String id);
}

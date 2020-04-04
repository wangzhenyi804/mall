package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.ChangeOrderInfo;
import com.zhenyiwang.mall.bean.EditOrderInfo;
import com.zhenyiwang.mall.bean.Order;
import com.zhenyiwang.mall.bean.State;
import com.zhenyiwang.mall.dao.OrderDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {


    @Override
    public List<Order> ordersByPage(String sql, List<Object> conditions) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Order> orders = null;
        try {
            orders = runner.query(sql, new BeanListHandler<>(Order.class), conditions.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public int getTotal(String sql, List<Object> conditions) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Long total = null;
        try {
            total = (Long) runner.query(sql, new ScalarHandler<>(), conditions.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total.intValue();
    }

    @Override
    public EditOrderInfo getEditOrderInfo(int orderId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        EditOrderInfo editOrderInfo = null;
        try {
            editOrderInfo = runner.query("select id,amount,goodsNum,goodsDetailId,stateId,goods from mall_order where id=?", new BeanHandler<>(EditOrderInfo.class), orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(editOrderInfo);
        return editOrderInfo;
    }

    @Override
    public List<State> getAllStates() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<State> states = null;
        try {
            states = runner.query("select * from mall_state", new BeanListHandler<>(State.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return states;
    }

    /**
     * 更新订单数据
     * {"id":"1848","state":1,"spec":1272,"num":2}
     *
     * @param orderInfo
     */
    @Override
    public void changeOrder(ChangeOrderInfo orderInfo) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("update mall_order set stateId=? ,goodsDetailId=? ,goodsNum=? where id=?",
                    orderInfo.getState(),
                    orderInfo.getSpec(),
                    orderInfo.getNum(),
                    orderInfo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除订单
     *
     * @param id
     */
    @Override
    public void deleteOrder(String id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from mall_order where id = ? ",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

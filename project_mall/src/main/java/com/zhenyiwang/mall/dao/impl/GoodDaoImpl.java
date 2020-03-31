package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class GoodDaoImpl implements GoodsDao {
    @Override
    public List<Goods> queryGoodsByType(String typeId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Goods> goodsList = null;
        try {
            goodsList = runner.query("select * from mall_goods where typeId = ?",
                    new BeanListHandler<>(Goods.class),
                    typeId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goodsList;
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    @Override
    public int addGoods(Goods goods) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("insert into goods values (null,?,?,?,?,?,?)",
                    goods.getName(),
                    goods.getTypeId(),
                    goods.getImg(),
                    goods.getDesc(),
                    goods.getPrice(),
                    goods.getStockNum());
        } catch (SQLException e) {
            e.printStackTrace();
            return 500;
        }
        return 200;
    }

    /**
     * 获取商品id(用来关联规格表)
     *
     * @return
     */
    @Override
    public int queryLastId() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        BigInteger query = null;
        try {
            query = (BigInteger) runner.query("select last_insert_id()", new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    /**
     * 添加商品规格
     *
     * @param specList
     * @param goodsId
     */
    @Override
    public void addSpeces(List<Spec> specList, int goodsId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        for (Spec spec : specList) {
            try {
                runner.update("insert into spec values (null,?,?,?,?)",
                        spec.getSpecName(),
                        spec.getStockNum(),
                        spec.getUnitPrice(),
                        goodsId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

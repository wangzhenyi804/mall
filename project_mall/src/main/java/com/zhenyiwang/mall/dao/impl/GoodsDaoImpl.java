package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.bean.SpecSign;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {
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
            runner.update("insert into mall_goods values (null,?,?,?,?,?,null)",
                    goods.getName(),
                    goods.getImg(),
                    goods.getDesc(),
                    goods.getPrice(),
                    goods.getTypeId());
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

    @Override
    public void deleteGoods(String id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from mall_goods where id=?", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Spec> queryGoodsSpec(String id) {
        List<Spec> specs = null;
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            specs = runner.query("select * from spec where goods_id=?", new BeanListHandler<>(Spec.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specs;
    }

    @Override
    public Goods queryGoods(String id) {
        Goods goods = null;
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            goods = runner.query("select * from mall_goods where id=?", new BeanHandler<>(Goods.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(goods);
        return goods;
    }

    @Override
    public void deleteSpec(SpecSign specSign) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from spec where goods_Id=? and specName=?",
                    specSign.getGoodsId(),
                    specSign.getSpecName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Spec querySpec(SpecSign specSign) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Spec spec = null;
        try {
            spec = runner.query("select * from spec where goods_Id=? and specName=?",
                    new BeanHandler<>(Spec.class),
                    specSign.getGoodsId(),
                    specSign.getSpecName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spec;
    }

    /**
     * 添加规格
     *
     * @param spec
     */
    @Override
    public void addSpec(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("insert into spec values(null,?,?,?,?)",
                    spec.getSpecName(),
                    spec.getStockNum(),
                    spec.getUnitPrice(),
                    spec.getGoodsId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGoods(Goods goods) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("update mall_goods set name=?,img=?,`desc`=?,price=?,typeId=?,stockNum=? where id=?",
                    goods.getName(),
                    goods.getImg(),
                    goods.getDesc(),
                    goods.getPrice(),
                    goods.getTypeId(),
                    goods.getStockNum(),
                    goods.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Goods queryGoodsName(Integer goodsId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Goods goods = null;
        try {
            goods = runner.query("select name from mall_goods where id=?", new BeanHandler<>(Goods.class), goodsId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goods;
    }

    /**
     * 查询商品所有规格
     *
     * @param spec
     * @return 返回一个specList
     */
    @Override
    public List<Spec> queryAllSpecs(Spec spec) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Spec> specs = null;
        try {
            specs = runner.query("select id,specName,unitPrice from spec where goodsId = ? ",
                    new BeanListHandler<>(Spec.class),
                    spec.getGoodsId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specs;
    }

    /**
     * 根据规格id查询商品规格具体信息
     *
     * @param goodsDetailId
     * @return 返回一个spec对象
     */
    @Override
    public Spec querySpecById(Integer goodsDetailId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Spec spec = null;
        try {
            spec = runner.query("select * from spec where id=?",
                    new BeanHandler<>(Spec.class),
                    goodsDetailId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spec;
    }

}

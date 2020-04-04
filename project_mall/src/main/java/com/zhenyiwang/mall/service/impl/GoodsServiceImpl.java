package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.GoodsInfo;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.bean.SpecSign;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.dao.impl.GoodsDaoImpl;
import com.zhenyiwang.mall.service.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public List<Goods> queryGoodsByType(String typeId) {
        return goodsDao.queryGoodsByType(typeId);
    }

    /**
     * 添加商品
     *
     * @param goods
     */
    @Override
    public int addGoods(Goods goods) {
        List<Spec> specList = goods.getSpecList();
        double price = specList.get(0).getUnitPrice();
        //取商品规格中的最低价作为该商品价格
        for (int i = 1; i < specList.size(); i++) {
            if (specList.get(i).getUnitPrice() < price) {
                price = specList.get(i).getUnitPrice();
            }
        }
        //放入商品表中
        goods.setPrice(price);
        int result = goodsDao.addGoods(goods);
        if (result == 200) {
            //取商品id存入规格表中，使两者关联起来
            int goodsId = goodsDao.queryLastId();
            //将商品id存入spec表中
            goodsDao.addSpeces(goods.getSpecList(), goodsId);
            return 200;
        }

        return 500;
    }

    @Override
    public void deleteGoods(String id) {
        goodsDao.deleteGoods(id);
    }

    @Override
    public GoodsInfo queryGoodsInfo(String id) {
        GoodsInfo goodsInfo = new GoodsInfo();
        List<Spec> specs = goodsDao.queryGoodsSpec(id);
        Goods goods = goodsDao.queryGoods(id);
        goodsInfo.setSpecs(specs);
        goodsInfo.setGoods(goods);
        return goodsInfo;
    }

    /**
     * 删除规格之前，先查询是否存在
     *
     * @param specSign
     */
    @Override
    public void deleteSpec(SpecSign specSign) {
        Spec spec = goodsDao.querySpec(specSign);
        if (spec == null) return;
        goodsDao.deleteSpec(specSign);
    }

    @Override
    public void addSpec(Spec spec) {
        goodsDao.addSpec(spec);
    }

    @Override
    public void updateGoods(Goods goods) {
        List<Spec> specList = goods.getSpecList();
        double price = specList.get(0).getUnitPrice();

        //取商品规格中的最低价作为该商品价格
        for (int i = 1; i < specList.size(); i++) {
            if (specList.get(i).getUnitPrice() < price) {
                price = specList.get(i).getUnitPrice();
            }
        }

        //放入商品表中
        goods.setPrice(price);
        goodsDao.updateGoods(goods);

    }

}

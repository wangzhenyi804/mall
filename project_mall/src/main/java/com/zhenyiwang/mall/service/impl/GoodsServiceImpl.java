package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.dao.impl.GoodDaoImpl;
import com.zhenyiwang.mall.service.GoodsService;

import java.util.List;

public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao = new GoodDaoImpl();

    @Override
    public List<Goods> queryGoodsByType(String typeId) {
        return goodsDao.queryGoodsByType(typeId);
    }

    /**
     * 添加商品
     * @param goods
     */
    @Override
    public int addGoods(Goods goods) {
        List<Spec> specList = goods.getSpecList();
        double price = specList.get(0).getUnitPrice();
        //取商品规格中的最低价作为该商品价格
        for (int i = 1; i < specList.size(); i++) {
            if(specList.get(i).getUnitPrice() < price){
                price = specList.get(i).getUnitPrice();
            }
        }
        //放入商品表中
        goods.setPrice(price);
        int result = goodsDao.addGoods(goods);
        if(result == 200){
            //取商品id存入规格表中，使两者关联起来
            int goodsId = goodsDao.queryLastId();
            //将商品id存入spec表中
            goodsDao.addSpeces(goods.getSpecList(), goodsId);
            return 200;
        }

        return 500;
    }
}

package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.admin.Goods;
import com.zhenyiwang.mall.bean.admin.GoodsInfo;
import com.zhenyiwang.mall.bean.admin.Spec;
import com.zhenyiwang.mall.bean.admin.SpecSign;
import com.zhenyiwang.mall.controller.client.Comment;
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

    /**
     * 查询商品详情
     *
     * @param id
     * @return
     */
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

    /**
     * 增加商品规格
     *
     * @param spec
     */
    @Override
    public void addSpec(Spec spec) {
        goodsDao.addSpec(spec);
    }

    /**
     * 编辑商品
     *
     * @param goods
     */
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

    /**
     * 根据关键字查询商品
     *
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> searchGoods(String keyword) {
        return goodsDao.searchGoods(keyword);
    }

    /**
     * 获取商品评价
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<Comment> getGoodsComment(String goodsId) {
        return goodsDao.getGoodsComment(goodsId);
    }

    /**
     * 计算好评率 rate = perfect/total
     *
     * @param goodsId
     * @return
     */
    @Override
    public double getCommentRate(String goodsId) {
        Integer total = goodsDao.queryTotalComment(Integer.parseInt(goodsId));
        Integer perfect = goodsDao.queryPerfectCommentNum(Integer.parseInt(goodsId));
        double rate = perfect / total;
        return rate;
    }

}

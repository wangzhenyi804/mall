package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.Goods;
import com.zhenyiwang.mall.bean.admin.GoodsInfo;
import com.zhenyiwang.mall.bean.admin.Spec;
import com.zhenyiwang.mall.bean.admin.SpecSign;
import com.zhenyiwang.mall.controller.client.Comment;

import java.util.List;

public interface GoodsService {
    List<Goods> queryGoodsByType(String typeId);

    int addGoods(Goods goods);

    void deleteGoods(String id);

    GoodsInfo queryGoodsInfo(String id);

    void deleteSpec(SpecSign specSign);

    void addSpec(Spec spec);

    void updateGoods(Goods goods);

    List<Goods> searchGoods(String keyword);

    List<Comment> getGoodsComment(String goodsId);


    double getCommentRate(String goodsId);
}

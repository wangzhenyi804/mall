package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.GoodsInfo;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.bean.SpecSign;

import java.util.List;

public interface GoodsService {
    List<Goods> queryGoodsByType(String typeId);

    int addGoods(Goods goods);

    void deleteGoods(String id);

    GoodsInfo queryGoodsInfo(String id);

    void deleteSpec(SpecSign specSign);

    void addSpec(Spec spec);

    void updateGoods(Goods goods);
}

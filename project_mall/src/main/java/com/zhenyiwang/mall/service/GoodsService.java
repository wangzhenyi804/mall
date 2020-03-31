package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> queryGoodsByType(String typeId);

    int addGoods(Goods goods);
}

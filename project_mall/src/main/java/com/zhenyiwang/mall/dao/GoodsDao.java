package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Spec;

import java.util.List;

public interface GoodsDao {
    List<Goods> queryGoodsByType(String typeId);

    int addGoods(Goods goods);

    int queryLastId();

    void addSpeces(List<Spec> specList, int goodsId);
}

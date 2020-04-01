package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Spec;
import com.zhenyiwang.mall.bean.SpecSign;

import java.util.List;

public interface GoodsDao {
    List<Goods> queryGoodsByType(String typeId);

    int addGoods(Goods goods);

    int queryLastId();

    void addSpeces(List<Spec> specList, int goodsId);

    void deleteGoods(String id);

    List<Spec> queryGoodsSpec(String id);

    Goods queryGoods(String id);

    void deleteSpec(SpecSign specSign);

    Spec querySpec(SpecSign specSign);

   void addSpec(Spec spec);

    void updateGoods(Goods goods);
}

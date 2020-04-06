package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.admin.Goods;
import com.zhenyiwang.mall.bean.admin.Spec;
import com.zhenyiwang.mall.bean.admin.SpecSign;
import com.zhenyiwang.mall.controller.client.Comment;

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
    Goods queryGoodsName(Integer goodsId);

    List<Spec> queryAllSpecs(Spec spec);

    Spec querySpecById(Integer goodsDetailId);

    List<Goods> searchGoods(String keyword);

    List<Comment> getGoodsComment(String goodsId);

    Integer queryTotalComment(Integer goodsId);

    Integer queryPerfectCommentNum(Integer goodsId);

}

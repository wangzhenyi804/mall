package com.zhenyiwang.mall.bean.admin;

import java.util.List;

public class GoodsInfo {
    private List<Spec> specs;
    private Goods goods;

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "ClientGoodsInfo{" +
                "specs=" + specs +
                ", goods=" + goods +
                '}';
    }
}

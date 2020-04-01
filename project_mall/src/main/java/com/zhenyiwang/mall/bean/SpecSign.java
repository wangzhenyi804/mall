package com.zhenyiwang.mall.bean;


//用来记录待被删除的规格名称和goods_id
public class SpecSign {
    private Integer goodsId;
    private String specName;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    @Override
    public String toString() {
        return "SpecSign{" +
                "goodsId=" + goodsId +
                ", specName='" + specName + '\'' +
                '}';
    }
}

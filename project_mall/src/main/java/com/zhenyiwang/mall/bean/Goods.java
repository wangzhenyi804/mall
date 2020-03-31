package com.zhenyiwang.mall.bean;

import java.util.List;

public class Goods {
    private Integer id;
    private String name;
    private Double typeId;
    private String img;
    private String describeGoods;
    private Double price;
    private Double stockNum;
    private List<Spec> specList;

    public String getDesc() {
        return describeGoods;
    }

    public void setDesc(String desc) {
        this.describeGoods = desc;
    }

    public List<Spec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<Spec> specList) {
        this.specList = specList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTypeId() {
        return typeId;
    }

    public void setTypeId(Double typeId) {
        this.typeId = typeId;
    }

    public Double getStockNum() {
        return stockNum;
    }

    public void setStockNum(Double stockNum) {
        this.stockNum = stockNum;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + describeGoods + '\'' +
                ", price=" + price +
                ", typeId=" + typeId +
                ", stockNum=" + stockNum +
                '}';
    }
}

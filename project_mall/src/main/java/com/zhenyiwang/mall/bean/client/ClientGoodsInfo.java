package com.zhenyiwang.mall.bean.client;

import com.zhenyiwang.mall.bean.admin.Spec;

import java.util.List;

//"img": "http://115.29.141.32:8084/static/image/1570850192433156699448698020190828201124.jpg",
//		"name": "半身裙",
//		"desc": "新品上市",
//		"typeId": 190,
//		"specs
//"unitPrice": 0.0
public class ClientGoodsInfo {
    private String img;
    private String name;
    private String desc;
    private Integer typeId;
    private List<Spec> specs;
    private Double unitPrice;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public Double getunitPrice() {
        return unitPrice;
    }

    public void setunitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

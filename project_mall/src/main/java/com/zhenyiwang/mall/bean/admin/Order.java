package com.zhenyiwang.mall.bean.admin;

import java.util.List;

public class Order {
    //订单号
    private Integer id;

    //用户id
    private Integer userId;

    //商品规格id
    private Integer goodsDetailId;

    private String goods;

    private String spec;

    private Integer goodsNum;

    private Double amount;

    private Integer stateId;

    private String state;

    private String name;

    private String address;

    private User user;

    private List<Spec> specs;

    private List<State> states;


    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStateId() {
        return stateId;
    }

    /**
     * dbutils在赋值时通过反射--- setFiled
     *
     * @param stateId
     */
    public void setStateId(Integer stateId) {
        this.stateId = stateId;
        //dbutils给stateId赋值，调用该方法
        switch (stateId) {
            case 0:
                setState("未付款");
                break;
            case 1:
                setState("未发货");
                break;
            case 2:
                setState("已发货");
                break;
            case 3:
                setState("已到货");
                break;
            default:
                setState("发货状态异常，请联系客服");
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsDetailId=" + goodsDetailId +
                ", goods='" + goods + '\'' +
                ", spec='" + spec + '\'' +
                ", goodsNum=" + goodsNum +
                ", amount=" + amount +
                ", stateId=" + stateId +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", specs=" + specs +
                ", states=" + states +
                '}';
    }
}

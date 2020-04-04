package com.zhenyiwang.mall.bean;

import java.util.List;

//      "id": 1815,
//		"amount": 432.0,
//		"num": 1,
//		"goodsDetailId": 1405,
//		"state": 0,
//		"goods": "半身裙",
public class EditOrderInfo {
    //订单号
    private Integer id;

    private Double amount;

    private Integer goodsNum;

    //商品规格id
    private Integer goodsDetailId;

    private Integer stateId;

    private String goods;

    private List<Spec> specs;

    private List<State> states;

    //      "curState": {
    //			"id": 1
    //		},
    //		"curSpec": {
    //			"id": 1271

    private State curState;

    private Spec curSpec;

    public State getCurState() {
        return curState;
    }

    public void setCurState(State curState) {
        this.curState = curState;
    }

    public Spec getCurSpec() {
        return curSpec;
    }

    public void setCurSpec(Spec curSpec) {
        this.curSpec = curSpec;
    }

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

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Override
    public String toString() {
        return "EditOrderInfo{" +
                "id=" + id +
                ", amount=" + amount +
                ", goodsNum=" + goodsNum +
                ", goodsDetailId=" + goodsDetailId +
                ", stateId=" + stateId +
                ", goods='" + goods + '\'' +
                ", specs=" + specs +
                ", states=" + states +
                ", curState=" + curState +
                ", curSpec=" + curSpec +
                '}';
    }
}

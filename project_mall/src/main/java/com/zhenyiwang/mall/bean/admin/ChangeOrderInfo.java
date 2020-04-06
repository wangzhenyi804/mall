package com.zhenyiwang.mall.bean.admin;

//{"id":"1848","state":1,"spec":1272,"num":2}

/**
 * 用来接收修改订单页面的请求
 */
public class ChangeOrderInfo {
    private Integer id;
    private Integer state;
    private Integer spec;
    private Integer num;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSpec() {
        return spec;
    }

    public void setSpec(Integer spec) {
        this.spec = spec;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ChangeOrderInfo{" +
                "id=" + id +
                ", state=" + state +
                ", spec=" + spec +
                ", num=" + num +
                '}';
    }
}

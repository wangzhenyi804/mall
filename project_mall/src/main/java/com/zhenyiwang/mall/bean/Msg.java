package com.zhenyiwang.mall.bean;

public class Msg {
    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private String content;
    private String replyContent;
    //已回复：0  未回复：1
    private Integer state;
    private String createtime;
    private Goods goods;
    private User user;

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

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", state=" + state +
                ", createtime='" + createtime + '\'' +
                ", goods=" + goods +
                ", user=" + user +
                '}';
    }
}

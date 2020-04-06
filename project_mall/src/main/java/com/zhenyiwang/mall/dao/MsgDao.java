package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.admin.Msg;
import com.zhenyiwang.mall.bean.client.ClientMsg;

import java.util.List;

public interface MsgDao {
    List<Msg> getMsg(int tag);

    void reply(Msg msg);

    List<Msg> getMsgByGoodsId(Integer id);
}

package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.Msg;

import java.util.List;

public interface MsgDao {
    List<Msg> getMsg(int tag);

    void reply(Msg msg);
}

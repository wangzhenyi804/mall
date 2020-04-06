package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.Msg;
import com.zhenyiwang.mall.bean.client.ClientMsg;

import java.sql.SQLException;
import java.util.List;

public interface MsgService {
    List<Msg> getMsg(int tag);

    void reply(Msg msg);

    List<Msg> getMsgByGoodsId(Integer id);
}

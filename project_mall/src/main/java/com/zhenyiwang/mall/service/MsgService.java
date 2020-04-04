package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Msg;

import java.sql.SQLException;
import java.util.List;

public interface MsgService {
    List<Msg> getMsg(int tag) throws SQLException;

    void reply(Msg msg);
}

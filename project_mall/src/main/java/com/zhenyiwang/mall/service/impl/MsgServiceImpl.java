package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Goods;
import com.zhenyiwang.mall.bean.Msg;
import com.zhenyiwang.mall.bean.User;
import com.zhenyiwang.mall.dao.AdminDao;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.dao.MsgDao;
import com.zhenyiwang.mall.dao.impl.AdminDaoImpl;
import com.zhenyiwang.mall.dao.impl.GoodsDaoImpl;
import com.zhenyiwang.mall.dao.impl.MsgDaoImpl;
import com.zhenyiwang.mall.service.MsgService;

import java.sql.SQLException;
import java.util.List;

public class MsgServiceImpl implements MsgService {

    MsgDao msgDao = new MsgDaoImpl();
    GoodsDao goodsDao = new GoodsDaoImpl();
    AdminDao adminDao = new AdminDaoImpl();

    @Override
    public List<Msg> getMsg(int tag) throws SQLException {
        List<Msg> msgs = msgDao.getMsg(tag);
        for (int i = 0; i < msgs.size(); i++) {
            Goods goods = goodsDao.queryGoodsName(msgs.get(i).getGoodsId());
            msgs.get(i).setGoods(goods);
            User user = adminDao.queryUserById(msgs.get(i).getUserId());
            msgs.get(i).setUser(user);
        }
        return msgs;
    }

    @Override
    public void reply(Msg msg) {
        msgDao.reply(msg);
    }
}

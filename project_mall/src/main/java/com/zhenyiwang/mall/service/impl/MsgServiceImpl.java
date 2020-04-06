package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.admin.Goods;
import com.zhenyiwang.mall.bean.admin.Msg;
import com.zhenyiwang.mall.bean.admin.User;
import com.zhenyiwang.mall.bean.client.ClientMsg;
import com.zhenyiwang.mall.dao.GoodsDao;
import com.zhenyiwang.mall.dao.MsgDao;
import com.zhenyiwang.mall.dao.UserDao;
import com.zhenyiwang.mall.dao.impl.GoodsDaoImpl;
import com.zhenyiwang.mall.dao.impl.MsgDaoImpl;
import com.zhenyiwang.mall.dao.impl.UserDaoImpl;
import com.zhenyiwang.mall.service.MsgService;

import java.sql.SQLException;
import java.util.List;

public class MsgServiceImpl implements MsgService {

    MsgDao msgDao = new MsgDaoImpl();
    GoodsDao goodsDao = new GoodsDaoImpl();
    UserDao userDao = new UserDaoImpl();

    /**
     * 获取商品提问以及提问用户id
     *
     * @param tag
     * @return
     * @throws SQLException
     */
    @Override
    public List<Msg> getMsg(int tag) {
        List<Msg> msgs = msgDao.getMsg(tag);
        for (int i = 0; i < msgs.size(); i++) {
            Goods goods = goodsDao.queryGoodsName(msgs.get(i).getGoodsId());
            msgs.get(i).setGoods(goods);
            User user = userDao.queryUserById(msgs.get(i).getUserId());
            msgs.get(i).setUser(user);
        }
        return msgs;
    }

    /**
     * 回复商品提问(留言)
     *
     * @param msg
     */
    @Override
    public void reply(Msg msg) {
        msgDao.reply(msg);
    }

    /**
     * 通过商品id来获取留言
     * * @param id
     *
     * @return
     */
    @Override
    public List<Msg> getMsgByGoodsId(Integer id) {
        List<Msg> msgs = msgDao.getMsgByGoodsId(id);
        for (int i = 0; i < msgs.size(); i++) {
            User user = userDao.queryUserById(msgs.get(i).getUserId());
            msgs.get(i).setUser(user);
        }
        return msgs;
    }

}

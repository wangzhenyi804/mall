package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.Msg;
import com.zhenyiwang.mall.dao.MsgDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class MsgDaoImpl implements MsgDao {


    @Override
    public List<Msg> getMsg(int tag) {
        List<Msg> msgs = null;
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            msgs = runner.query("select * from mall_msg where state=?", new BeanListHandler<>(Msg.class),tag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("dao:"+msgs);
        return msgs;
    }

    @Override
    public void reply(Msg msg) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("update mall_msg set state=0,replyContent=? where id=?",msg.getContent(),msg.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

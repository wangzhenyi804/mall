package com.zhenyiwang.mall.dao.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhenyiwang.mall.bean.admin.User;
import com.zhenyiwang.mall.dao.UserDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> queryAllUser() {
        List<User> userList = null;
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            userList = runner.query("select * from mall_user", new BeanListHandler<>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> searchUser(String word) {
        List<User> userList = null;
        List<Object> params = new ArrayList<>();
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        String baseSql = "select * from mall_user where 1 = 1";
        if (!StringUtils.isEmpty(word)) {
            baseSql = baseSql + " and nickname like ?";
            params.add("%" + word + "%");
        }
        try {
            userList = runner.query(baseSql, new BeanListHandler<>(User.class), params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void deleteUser(String id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from mall_user where id=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户id来查询用户
     * @param userId
     * @return
     */
    @Override
    public User queryUserById(Integer userId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = null;
        try {
            user = runner.query("select * from mall_user where id=?", new BeanHandler<>(User.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 前台用户登录
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            user = runner.query("select * from mall_user where email=? and pwd=?",
                    new BeanHandler<>(User.class),
                    user.getEmail(),
                    user.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 前台注册
     *
     * @param user
     * @return 0表示注册成功，1表示注册异常
     */
    @Override
    public Integer signup(User user) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Integer tag = 0;
        try {
            runner.update("insert into mall_user values(?,?,?,?,?,?,?)",
                    null,
                    user.getEmail(),
                    user.getNickname(),
                    user.getPwd(),
                    user.getRecipient(),
                    user.getAddress(),
                    user.getPhone()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            tag = 1;
        }
        return tag;
    }

    /**
     * 由用户id获取用户名
     * @param userId
     * @return
     */
    @Override
    public String queryUserNameById(String userId) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        String userName = null;
        try {
            userName = runner.query("select nickname from mall_user where id = ?", new BeanHandler<>(String.class), userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userName;
    }
}

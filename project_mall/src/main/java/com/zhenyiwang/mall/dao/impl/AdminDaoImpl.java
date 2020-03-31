package com.zhenyiwang.mall.dao.impl;

import com.alibaba.druid.util.StringUtils;
import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.bean.ChangePwdInfo;
import com.zhenyiwang.mall.bean.User;
import com.zhenyiwang.mall.dao.AdminDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    //查询管理员信息这一块可以提取出写一个方法
    @Override
    public Admin login(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin queryAdmin = null;
        try {
            queryAdmin = runner.query("select * from mall_admin where email=? and pwd=?",
                    new BeanHandler<>(Admin.class),
                    admin.getEmail(),
                    admin.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryAdmin;
    }

    @Override
    public List<Admin> queryAllAdmins() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> adminList = null;
        try {
            adminList = runner.query("select * from mall_admin", new BeanListHandler<>(Admin.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList;
    }

    @Override
    public int addAdmin(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("insert into mall_admin values(?,?,?,?)",
                    null,
                    admin.getEmail(),
                    admin.getNickname(),
                    admin.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public void updateAdminss(Admin admin) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        System.out.println(admin);
        try {
            runner.update("update mall_admin set email=?,nickname=?,pwd=? where id=?",
                    admin.getEmail(),
                    admin.getNickname(),
                    admin.getPwd(),
                    admin.getId()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Admin getAdminsInfo(int id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = null;
        try {
            admin = runner.query("select * from mall_admin where id=?", new BeanHandler<>(Admin.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public List<Admin> getSearchAdmins(Admin admin) {
        String baseSql = "select * from mall_admin where 1 = 1 ";
        List<Object> params = new ArrayList<>();
        if (!StringUtils.isEmpty(admin.getEmail())) {
            baseSql = baseSql + " and email like ?";
            params.add("%" + admin.getEmail() + "%");
        }
        if (!StringUtils.isEmpty(admin.getNickname())) {
            baseSql = baseSql + " and nickname like ?";
            params.add("%" + admin.getNickname() + "%");
        }

        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> admins = null;
        System.out.println(baseSql);
        try {
            admins = runner.query(baseSql, new BeanListHandler<>(Admin.class), params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    /**
     * 更改用户密码
     * *  0:修改成功
     * *  1:旧密码错误
     * *  2:请保证确认密码与新密码一致
     *
     * @param changePwdInfo
     * @return
     */
    @Override
    public int changePwd(ChangePwdInfo changePwdInfo) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        if (!changePwdInfo.getNewPwd().equals(changePwdInfo.getConfirmPwd())) {
        }
        try {
            runner.update("update mall_admin set pwd=? where nickname=?",
                    changePwdInfo.getNewPwd(),
                    changePwdInfo.getAdminToken()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<User> queryAllUser() {
        List<User> userList = null;
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            userList = runner.query("select * from mall_user",new BeanListHandler<>(User.class));
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
            userList = runner.query(baseSql,new BeanListHandler<>(User.class),params.toArray());
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

    @Override
    public void deleteAdmins(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("delete from mall_admin where id=?", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

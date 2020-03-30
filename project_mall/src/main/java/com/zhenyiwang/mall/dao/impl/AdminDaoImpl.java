package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.dao.AdminDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {
    QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());

    //查询管理员信息这一块可以提取出写一个方法
    @Override
    public Integer login(Admin admin) {
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

        if(queryAdmin != null){
            //查询成功
            return 200;
        }
        return 404;
    }

    @Override
    public List<Admin> queryAllAdmins() {
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
    public void updateAdminss(Admin admin,int id) {
        try {
            runner.update("update mall_admin set email=?,nickname=?,pwd=? where id=?",
                    admin.getEmail(),
                    admin.getNickname(),
                    admin.getPwd(),
                    id
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Admin getAdminsInfo(Admin admin,int id) {
        try {
            admin = runner.query("select * from mall_admin where id=?", new BeanHandler<>(Admin.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    @Override
    public void deleteAdmins(HttpServletRequest request, HttpServletResponse response) {
        Integer id = Integer.valueOf(request.getQueryString().substring(3));
        try {
            runner.update("delete from mall_admin where id=?",id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

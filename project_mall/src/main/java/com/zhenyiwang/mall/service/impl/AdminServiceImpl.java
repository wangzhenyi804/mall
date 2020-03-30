package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.dao.AdminDao;
import com.zhenyiwang.mall.dao.impl.AdminDaoImpl;
import com.zhenyiwang.mall.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public int login(Admin admin) {
        return adminDao.login(admin);
    }

    @Override
    public List<Admin> queryAllAdmins() {

        return adminDao.queryAllAdmins();
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    @Override
    public void deleteAdmins(HttpServletRequest request, HttpServletResponse response) {
        adminDao.deleteAdmins(request,response);
    }

    @Override
    public void updateAdminss(Admin admin,int id) {
        adminDao.updateAdminss(admin,id);
    }

    @Override
    public Admin getAdminsInfo(Admin admin,int id) {
        return adminDao.getAdminsInfo(admin,id);
    }
}

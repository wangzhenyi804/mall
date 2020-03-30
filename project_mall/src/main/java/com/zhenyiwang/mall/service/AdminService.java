package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminService {

    int login(Admin admin);

    List<Admin> queryAllAdmins();

    int addAdmin(Admin admin);

    void deleteAdmins(HttpServletRequest request, HttpServletResponse response);

    void updateAdminss(Admin admin,int id);

    Admin getAdminsInfo(Admin admin,int id);
}

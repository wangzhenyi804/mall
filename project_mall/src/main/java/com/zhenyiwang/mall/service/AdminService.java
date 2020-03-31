package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.bean.ChangePwdInfo;
import com.zhenyiwang.mall.bean.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AdminService {

    Admin login(Admin admin);

    List<Admin> queryAllAdmins();

    int addAdmin(Admin admin);

    void deleteAdmins(HttpServletRequest request, HttpServletResponse response);

    void updateAdminss(Admin admin);

    Admin getAdminsInfo(int id);

    List<Admin> getSearchAdmins(Admin admin);

    int changePwd(ChangePwdInfo changePwdInfo);

    List<User> queryAllUser();

    List<User> searchUser(String word);

    void deleteUser(String id);
}

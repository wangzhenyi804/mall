package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.Admin;
import com.zhenyiwang.mall.bean.admin.ChangePwdInfo;

import java.util.List;

public interface AdminService {

    Admin login(Admin admin);

    List<Admin> queryAllAdmins();

    int addAdmin(Admin admin);

    void deleteAdmins(Integer id);

    void updateAdminss(Admin admin);

    Admin getAdminsInfo(int id);

    List<Admin> getSearchAdmins(Admin admin);

    int changePwd(ChangePwdInfo changePwdInfo);
}

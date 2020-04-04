package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.bean.ChangePwdInfo;
import com.zhenyiwang.mall.bean.User;

import java.util.List;

public interface AdminDao {

    Admin login(Admin admin);

    List<Admin> queryAllAdmins();

    int addAdmin(Admin admin);

    void deleteAdmins(Integer id);

    void updateAdminss(Admin admin);

    Admin getAdminsInfo(int id);

    List<Admin> getSearchAdmins(Admin admin);

    int changePwd(ChangePwdInfo changePwdInfo);

    List<User> queryAllUser();

    List<User> searchUser(String word);

    void deleteUser(String id);


    User queryUserById(Integer userId);
}

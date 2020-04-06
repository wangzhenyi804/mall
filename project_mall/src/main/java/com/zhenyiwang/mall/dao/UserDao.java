package com.zhenyiwang.mall.dao;

import com.zhenyiwang.mall.bean.admin.User;

import java.util.List;

public interface UserDao {
    List<User> queryAllUser();

    List<User> searchUser(String word);

    void deleteUser(String id);


    User queryUserById(Integer userId);

    User login(User user);

    Integer signup(User user);

    String queryUserNameById(String userId);
}

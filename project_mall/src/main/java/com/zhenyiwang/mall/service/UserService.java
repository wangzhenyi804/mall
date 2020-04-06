package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.User;

import java.util.List;

public interface UserService {
    List<User> queryAllUser();

    List<User> searchUser(String word);

    void deleteUser(String id);

    User login(User user);

    Integer signup(User user);

    User queryUserById(Integer userId);
}

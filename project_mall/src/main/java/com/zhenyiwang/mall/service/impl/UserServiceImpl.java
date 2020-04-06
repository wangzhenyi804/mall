package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.admin.User;
import com.zhenyiwang.mall.dao.UserDao;
import com.zhenyiwang.mall.dao.impl.UserDaoImpl;
import com.zhenyiwang.mall.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public List<User> queryAllUser() {
        return userDao.queryAllUser();
    }

    @Override
    public List<User> searchUser(String word) {
        return userDao.searchUser(word);
    }

    @Override
    public void deleteUser(String id) {
        userDao.deleteUser(id);
    }

    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public Integer signup(User user) {
        return userDao.signup(user);
    }

    @Override
    public User queryUserById(Integer userId) {
            return userDao.queryUserById(userId);
    }

}

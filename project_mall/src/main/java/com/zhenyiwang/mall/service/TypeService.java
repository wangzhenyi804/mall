package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.admin.Type;

import java.util.List;

public interface TypeService {

    List<Type> getType();

    void addType(Type type);
}

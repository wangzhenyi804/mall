package com.zhenyiwang.mall.service;

import com.zhenyiwang.mall.bean.Type;

import java.util.List;

public interface TypeService {

    List<Type> getType();

    void addType(Type type);
}

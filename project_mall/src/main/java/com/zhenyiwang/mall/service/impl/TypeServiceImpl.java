package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Type;
import com.zhenyiwang.mall.dao.TypeDao;
import com.zhenyiwang.mall.dao.impl.TypeDaoImpl;
import com.zhenyiwang.mall.service.TypeService;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;


public class TypeServiceImpl implements TypeService {

    TypeDao typeDao = new TypeDaoImpl();


    @Override
    public List<Type> getType() {

        return typeDao.getType();
    }

    @Override
    public void addType(Type type) {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        try {
            runner.update("insert into mall_type values(null,?)",type.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

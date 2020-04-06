package com.zhenyiwang.mall.dao.impl;

import com.zhenyiwang.mall.bean.admin.Type;
import com.zhenyiwang.mall.dao.TypeDao;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class TypeDaoImpl implements TypeDao {
    @Override
    public List<Type> getType() {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Type> typeList = null;
        try {
            typeList = runner.query("select * from mall_type", new BeanListHandler<>(Type.class));
            //System.out.println(typeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeList;
    }
}

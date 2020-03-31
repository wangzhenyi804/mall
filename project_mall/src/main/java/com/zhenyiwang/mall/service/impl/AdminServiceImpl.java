package com.zhenyiwang.mall.service.impl;

import com.zhenyiwang.mall.bean.Admin;
import com.zhenyiwang.mall.bean.ChangePwdInfo;
import com.zhenyiwang.mall.bean.User;
import com.zhenyiwang.mall.dao.AdminDao;
import com.zhenyiwang.mall.dao.impl.AdminDaoImpl;
import com.zhenyiwang.mall.service.AdminService;
import com.zhenyiwang.mall.utils.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin login(Admin admin) {
        return adminDao.login(admin);
    }

    @Override
    public List<Admin> queryAllAdmins() {

        return adminDao.queryAllAdmins();
    }

    @Override
    public int addAdmin(Admin admin) {
        return adminDao.addAdmin(admin);
    }

    @Override
    public void deleteAdmins(Integer id) {
        adminDao.deleteAdmins(id);
    }

    @Override
    public void updateAdminss(Admin admin) {
        adminDao.updateAdminss(admin);
    }

    @Override
    public Admin getAdminsInfo(int id) {
        return adminDao.getAdminsInfo(id);
    }

    @Override
    public List<Admin> getSearchAdmins(Admin admin) {
        return adminDao.getSearchAdmins(admin);
    }

    @Override
    public int changePwd(ChangePwdInfo changePwdInfo) {
        if (!changePwdInfo.getNewPwd().equals(changePwdInfo.getConfirmPwd())) {
            return 2;
        }
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = null;
        try {
            admin = runner.query("select * from mall_admin where nickname=?",
                    new BeanHandler<>(Admin.class),
                    changePwdInfo.getAdminToken()
                    );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!changePwdInfo.getOldPwd().equals(admin.getPwd())){
            return 1;
        }
        return adminDao.changePwd(changePwdInfo);
    }

    @Override
    public List<User> queryAllUser() {
        return adminDao.queryAllUser();
    }

    @Override
    public List<User> searchUser(String word) {
        return adminDao.searchUser(word);
    }

    @Override
    public void deleteUser(String id) {
        adminDao.deleteUser(id);
    }
}

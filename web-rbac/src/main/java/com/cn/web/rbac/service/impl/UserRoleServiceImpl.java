package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.UserRoleDao;
import com.cn.web.rbac.domain.UserRole;
import com.cn.web.rbac.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public UserRole get(Serializable id) {
        Optional<UserRole> optional = userRoleDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public UserRole save(UserRole userRole) {
        return userRoleDao.save(userRole);
    }

    @Override
    public UserRole update(UserRole userRole) {
        return userRoleDao.save(userRole);
    }

    @Override
    public void delete(Serializable id) {
        userRoleDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<UserRole> list() {
        return userRoleDao.findAll();
    }

    @Override
    public void deleteByUserIdAndRoleId(String userId, String roleId) {
        userRoleDao.deleteUserRoleByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public List<String> findByUserId(String userId) {
        return userRoleDao.findByUserId(userId);
    }

}

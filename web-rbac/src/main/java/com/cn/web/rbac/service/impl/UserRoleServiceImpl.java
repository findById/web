package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.UserRoleDao;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.UserRole;
import com.cn.web.rbac.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Transactional
    public void updateUserRole(String userId, List<String> roleIds) {
        List<String> oldRoleIds = findByUserId(userId);

        // need to delete?
        if (oldRoleIds == null) {
            oldRoleIds = new ArrayList<>(0);
        }
        for (String roleId : oldRoleIds) {
            if (!roleIds.contains(roleId)) {
                deleteByUserIdAndRoleId(userId, roleId);
            }
        }
        // need to save?
        for (String roleId : roleIds) {
            if (!oldRoleIds.contains(roleId)) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                save(userRole);
            }
        }
    }

    @Override
    public void delete(Serializable id) {
        userRoleDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<UserRole> list() {
        UserRole item = new UserRole();
        item.setDelFlg(BaseEntity.FLAG_NORMAL);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("delFlg", ExampleMatcher.GenericPropertyMatchers.regex())
                .withIgnorePaths("updateTime", "state");
        Example<UserRole> example = Example.of(item, matcher);
        return userRoleDao.findAll(example);
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

package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.RolePermissionDao;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.RolePermission;
import com.cn.web.rbac.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Override
    public RolePermission get(Serializable id) {
        Optional<RolePermission> optional = rolePermissionDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public RolePermission save(RolePermission rolePermission) {
        return rolePermissionDao.save(rolePermission);
    }

    @Override
    public RolePermission update(RolePermission rolePermission) {
        return rolePermissionDao.save(rolePermission);
    }

    @Override
    public void delete(Serializable id) {
        rolePermissionDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<String> findPermissionIdListByRoleId(String roleId) {
        return rolePermissionDao.findPermissionIdListByRoleId(roleId);
    }

    @Override
    public void deleteByRoleIdAndPermissionId(String roleId, String permissionId) {
        rolePermissionDao.deleteByRoleIdAndPermissionId(roleId, permissionId);
    }

    @Override
    public List<RolePermission> list() {
        RolePermission item = new RolePermission();
        item.setDelFlg(BaseEntity.FLAG_NORMAL);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("delFlg", ExampleMatcher.GenericPropertyMatchers.regex())
                .withIgnorePaths("updateTime", "state");
        Example<RolePermission> example = Example.of(item, matcher);
        return rolePermissionDao.findAll(example);
    }

}

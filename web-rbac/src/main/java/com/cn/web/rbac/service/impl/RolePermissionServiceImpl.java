package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.PermissionDao;
import com.cn.web.rbac.dao.RolePermissionDao;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.domain.RolePermission;
import com.cn.web.rbac.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    PermissionDao permissionDao;

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
    @Transactional
    public RolePermission update(RolePermission rolePermission) {
        return rolePermissionDao.save(rolePermission);
    }

    @Override
    @Transactional
    public void updateRolePermission(String roleId, List<String> permIds) {
        List<String> oldPermIdList = findPermissionIdListByRoleId(roleId);

        // need to delete?
        if (oldPermIdList == null) {
            oldPermIdList = new ArrayList<>(0);
        }
        for (String permId : oldPermIdList) {
            if (permId != null && !permId.isEmpty() && !permIds.contains(permId)) {
                deleteByRoleIdAndPermissionId(roleId, permId);
            }
        }
        // need to save?
        for (String permId : permIds) {
            if (permId != null && !permId.isEmpty() && !oldPermIdList.contains(permId)) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permId);
                save(rolePermission);
            }
        }

        // save root node
        Permission root = permissionDao.findByParentIdIsNull();
        if (root != null && !permIds.contains(root.getId())) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(root.getId());
            save(rolePermission);
        }
    }

    @Override
    @Transactional
    public void delete(Serializable id) {
        rolePermissionDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<String> findPermissionIdListByRoleId(String roleId) {
        return rolePermissionDao.findPermissionIdListByRoleId(roleId);
    }

    @Override
    public void deleteByRoleIdAndPermissionId(String roleId, String permissionId) {
        rolePermissionDao.deleteRolePermissionByRoleIdAndPermissionId(roleId, permissionId);
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

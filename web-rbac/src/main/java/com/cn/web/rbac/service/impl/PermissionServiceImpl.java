package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.PermissionDao;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.Permission;
import com.cn.web.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "permissionService")
//@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public Permission get(Serializable id) {
        Optional<Permission> optional = permissionDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public Permission save(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    @Transactional
    public Permission update(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    @Transactional
    public void delete(Serializable id) {
        permissionDao.deleteById(String.valueOf(id));
    }

    @Override
    public void deleteByLogic(Serializable id) {
        Optional<Permission> optional = permissionDao.findById(String.valueOf(id));
        Permission permission = optional.orElse(null);
        if (permission != null) {
            permission.setDelFlg(BaseEntity.FLAG_DELETE);
            permissionDao.save(permission);
        }
    }

    @Override
    public List<Permission> list() {
        Permission permission = new Permission();
        permission.setDelFlg(BaseEntity.FLAG_NORMAL);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("delFlg", ExampleMatcher.GenericPropertyMatchers.regex())
                .withIgnorePaths("updateTime", "state", "visible");
        Example<Permission> example = Example.of(permission, matcher);
        // Sort.by(Sort.Order.asc("parentId"), Sort.Order.desc("position"))
        return permissionDao.findAll(example, Sort.by("parentId", "position").ascending());
    }

    @Override
    public List<Permission> findByUserId(Serializable userId) {
        // SELECT * FROM permission AS p,role_permission AS rp,user_role AS ur WHERE p.id=rp.permission_id AND rp.role_id=ur.role_id AND ur.user_id=:userId AND p.del_flg=0 AND rp.del_flg=0 AND ur.del_flg=0 ORDER BY p.parent_id,p.position
        // SELECT * FROM permission AS p,role_permission AS rp,user_role AS ur WHERE p.id=rp.permission_id AND rp.role_id=ur.role_id AND ur.user_id=:userId AND p.type='menu' AND p.del_flg=0 AND rp.del_flg=0 AND ur.del_flg=0 ORDER BY p.parent_id,p.position
        return permissionDao.findPermissionsByUserId(String.valueOf(userId));
    }

    @Override
    public List<Permission> findByParentId(String parentId) {
        return permissionDao.findByParentId(parentId);
    }

    @Override
    @Transactional
    public void saveAll(List<Permission> permissions) {
        for (Permission perm : permissions) {
            permissionDao.save(perm);
        }
    }

    @Override
    public void saveBaseOperation(String parentId, String name) {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission("view", "permission", null, "", "sys:" + name + ":view", parentId, ""));
        permissions.add(new Permission("add", "permission", null, "", "sys:" + name + ":save", parentId, ""));
        permissions.add(new Permission("edit", "permission", null, "", "sys:" + name + ":update", parentId, ""));
        permissions.add(new Permission("delete", "permission", null, "", "sys:" + name + ":delete", parentId, ""));

        List<Permission> existPList = permissionDao.findOperationPermissionByParentId(parentId);
        for (Permission permission : permissions) {
            boolean exist = false;
            for (Permission existPermission : existPList) {
                if (permission.getPermCode().equals(existPermission.getPermCode())) {
                    exist = true;
                    break;
                } else {
                    exist = false;
                }
            }
            if (!exist) {
                save(permission);
            }
        }
    }

    @Override
    public List<Permission> findOperationPermissionByParentId(String parentId) {
        return permissionDao.findOperationPermissionByParentId(parentId);
    }

    @Override
    public Permission findRootNote() {
        return permissionDao.findByParentIdIsNull();
    }
}

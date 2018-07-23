package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.Role;
import com.cn.web.rbac.service.RoleService;
import com.cn.web.rbac.web.request.RolePermReq;
import com.cn.web.rbac.web.request.RoleReq;
import com.cn.web.rbac.web.vo.RoleBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("roleHandler")
public class RoleHandler {

    @Autowired
    RoleService roleService;

    @Autowired
    RolePermissionHandler rolePermissionHandler;

    public HashMap<String, Object> search(String keywords, int page, int size) {

        Page<Role> list = roleService.search(keywords, page - 1, size);

        List<RoleBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Role item : list.getContent()) {
                if (item.getDelFlg() != BaseEntity.FLAG_NORMAL) {
                    continue;
                }
                RoleBean bean = new RoleBean();
                BeanUtils.copyProperties(item, bean);
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

    public RoleBean save(RoleReq req) {

        if (req == null || req.getName() == null || req.getName().isEmpty()) {
            throw new HandlerException(201, "'name' must not be null.");
        }
        if (req.getCode() == null || req.getCode().isEmpty()) {
            throw new HandlerException(201, "'code' must not be null.");
        }

        Role role = new Role();
        role.setName(req.getName());
        role.setCode(req.getCode());
        role.setType(req.getType());
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            Role parent = roleService.get(req.getParentId());
            if (parent == null) {
                throw new HandlerException(201, "Parent permission not exists");
            }
            role.setParentId(req.getParentId());
        }
        role.setDescription(req.getDescription());

        roleService.save(role);

        if (req.getPermIds() != null && !req.getPermIds().isEmpty()) {
            rolePermissionHandler.update(new RolePermReq(role.getId(), req.getPermIds()));
        }

        RoleBean bean = new RoleBean();
        BeanUtils.copyProperties(role, bean);

        return bean;
    }

    public boolean update(RoleReq req) {

        if (req == null || req.getId() == null) {
            throw new HandlerException(201, "role not exists");
        }

        Role role = roleService.get(req.getId());
        if (role == null) {
            throw new HandlerException(201, "role not exists");
        }
        if (req.getName() != null && !req.getName().isEmpty()) {
            role.setName(req.getName());
        }
        if (req.getCode() != null && !req.getCode().isEmpty()) {
            role.setCode(req.getCode());
        }
        if (req.getType() != null && !req.getType().isEmpty()) {
            role.setType(req.getType());
        }
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            role.setDescription(req.getDescription());
        }

        roleService.update(role);

        if (req.getPermIds() != null && !req.getPermIds().isEmpty()) {
            rolePermissionHandler.update(new RolePermReq(role.getId(), req.getPermIds()));
        }

        return true;
    }

    public boolean delete(String[] ids) {
        roleService.delete(ids);
        return true;
    }

    public void deleteByLogic(String[] ids) {
        roleService.deleteByLogic(ids);
    }

    public RoleBean findById(String id) {
        Role role = roleService.get(id);
        if (role != null && role.getDelFlg() == BaseEntity.FLAG_NORMAL) {
            RoleBean bean = new RoleBean();
            BeanUtils.copyProperties(role, bean);
            bean.setPermIds(rolePermissionHandler.list(bean.getId()));
            return bean;
        }
        return null;
    }

    public List<RoleBean> list() {
        List<RoleBean> beanList = new ArrayList<>();

        List<Role> list = roleService.list();
        if (list != null && !list.isEmpty()) {
            for (Role role : list) {
                if (role.getDelFlg() == BaseEntity.FLAG_DELETE) {
                    continue;
                }
                RoleBean bean = new RoleBean();
                BeanUtils.copyProperties(role, bean);
                beanList.add(bean);
            }
        }

        return beanList;
    }

    public HashMap<String, Object> list(int page, int size) {

        Page<Role> list = roleService.list(page - 1, size);

        List<RoleBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Role role : list.getContent()) {
                if (role.getDelFlg() == BaseEntity.FLAG_DELETE) {
                    continue;
                }
                RoleBean bean = new RoleBean();
                bean.setId(role.getId());
                bean.setName(role.getName());
                bean.setCode(role.getCode());
                bean.setType(role.getType());
                bean.setParentId(role.getParentId());
                bean.setDescription(role.getDescription());
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }
}

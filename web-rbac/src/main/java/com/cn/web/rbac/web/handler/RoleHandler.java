package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.Role;
import com.cn.web.rbac.service.RoleService;
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

    public HashMap<String, Object> search(String keywords, int page, int size) {

        Page<Role> list = roleService.search(keywords, page - 1, size);

        List<RoleBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Role item : list.getContent()) {
                if (item.getDelFlg() != BaseEntity.FLAG_NORMAL && item.getDelFlg() != BaseEntity.FLAG_ENABLE) {
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
        role.setId(req.getId());
        role.setName(req.getName());
        role.setCode(req.getCode());
        role.setDescription(req.getDescription());

        roleService.save(role);

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
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            role.setDescription(req.getDescription());
        }

        roleService.update(role);

        return true;
    }

    public boolean delete(String id) {

        roleService.delete(id);

        return true;
    }

    public Role findById(String id) {
        return roleService.get(id);
    }

    public HashMap<String, Object> list(int page, int size) {
        if (page <= 0) {
            page = 1;
        }
        if (size > 20) {
            size = 20;
        }

        Page<Role> list = roleService.list(page - 1, size);

        List<RoleBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Role role : list.getContent()) {
                RoleBean bean = new RoleBean();
                bean.setId(role.getId());
                bean.setName(role.getName());
                bean.setCode(role.getCode());
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

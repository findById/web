package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.RoleDao;
import com.cn.web.rbac.domain.Role;
import com.cn.web.rbac.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public Role get(Serializable id) {
        Optional<Role> optional = roleDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public Role save(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleDao.save(role);
    }

    @Override
    public void delete(Serializable id) {
        roleDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<Role> list() {
        return roleDao.findAll();
    }

    @Override
    public Page<Role> list(int page, int size) {
        return roleDao.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

    @Override
    public Page<Role> search(String keywords, int page, int size) {
        Role role = new Role();
        role.setId(keywords);
        role.setName(keywords);
        role.setCode(keywords);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("code", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("updateTime", "delFlg");
        Example<Role> example = Example.of(role, matcher);
        return roleDao.findAll(example, PageRequest.of(page, size, Sort.by("id")));
    }
}

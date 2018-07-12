package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Role;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface RoleService {
    Role get(Serializable id);

    Role save(Role role);

    Role update(Role role);

    void delete(Serializable id);

    List<Role> list();

    Page<Role> list(int page, int size);

    Page<Role> search(String keywords, int page, int size);
}

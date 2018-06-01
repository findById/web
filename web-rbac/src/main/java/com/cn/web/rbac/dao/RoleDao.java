package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleDao")
public interface RoleDao extends JpaRepository<Role, String> {
}

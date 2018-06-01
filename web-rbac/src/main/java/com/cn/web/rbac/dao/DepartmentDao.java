package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("departmentDao")
public interface DepartmentDao extends JpaRepository<Department, String> {
}

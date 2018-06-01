package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Department;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface DepartmentService {

    Department get(Serializable id);

    Department save(Department user);

    Department update(Department user);

    void delete(Serializable id);

    List<Department> list();

    Page<Department> list(int offset, int size);

}

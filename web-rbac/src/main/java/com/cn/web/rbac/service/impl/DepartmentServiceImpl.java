package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.DepartmentDao;
import com.cn.web.rbac.domain.Department;
import com.cn.web.rbac.service.DepartmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao departmentDao;

    @Override
    public Department get(Serializable id) {
        Optional<Department> optional = departmentDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public Department save(Department department) {

        departmentDao.save(department);

        return department;
    }

    @Override
    @Transactional
    public Department update(Department department) {
        // departmentDao.update(department);
        departmentDao.save(department);
        return department;
    }

    @Override
    @Transactional
    public void delete(Serializable id) {
        departmentDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<Department> list() {
        return departmentDao.findAll();
    }

    @Override
    public Page<Department> list(int offset, int size) {
        int page = offset;
        return departmentDao.findAll(PageRequest.of(page, size, Sort.by("id")));
    }

}

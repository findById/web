package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.DictDao;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    DictDao dictDao;

    @Override
    public Page<Dict> search(String label, String value, String type, Pageable page) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM dict WHERE del_flag=0");
        if (!StringUtils.isEmpty(label)) {
            sql.append(" AND label LIKE %").append(label).append('%');
        }
        if (!StringUtils.isEmpty(value)) {
            sql.append(" AND value LIKE %").append(value).append('%');
        }
        if (!StringUtils.isEmpty(type)) {
            sql.append(" AND type LIKE %").append(type).append('%');
        }
        System.out.println(sql.toString());

        return dictDao.findAll(page);
    }

    @Override
    public Dict get(Serializable id) {
        Optional<Dict> optional = dictDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public Dict save(Dict dict) {
        return dictDao.save(dict);
    }

    @Override
    public Dict update(Dict dict) {
        if (dict.getId() != null) {
            return dictDao.saveAndFlush(dict);
        }
        return dict;
    }

    @Override
    public void delete(Serializable id) {
        dictDao.deleteById(String.valueOf(id));
    }

    @Override
    public List<Dict> list() {
        return dictDao.findAll();
    }
}

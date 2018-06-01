package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.DictDao;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "dictService")
public class DictServiceImpl implements DictService {

    @Autowired
    DictDao dictDao;

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

package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.DictDao;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    @Override
    public Page<Dict> list(int page, int size) {
        return dictDao.findAll(PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    public Page<Dict> search(String keywords, int page, int size) {
        Dict user = new Dict();
        user.setId(keywords);
        user.setLabel(keywords);
        user.setValue(keywords);
        user.setType(keywords);
        user.setRemark(keywords);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("label", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("value", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remark", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("updateTime", "delFlg");
        Example<Dict> example = Example.of(user, matcher);
        return dictDao.findAll(example, PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

}

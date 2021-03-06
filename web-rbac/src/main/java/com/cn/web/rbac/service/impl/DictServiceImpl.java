package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.DictDao;
import com.cn.web.rbac.domain.BaseEntity;
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
    @Transactional
    public void deleteByLogic(Serializable[] ids) {
        if (ids == null || ids.length <= 0) {
            return;
        }
        for (Serializable id : ids) {
            Optional<Dict> optional = dictDao.findById(String.valueOf(id));
            Dict item = optional.orElse(null);
            if (item != null) {
                item.setDelFlg(BaseEntity.FLAG_DELETE);
                dictDao.save(item);
            }
        }
    }

    @Override
    public List<Dict> list() {
        Dict item = new Dict();
        item.setDelFlg(BaseEntity.FLAG_NORMAL);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("delFlg", ExampleMatcher.GenericPropertyMatchers.regex())
                .withIgnorePaths("updateTime", "state");
        Example<Dict> example = Example.of(item, matcher);
        return dictDao.findAll(example);
    }

    @Override
    public Page<Dict> list(int page, int size) {
        return dictDao.findAll(PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    public List<Dict> findByType(String type) {
        return dictDao.findAllByType(type);
    }

    @Override
    public List<Dict> findByParentId(String parentId) {
        return dictDao.findAllByParentId(parentId);
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

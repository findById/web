package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import com.cn.web.rbac.web.request.DictReq;
import com.cn.web.rbac.web.vo.DictBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("dictHandler")
public class DictHandler {

    @Autowired
    DictService dictService;

    public DictBean save(DictReq req) {
        if (req == null) {
            throw new HandlerException(201, "Request body must not be null.");
        }
        Dict dict = new Dict();
        dict.setLabel(req.getLabel());
        dict.setValue(req.getValue());
        dict.setType(req.getType());
        dict.setPosition(req.getPosition() != null ? req.getPosition() : 0);
        dict.setRemark(req.getRemark());
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            Dict parent = dictService.get(req.getParentId());
            if (parent == null) {
                throw new HandlerException(201, "Parent dict not exists");
            }
            dict.setParentId(req.getParentId());
        }

        dictService.save(dict);

        DictBean bean = new DictBean();
        BeanUtils.copyProperties(dict, bean);

        return bean;
    }

    public boolean update(DictReq req) {
        if (req == null || req.getId() == null) {
            throw new HandlerException(201, "dict not exists");
        }
        Dict dict = dictService.get(req.getId());
        if (dict == null) {
            throw new HandlerException(201, "dict not exists");
        }
        if (req.getLabel() != null && !req.getLabel().isEmpty()) {
            dict.setLabel(req.getLabel());
        }
        if (req.getValue() != null && !req.getLabel().isEmpty()) {
            dict.setValue(req.getValue());
        }
        if (req.getType() != null && !req.getType().isEmpty()) {
            dict.setType(req.getType());
        }
        if (req.getPosition() != null) {
            dict.setPosition(req.getPosition());
        }
        if (req.getParentId() != null && !req.getParentId().isEmpty()) {
            Dict parent = dictService.get(req.getParentId());
            if (parent == null) {
                throw new HandlerException(201, "Parent dict not exists");
            }
            dict.setParentId(req.getParentId());
        }
        dictService.update(dict);
        return true;
    }

    public boolean delete(String[] ids) {
        for (String id : ids) {
            dictService.delete(id);
        }
        return true;
    }

    public List<Dict> list() {
        return dictService.list();
    }

    public HashMap<String, Object> list(int page, int size) {
        Page<Dict> list = dictService.list(page - 1, size);

        List<DictBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Dict item : list.getContent()) {
                if (item.getDelFlg() == BaseEntity.FLAG_DELETE) {
                    continue;
                }
                DictBean bean = new DictBean();
                BeanUtils.copyProperties(item, bean);
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

    public List<Dict> findByType(String type) {
        return dictService.findByType(type);
    }

    public List<Dict> findByParentId(String parentId) {
        return dictService.findByParentId(parentId);
    }

    public HashMap<String, Object> search(String keywords, int page, int size) {

        Page<Dict> list = dictService.search(keywords, page - 1, size);

        List<DictBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (Dict item : list.getContent()) {
                if (item.getDelFlg() == BaseEntity.FLAG_DELETE) {
                    continue;
                }
                DictBean bean = new DictBean();
                BeanUtils.copyProperties(item, bean);
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

}

package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.Dict;
import com.cn.web.rbac.service.DictService;
import com.cn.web.rbac.web.request.DictReq;
import com.cn.web.rbac.web.vo.DictBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        dict.setPosition(req.getPosition());
        dict.setRemark(req.getRemark());

        dictService.save(dict);

        DictBean bean = new DictBean();
        BeanUtils.copyProperties(dict, bean);

        return bean;
    }

    public boolean update(Dict dict) {
        dictService.update(dict);
        return true;
    }

    public boolean delete(String id) {
        dictService.delete(id);
        return true;
    }

    public List<Dict> list() {
        return dictService.list();
    }
}

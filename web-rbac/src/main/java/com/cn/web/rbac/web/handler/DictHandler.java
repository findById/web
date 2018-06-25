package com.cn.web.rbac.web.handler;

import com.alibaba.fastjson.JSON;
import com.cn.web.core.platform.web.ResponseBuilder;
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

    public String save(DictReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        if (req == null) {
            builder.statusCode(201);
            builder.message("Request body must not be null.");
            return builder.buildJSONString();
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

        builder.statusCode(200);
        builder.message("message");
        builder.result(bean);
        return builder.buildJSONString();
    }

    public String update(Dict dict) {
        dictService.update(dict);
        return "unimplemented";
    }

    public String delete(String id) {
        dictService.delete(id);
        return "success";
    }

    public String list() {
        List<Dict> list = dictService.list();
        return JSON.toJSONString(list);
    }
}

package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Dict;

import java.io.Serializable;
import java.util.List;

public interface DictService {
    Dict get(Serializable id);

    Dict save(Dict role);

    Dict update(Dict role);

    void delete(Serializable id);

    List<Dict> list();
}

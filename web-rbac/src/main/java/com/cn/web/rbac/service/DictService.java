package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface DictService {

    Page<Dict> search(String label, String value, String type, Pageable page);

    Dict get(Serializable id);

    Dict save(Dict role);

    Dict update(Dict role);

    void delete(Serializable id);

    List<Dict> list();
}

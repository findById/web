package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.Dict;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface DictService {

    Dict get(Serializable id);

    Dict save(Dict role);

    Dict update(Dict role);

    void delete(Serializable id);

    void deleteByLogic(Serializable[] ids);

    List<Dict> list();

    Page<Dict> list(int page, int size);

    List<Dict> findByType(String type);

    List<Dict> findByParentId(String parentId);

    Page<Dict> search(String keywords, int page, int size);

}

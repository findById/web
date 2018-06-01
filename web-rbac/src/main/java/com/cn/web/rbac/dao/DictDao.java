package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("dictDao")
public interface DictDao extends JpaRepository<Dict, String> {
}

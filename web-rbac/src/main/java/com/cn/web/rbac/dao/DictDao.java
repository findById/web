package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dictDao")
public interface DictDao extends JpaRepository<Dict, String> {

    @Query(value = "SELECT * FROM dict AS d WHERE d.type=:t AND d.del_flg=0 ORDER BY d.type,d.parent_id,d.position", nativeQuery = true)
    List<Dict> findAllByType(@Param("t") String type);

    @Query(value = "SELECT * FROM dict AS d WHERE d.parent_id=:parentId AND d.del_flg=0 ORDER BY d.type,d.parent_id,d.position", nativeQuery = true)
    List<Dict> findAllByParentId(@Param("parentId") String parentId);

    @Query(value = "SELECT * FROM dict AS d WHERE (d.type=:gt OR d.type=:t) AND d.del_flg=0 ORDER BY d.type,d.parent_id,d.position", nativeQuery = true)
    List<Dict> findAllByGroupType(@Param("gt") String groupType, @Param("t") String type);

}

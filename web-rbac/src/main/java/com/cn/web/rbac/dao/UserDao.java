package com.cn.web.rbac.dao;

import com.cn.web.rbac.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface UserDao extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user WHERE username=:username OR email=:email OR mobile=:mobile", nativeQuery = true)
    List<User> findUserByUsernameOrEmailOrMobile(@Param("username") String username, @Param("email") String email, @Param("mobile") String mobile);

    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    List<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE username=:email OR email=:email OR mobile=:email", nativeQuery = true)
    List<User> findByUsernameOrEmailOrMobile(@Param("email") String email);
}

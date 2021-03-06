package com.cn.web.rbac.service;

import com.cn.web.rbac.domain.User;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface UserService {
    User get(Serializable id);

    User save(User user);

    User update(User user);

    void delete(Serializable[] ids);

    void deleteByLogic(Serializable[] ids);

    List<User> list();

    Page<User> list(int page, int size);

    Page<User> search(String keywords, int page, int size);

    User login(String email, String password);

    User findByEmail(String email);

    User isExists(String username, String email, String mobile);

    boolean checkPassword(User user, String password);

    void updatePassword(User user);

    void updateUserLogin(User user);

}

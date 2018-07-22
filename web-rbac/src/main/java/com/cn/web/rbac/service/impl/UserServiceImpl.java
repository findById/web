package com.cn.web.rbac.service.impl;

import com.cn.web.rbac.dao.UserDao;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.User;
import com.cn.web.rbac.service.UserService;
import com.cn.web.rbac.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User get(Serializable id) {
        Optional<User> optional = userDao.findById(String.valueOf(id));
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public User save(User user) {
        encryptPassword(user);
        return userDao.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public void delete(Serializable[] ids) {
        if (ids == null || ids.length <= 0) {
            return;
        }
        for (Serializable id : ids) {
            userDao.deleteById(String.valueOf(id));
        }
    }

    @Override
    @Transactional
    public void deleteByLogic(Serializable[] ids) {
        if (ids == null || ids.length <= 0) {
            return;
        }
        for (Serializable id : ids) {
            Optional<User> optional = userDao.findById(String.valueOf(id));
            User user = optional.orElse(null);
            if (user != null) {
                user.setDelFlg(BaseEntity.FLAG_DELETE);
                userDao.save(user);
            }
        }
    }

    @Override
    public List<User> list() {
        return userDao.findAll();
    }

    @Override
    public Page<User> list(int page, int size) {
        return userDao.findAll(PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    public Page<User> search(String keywords, int page, int size) {
        User user = new User();
        user.setId(keywords);
        user.setUsername(keywords);
        user.setEmail(keywords);
        user.setMobile(keywords);
        user.setDepartment(keywords);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("id", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("mobile", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("department", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("password", "updateTime", "delFlg");
        Example<User> example = Example.of(user, matcher);
        return userDao.findAll(example, PageRequest.of(page, size, Sort.by("updateTime").descending()));
    }

    @Override
    public User login(String email, String password) {
        List<User> list = userDao.findByUsernameOrEmailOrMobile(email);
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (User user : list) {
            if (AuthUtils.verify(user.getPassword(), user.getSalt(), password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        List<User> list = userDao.findByEmail(email);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public User isExists(String username, String email, String mobile) {
        List<User> list = userDao.findUserByUsernameOrEmailOrMobile(username, email, mobile);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return AuthUtils.verify(user.getPassword(), user.getSalt(), password);
    }

    @Override
    public void updatePassword(User user) {
        encryptPassword(user);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void updateUserLogin(User user) {
        user.setLoginCount((user.getLoginCount() == null ? 0 : user.getLoginCount()) + 1);
        user.setLastVisit(System.currentTimeMillis());
        update(user);
    }

    private void encryptPassword(User user) {
        user.setSalt(AuthUtils.randomSalt());

        String password = user.getPassword();
        user.setPassword(AuthUtils.encode(password, user.getSalt()));
    }
}

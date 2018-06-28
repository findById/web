package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.User;
import com.cn.web.rbac.service.UserService;
import com.cn.web.rbac.web.request.UserLoginReq;
import com.cn.web.rbac.web.request.UserReq;
import com.cn.web.rbac.web.request.UserUpdatePasswdReq;
import com.cn.web.rbac.web.vo.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("userHandler")
public class UserHandler {

    @Autowired
    UserService userService;

    public UserBean save(UserReq req) {

        if (req == null) {
            throw new HandlerException(201, "Email and password must not be null.");
        }
        if (req.getEmail() == null) {
            throw new HandlerException(201, "Email must not be null.");
        }
        if (req.getPassword() == null) {
            throw new HandlerException(201, "Password must not be null.");
        }

        User temp = userService.findByEmail(req.getEmail());
        if (temp != null) {
            throw new HandlerException(201, "Email already exists.");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setGender(req.getGender());
        user.setAvatar(req.getAvatar());
        user.setDepartment(req.getDepartment());
        user.setDescription(req.getDescription());

        userService.save(user);

        UserBean bean = new UserBean();
        BeanUtils.copyProperties(user, bean);

        return bean;
    }

    public boolean update(UserReq req) {

        if (req == null || req.getId() == null) {
            throw new HandlerException(201, "user not exists");
        }

        User user = userService.get(req.getId());
        if (user == null) {
            throw new HandlerException(201, "user not exists");
        }
        if (req.getEmail() != null && !req.getEmail().isEmpty()) {
            user.setEmail(req.getEmail());
        }
        if (req.getMobile() != null && !req.getMobile().isEmpty()) {
            user.setMobile(req.getMobile());
        }
        if (req.getUsername() != null && !req.getUsername().isEmpty()) {
            user.setUsername(req.getUsername());
        }
        if (req.getGender() != null && req.getGender() > 0 && req.getGender() <= 2) {
            user.setGender(req.getGender());
        }
        if (req.getDepartment() != null && !req.getDepartment().isEmpty()) {
            user.setDepartment(req.getDepartment());
        }
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            user.setDescription(req.getDescription());
        }
        userService.update(user);

        return true;
    }

    public boolean delete(String ids) {

        userService.delete(ids);

        return true;
    }

    public HashMap<String, Object> list(int page, /* @Max(20) */ int size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 20) {
            size = 20;
        }

        Page<User> list = userService.list(page - 1, size);

        List<UserBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (User user : list.getContent()) {
                UserBean bean = new UserBean();
                bean.setId(user.getId());
                bean.setUsername(user.getUsername());
                bean.setMobile(user.getMobile());
                bean.setEmail(user.getEmail());
                bean.setUpdateTime(user.getUpdateTime());
                bean.setDescription(user.getDescription());
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

    public UserBean findById(String id) {

        User user = userService.get(id);
        if (user == null) {
            throw new HandlerException(201, "user not exists");
        }

        UserBean bean = new UserBean();
        bean.setId(user.getId());
        bean.setUsername(user.getUsername());
        bean.setMobile(user.getMobile());
        bean.setEmail(user.getEmail());
        bean.setUpdateTime(user.getUpdateTime());
        bean.setDescription(user.getDescription());

        return bean;
    }

    public UserBean login(UserLoginReq req) {
        if (req == null) {
            throw new HandlerException(201, "'loginName' and 'password' must not be null.");
        }
        if (req.getType() == 0 && (req.getLoginName() == null || req.getLoginName().isEmpty())) {
            throw new HandlerException(201, "'loginName' must not be null.");
        }
        if (req.getType() == 1 && (req.getUsername() == null || req.getUsername().isEmpty())) {
            throw new HandlerException(201, "'username' must not be null.");
        }
        if (req.getType() == 2 && (req.getEmail() == null || req.getEmail().isEmpty())) {
            throw new HandlerException(201, "'email' must not be null.");
        }
        if (req.getType() == 3 && (req.getMobile() == null || req.getMobile().isEmpty())) {
            throw new HandlerException(201, "'mobile' must not be null.");
        }

        String email = req.getEmail();
        if (email == null) {
            if (req.getUsername() != null) {
                email = req.getUsername();
            } else if (req.getMobile() != null) {
                email = req.getMobile();
            }
        }

        User user = userService.login(email, req.getPassword());
        if (user == null) {
            throw new HandlerException(201, "Authentication failure");
        }

        UserBean bean = new UserBean();
        BeanUtils.copyProperties(user, bean);

        // update login history
        userService.updateUserLogin(user);

        return bean;
    }

    public boolean updatePassword(String userId, UserUpdatePasswdReq req) {

        if (req == null || req.getUserId() == null) {
            throw new HandlerException(201, "user not exists");
        }

        if (!userId.equals(req.getUserId())) {
            throw new HandlerException(201, "owner operating only");
        }

        User user = userService.get(req.getUserId());
        if (user == null) {
            throw new HandlerException(201, "user not exists");
        }
        if (req.getOldPassword() == null || req.getOldPassword().isEmpty()) {
            throw new HandlerException(201, "'userId' must not be null");
        }
        if (req.getOldPassword() == null || req.getOldPassword().isEmpty()) {
            throw new HandlerException(201, "'oldPassword' must not be null");
        }
        if (req.getNewPassword() == null || req.getNewPassword().isEmpty()) {
            throw new HandlerException(201, "'newPassword' must not be null");
        }
        if (!userService.checkPassword(user, req.getOldPassword())) {
            throw new HandlerException(201, "Authentication failure");
        }
        userService.update(user);

        return true;
    }

}

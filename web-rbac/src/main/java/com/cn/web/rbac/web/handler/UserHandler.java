package com.cn.web.rbac.web.handler;

import com.cn.web.core.platform.exception.HandlerException;
import com.cn.web.rbac.domain.BaseEntity;
import com.cn.web.rbac.domain.User;
import com.cn.web.rbac.domain.UserRole;
import com.cn.web.rbac.service.UserRoleService;
import com.cn.web.rbac.service.UserService;
import com.cn.web.rbac.util.AuthUtils;
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

    @Autowired
    UserRoleService userRoleService;

    public HashMap<String, Object> search(String keywords, int page, int size) {

        Page<User> list = userService.search(keywords, page - 1, size);

        List<UserBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (User user : list.getContent()) {
                if (user.getDelFlg() != BaseEntity.FLAG_NORMAL && user.getDelFlg() != BaseEntity.FLAG_ENABLE) {
                    continue;
                }
                UserBean bean = new UserBean();
                BeanUtils.copyProperties(user, bean);
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

    public UserBean save(UserReq req) {
        if (req == null) {
            throw new HandlerException(201, "'email' must not be null");
        }
        if (req.getEmail() == null) {
            throw new HandlerException(201, "'email' must not be null");
        }
        if (req.getPassword() == null) {
            throw new HandlerException(201, "'password' must not be null");
        }

        User temp = userService.findByEmail(req.getEmail());
        if (temp != null) {
            throw new HandlerException(201, "'email' already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setPassword(req.getPassword());
        user.setDescription(req.getDescription());

        User u = userService.save(user);

        if (req.getRoleIds() != null && !req.getRoleIds().isEmpty()) {
            for (String roleId : req.getRoleIds()) {
                userRoleService.save(new UserRole(u.getId(), roleId, null));
            }
        }

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
        if (req.getDescription() != null && !req.getDescription().isEmpty()) {
            user.setDescription(req.getDescription());
        }
        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            user.setPassword(AuthUtils.encode(req.getPassword(), user.getSalt()));
        }

        if (req.getRoleIds() != null && !req.getRoleIds().isEmpty()) {
            for (String roleId : req.getRoleIds()) {
                System.out.println(roleId);
            }
        }

        userService.update(user);

        return true;
    }

    public boolean delete(String ids) {
        String[] array;
        if (ids.contains(",")) {
            array = ids.split(",");
        } else {
            array = new String[]{ids};
        }
        for (String id : array) {
            if ("1".equalsIgnoreCase(id)) {
                continue;
            }
            User user = userService.get(id);
            if (user != null) {
                userService.delete(id);
            }
        }
        return true;
    }

    public HashMap<String, Object> list(int page, int size) {
        if (page <= 0) {
            page = 1;
        }
        if (size > 50) {
            size = 50;
        }

        Page<User> list = userService.list(page - 1, size);

        List<UserBean> beanList = new ArrayList<>();
        if (list.hasContent()) {
            for (User user : list.getContent()) {
                UserBean bean = new UserBean();
                BeanUtils.copyProperties(user, bean);
                beanList.add(bean);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("page", page);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        return result;
    }

    public UserBean findById(String userId) {

        User user = userService.get(userId);
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
            throw new HandlerException(201, "Authentication failure");
        }
        if (req.getType() == 0 && (req.getLoginName() == null || req.getLoginName().isEmpty())) {
            throw new HandlerException(201, "'loginName' must not be null");
        }
        if (req.getType() == 1 && (req.getUsername() == null || req.getUsername().isEmpty())) {
            throw new HandlerException(201, "'username' must not be null");
        }
        if (req.getType() == 2 && (req.getEmail() == null || req.getEmail().isEmpty())) {
            throw new HandlerException(201, "'email' must not be null");
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
        if (req.getUserId() == null || req.getUserId().isEmpty()) {
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

        user.setPassword(req.getNewPassword());

        userService.updatePassword(user);

        return true;
    }

    public boolean resetPassword(String userId, String password) {
        if (userId == null || userId.isEmpty()) {
            throw new HandlerException(201, "'userId' must not be null");
        }
        if (password == null || password.isEmpty()) {
            throw new HandlerException(201, "'password' must not be null");
        }
        User user = userService.get(userId);
        if (user == null) {
            throw new HandlerException(201, "user not exists");
        }

        user.setPassword(password);

        userService.updatePassword(user);

        return true;
    }
}

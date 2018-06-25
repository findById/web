package com.cn.web.rbac.web.handler;

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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("userHandler")
public class UserHandler {

    @Autowired
    UserService userService;

    public String save(UserReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null) {
            builder.statusCode(201);
            builder.message("Email and password must not be null.");
            return builder.buildJSONString();
        }
        if (req.getEmail() == null) {
            builder.statusCode(201);
            builder.message("Email must not be null.");
            return builder.buildJSONString();
        }
        if (req.getPassword() == null) {
            builder.statusCode(201);
            builder.message("Password must not be null.");
            return builder.buildJSONString();
        }

        User temp = userService.findByEmail(req.getEmail());
        if (temp != null) {
            builder.statusCode(201);
            builder.message("Email already exists");
            return builder.buildJSONString();
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

        builder.statusCode(200);
        builder.message("success");
        builder.result(bean);
        return builder.buildJSONString();
    }

    public String update(UserReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getId() == null) {
            builder.statusCode(201);
            builder.message("user not exists");
            return builder.buildJSONString();
        }

        User user = userService.get(req.getId());
        if (user == null) {
            builder.statusCode(201);
            builder.message("user not exists");
            return builder.buildJSONString();
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

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        userService.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    public String list(int page, /* @Max(20) */ int size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
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

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);

        return builder.buildJSONString();
    }

    public String findById(String id) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        User user = userService.get(id);
        if (user == null) {
            builder.statusCode(201);
            builder.message("Not existed.");
            return builder.buildJSONString();
        }

        UserBean bean = new UserBean();
        bean.setId(user.getId());
        bean.setUsername(user.getUsername());
        bean.setMobile(user.getMobile());
        bean.setEmail(user.getEmail());
        bean.setUpdateTime(user.getUpdateTime());
        bean.setDescription(user.getDescription());

        builder.statusCode(200);
        builder.message("success");
        builder.result(bean);
        return builder.buildJSONString();
    }

    public String login(@RequestBody UserLoginReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {
            if (req == null) {
                builder.statusCode(201);
                builder.message("loginName and password must not be null.");
                return builder.buildJSONString();
            }
            if (req.getType() == 0 && (req.getLoginName() == null || req.getLoginName().isEmpty())) {
                builder.statusCode(201);
                builder.message("'loginName' must not be null.");
                return builder.buildJSONString();
            }
            if (req.getType() == 1 && (req.getUsername() == null || req.getUsername().isEmpty())) {
                builder.statusCode(201);
                builder.message("'username' must not be null.");
                return builder.buildJSONString();
            }
            if (req.getType() == 2 && (req.getEmail() == null || req.getEmail().isEmpty())) {
                builder.statusCode(201);
                builder.message("'email' must not be null.");
                return builder.buildJSONString();
            }
            if (req.getType() == 3 && (req.getMobile() == null || req.getMobile().isEmpty())) {
                builder.statusCode(201);
                builder.message("'mobile' must not be null.");
                return builder.buildJSONString();
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
                builder.statusCode(201);
                builder.message("Authentication failure");
                return builder.buildJSONString();
            }

            UserBean bean = new UserBean();
            BeanUtils.copyProperties(user, bean);

            // update login history
            userService.updateUserLogin(user);

            builder.statusCode(200);
            builder.message("success");
            builder.result(bean);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return builder.buildJSONString();
    }

    public String updatePassword(String userId, UserUpdatePasswdReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        if (req == null || req.getUserId() == null) {
            builder.statusCode(201);
            builder.message("user not exists");
            return builder.buildJSONString();
        }

        if (!userId.equals(req.getUserId())) {
            builder.statusCode(201);
            builder.message("Must be own operation");
            return builder.buildJSONString();
        }

        User user = userService.get(req.getUserId());
        if (user == null) {
            builder.statusCode(201);
            builder.message("user not exists");
            return builder.buildJSONString();
        }
        if (req.getOldPassword() == null || req.getOldPassword().isEmpty()) {
            builder.statusCode(201);
            builder.message("'userId' must not be null");
            return builder.buildJSONString();
        }
        if (req.getOldPassword() == null || req.getOldPassword().isEmpty()) {
            builder.statusCode(201);
            builder.message("'oldPassword' must not be null");
            return builder.buildJSONString();
        }
        if (req.getNewPassword() == null || req.getNewPassword().isEmpty()) {
            builder.statusCode(201);
            builder.message("'newPassword' must not be null");
            return builder.buildJSONString();
        }
        if (!userService.checkPassword(user, req.getOldPassword())) {
            builder.statusCode(201);
            builder.message("failure");
            return builder.buildJSONString();
        }
        userService.update(user);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

}

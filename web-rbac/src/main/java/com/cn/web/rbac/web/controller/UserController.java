package com.cn.web.rbac.web.controller;

import com.cn.web.core.platform.web.ResponseBuilder;
import com.cn.web.rbac.domain.User;
import com.cn.web.rbac.service.UserService;
import com.cn.web.rbac.util.PageUtils;
import com.cn.web.rbac.web.interceptor.PermissionRequired;
import com.cn.web.rbac.web.request.UserReq;
import com.cn.web.rbac.web.vo.UserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    UserService userService;

    @PermissionRequired(value = "sys:user:save")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public String save(@RequestBody UserReq req) {
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

    @PermissionRequired(value = "sys:user:update")
    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public String update(@RequestBody UserReq req) {
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

    @PermissionRequired(value = "sys:user:delete")
    @RequestMapping(value = "delete")
    public String delete(String ids) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        userService.delete(ids);

        builder.statusCode(200);
        builder.message("success");
        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "list")
    public String list(String page, String size) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();

        int[] temp = PageUtils.of(page, size);

        Page<User> list = userService.list(temp[0], temp[1]);

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
        result.put("page", temp[0]);
        result.put("total", list.getTotalElements());
        result.put("list", beanList);

        builder.statusCode(200);
        builder.message("success");
        builder.result(result);

        return builder.buildJSONString();
    }

    @PermissionRequired(value = "sys:user:view")
    @RequestMapping(value = "findById", method = {RequestMethod.GET})
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

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody UserReq req) {
        ResponseBuilder.Builder builder = ResponseBuilder.newBuilder();
        try {
            if (req == null) {
                builder.statusCode(201);
                builder.message("Email and password must not be null.");
                return builder.buildJSONString();
            }
            if (req.getEmail() == null && req.getMobile() == null && req.getUsername() == null) {
                builder.statusCode(201);
                builder.message("Email must not be null.");
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

            builder.statusCode(200);
            builder.message("success");
            builder.result(bean);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return builder.buildJSONString();
    }
}

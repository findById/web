package com.cn.web.rbac.web.controller;

import com.alibaba.fastjson.JSON;
import com.cn.web.rbac.domain.*;
import com.cn.web.rbac.service.*;
import com.cn.web.rbac.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "system")
public class InstallController {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserService userService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    UserRoleService userRoleService;

    @RequestMapping(value = {"install"})
    public String install() {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        try {
            List<Permission> temp = permissionService.list();
            if (temp != null && !temp.isEmpty()) {
                map.put("statusCode", 200);
                map.put("message", "installed");
                map.put("elapsedTime", System.currentTimeMillis() - beginTime);
                return JSON.toJSONString(map);
            }

            List<Permission> permissions = new ArrayList<>();
            // Default start
            // menu & permission
            Permission root = new Permission("ROOT", "menu", 0, null, null, null, "ROOT");
            permissionService.save(root);

            Permission sys = new Permission("System Manager", "menu", 0, null, null, root.getId(), "");
            permissionService.save(sys);

            Permission role = new Permission("Role Manager", "menu", 0, "role", null, sys.getId(), "");
            permissionService.save(role);

            permissions.add(new Permission("View", "permission", null, null, "sys:role:view", role.getId(), ""));
            permissions.add(new Permission("Add", "permission", null, "role/save", "sys:role:save", role.getId(), ""));
            permissions.add(new Permission("Edit", "permission", null, "role/update", "sys:role:update", role.getId(), ""));
            permissions.add(new Permission("Del", "permission", null, "role/delete", "sys:role:delete", role.getId(), ""));
            permissions.add(new Permission("Lock", "permission", null, "role/lock", "sys:role:lock", role.getId(), ""));
            permissions.add(new Permission("Reset", "permission", null, "role/reset", "sys:role:reset", role.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            Permission perm = new Permission("Permission Manager", "menu", 1, "permission", null, sys.getId(), "");
            permissionService.save(perm);

            permissions.add(new Permission("View", "permission", null, null, "sys:permission:view", perm.getId(), ""));
            permissions.add(new Permission("Add", "permission", null, "permission/save", "sys:permission:save", perm.getId(), ""));
            permissions.add(new Permission("Edit", "permission", null, "permission/update", "sys:permission:update", perm.getId(), ""));
            permissions.add(new Permission("Del", "permission", null, "permission/delete", "sys:permission:delete", perm.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            Permission user = new Permission("User Manager", "menu", 2, "user", null, sys.getId(), "");
            permissionService.save(user);

            permissions.add(new Permission("View", "permission", null, null, "sys:user:view", user.getId(), ""));
            permissions.add(new Permission("Add", "permission", null, "user/save", "sys:user:save", user.getId(), ""));
            permissions.add(new Permission("Edit", "permission", null, "user/update", "sys:user:update", user.getId(), ""));
            permissions.add(new Permission("Del", "permission", null, "user/delete", "sys:user:delete", user.getId(), ""));
            permissions.add(new Permission("EditRole", "permission", null, "user/roleUpdate", "sys:user:roleUpdate", user.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            Permission dict = new Permission("Dict Manager", "menu", 3, "dict", null, sys.getId(), "");
            permissionService.save(dict);

            permissions.add(new Permission("View", "permission", null, null, "sys:dict:view", dict.getId(), ""));
            permissions.add(new Permission("Add", "permission", null, "dict/save", "sys:dict:save", dict.getId(), ""));
            permissions.add(new Permission("Edit", "permission", null, "dict/update", "sys:dict:update", dict.getId(), ""));
            permissions.add(new Permission("Del", "permission", null, "dict/delete", "sys:dict:delete", dict.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            Permission monitor = new Permission("Monitor", "menu", 1, null, null, root.getId(), "");
            permissionService.save(monitor);

            Permission log = new Permission("Log", "menu", 0, "log", null, monitor.getId(), "");
            permissionService.save(log);

            permissions.add(new Permission("View", "permission", null, null, "monitor:log:view", log.getId(), ""));
            permissions.add(new Permission("Search", "permission", null, "log/search", "monitor:log:search", log.getId(), ""));
            permissions.add(new Permission("Export", "permission", null, "log/export", "monitor:log:export", log.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            Permission task = new Permission("Schedule Job", "menu", 1, "task", null, monitor.getId(), "");
            permissionService.save(task);

            permissions.add(new Permission("View", "permission", null, null, "monitor:task:view", task.getId(), ""));
            permissions.add(new Permission("Add", "permission", null, "task/save", "monitor:task:save", task.getId(), ""));
            permissions.add(new Permission("Edit", "permission", null, "task/update", "monitor:task:update", task.getId(), ""));
            permissions.add(new Permission("Del", "permission", null, "task/delete", "monitor:task:delete", task.getId(), ""));
            permissions.add(new Permission("Pause", "permission", null, "task/pause", "monitor:task:pause", task.getId(), ""));
            permissions.add(new Permission("Resume", "permission", null, "task/resume", "monitor:task:resume", task.getId(), ""));
            permissions.add(new Permission("Start", "permission", null, "task/start", "monitor:task:start", task.getId(), ""));
            permissionService.saveAll(permissions);
            permissions.clear();

            // admin role & permissions
            Role adminRole = new Role("admin", "admin", "", null, "admin");
            roleService.save(adminRole);

            List<Permission> list = permissionService.list();
            if (list == null || list.size() <= 0) {
                throw new RuntimeException("Unknown permissions");
            }
            for (Permission permission : list) {
                rolePermissionService.save(new RolePermission(adminRole.getId(), permission.getId(), ""));
            }

            String password = AuthUtils.randomPassword(6);

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@admin.com");
            adminUser.setPassword(password);
            userService.save(adminUser);

            userRoleService.save(new UserRole(adminUser.getId(), adminRole.getId(), ""));
            // Default end

            map.put("elapsedTime", System.currentTimeMillis() - beginTime);
            map.put("statusCode", 200);
            map.put("message", "ok");
            map.put("password", password);
        } catch (Throwable e) {
            e.printStackTrace();
            map.put("elapsedTime", System.currentTimeMillis() - beginTime);
            map.put("statusCode", 201);
            map.put("message", e.getMessage());
        }
        return JSON.toJSONString(map);
    }
}

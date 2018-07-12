package org.cn.web.admin.web.controller;

import com.cn.web.core.platform.web.DefaultController;
import org.cn.web.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dashboard")
public class AdminController extends DefaultController {

    @Autowired
    AdminService adminService;

}

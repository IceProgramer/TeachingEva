package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.R;
import com.itmo.teachingeva.entity.Admin;
import com.itmo.teachingeva.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 负责管理员登陆
 *
 * @author chenjiahan
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 登录测试
     *
     * @param admin 管理员信息
     * @return 返回token
     */
    @PostMapping("/login")
    public R<String> userLogin(@RequestBody Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        return adminService.doLogin(username, password);
    }
    /**
     * 获取登录用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("getUser")
    public Admin gerUser(HttpServletRequest request) {
        Admin admin = (Admin) request.getAttribute("user");
        return admin;
    }

}

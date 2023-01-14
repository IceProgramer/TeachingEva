package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.entity.Admin;
import com.itmo.teachingeva.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


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
    public BaseResponse<String> userLogin(@RequestBody Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();

        return adminService.doLogin(username, password);
    }

    /**
     * 获取登录用户信息
     *
     * @param token 前端返回的token值
     * @return 脱敏后的用户信息
     */
    @GetMapping("getUser")
    public BaseResponse<Admin> gerUser(@RequestHeader("access-token") String token) {
        return adminService.getUser(token);
    }

}

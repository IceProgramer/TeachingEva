package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.AdminDto;
import com.itmo.teachingeva.domain.Admin;
import com.itmo.teachingeva.exceptions.BusinessException;
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
        log.info("插入成功");
        String token = adminService.doLogin(username, password);
        return ResultUtils.success(token);
    }

    /**
     * 获取登录用户信息
     *
     * @param token 前端返回的token值
     * @return 脱敏后的用户信息
     */
    @GetMapping("getUser")
    public BaseResponse<AdminDto> gerUser(@RequestHeader("access-token") String token) {
        log.info("获取用户信息成功");
        AdminDto adminInfo = adminService.getUser(token);

        if (adminInfo ==  null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIT);
        }

        return ResultUtils.success(adminInfo);
    }

}

package com.itmo.teachingeva.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.entity.Admin;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.AdminService;
import com.itmo.teachingeva.mapper.AdminMapper;
import com.itmo.teachingeva.utilts.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;



/**
* @author chenjiahan
* @description 针对表【e_admin】的数据库操作Service实现
* @createDate 2023-01-13 15:34:56
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    @Resource
    private AdminMapper adminMapper;

    /**
     * 登陆处理
     *
     * @param username 用户名
     * @param password 密码
     * @return 登陆结果和token
     */
    @Override
    public BaseResponse<String> doLogin(String username, String password) {
        // 1. 对账号密码进行校验
        // 账号密码不为空
        if (StringUtils.isAnyEmpty(username, password)) {
            throw new BusinessException(ErrorCode.ACCOUNT_EMPTY);
        }

        // 2.查询账号密码
        Admin admin = adminMapper.getByUsername(username);

        // 判断账户是否存在
        if (admin == null) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIT);
        }

        // 判断密码是否正确
        if (!admin.getPassword().equals(password)) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        //判断是否拥有权限
        if (admin.getRole() != 1) {
            throw new BusinessException(ErrorCode.NO_PERMISSION);
        }

        // 3. 返回token和登陆结果
        HashMap<String, String> jwt = new HashMap<>();
        jwt.put("username", username);
        jwt.put("id", admin.getId().toString());
        String token = JwtUtil.generateToken(jwt);

        return ResultUtils.success(token);

    }

    /**
     * 获取当前登陆的用户信息
     *
     * @param token 前端返回的token值
     * @return
     */
    @Override
    public BaseResponse<Admin> getUser(String token) {
        // 对传回来的token进行解析 -> 解析出token中对应用户的id
        DecodedJWT decodedJWT = JwtUtil.decodeToken(token);
        Integer id = Integer.valueOf(decodedJWT.getClaim("id").asString());

        // 根据解析出来的id来查找相对应的用户
        Admin admin = this.baseMapper.selectById(id);

        // 校验用户是否存在
        if (admin == null) {
            // 如果不存在，抛出业务异常
            throw new BusinessException(ErrorCode.USER_NOT_EXIT);
        }

        // 用户脱敏
        Admin safetyAdmin = new Admin();
        safetyAdmin.setId(admin.getId());
        safetyAdmin.setUsername(admin.getUsername());
        safetyAdmin.setName(admin.getName());

        return ResultUtils.success(safetyAdmin);
    }



}





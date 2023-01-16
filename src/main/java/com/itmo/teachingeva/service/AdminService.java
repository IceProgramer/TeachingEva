package com.itmo.teachingeva.service;

import com.itmo.teachingeva.dto.AdminDto;
import com.itmo.teachingeva.domain.Admin;
import com.baomidou.mybatisplus.extension.service.IService;



/**
* @author chenjiahan
* @description 针对表【e_admin】的数据库操作Service
* @createDate 2023-01-13 15:34:56
*/
public interface AdminService extends IService<Admin> {

    //todo 代码优化 将所有service层中的BaseResponse换掉
    /**
     * 登陆验证
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    String doLogin(String username, String password);

    /**
     * 获取当前登陆用户信息
     *
     * @param token 前端返回的token值
     * @return
     */
    AdminDto getUser(String token);

}

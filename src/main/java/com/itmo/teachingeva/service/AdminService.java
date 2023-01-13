package com.itmo.teachingeva.service;

import com.itmo.teachingeva.common.R;
import com.itmo.teachingeva.dto.AdminDto;
import com.itmo.teachingeva.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chenjiahan
* @description 针对表【e_admin】的数据库操作Service
* @createDate 2023-01-13 15:34:56
*/
public interface AdminService extends IService<Admin> {

    /**
     * 登陆验证
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    R<String> doLogin(String username, String password);


}

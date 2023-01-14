package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_admin】的数据库操作Mapper
* @createDate 2023-01-13 15:34:56
* @Entity com.itmo.teachingeva.entity.Admin
*/
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据用户名查找用户信息
     * @param username 账号
     * @return 对应用户信息
     */
    @Select("select * from e_admin where username = #{username}")
    Admin getByUsername(String username);


}





package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author chenjiahan
* @description 针对表【e_teacher】的数据库操作Mapper
* @createDate 2023-01-15 20:17:42
* @Entity Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("select * from e_teacher where name = #{name} and email = #{email}")
    Teacher queryTeacherByNameAndEmail(String name, String email);

}





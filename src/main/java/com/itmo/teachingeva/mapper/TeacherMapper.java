package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author chenjiahan
* @description 针对表【e_teacher】的数据库操作Mapper
* @createDate 2023-01-15 20:17:42
* @Entity Teacher
*/
public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 根据名称和邮件来查询教师
     * @param name 姓名
     * @param email 邮箱
     * @return 教师信息
     */
    @Select("select * from e_teacher where name = #{name} and email = #{email}")
    Teacher queryTeacherByNameAndEmail(String name, String email);

    /**
     * 查询教师
     * @return 所有教师【列表】
     */
    @Select("select * from e_teacher")
    List<Teacher> queryTeacherName();

    /**
     * 根据姓名查询id
     * @param name 姓名
     * @return id
     */
    @Select("select id from e_teacher where name = #{name}")
    Integer queryTeacherId(String name);

    /**
     * 根据id查询姓名
     * @param id id
     * @return 教师姓名
     */
    @Select("select name from e_teacher where id = #{id}")
    String queryTeacherNameById(Integer id);

}





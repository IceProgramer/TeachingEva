package com.itmo.teachingeva.intercepts.mapper;

import com.itmo.teachingeva.domain.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_student】的数据库操作Mapper
* @createDate 2023-01-14 14:40:04
* @Entity com.itmo.teachingeva.domain.Student
*/
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 查询所有学生（在校）
     * @return 所有学生
     */
    @Select("select * from e_student where grade <= 8")
    List<Student> getAllStudents();

    /**
     * 根据学号来查找学生
     * @param sid 学号
     * @return 学生信息
     */
    @Select("select * from e_student where sid = #{sid}")
    Student getStudentBySid(String sid);




}




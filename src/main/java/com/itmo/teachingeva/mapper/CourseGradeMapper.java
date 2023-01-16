package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.CourseGrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_course_to_grade】的数据库操作Mapper
* @createDate 2023-01-16 22:44:36
* @Entity com.itmo.teachingeva.domain.CourseGrade
*/
public interface CourseGradeMapper extends BaseMapper<CourseGrade> {

    /**
     * 列出所有相关联系
     */
    @Select("select * from e_course_to_grade")
    List<CourseGrade> listAllCourseToGrade();

    /**
     * 查找对应的课程
     */


}





package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_course】的数据库操作Mapper
* @createDate 2023-01-16 17:41:54
* @Entity Course
*/
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询课程信息  【名称和教师id查询】
     */
    @Select("select * from e_course where c_name =#{name} and tid = #{tid}")
    Course queryCourseByNameAndTid(String name, Integer tid);

    /**
     * 根据主键id来查找课程名称
     */
    @Select("select c_name from e_course where id = #{id}")
    String queryCourseName(Integer id);

    /**
     * 根据课程名称来找到所有的老师(List)
     */
    @Select("select tid from e_course where c_name = #{name};")
    List<Integer> queryTeacherByName(String name);

    /**
     * 查找所有课程信息
     */
    @Select("Select * from e_course")
    List<Course> queryAllCourse();
}





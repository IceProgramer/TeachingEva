package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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

}





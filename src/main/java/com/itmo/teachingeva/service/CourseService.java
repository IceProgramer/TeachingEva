package com.itmo.teachingeva.service;

import com.itmo.teachingeva.domain.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.teachingeva.dto.CourseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_course】的数据库操作Service
* @createDate 2023-01-16 17:41:54
*/
public interface CourseService extends IService<Course> {
    /**
     * 查询所有课程信息
     *
     * @return 所有课程信息
     */
    List<CourseDto> listAllCourses();

    /**
     * 添加课程信息 （单个）
     *
     * @param courseDto 课程添加信息
     * @return 是否添加成功
     */
    Boolean addCourse(CourseDto courseDto);

    /**
     * 删除课程数据
     * @param courseDto 课程的id
     * @return 删除成功
     */
    Boolean deleteCourse(CourseDto courseDto);

    /**
     * 更新课程数据
     * @param courseDto 课程信息
     * @return 更新成功
     */
    Boolean updateCourse(CourseDto courseDto);

    /**
     * 获取单个课程信息
     *
     * @param id 课程的id
     * @return 课程信息
     */
    CourseDto getCourse(Integer id);


    /**
     * Excel文件批量上传教师信息
     * @param file excel文件
     * @return 保存成功
     */
    Boolean excelImport(MultipartFile file);

}

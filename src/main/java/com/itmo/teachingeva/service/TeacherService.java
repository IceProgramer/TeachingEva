package com.itmo.teachingeva.service;

import com.itmo.teachingeva.domain.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.teachingeva.dto.TeacherDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_teacher】的数据库操作Service
* @createDate 2023-01-15 20:17:42
*/
public interface TeacherService extends IService<Teacher> {

    /**
     * 查询所有教师信息
     *
     * @return 所有教师信息
     */
    List<TeacherDto> listAllTeachers();

    /**
     * 添加教师信息 （单个）
     *
     * @param teacherDto 教师添加信息
     * @return 是否添加成功
     */
    Boolean addTeacher(TeacherDto teacherDto);

    /**
     * 删除教师数据
     * @param teacherDto 教师的id
     * @return 删除成功
     */
    Boolean deleteTeacher(TeacherDto teacherDto);

    /**
     * 更新教师数据
     * @param teacherDto 教师信息
     * @return 更新成功
     */
    Boolean updateTeacher(TeacherDto teacherDto);

    /**
     * 获取单个教师信息
     *
     * @param id 教师的id
     * @return 教师信息
     */
    TeacherDto getTeacher(Integer id);


    /**
     * Excel文件批量上传教师信息
     * @param file excel文件
     * @return 保存成功
     */
    Boolean excelImport(MultipartFile file);

}

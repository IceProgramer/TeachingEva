package com.itmo.teachingeva.service;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_student】的数据库操作Service
* @createDate 2023-01-14 14:40:04
*/
public interface StudentService extends IService<Student> {

    /**
     * 查询所有学生信息
     *
     * @return 所有学生信息
     */
    BaseResponse<List<StudentDto>> listAllStudents();

    /**
     * 添加学生信息 （单个）
     *
     * @return 是否添加成功
     */
    BaseResponse<Boolean> addNewStudents(Student student);

    BaseResponse<Boolean> deleteStudent(Student student);
}

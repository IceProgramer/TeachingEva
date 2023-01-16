package com.itmo.teachingeva.service;

import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

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
    List<StudentDto> listAllStudents();

    /**
     * 添加学生信息 （单个）
     *
     * @return 是否添加成功
     */
    Boolean addStudents(StudentDto studentDto);

    /**
     * 删除学生数据
     *
     * @param studentDto 学生的id
     * @return 删除成功
     */
    Boolean deleteStudent(StudentDto studentDto);

    /**
     * 更新学生数据
     *
     * @param studentDto 学生信息
     * @return 更新成功
     */
    Boolean updateStudent(StudentDto studentDto);

    /**
     * 获取单个学生信息
     *
     * @param id 学生的id
     * @return 学生信息
     */
    StudentDto getStudent(Integer id);


    /**
     * Excel文件批量上传学生信息
     * @param file excel文件
     * @return 保存成功
     */
    Boolean excelImport(MultipartFile file);

}

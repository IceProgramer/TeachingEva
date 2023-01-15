package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.entity.Student;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.mapper.StudentClassMapper;
import com.itmo.teachingeva.service.StudentService;
import com.itmo.teachingeva.mapper.StudentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_student】的数据库操作Service实现
* @createDate 2023-01-14 14:40:03
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private StudentClassMapper studentClassMapper;

    /**
     * 返回所有学生信息
     *
     * @return 学生信息
     */
    @Override
    public BaseResponse<List<StudentDto>> listAllStudents() {
        // 只有学期数小于8的学生才能被查询
        List<Student> allStudents = studentMapper.getAllStudents();
        if (allStudents == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请联系开发人员");
        }

        // 将do数据转到dto
        List<StudentDto> studentDtoList = new ArrayList<>();
        for (Student student : allStudents) {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(student.getId());
            studentDto.setSid(student.getSid());
            studentDto.setName(student.getName());
            studentDto.setSex(student.getSex());
            studentDto.setAge(student.getAge());
            studentDto.setMajor(student.getMajor());
            studentDto.setCid(studentClassMapper.queryClass(student.getCid()));
            studentDtoList.add(studentDto);
        }

        return ResultUtils.success(studentDtoList);
    }

    /**
     * 添加学生信息（单个）
     * @return 添加成功
     */
    @Override
    public BaseResponse<Boolean> addStudents(StudentDto studentDto) {
        // 1.对student进行校验
        // a.student不为空
        if (studentDto == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "插入信息有误");
        }
        // b.学生的年级不大于8
        if (studentDto.getGrade() > 8) {
            throw new BusinessException(ErrorCode.STUDENT_GRADUATE, "请检查输入信息");
        }
         // c.查找该学生是否已存在
        Student studentBySid = studentMapper.getStudentBySid(studentDto.getSid());
        if (studentBySid != null) {
            throw new BusinessException(ErrorCode.STUDENT_EXIT, "请重新输入");
        }

        // 2.将学生的密码设为账号
        String password = studentDto.getSid();
        String cid = studentDto.getCid();

        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        //密码加密
        student.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        student.setCid(studentClassMapper.queryClassId(cid));
        // 插入数据
        boolean save = this.save(student);

        return ResultUtils.success(save);
    }

    /**
     * 添加学生信息 （单个）
     *
     * @return 是否添加成功
     */
    @Override
    public BaseResponse<Boolean> deleteStudent(StudentDto studentDto) {
        Integer id = studentDto.getId();

        boolean isDelete = this.removeById(id);

        return ResultUtils.success(isDelete);
    }

    /**
     * 删除学生数据
     * @param studentDto 学生的id
     * @return 删除成功
     */
    @Override
    public BaseResponse<Boolean> updateStudent(StudentDto studentDto) {
        Student student = new Student();

        BeanUtils.copyProperties(studentDto, student);

        this.updateById(student);

        return ResultUtils.success(true);
    }

    /**
     * 获取单个学生信息
     *
     * @param studentDto 学生的id
     * @return 学生信息
     */
    @Override
    public StudentDto getStudent(StudentDto studentDto) {
        // 校验学生信息
        Integer id = studentDto.getId();
        if (id == null) {
            // 学生是否存在
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请检查id");
        }

        Student student = this.getById(id);

        StudentDto studentInfo = new StudentDto();
        BeanUtils.copyProperties(student, studentInfo);

        return studentInfo;
    }


}





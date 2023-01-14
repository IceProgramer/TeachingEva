package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.entity.Student;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.StudentService;
import com.itmo.teachingeva.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 返回所有学生信息
     *
     * @return 学生信息
     */
    @Override
    public BaseResponse<List<Student>> listAllStudents() {
        // 只有学期数小于8的学生才能被查询
        List<Student> allStudents = studentMapper.getAllStudents();
        if (allStudents == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请联系开发人员");
        }
        allStudents.stream().map(student -> {
            student.setPassword(null);
            return student;
        }).collect(Collectors.toList());

        return ResultUtils.success(allStudents);
    }

    /**
     * 添加学生信息（单个）
     * @return
     */
    @Override
    public BaseResponse<Boolean> addNewStudents(Student student) {
        // 1.对student进行校验
        // a.student不为空
        if (student == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "插入信息有误");
        }
        // b.学生的年级不大于8
        if (student.getGrade() > 8) {
            throw new BusinessException(ErrorCode.STUDENT_GRADUATE, "请检查输入信息");
        }
        // c.查找该学生是否已存在
        Student studentBySid = studentMapper.getStudentBySid(student.getSid());
        if (studentBySid != null) {
            throw new BusinessException(ErrorCode.STUDENT_EXIT, "请重新输入");
        }

        // 2.将学生的密码设为账号
        String password = student.getSid();
        // 信息脱敏
        Student safetyStudent = new Student();
        safetyStudent.setSid(student.getSid());
        safetyStudent.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));
        safetyStudent.setName(student.getName());
        safetyStudent.setSex(student.getSex());
        safetyStudent.setAge(student.getAge());
        safetyStudent.setMajor(student.getMajor());
        safetyStudent.setCid(student.getCid());
        safetyStudent.setGrade(student.getGrade());

        // 插入数据
        boolean save = this.save(safetyStudent);

        return ResultUtils.success(save);
    }


}





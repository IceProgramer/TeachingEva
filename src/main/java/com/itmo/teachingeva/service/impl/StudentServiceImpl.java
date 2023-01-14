package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.entity.Student;
import com.itmo.teachingeva.service.StudentService;
import com.itmo.teachingeva.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    /**
     * 返回所有学生信息
     *
     * @return 学生信息
     */
    @Override
    public BaseResponse<List<Student>> listAllStudents() {
        // 只有学期数小于8的学生才能被查询
        List<Student> allStudents = studentMapper.getAllStudents();

        return ResultUtils.success(allStudents);
    }
}





package com.itmo.teachingeva.controller;


import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.entity.Student;
import com.itmo.teachingeva.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员对学生信息进行管理
 *
 * @author chenjiahan
 */
@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 获取所有学生信息
     */
    @GetMapping("/page")
    public BaseResponse<List<Student>> getAllStudents() {
        return studentService.listAllStudents();
    }

}

package com.itmo.teachingeva.controller;


import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.entity.Student;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
     * 展示所有学生信息【分页由前端完成】
     * @return 所有学生信息
     */
    @GetMapping("/page")
    public BaseResponse<List<Student>> getAllStudents() {
        // 将年级不大于8的数据全部传输到前台
        log.info("查询数据中...");
        return studentService.listAllStudents();
    }


    /**
     * 添加学生信息
     * @return 新添加的学生信息
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addNewStudents(@RequestBody Student student) {
        boolean isSave = studentService.addNewStudents(student).getData();

        if (!isSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据失败");
        }
        log.info("数据插入成功");
        return ResultUtils.success(true);
    }



}

package com.itmo.teachingeva.controller;


import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.StudentDto;
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
    public BaseResponse<List<StudentDto>> getAllStudents() {
        // 将年级不大于8的数据全部传输到前台
        log.info("查询数据中...");
        return studentService.listAllStudents();
    }


    /**
     * 添加学生信息
     * @return 新添加的学生信息
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addStudent(@RequestBody StudentDto studentDto) {
        boolean isSave = studentService.addStudents(studentDto).getData();

        if (!isSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据失败");
        }

        log.info("数据插入成功");
        return ResultUtils.success(true);
    }

    /**
     * 删除单条学生数据
     * @param studentDto 学生数据【只传id】
     * @return 删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteStudent(@RequestBody StudentDto studentDto) {
        boolean isDelete = studentService.deleteStudent(studentDto).getData();

        if (!isDelete) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除数据失败");
        }
        log.info("删除数据成功");
        return ResultUtils.success(true);
    }

    /**
     * 更新单条学生数据
     * @param studentDto 学生数据
     * @return 更新成功
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> updateStudent(@RequestBody StudentDto studentDto) {
        boolean isUpdate = studentService.updateStudent(studentDto).getData();

        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新数据失败");
        }

        log.info("更新数据成功");
        return ResultUtils.success(true);
    }

    @GetMapping("/info")
    public BaseResponse<StudentDto> getStudent(@RequestBody StudentDto studentDto) {
        StudentDto studentInfo = studentService.getStudent(studentDto);

        if (studentInfo == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取失败");
        }
        log.info("获取学生信息成功");
        return ResultUtils.success(studentInfo);
    }



}

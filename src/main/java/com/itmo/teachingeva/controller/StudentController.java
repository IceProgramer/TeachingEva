package com.itmo.teachingeva.controller;


import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.StudentDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

        List<StudentDto> studentDtos = studentService.listAllStudents();

        if (studentDtos == null) {
            throw new BusinessException(ErrorCode.STUDENT_EMPTY, "请检查库表");
        }

        return ResultUtils.success(studentDtos);
    }


    /**
     * 添加学生信息
     * @return 新添加的学生信息
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addStudent(@RequestBody StudentDto studentDto) {
        boolean isSave = studentService.addStudents(studentDto);

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
        boolean isDelete = studentService.deleteStudent(studentDto);

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
        boolean isUpdate = studentService.updateStudent(studentDto);

        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新数据失败");
        }

        log.info("更新数据成功");
        return ResultUtils.success(true);
    }

    /**
     * 获取单个学生信息
     * @param studentDto 学生id
     * @return 学生信息
     */
    @GetMapping("/info")
    public BaseResponse<StudentDto> getStudent(@RequestBody StudentDto studentDto) {
        Integer id = studentDto.getId();

        if (id == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "id值为空！");
        }

        StudentDto studentInfo = studentService.getStudent(id);

        if (studentInfo == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取失败");
        }
        log.info("获取学生信息成功");
        return ResultUtils.success(studentInfo);
    }

    /**
     * Excel文件批量上传学生信息
     * @param file
     * @return
     */
    @PostMapping("/excel/import")
    public BaseResponse<Boolean> saveExcel(@RequestParam("file") MultipartFile file) {

        Boolean isImport = studentService.excelImport(file);

        if (!isImport) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存文件失败！");
        }

        return ResultUtils.success(true);
    }


    @GetMapping("/excel/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response) {

        //2.建立Excel对象，封装数据
        response.setCharacterEncoding("UTF-8");
        //2.1创建Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();
        //2.3创建sheet对象
        XSSFSheet sheet = wb.createSheet("学生信息表");
        //2.3创建表头
        XSSFRow xssfRow = sheet.createRow(0);
        xssfRow.createCell(0).setCellValue("学生名称");
        xssfRow.createCell(1).setCellValue("学号");
        xssfRow.createCell(2).setCellValue("性别");
        xssfRow.createCell(3).setCellValue("年龄");
        xssfRow.createCell(4).setCellValue("专业（0为计算机，1为计算机）");
        xssfRow.createCell(5).setCellValue("班级号");
        xssfRow.createCell(6).setCellValue("学期（1-8）");

        return ResultUtils.success(true);
    }

    //todo 完成随着时间增加学生年级也会增加

}

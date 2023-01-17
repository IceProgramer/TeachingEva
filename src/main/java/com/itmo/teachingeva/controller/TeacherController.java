package com.itmo.teachingeva.controller;


import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.TeacherDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    /**
     * 展示所有老师信息
     * @return 所有老师
     */
    @GetMapping("/page")
    public BaseResponse<List<TeacherDto>> getAllTeachers() {
        log.info("查询所有教师信息中...");

        List<TeacherDto> allTeachersInfo = teacherService.listAllTeachers();

        if (allTeachersInfo == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "无教师信息");
        }

        return ResultUtils.success(allTeachersInfo);
    }

    /**
     * 新增教师信息
     * @param teacherDto 教师信息
     * @return 新增成功
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addTeacher(@RequestBody TeacherDto teacherDto) {
        boolean isSave = teacherService.addTeacher(teacherDto);

        if (!isSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入信息失败");
        }

        log.info("添加信息成功！");
        return ResultUtils.success(true);

    }

    /**
     * 删除单条学生信息
     * @param teacherDto 学生id
     * @return 删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeacher(@RequestBody TeacherDto teacherDto) {

        if (teacherDto.getId() == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有教师信息");
        }

        boolean isDelete = teacherService.deleteTeacher(teacherDto);

        if (!isDelete) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有该教师信息");
        }

        log.info("删除数据成功");
        return ResultUtils.success(true);
    }

    /**
     * 更新教师信息
     * @param teacherDto 教师信息
     * @return 更新成功
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> updateTeacher(@RequestBody TeacherDto teacherDto) {
        boolean isUpdate = teacherService.updateTeacher(teacherDto);

        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }

        log.info("更新数据成功！");
        return ResultUtils.success(true);
    }

    /**
     * 获取单条教师信息
     * @param teacherDto 教师id
     * @return 教师信息
     */
    @GetMapping("/info")
    public BaseResponse<TeacherDto> getTeacher(@RequestBody TeacherDto teacherDto) {
        Integer id = teacherDto.getId();

        if (id == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "id值为空");
        }

        TeacherDto teacherInfo = teacherService.getTeacher(id);

        if (teacherInfo == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "该教师不存在");
        }

        log.info("获取教师信息成功");
        return ResultUtils.success(teacherInfo);
    }

    /**
     * Excel文件批量上传教师信息
     * @param file excel
     * @return 保存成功
     */
    @PostMapping("/excel/import")
    public BaseResponse<Boolean> saveExcel(@RequestParam("file") MultipartFile file) {

        Boolean isImport = teacherService.excelImport(file);

        if (!isImport) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存文件失败！");
        }

        return ResultUtils.success(true);
    }

    /**
     * 下载示例Excel
     * @param response 请求
     * @return 示例
     */
    @GetMapping("/excel/export")
    public BaseResponse<Boolean> exportExcel(HttpServletResponse response) {

        //2.建立Excel对象，封装数据
        response.setCharacterEncoding("UTF-8");
        //2.1创建Excel对象
        XSSFWorkbook wb = new XSSFWorkbook();
        //2.3创建sheet对象
        XSSFSheet sheet = wb.createSheet("教师信息表");
        //2.3创建表头
        XSSFRow xssfRow = sheet.createRow(0);
        xssfRow.createCell(0).setCellValue("教师名称");
        xssfRow.createCell(1).setCellValue("性别");
        xssfRow.createCell(2).setCellValue("年龄");
        xssfRow.createCell(3).setCellValue("职位");
        xssfRow.createCell(4).setCellValue("职称");
        xssfRow.createCell(5).setCellValue("专业（0为计算机，1为自动化）");
        xssfRow.createCell(6).setCellValue("邮箱");
        xssfRow.createCell(7).setCellValue("国籍（0为俄方，1为中方）");

        return ResultUtils.success(true);
    }

    /**
     * 获取中方所有教师信息
     * @return 中方教师信息
     */
    @GetMapping("/info/china")
    public BaseResponse<List<TeacherDto>> getAllChinaTeacher() {
        List<TeacherDto> ChinaTeacherInfo = teacherService.getChinaTeacher();

        if (ChinaTeacherInfo == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有查询到中方教师！");
        }
        return ResultUtils.success(ChinaTeacherInfo);
    }

    /**
     * 获取所有俄方教师信息
     * @return 俄方教师信息
     */
    @GetMapping("/info/russian")
    public BaseResponse<List<TeacherDto>> getAllRussianTeacher() {
        List<TeacherDto> RussianTeacherInfo = teacherService.getRussianTeacher();

        if (RussianTeacherInfo == null) {
            throw new BusinessException(ErrorCode.TEACHER_EMPTY, "没有查询到俄方教师！");
        }
        return ResultUtils.success(RussianTeacherInfo);
    }



}

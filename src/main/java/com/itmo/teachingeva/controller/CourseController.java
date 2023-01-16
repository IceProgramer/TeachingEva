package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.CourseDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.CourseService;
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
 * 课程管理
 *
 * @author chenjiahan
 */
@RestController
@Slf4j
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 展示所有课程信息
     * @return 所有课程
     */
    @GetMapping("/page")
    public BaseResponse<List<CourseDto>> getAllCourses() {
        log.info("查询所有课程信息中...");

        List<CourseDto> allCoursesInfo = courseService.listAllCourses();

        if (allCoursesInfo == null) {
            throw new BusinessException(ErrorCode.COURSE_EMPTY, "请联系开发人员");
        }

        return ResultUtils.success(allCoursesInfo);
    }

    /**
     * 新增课程信息
     * @param courseDto 课程信息
     * @return 新增成功
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addCourse(@RequestBody CourseDto courseDto) {
        boolean isSave = courseService.addCourse(courseDto);

        if (!isSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入信息失败");
        }

        log.info("添加信息成功！");
        return ResultUtils.success(true);

    }

    /**
     * 删除单条课程信息
     * @param courseDto 课程id
     * @return 删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCourse(@RequestBody CourseDto courseDto) {

        if (courseDto.getId() == null) {
            throw new BusinessException(ErrorCode.COURSE_NO_EXIT, "没有课程信息");
        }

        boolean isDelete = courseService.deleteCourse(courseDto);

        if (!isDelete) {
            throw new BusinessException(ErrorCode.COURSE_NO_EXIT, "没有该课程信息");
        }

        log.info("删除数据成功");
        return ResultUtils.success(true);
    }

    /**
     * 更新课程信息
     * @param courseDto 课程信息
     * @return 更新成功
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> updateCourse(@RequestBody CourseDto courseDto) {
        boolean isUpdate = courseService.updateCourse(courseDto);

        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }

        log.info("更新数据成功！");
        return ResultUtils.success(true);
    }

    /**
     * 获取单条课程信息
     * @param courseDto 课程id
     * @return 课程信息
     */
    @GetMapping("/info")
    public BaseResponse<CourseDto> getTeacher(@RequestBody CourseDto courseDto) {
        Integer id = courseDto.getId();

        if (id == null) {
            throw new BusinessException(ErrorCode.COURSE_EXIT, "id值为空");
        }

        CourseDto courseInfo = courseService.getCourse(id);

        if (courseInfo == null) {
            throw new BusinessException(ErrorCode.COURSE_NO_EXIT, "该课程不存在");
        }

        log.info("获取教师信息成功");
        return ResultUtils.success(courseInfo);
    }

    /**
     * Excel文件批量上传教师信息
     * @param file excel
     * @return 保存成功
     */
    @PostMapping("/excel/import")
    public BaseResponse<Boolean> saveExcel(@RequestParam("file") MultipartFile file) {

        Boolean isImport = courseService.excelImport(file);

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
        XSSFSheet sheet = wb.createSheet("课程信息表");
        //2.3创建表头
        XSSFRow xssfRow = sheet.createRow(0);
        xssfRow.createCell(0).setCellValue("课程名称");
        xssfRow.createCell(1).setCellValue("课程英文名");
        xssfRow.createCell(2).setCellValue("授课专业");
        xssfRow.createCell(3).setCellValue("授课老师");

        return ResultUtils.success(true);
    }

}

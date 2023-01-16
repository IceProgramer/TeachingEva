package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.Course;
import com.itmo.teachingeva.domain.Teacher;
import com.itmo.teachingeva.dto.CourseDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.mapper.TeacherMapper;
import com.itmo.teachingeva.service.CourseService;
import com.itmo.teachingeva.mapper.CourseMapper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author chenjiahan
* @description 针对表【e_course】的数据库操作Service实现
* @createDate 2023-01-16 17:41:54
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private TeacherMapper teacherMapper;


    /**
     * 展示所有课程信息
     * @return 所有课程信息
     */
    @Override
    public List<CourseDto> listAllCourses() {
        // 查询所有课程信息
        List<Course> courseList = this.list();
        if (courseList == null) {
            throw new BusinessException(ErrorCode.COURSE_EMPTY, "请联系开发人员");
        }

        // 将do转为dto
        // 取出所有老师信息
        List<Teacher> teacherList = teacherMapper.queryTeacherName();

        // key为主键 value为教师名称的map
        Map<Integer, String> teacherMap = teacherList.stream().collect(Collectors.toMap(Teacher::getId, Teacher::getName));

        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course course : courseList) {
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course, courseDto, "tid");
            courseDto.setTeacher(teacherMap.get(course.getTid()));

            courseDtoList.add(courseDto);
        }

        return courseDtoList;
    }

    /**
     * 添加课程信息 【单个】
     * @param courseDto 课程添加信息
     * @return 添加成功
     */
    @Override
    public Boolean addCourse(CourseDto courseDto) {
        // 对课程信息进行校验
        // a.课程信息不为空
        if (courseDto == null) {
            throw new BusinessException(ErrorCode.COURSE_EMPTY, "请重新添加");
        }
        // b.课程信息是否已经存在【课程名称和教师id两个字段进行判断】
        String name = courseDto.getCName();
        Integer tid = teacherMapper.queryTeacherId(courseDto.getTeacher());
        Course courseExit = courseMapper.queryCourseByNameAndTid(name, tid);

        if (courseExit != null) {
            throw new BusinessException(ErrorCode.COURSE_EXIT, "课程已存在");
        }

        // 将dto转成do插入数据表中
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course, "teacher");
        course.setTid(tid);

        boolean save = this.save(course);

        return save;
    }

    /**
     * 删除课程信息【单个】
     * @param courseDto 课程的id
     * @return 删除成功
     */
    @Override
    public Boolean deleteCourse(CourseDto courseDto) {
        Integer id = courseDto.getId();

        boolean isDelete = this.removeById(id);

        return isDelete;
    }

    /**
     * 更新课程信息
     * @param courseDto 课程信息
     * @return 更新成功
     */
    @Override
    public Boolean updateCourse(CourseDto courseDto) {
        Course course = new Course();

        BeanUtils.copyProperties(courseDto, course, "teacher");
        course.setTid(teacherMapper.queryTeacherId(courseDto.getTeacher()));

        boolean update = this.updateById(course);

        return update;
    }

    /**
     * 获取单个课程信息
     * @param id 课程的id
     * @return
     */
    @Override
    public CourseDto getCourse(Integer id) {
        Course course = this.getById(id);

        // 校验课程是否为空
        if (course == null) {
            throw new BusinessException(ErrorCode.COURSE_NO_EXIT);
        }

        CourseDto courseInfo = new CourseDto();
        BeanUtils.copyProperties(course, courseInfo, "tid");
        courseInfo.setTeacher(teacherMapper.queryTeacherNameById(course.getTid()));

        return courseInfo;
    }

    /**
     * Excel表格批量上传
     * @param file excel文件
     * @return 上传成功
     */
    @Override
    @Transactional
    public Boolean excelImport(MultipartFile file) {

        // 1.判断文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException(ErrorCode.FILE_EMPTY, "请重新上传文件");
        }
        XSSFWorkbook wb = null;

        try {
            // 2.POI获取Excel文件信息
            wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);

            List<Course> courseList = new ArrayList<>();

            // 取出所有老师信息
            List<Teacher> teacherList = teacherMapper.queryTeacherName();
            XSSFRow row = null;

            // key为主键 value为教师名称的map
            Map<String, Integer> teacherMap = teacherList.stream().collect(Collectors.toMap(Teacher::getName, Teacher::getId));

            //4.接收数据 装入集合中
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                Course course = new Course();
                course.setCName(row.getCell(0).getStringCellValue());
                course.setEName(row.getCell(1).getStringCellValue());
                course.setMajor(Integer.valueOf(new DataFormatter().formatCellValue(row.getCell(2))));
                course.setTid(teacherMap.get(row.getCell(3).getStringCellValue()));


                courseList.add(course);
            }

            // 将列表保存到数据库中
            boolean save = this.saveBatch(courseList);

            return save;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}





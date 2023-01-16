package com.itmo.teachingeva;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.itmo.teachingeva.domain.Course;
import com.itmo.teachingeva.domain.Position;
import com.itmo.teachingeva.domain.StudentClass;
import com.itmo.teachingeva.domain.Teacher;
import com.itmo.teachingeva.dto.CourseDto;
import com.itmo.teachingeva.mapper.StudentClassMapper;
import com.itmo.teachingeva.mapper.TeacherMapper;
import com.itmo.teachingeva.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.util.Lists.list;

@SpringBootTest
class TeachingEvaApplicationTests {

    @Resource
    private StudentClassMapper studentClassMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private CourseService courseService;


    @Test
    void contextLoads() {
        List<Course> courseList = courseService.list();

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

        System.out.println(courseDtoList);
    }

}

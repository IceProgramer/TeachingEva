package com.itmo.teachingeva;

import com.itmo.teachingeva.domain.Course;
import com.itmo.teachingeva.domain.Teacher;
import com.itmo.teachingeva.dto.CourseDto;
import com.itmo.teachingeva.mapper.StudentClassMapper;
import com.itmo.teachingeva.mapper.TeacherMapper;
import com.itmo.teachingeva.service.CourseService;
import com.itmo.teachingeva.service.EvaluateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class TeachingEvaApplicationTests {

    @Resource
    private StudentClassMapper studentClassMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    private CourseService courseService;

    @Resource
    private EvaluateService evaluateService;


    @Test
    void contextLoads() {
        evaluateService.handOutEvaluations(1);
    }

}

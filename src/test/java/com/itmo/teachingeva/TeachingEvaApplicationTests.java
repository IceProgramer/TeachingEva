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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class TeachingEvaApplicationTests {



    @Test
    void contextLoads() {
        List<Integer> list = Arrays.asList(1, 2, 4, 5, 0);
        Integer integer = list.stream().reduce(Integer::sum).orElse(0);
        System.out.println(list.size());
        System.out.println(integer);
    }

}

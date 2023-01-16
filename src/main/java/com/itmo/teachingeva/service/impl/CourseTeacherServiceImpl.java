package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.domain.CourseTeacher;
import com.itmo.teachingeva.intercepts.mapper.CourseTeacherMapper;
import com.itmo.teachingeva.service.CourseTeacherService;
import org.springframework.stereotype.Service;

@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher> implements CourseTeacherService {
}

package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.domain.Course;
import com.itmo.teachingeva.service.CourseService;
import com.itmo.teachingeva.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【e_course】的数据库操作Service实现
* @createDate 2023-01-16 17:41:54
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}





package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.domain.System;
import com.itmo.teachingeva.service.SystemService;
import com.itmo.teachingeva.mapper.SystemMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【e_system】的数据库操作Service实现
* @createDate 2023-01-16 22:23:05
*/
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System>
    implements SystemService{

}





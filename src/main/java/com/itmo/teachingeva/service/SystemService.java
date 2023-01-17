package com.itmo.teachingeva.service;

import com.itmo.teachingeva.domain.System;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.teachingeva.dto.SystemDto;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_system】的数据库操作Service
* @createDate 2023-01-16 22:23:05
*/
public interface SystemService extends IService<System> {

    /**
     * 展示所有的俄方评价体系
     */
    List<SystemDto> listAllFirstSystemOfRussian();

    /**
     * 展示所有的中方评价体系
     */
    List<SystemDto> listAllFirstSystemOfChina();

    /**
     * 更改俄方系统
     */
    Boolean updateRussianSystem(List<SystemDto> systemDtoList);

    /**
     * 更改中方系统
     */
    Boolean updateChinaSystem(List<SystemDto> systemDtoList);
}

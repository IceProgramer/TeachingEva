package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.System;
import com.itmo.teachingeva.dto.SystemDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.SystemService;
import com.itmo.teachingeva.mapper.SystemMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_system】的数据库操作Service实现
* @createDate 2023-01-16 22:23:05
*/
@Service
public class SystemServiceImpl extends ServiceImpl<SystemMapper, System>
    implements SystemService{

    @Resource
    private SystemMapper systemMapper;

    /**
     * 展示所有的俄方评价体系
     */
    @Override
    public List<SystemDto> listAllFirstSystemOfRussian() {
        List<System> allRussianSystem = systemMapper.queryAllRussianSystem();

        if (allRussianSystem == null) {
            throw new BusinessException(ErrorCode.EVALUATION_SYSTEM_EMPTY, "没有数据");
        }

        List<SystemDto> systemDtoList = new ArrayList<>();
        BeanUtils.copyProperties(allRussianSystem, systemDtoList);

        return systemDtoList;
    }

    /**
     * 展示所有的中方评价体系
     */
    public List<SystemDto> listAllFirstSystemOfChina() {
        List<System> allChinaSystem = systemMapper.queryAllChinaSystem();

        if (allChinaSystem == null) {
            throw new BusinessException(ErrorCode.EVALUATION_SYSTEM_EMPTY, "没有数据");
        }

        List<SystemDto> systemDtoList = new ArrayList<>();
        BeanUtils.copyProperties(allChinaSystem, systemDtoList);

        return systemDtoList;
    }
}





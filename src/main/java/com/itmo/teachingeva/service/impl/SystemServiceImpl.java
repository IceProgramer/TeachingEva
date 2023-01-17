package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.System;
import com.itmo.teachingeva.dto.SystemDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.SystemService;
import com.itmo.teachingeva.mapper.SystemMapper;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    /**
     * 更新新的俄方数据
     * @param systemDtoList
     * @return
     */
    @Override
    public Boolean updateRussianSystem(List<SystemDto> systemDtoList) {
        for (SystemDto systemDto : systemDtoList) {
            Integer id = systemDto.getId();     // 获取id
            System system = this.getById(id);
            // 对信息进行校验
            // a. 判断更新的内容是否为俄方数据
            if (system.getKind() != 1) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该信息为中方信息， 请重新检查更改对象");
            }
            // b. 判断更新数据是否与原来数据相同
            String name = system.getName();
            if (!name.equals(systemDto.getName())) {
                // 更新数据
                Boolean update = systemMapper.updateRussianName(name, id);
                if (!update) {
                    log.info("system库中第{}条数据更新失败", id);
                }
            }
        }
        return true;
    }

    @Override
    public Boolean updateChinaSystem(List<SystemDto> systemDtoList) {
        for (SystemDto systemDto : systemDtoList) {
            Integer id = systemDto.getId();     // 获取id
            System system = this.getById(id);
            // 对信息进行校验
            // a. 判断更新的内容是否为俄方数据
            if (system.getKind() != 0) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该信息为俄方信息， 请重新检查更改对象");
            }
            // b. 判断更新数据是否与原来数据相同
            String name = system.getName();
            if (!name.equals(systemDto.getName())) {
                // 更新数据
                Boolean update = systemMapper.updateRussianName(name, id);
                if (!update) {
                    log.info("system库中第{}条数据更新失败", id);
                }
            }
        }
        return true;
    }
}





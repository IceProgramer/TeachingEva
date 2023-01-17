package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.dto.SystemDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评测系统管理
 *
 * @author chenjiahan
 */
@RestController
@Slf4j
@RequestMapping("/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    /**
     * 查询所有俄方评测系统
     * @return 俄方评测系统
     */
    @GetMapping("/page/russian")
    public BaseResponse<List<SystemDto>> listRussianSystem() {
        log.info("查询所有俄方评测系统...");

        List<SystemDto> allSystemInfo = systemService.listAllFirstSystemOfRussian();

        if (allSystemInfo == null) {
            throw new BusinessException(ErrorCode.EVALUATION_SYSTEM_EMPTY, "没有相关信息");
        }

        return ResultUtils.success(allSystemInfo);
    }

    /**
     * 查询所有中方评测系统
     * @return 中方评测系统
     */
    @GetMapping("/page/china")
    public BaseResponse<List<SystemDto>> listChinaSystem() {
        log.info("查询所有俄方评测系统...");

        List<SystemDto> allSystemInfo = systemService.listAllFirstSystemOfChina();

        if (allSystemInfo == null) {
            throw new BusinessException(ErrorCode.EVALUATION_SYSTEM_EMPTY, "没有相关信息");
        }

        return ResultUtils.success(allSystemInfo);
    }

    /**
     * 更改俄方系统
     */
    @PostMapping("/edit/russian")
    public BaseResponse<Boolean> updateRussianSystem(@RequestBody SystemDto systemDto) {
        return null;
    }

    /**
     * 更改中方系统
     */
    @PostMapping("/edit/china")
    public BaseResponse<Boolean> updateChinaSystem(@RequestBody SystemDto systemDto) {
        return null;
    }
}

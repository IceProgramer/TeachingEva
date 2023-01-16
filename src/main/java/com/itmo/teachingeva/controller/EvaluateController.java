package com.itmo.teachingeva.controller;

import com.itmo.teachingeva.common.BaseResponse;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.common.ResultUtils;
import com.itmo.teachingeva.domain.Evaluate;
import com.itmo.teachingeva.dto.CourseDto;
import com.itmo.teachingeva.dto.EvaluateDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.EvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评测管理
 *
 * @author chenjiahan
 */
@RestController
@Slf4j
@RequestMapping("/evaluate")
public class EvaluateController {

    @Resource
    private EvaluateService evaluateService;

    /**
     * 展示所有评测信息
     * @return 所有评测
     */
    @GetMapping("/page")
    public BaseResponse<List<EvaluateDto>> getAllEvaluates() {
        log.info("查询所有课程信息中...");

        List<EvaluateDto> allEvaluatesInfo = evaluateService.listAllEvaluations();

        if (allEvaluatesInfo == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "请联系开发人员");
        }

        return ResultUtils.success(allEvaluatesInfo);
    }

    /**
     * 新增评测信息
     * @param evaluateDto 评测信息
     * @return 新增成功
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addEvaluate(@RequestBody EvaluateDto evaluateDto) {
        boolean isSave = evaluateService.addEvaluation(evaluateDto);

        if (!isSave) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入信息失败");
        }

        log.info("添加信息成功！");
        return ResultUtils.success(true);
    }

    /**
     * 删除单条课程信息
     * @param evaluateDto 课程id
     * @return 删除成功
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteEvaluate(@RequestBody EvaluateDto evaluateDto) {

        if (evaluateDto.getId() == null) {
            throw new BusinessException(ErrorCode.EVALUATION_NO_EXIT, "没有评测信息");
        }

        boolean isDelete = evaluateService.deleteEvaluation(evaluateDto);

        if (!isDelete) {
            throw new BusinessException(ErrorCode.EVALUATION_NO_EXIT, "没有该评测信息");
        }

        log.info("删除数据成功");
        return ResultUtils.success(true);
    }

    /**
     * 更新评测信息
     * @param evaluateDto 评测信息
     * @return 更新成功
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> updateEvaluate(@RequestBody EvaluateDto evaluateDto) {
        boolean isUpdate = evaluateService.updateEvaluation(evaluateDto);

        if (!isUpdate) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }

        log.info("更新数据成功！");
        return ResultUtils.success(true);
    }

    /**
     * 获取单条评测信息
     * @param evaluateDto 评测id
     * @return 评测信息
     */
    @GetMapping("/info")
    public BaseResponse<EvaluateDto> getEvaluate(@RequestBody EvaluateDto evaluateDto) {
        Integer id = evaluateDto.getId();

        if (id == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "id值为空");
        }

        EvaluateDto evaluateInfo = evaluateService.getEvaluation(id);

        if (evaluateInfo == null) {
            throw new BusinessException(ErrorCode.EVALUATION_NO_EXIT, "该评测不存在");
        }

        log.info("获取评测信息成功");
        return ResultUtils.success(evaluateInfo);
    }

}

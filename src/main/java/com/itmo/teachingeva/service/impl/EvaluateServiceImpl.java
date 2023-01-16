package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.common.ErrorCode;
import com.itmo.teachingeva.domain.Evaluate;
import com.itmo.teachingeva.dto.EvaluateDto;
import com.itmo.teachingeva.dto.TeacherDto;
import com.itmo.teachingeva.exceptions.BusinessException;
import com.itmo.teachingeva.service.EvaluateService;
import com.itmo.teachingeva.intercepts.mapper.EvaluateMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_evaluate】的数据库操作Service实现
* @createDate 2023-01-16 20:44:13
*/
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate>
    implements EvaluateService{

    @Resource
    private EvaluateMapper evaluateMapper;

    /**
     * 展示所有评测信息
     * @return 所有评测信息
     */
    @Override
    public List<EvaluateDto> listAllEvaluations() {
        // 查询所有评测信息
        List<Evaluate> evaluateList = this.list();
        if (evaluateList == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "暂无评测结果");
        }
        // 将do转成dto
        List<EvaluateDto> evaluateDtoList = new ArrayList<>();
        for (Evaluate evaluate : evaluateList) {
            EvaluateDto evaluateDto = new EvaluateDto();
            BeanUtils.copyProperties(evaluate, evaluateDto);
            evaluateDtoList.add(evaluateDto);
        }

        return evaluateDtoList;
    }

    /**
     * 发布新的评测
     * @param evaluateDto 评测信息
     * @return 添加成功
     *///todo 发布评测后向各位学生发布相应的评测
    @Override
    public Boolean addEvaluation(EvaluateDto evaluateDto) {
        // 对评测信息进行校验
        // a.课程信息不为空
        if (evaluateDto == null) {
            throw new BusinessException(ErrorCode.EVALUATION_EMPTY, "请重新添加");
        }
        // b.判断评测信息是否已存在【根据名称查询】
        String name = evaluateDto.getName();
        Evaluate evaluateExit = evaluateMapper.queryEvaluationByName(name);
        if (evaluateExit != null) {
            throw new BusinessException(ErrorCode.EVALUATION_EXIT, "评测已存在");
        }




        return null;
    }

    @Override
    public Boolean deleteEvaluation(EvaluateDto evaluateDto) {
        return null;
    }

    @Override
    public Boolean updateEvaluation(EvaluateDto evaluateDto) {
        return null;
    }

    @Override
    public TeacherDto getEvaluation(Integer id) {
        return null;
    }
}





package com.itmo.teachingeva.service;

import com.itmo.teachingeva.domain.Evaluate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.teachingeva.dto.EvaluateDto;
import com.itmo.teachingeva.dto.StudentDto;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_evaluate】的数据库操作Service
* @createDate 2023-01-16 20:44:13
*/
public interface EvaluateService extends IService<Evaluate> {

    /**
     * 查询所有评测信息
     *
     * @return 评测信息
     */
    List<EvaluateDto> listAllEvaluations();

    /**
     * 添加评测
     *
     * @param evaluateDto 评测添加信息
     * @return 是否添加成功
     */
    Boolean addEvaluation(EvaluateDto evaluateDto);

    /**
     * 删除评测数据
     * @param evaluateDto 评测的id
     * @return 删除成功
     */
    Boolean deleteEvaluation(EvaluateDto evaluateDto);

    /**
     * 更新评测数据
     * @param evaluateDto 评测信息
     * @return 更新成功
     */
    Boolean updateEvaluation(EvaluateDto evaluateDto);

    /**
     * 获取单个评测信息
     *
     * @param id 评测的id
     * @return 评测信息
     */
    EvaluateDto getEvaluation(Integer id);

    /**
     * 分发各个测评
     * @param eid
     * @return
     */
    Boolean handOutEvaluations(Integer eid);

    /**
     * 统计已完成问卷人数
     */
    List<StudentDto> studentsDoneEvaluation(Integer eid);

    /**
     * 统计未完成问卷人数
     */
    List<StudentDto> studentsUndoneEvaluation(Integer eid);

}

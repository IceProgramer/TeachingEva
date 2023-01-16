package com.itmo.teachingeva.intercepts.mapper;

import com.itmo.teachingeva.domain.Evaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author chenjiahan
* @description 针对表【e_evaluate】的数据库操作Mapper
* @createDate 2023-01-16 20:44:13
* @Entity Evaluate
*/
public interface EvaluateMapper extends BaseMapper<Evaluate> {

    @Select("select * from e_evaluate where name = #{name}")
    Evaluate queryEvaluationByName(String name);

}





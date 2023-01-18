package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.MarkHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_mark_history】的数据库操作Mapper
* @createDate 2023-01-16 22:24:22
* @Entity com.itmo.teachingeva.domain.MarkHistory
*/
public interface MarkHistoryMapper extends BaseMapper<MarkHistory> {

    /**
     * 根据总的一次评价来删除所有该评价下的记录
     * @param eid 评价id
     */
   @Delete("delete * from e_mark_history where eid = #{eid}")
    void deleteByEid(Integer eid);

    /**
     * 获取全部信息
     */
    @Select("select * from e_mark_history")
    List<MarkHistory> getAllEvaluation();

    /**
     * 根据学生id来查找该学生的所有信息
     */
    @Select("select state from e_mark_history where aid = #{aid} and eid = #{eid}")
    List<Integer> getStateByAid(Integer aid, Integer eid);
}





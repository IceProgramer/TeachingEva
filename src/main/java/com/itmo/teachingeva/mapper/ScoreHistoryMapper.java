package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.ScoreHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
* @author chenjiahan
* @description 针对表【e_score_history】的数据库操作Mapper
* @createDate 2023-01-17 01:54:58
* @Entity ScoreHistory
*/
public interface ScoreHistoryMapper extends BaseMapper<ScoreHistory> {

    /**
     * 删除评测相关结果
     * @param eid 评测id
     */
    @Delete("delete * from e_score_history where eid = #{eid}")
    void deleteByEid(Integer eid);
}





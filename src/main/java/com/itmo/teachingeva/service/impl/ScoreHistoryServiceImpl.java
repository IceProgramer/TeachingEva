package com.itmo.teachingeva.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.teachingeva.domain.ScoreHistory;
import com.itmo.teachingeva.service.ScoreHistoryService;
import com.itmo.teachingeva.mapper.ScoreHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【e_score_history】的数据库操作Service实现
* @createDate 2023-01-17 01:54:58
*/
@Service
public class ScoreHistoryServiceImpl extends ServiceImpl<ScoreHistoryMapper, ScoreHistory>
    implements ScoreHistoryService{

}





package com.itmo.teachingeva.timer;

import com.itmo.teachingeva.mapper.StudentMapper;
import com.itmo.teachingeva.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
@Slf4j
public class ScheduledTask {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 自动增加学期
     */
    @Scheduled(cron = "0 0 0 31 7/1 0 *")  // 每年的7月31日和1月31日自动执行
    private void configureTasks() {
        // 学期自动加1
        studentMapper.addGradeByAuto();
        log.info("学期数自动+1");

        // 删除学期数大于8的学生
        studentMapper.deleteStudentGradeThan8();
        log.info("自动删除已不再学校学生");
    }

}

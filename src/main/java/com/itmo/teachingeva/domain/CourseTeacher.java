package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_course_to_teacher
 */
@TableName(value ="e_course_to_teacher")
@Data
public class CourseTeacher implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程主键
     */
    private Integer cid;

    /**
     * 教师主键
     */
    private Integer tid;

}
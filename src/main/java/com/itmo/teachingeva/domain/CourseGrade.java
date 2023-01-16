package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_course_to_grade
 */
@TableName(value ="e_course_to_grade")
@Data
public class CourseGrade implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 课程主键
     */
    private Integer cid;

    /**
     * 年级
     */
    private Integer gid;

    /**
     * 专业
     */
    private Integer major;

}
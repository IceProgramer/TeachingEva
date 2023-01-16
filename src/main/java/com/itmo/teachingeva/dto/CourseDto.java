package com.itmo.teachingeva.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程管理
 */
@Data
public class CourseDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 中文名称
     */
    private String cName;

    /**
     * 英文名称
     */
    private String eName;

    /**
     * 专业
     */
    private Integer major;

    /**
     * 授课老师
     */
    private String teacher;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}

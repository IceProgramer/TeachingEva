package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_course
 */
@TableName(value ="e_course")
@Data
public class Course implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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
     * 专业，0为计算机，1为自动化
     */
    private Integer major;

    /**
     * 教师id
     */
    private Integer tid;

}
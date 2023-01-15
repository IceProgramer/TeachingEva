package com.itmo.teachingeva.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_class
 */
@TableName(value ="e_class")
@Data
public class StudentClass implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 班级号
     */
    private String cid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
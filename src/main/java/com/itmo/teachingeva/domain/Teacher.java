package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_teacher
 */
@TableName(value ="e_teacher")
@Data
public class Teacher implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别，0为男，1为女
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private Integer position;

    /**
     * 职称
     */
    private Integer pTitle;

    /**
     * 专业，0为计算机，1为自动化
     */
    private Integer major;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0为俄方，1为中方
     */
    private Integer identity;


}
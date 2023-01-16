package com.itmo.teachingeva.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private String position;

    /**
     * 职称
     */
    private String pTitle;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}

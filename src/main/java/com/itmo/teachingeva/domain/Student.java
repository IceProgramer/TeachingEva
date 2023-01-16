package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 学生表
 *
 * @TableName e_student
 */
@TableName(value ="e_student")
@Data
public class Student implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    private String sid;

    /**
     * 密码
     */
    private String password;

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
     * 专业，0为计算机，1为自动化
     */
    private Integer major;

    /**
     * 班级主键
     */
    private Integer cid;

    /**
     * 年级，1-8代表八个学期
     */
    private Integer grade;



}
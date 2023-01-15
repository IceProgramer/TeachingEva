package com.itmo.teachingeva.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Student的Dto类
 *
 * @author chenjiahan
 */
@Data
public class StudentDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 学号
     */
    private String sid;

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
     * 专业
     */
    private Integer major;

    /**
     * 班级编号
     */
    private String cid;

    /**
     * 年级
     */
    private Integer grade;

}

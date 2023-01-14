package com.itmo.teachingeva.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * Admin的Dto类
 *
 * @author chenjiahan
 */
@Data
public class AdminDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 姓名
     */
    private String name;


}

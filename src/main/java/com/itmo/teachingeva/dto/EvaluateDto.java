package com.itmo.teachingeva.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvaluateDto implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 评测名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String eTime;

    /**
     * 发布状态
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

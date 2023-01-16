package com.itmo.teachingeva.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class SystemDto implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * sid
     */
    private Integer sid;

    /**
     * 评测内容
     */
    private String name;

    /**
     * 评测等级
     */
    private Integer level;

    /**
     * 国籍 0中方，1俄方
     */
    private Integer kind;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

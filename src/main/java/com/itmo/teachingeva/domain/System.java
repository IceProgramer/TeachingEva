package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_system
 */
@TableName(value ="e_system")
@Data
public class System implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 指标名称
     */
    private String name;

    /**
     * sid
     */
    private Integer sid;

    /**
     * 评价级别，1为一级指标，2为二级指标
     */
    private Integer level;

    /**
     * 0为中方，1为俄方
     */
    private Integer kind;

}
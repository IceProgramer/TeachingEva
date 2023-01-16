package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 
 * @TableName e_score_history
 */
@TableName(value ="e_score_history")
@Data
public class ScoreHistory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 教师主键
     */
    private Integer tid;

    /**
     * 课程主键
     */
    private Integer cid;

    /**
     * 总分
     */
    private BigDecimal score;

    /**
     * 评价主键
     */
    private Integer eid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
package com.itmo.teachingeva.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName e_mark_history
 */
@TableName(value ="e_mark_history")
@Data
public class MarkHistory implements Serializable {
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
     * 评价主键
     */
    private Integer eid;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评价体系主键
     */
    private Integer sid;

    /**
     * 学生主键
     */
    private Integer aid;

}
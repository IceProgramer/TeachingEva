package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.PTitle;
import com.itmo.teachingeva.domain.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
* @author chenjiahan
* @description 针对表【e_position】的数据库操作Mapper
* @createDate 2023-01-16 11:38:48
* @Entity com.itmo.teachingeva.domain.Position
*/
public interface PositionMapper extends BaseMapper<Position> {

    /**
     * 查找表中全部数据转为List
     * @return 职位
     */
    @Select("select * from e_position")
    List<Position> queryPositionToList();

    /**
     * 根据职位来获取职称对应的id
     * @param position 职位名称
     * @return
     */
    @Select("select id from e_position where name = #{position}")
    Integer queryPositionId(String position);

    @Select("select name from e_position where id = #{id}")
    String queryPositionName(Integer id);

}





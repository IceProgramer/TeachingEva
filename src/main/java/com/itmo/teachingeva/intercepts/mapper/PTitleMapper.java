package com.itmo.teachingeva.intercepts.mapper;

import com.itmo.teachingeva.domain.PTitle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmo.teachingeva.domain.Position;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_p_title】的数据库操作Mapper
* @createDate 2023-01-16 11:36:32
* @Entity com.itmo.teachingeva.domain.PTitle
*/
public interface PTitleMapper extends BaseMapper<PTitle> {

    /**
     * 查找表中全部数据转为List
     * @return 职称
     */
    @Select("select * from e_p_title")
    List<PTitle> queryPTitleToList();

    /**
     * 根据职称名称来查找对应的主键id
     * @param pTile 职称名称
     * @return id
     */
    @Select("select id from e_p_title where name = #{pTile};")
    Integer queryPTileId(String pTile);

    /**
     * 根据职称主键来查找对应的职称名称
     * @param id 职称主键
     * @return 职称名称
     */
    @Select("select name from e_p_title where id = #{id}")
    String queryPTileName(Integer id);

}





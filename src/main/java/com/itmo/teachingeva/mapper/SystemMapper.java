package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.System;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_system】的数据库操作Mapper
* @createDate 2023-01-16 22:23:05
* @Entity com.itmo.teachingeva.domain.System
*/
public interface SystemMapper extends BaseMapper<System> {

    /**
     * 获取所有的一级评价体系 【俄方】
     * @return 俄方的一级指标
     */
    @Select("select sid from e_system where kind = 1 and level = 1")
    List<Integer> queryFirstSystemOfRussian();

    /**
     * 获取所有的一级指标 【中方】
     * @return 中方的一级指标
     */
    @Select("select sid from e_system where kind = 0 and level = 1")
    List<Integer> queryFirstSystemOfChina();

    /**
     * 取出所有一级指标信息
     */
    @Select("select * from e_system where level = 1")
    List<System> queryAllFirstSystem();

    /**
     * 取出所有的中方指标
     */
    @Select("select * from e_system where kind = 0")
    List<System> queryAllChinaSystem();

    /**
     * 取出所有的俄方指标
     */
    @Select("select * from e_system where kind = 1")
    List<System> queryAllRussianSystem();

    /**
     * 更新俄方的数据
     */
    @Update("update e_system set name = #{name} where id = #{id}")
    Boolean updateRussianName(String name, Integer id);

}





package com.itmo.teachingeva.mapper;

import com.itmo.teachingeva.domain.StudentClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author chenjiahan
* @description 针对表【e_class】的数据库操作Mapper
* @createDate 2023-01-15 16:16:52
* @Entity com.itmo.teachingeva.domain.StudentClass
*/
public interface StudentClassMapper extends BaseMapper<StudentClass> {

    /**
     * 根据id来查找班级号
     * @param id id
     * @return 班级号
     */
    @Select("select cid from e_class where id = #{id}")
    String queryClass(Integer id);

    /**
     * 根据班级号来查找id
     * @param cid 班级号
     * @return id
     */
    @Select("select id from e_class where cid = #{cid}")
    Integer queryClassId(String cid);

    /**
     * 根据id来查找班级号
     * @param
     * @return 班级号
     */
    @Select("select * from e_class")
    List<StudentClass> queryClassToList();
}





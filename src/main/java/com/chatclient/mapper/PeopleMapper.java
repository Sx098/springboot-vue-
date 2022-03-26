package com.chatclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatclient.entity.People;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleMapper extends BaseMapper<People> {
    //模糊查询like使用 ${} 其他变量使用#{}
    @Select("SELECT * FROM people WHERE `name` LIKE '%${ming}%' AND `name` NOT IN (#{mingzi})")
    List<People> huoqu(String ming,String mingzi);
}

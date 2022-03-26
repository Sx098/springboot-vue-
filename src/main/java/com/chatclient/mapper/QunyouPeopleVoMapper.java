package com.chatclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatclient.vo.QunyouPeopleVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface QunyouPeopleVoMapper extends BaseMapper<QunyouPeopleVoMapper> {
    //通过qunyou表获取群信息
    @Select("with A as (SELECT `name`,tou FROM qun WHERE id = ANY(SELECT `group` FROM qunyou WHERE member=#{bianhao} AND tongyi=#{agree})) SELECT * ,'${agree}' as 'tongyi' FROM A")
    List<QunyouPeopleVo> huoqu(Integer bianhao,Integer agree);
    @Select("INSERT INTO qunyou(member,`group`,tongyi) VALUES ('${a}','${b}','${c}')")
    List<QunyouPeopleVo> tianjia(Integer a,Integer b,Integer c);
    @Select("UPDATE qunyou SET tongyi=1 WHERE member=#{a} AND group=#{b}")
    List<QunyouPeopleVo> tongguo(Integer a,Integer b);
    //获取添加此群的群的用户信息
    @Select("SELECT * FROM people WHERE id=ANY(SELECT member FROM qunyou WHERE tongyi=1 AND `group` =#{a})")
    List<QunyouPeopleVo> sousuo(Integer a);
}

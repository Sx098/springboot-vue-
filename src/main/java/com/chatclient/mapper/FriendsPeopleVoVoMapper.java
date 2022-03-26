package com.chatclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chatclient.vo.FriendsPeopleVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FriendsPeopleVoVoMapper extends BaseMapper<FriendsPeopleVo> {

    //查询当前people.id==friends.frienda，获取friends.friendb，如果tongyi==1，就去people获取friendb的信息
    @Select("with A as (SELECT name,tou,id FROM people WHERE id=ANY(SELECT friendb FROM friends WHERE frienda=#{bianhao} AND tongyi=#{agree}))\n" +
            "select * ,'${agree}' as 'tongyi' from A")
    List<FriendsPeopleVo> huoqu(Integer bianhao,Integer agree);

    //查询当前id已添加好友的name、id、tou、tongyi
    @Select("with A as (SELECT name,tou,id FROM people WHERE id=ANY(SELECT frienda FROM friends WHERE friendb=#{bianhao} AND tongyi=#{agree}))\n" +
            "select * ,'${agree}' as 'tongyi' from A")
    List<FriendsPeopleVo> huoqu2(Integer bianhao,Integer agree);

    //向friends表添加frienda与friendb，以及tongyi为0
    @Select("INSERT INTO friends(frienda,friendb,tongyi) VALUES ('${a}','${b}','${c}')")
    List<FriendsPeopleVo> tianjia(Integer a,Integer b,Integer c);

    //将friends表中frienda与friendb的tongyi改为1，即同意好友
    @Update("UPDATE friends SET tongyi=1 WHERE frienda=#{a} AND friendb=#{b}")
    int tongguo(Integer a,Integer b);

    //查询当前用户收到的好友请求
    @Select("SELECT friendb FROM friends WHERE frienda=#{a} AND tongyi=0 UNION SELECT frienda FROM friends WHERE friendb=#{a} AND tongyi=0")
    List<FriendsPeopleVo> shenqing(Integer a);

    //删除friends关系
    @Delete("DELETE FROM friends WHERE frienda=#{a} AND friendb=#{b}")
    int shan(Integer a,Integer b);

/*    @Select("SELECT name,tou,id FROM people WHERE id=ANY(\n" +
            "SELECT friendb FROM friends WHERE frienda=#{bianhao} AND tongyi=1)")
    List<FriendsPeopleVo> huoqu(Integer bianhao);
    @Select("SELECT name,tou,id FROM people WHERE id=ANY(\n" +
            "SELECT frienda FROM friends WHERE friendb=#{bianhao} AND tongyi=1)")
    List<FriendsPeopleVo> huoqu2(Integer bianhao);*/
}
